#!/bin/sh
cd `dirname $0`

PATH=/usr/local/jdk1.6.0/bin:$PATH

java -Xmx512m -classpath `perl -e "print join(':',glob('lib/*'))"` jmirror.JMirror

