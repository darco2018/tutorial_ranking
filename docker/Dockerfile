FROM openjdk:8-jre-alpine
RUN apk add bash

ARG app_dir="/usr/src/app"
RUN mkdir -p ${app_dir}
WORKDIR ${app_dir}

ARG log_config="./logback.xml"
ENV container_log_confir  "/logback.xml"
COPY  ${log_config} ${container_log_confir}

ARG jar_name
#ARG wont be visible at ENTRYPOINT/CMD
ENV APP_JAR  ${jar_name}
ADD docker/*.jar   ${APP_JAR}

EXPOSE 8080
VOLUME /tmp

#when splitting "java -jar ${JAR}" it stops working
ENTRYPOINT ["/bin/bash", "-c", "java -jar ${APP_JAR}"]
CMD  ["-Dspring.profiles.active=mysql", "-Dlogging.config=${container_log_confir}" ]
