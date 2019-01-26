#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/ubuntu-ts.pem \
    target/wireframe-0.0.1-SNAPSHOT.jar \
    ubuntu@ec2-18-185-61-126.eu-central-1.compute.amazonaws.com:/home/ubuntu/

echo 'Restart server...'

ssh -i ~/.ssh/ubuntu-ts.pem ubuntu@ec2-18-185-61-126.eu-central-1.compute.amazonaws.com << EOF
pgrep java | xargs kill -9
nohup java -jar wireframe-0.0.1-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'