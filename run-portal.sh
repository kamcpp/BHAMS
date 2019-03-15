#!/bin/bash -e -x

docker run -v $PWD/assignment:/bh --entrypoint=mvn  \
  --net blueharvest --ip 172.18.0.13 -p 8080:8080 io.bh.ams -f /bh spring-boot:run -pl clients.portal
