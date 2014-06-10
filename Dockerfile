FROM debian:wheezy
MAINTAINER Sergey Sudakovich <sergey@sudakovich.com>

RUN apt-get update && apt-get install -y curl net-tools
RUN curl http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | apt-key add -
RUN echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list
RUN apt-get update && apt-get install -y jenkins && service jenkins start
EXPOSE 8080
