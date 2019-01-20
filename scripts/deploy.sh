#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    target/wireframe-0.0.1-SNAPSHOT.jar \
    konstantin@192.168.0.110:/home/konstantin/

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa konstantin@192.168.0.110 << EOF
pgrep java | xargs kill -9
nohup java -jar wireframe-0.0.1-SNAPSHOT.war > log.txt &
EOF

echo 'Bye'