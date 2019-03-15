FROM ubuntu:18.04
RUN apt update
RUN apt -y install default-jdk
RUN apt -y install maven
RUN mkdir /bh
COPY assignment/ /bh
RUN mvn -f /bh install
RUN rm -rf /bh/*
