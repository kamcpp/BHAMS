#!/bin/bash -e -x
NETWORK_NAME=blueharvest

if [ "`docker network ls | grep $NETWORK_NAME`" != "" ]; then
  docker network rm $NETWORK_NAME
fi

docker network create --subnet=172.18.0.0/16 $NETWORK_NAME
docker build . -t io.bh.ams
