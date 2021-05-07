package com.whalefall.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

// 非阻塞式EchoClient 代码源自《Java网络编程核心技术详解 4.4客户端编程示范》
public class EchoClient {
    private final static Logger log = LoggerFactory.getLogger(EchoClient.class);

    private SocketChannel socketChannel = null;
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
    private Charset charset = Charset.forName("GBK");
    private Selector selector;

    public EchoClient() throws IOException {
        socketChannel = SocketChannel.open();
        InetAddress ia = InetAddress.getLocalHost();
        InetSocketAddress isa = new InetSocketAddress(ia, 8000);
        socketChannel.connect(isa); //采用阻塞模式连接
        socketChannel.configureBlocking(false);//设置为非阻塞模式

        log.info("与服务器的连接创建成功");
        selector = Selector.open();
    }

    public static void main(String[] args) throws IOException {
        final EchoClient client = new EchoClient();
        Thread receiver = new Thread() {
            @Override
            public void run() {
                client.receiveFromUser(); // 接收用户向控制台输入的数据
            }
        };

        receiver.start();
        client.talk();
    }

    public void receiveFromUser() {
        try {
            BufferedReader localReader = new BufferedReader(
                    new InputStreamReader(System.in));
            String msg = null;
            while ((msg = localReader.readLine()) != null) {
                synchronized (sendBuffer) {
                    sendBuffer.put(encode(msg + "\r\n"));
                }

                if (msg.equals("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void talk() throws IOException {
        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        while (selector.select() > 0) {
            Set readyKeys = selector.selectedKeys();
            Iterator it = readyKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = null;
                try {
                    key = (SelectionKey) it.next();
                    it.remove();

                    if (key.isReadable()) {
                        receive(key);
                    }

                    if (key.isWritable()) {
                        send(key);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    try {
                        if (key != null) {
                            key.channel();
                            key.channel().close();
                        }
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }// while
        }// while
    }

    public void send(SelectionKey key) throws IOException {
        // 发送 sendBuffer中的数据
        SocketChannel socketChannel = (SocketChannel) key.channel();
        synchronized (sendBuffer){
            sendBuffer.flip(); // 把极限设为位置，把位置设为0
            socketChannel.write(sendBuffer); //发送数据
            sendBuffer.compact(); //删除已经发送的数据
        }
    }
    public void receive(SelectionKey key) throws IOException {
        // 接收EchoServer发送的数据，把它放到receiveBuffer中
        // 如果receiveBuffer中有一行数据，就打印这行数据
        // 然后把它从receiveBuffer中删除
        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.read(receiveBuffer);
        receiveBuffer.flip();
        String receiveData = decode(receiveBuffer);

        if (receiveData.indexOf("\n")==-1) return;

        String outputData = receiveData.substring(0,receiveData.indexOf("\n")+1);

        log.info(outputData);

        if (outputData.equals("cho:bye\r\n")) {
            key.cancel();
            socketChannel.close();
            log.info("关闭与服务器的连接");
            System.exit(0);
        }

        ByteBuffer tmp = encode(outputData);
        receiveBuffer.position(tmp.limit());
        receiveBuffer.compact();// 删除已打印的程序
    }

    public String decode(ByteBuffer buffer) {// 解码
        CharBuffer charBuffer = charset.decode(buffer);
        return charBuffer.toString();
    }
    public ByteBuffer encode(String str) {// 编码
        return charset.encode(str);
    }

}
