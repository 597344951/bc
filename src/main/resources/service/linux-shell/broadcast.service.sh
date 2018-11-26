#!/bin/sh
###########################
#    服务控制脚本
###########################
broadcast_home="/home/zltel/broadcast/"
app_name="broadcast"
log_file=${broadcast_home}logs/call_log.log
app_log_file=${broadcast_home}logs/logs_`date +%Y-%m-%d`.log

pid_file=${broadcast_home}broadcast.pid

#GC日志文件存储位置
GC_LOG_FILE=${broadcast_home}logs/gc-log_`date +%Y-%m-%d`.log
#JAVA运行参数
JAVA_OPTS=""
#设定堆内存配置
JAVA_OPTS="$JAVA_OPTS -Xmx2g -Xms1g -Xmn1g"
#设定打印GC详情日志
JAVA_OPTS="$JAVA_OPTS -XX:+UseParallelOldGC -Xloggc:${GC_LOG_FILE} -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution"

#配置远程jconsole连接
#在jconsole中输入 ${hostname}:${port}即可连接
JAVA_OPTS="$JAVA_OPTS -Djava.rmi.server.hostname=192.168.1.8 -Dcom.sun.management.jmxremote"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.port=8888"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.rmi.port=8889"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.authenticate=false"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.ssl=false"



RUN_USER=zltel
#当前用户
user=$(env | grep USER | cut -d "=" -f 2)


RETVAL=0
# Source function library.
. /etc/init.d/functions

logout(){
  msg="`date +%Y-%m-%d_%H:%M:%S`:$1"
    echo $msg
    echo $msg >> $log_file
}

case "$1" in
    start)
        echo -n "Starting broadcast "
		if [ "$user" == "root"  ]
		  then
			#root
			echo "当前用户是root,切换用户运行"
			sudo -u $RUN_USER java -jar ${JAVA_OPTS} ${broadcast_home}broadcast*.war >> ${app_log_file} &
		  else 
			#其他用户
			java -jar ${JAVA_OPTS} ${broadcast_home}broadcast*.war >> ${app_log_file} &
		fi
        
		echo $! > ${pid_file}
		logout "启动应用,pid: ${pid_file}"
        echo
        ;;
    stop)
        echo -n "Shutting down broadcast "
        
		runpids=$(cat ${pid_file})
		logout "停止: ${runpids}"
		kill ${runpids}

        RETVAL=$?
		
		rm -rf ${pid_file}
        echo
        ;;
    try-restart|condrestart)
        if test "$1" = "condrestart"; then
                echo "${attn} Use try-restart ${done}(LSB)${attn} rather than condrestart ${warn}(RH)${norm}"
        fi
        $0 status
        if test $? = 0; then
                $0 restart
        else
                : # Not running is not a failure.
        fi
        ;;
    restart)
        $0 stop
        $0 start
        ;;
    force-reload)
        echo -n "Reload service Jenkins "
        $0 try-restart
        ;;
    reload)
        $0 restart
        ;;
    status)
		status -p ${pid_file}
        RETVAL=$?
        ;;
    probe)
        ## Optional: Probe for the necessity of a reload, print out the
        ## argument to this init script which is required for a reload.
        ## Note: probe is not (yet) part of LSB (as of 1.9)

        test "$JENKINS_CONFIG" -nt "$JENKINS_PID_FILE" && echo reload
        ;;
    *)
        echo "Usage: $0 {start|stop|status|try-restart|restart|force-reload|reload|probe}"
        exit 1
        ;;
esac
exit $RETVAL