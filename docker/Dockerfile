FROM openjdk:8-jre-alpine
RUN apk add bash

ARG APP_DIR=/usr/src/tutorial-app
RUN mkdir -p ${APP_DIR}
#COPY . ${APP_DIR}
WORKDIR ${APP_DIR}

VOLUME /tmp
COPY ./src/main/resources/logback.xml /logback.xml

ENV JAR_NAME  tutorial_ranking-0.0.1-SNAPSHOT.jar
ADD docker/${JAR_NAME}   ${JAR_NAME}
EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=mysql", "/tutorial_ranking-0.0.1-SNAPSHOT.jar"]
CMD  java  -jar  -Dspring.profiles.active=mysql  "-Dlogging.config=/logback.xml"  ${JAR_NAME}
