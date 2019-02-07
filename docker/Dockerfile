FROM openjdk:8-jre-alpine
# apk alpine package manager
RUN apk update && apk upgrade \
                &&  add bash

LABEL maintainer="newsecond@outlook.com"
ENV ADMIN="darek"

ARG app_dir="/usr/src/app"
RUN mkdir -p ${app_dir}
WORKDIR ${app_dir}

ARG log_config="./logback.xml"
ENV container_log_config  "/logback.xml"
COPY  ${log_config} ${container_log_config}

ARG jar_name
#ARG wont be visible at ENTRYPOINT/CMD
ENV APP_JAR  ${jar_name}
# ADD, unlike COPY, can be used to use a URL (eg git repo)
ADD docker/*.jar   ${APP_JAR}

ARG SPRING_PROFILE
ENV PROFILE  ${SPRING_PROFILE}

EXPOSE 8080
VOLUME /tmp

#when splitting "java -jar ${JAR}" it stops working

ENTRYPOINT ["/bin/bash", "-c", "java -jar -Dspring.profiles.active=${PROFILE} -Dlogging.config=${container_log_config} ${APP_JAR}"]

ENTRYPOINT ["/bin/bash", "-c", "java -jar ${APP_JAR}"]
CMD  ["-Dspring.profiles.active=mysql", "-Dlogging.config=${container_log_config}"]

