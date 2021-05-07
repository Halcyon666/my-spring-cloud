# .bash_profile

# Get the aliases and functions
if [ -f ~/.bashrc ]; then
	. ~/.bashrc
fi

# User specific environment and startup programs

PATH=$PATH:$HOME/bin

export PATH
unset USERNAME

export ORACLE_SID=jc
export NLS_LANG=AMERICAN_AMERICA.ZHS16GBK
umask 022
export ORACLE_BASE=/usr/oracle/app
export ORACLE_HOME=$ORACLE_BASE/product/11.2.0/dbhome_1
export PATH=$PATH:$ORACLE_HOME/bin
