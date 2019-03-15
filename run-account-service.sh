#!/bin/bash -e -x

docker run -v $PWD/assignment:/bh --entrypoint=mvn  \
  --net blueharvest --ip 172.18.0.11 -p 9001:8080 io.bh.ams -f /bh spring-boot:run -pl services.account
