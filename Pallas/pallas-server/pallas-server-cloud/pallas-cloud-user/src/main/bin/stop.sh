#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
SERVER_NAME='pallas-cloud-user'
if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi

CONF_DIR=$SERVER_NAME

PIDS=`ps -ef | grep java | grep "$CONF_DIR" |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME does not started!"
    exit 1
fi

echo -e "Stopping the $SERVER_NAME ...\c"
for PID in $PIDS ; do
    kill -9 $PID > /dev/null 2>&1
done

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=1
    for PID in $PIDS ; do
        PID_EXIST=`ps -f -p $PID | grep java`
        if [ -n "$PID_EXIST" ]; then
            COUNT=0
            break
        fi
    done
done

echo "OK!"
echo "PID: $PIDS"