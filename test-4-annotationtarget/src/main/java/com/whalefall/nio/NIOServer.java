package com.whalefall.nio;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

@Component("NIOServer")
public class NIOServer {

    private final static Logger log = LoggerFactory.getLogger(NIOServer.class);
    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private int port = 8000;
    private Charset charset = Charset.forName("GBK");

    public NIOServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
    }

    public void service() throws IOException {
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("服务器启动");
        while (selector.select()>0) {
            Set readyKeys = selector.selectedKeys();
            Iterator it = readyKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = null;
                try {
                    key = (SelectionKey) it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = ssc.accept();
                        log.info("接收到客户端连接，来自：" +
                                socketChannel.socket().getInetAddress()+
                                " : "+socketChannel.socket().getPort());
                        socketChannel.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.register(selector,SelectionKey.OP_READ |
                                SelectionKey.OP_WRITE,buffer);
                    }

                    if (key.isReadable()){
                        log.info("开始业务逻辑处理");
                        receive(key);
                    }

                    if( key.isWritable()){
                        log.info("开始返回相应报文");
                        send(key);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    try {
                        if (key != null) {
                            key.cancel();
                            key.channel().close();
                        }
                    } catch (Exception ex) { e.printStackTrace(); }
                }

            }// while
        }// while
    }

    // 给客户端相应报文
    public void send(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        buffer.flip(); // 把极限设为位置 把位置设为0
        String data = decode(buffer);

        log.info(data);

        ByteBuffer outputBuffer = encode("响应报文为: "+data);
        socketChannel.write(outputBuffer);

        buffer.position();
        buffer.compact(); //删除已经处理的字符串
    }

    // 处理请求
    public void receive(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(32);
        socketChannel.read(readBuffer);
        readBuffer.flip();

        buffer.limit(buffer.capacity());
        buffer.put(readBuffer); // 将读取到的数据放到buffer中

        log.info("服务端获取到报文"+decode(readBuffer));
    }

    public String decode(ByteBuffer buffer) {// 解码
        CharBuffer charBuffer = charset.decode(buffer);
        return charBuffer.toString();
    }

    public ByteBuffer encode(String str) {// 编码
        return charset.encode(str);
    }

}
