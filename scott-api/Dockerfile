FROM ubuntu:16.04

ENV LANG=C.UTF-8

RUN apt-get -y update
RUN apt-get install -y software-properties-common vim unzip curl

# Java 설치
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
ENV CLASSPATH .:${CLASSPATH}
ENV PATH ${JAVA_HOME}/bin:${PATH}

RUN mkdir app logs

ADD build/libs/scott-api-1.0.war /app/scott-api.war

#ENV SPRING_PROFILES_ACTIVE=real
#ENV LOG_DIR=/logs
#ENV DATABASE_SERVER=mysql
#ENV DATABASE_NAME=scott
#ENV DATABASE_PORT=3306
#ENV DATABASE_USER=
#ENV DATABASE_PASSWD=

# visualvm
EXPOSE 9010

CMD ["java", \
"-Dcom.sun.management.jmxremote", \
"-Dcom.sun.management.jmxremote.rmi.port=9010", \
"-Dcom.sun.management.jmxremote.port=9010", \
"-Dcom.sun.management.jmxremote.local.only=false", \
"-Dcom.sun.management.jmxremote.authenticate=false", \
"-Dcom.sun.management.jmxremote.ssl=false", \
"-Djava.rmi.server.hostname=0.0.0.0", \
"-jar",  "/app/scott-api.war"]
