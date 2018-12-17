FROM openjdk:8-jre-alpine
VOLUME /tmp
ENV JAR_NAME  tutorial_ranking-0.0.1-SNAPSHOT.jar
ARG APP_DIR=/usr/src/tutorial-app
RUN mkdir -p ${APP_DIR}
#COPY . ${APP_DIR}
WORKDIR ${APP_DIR}
ADD docker/${JAR_NAME}   ${JAR_NAME}
EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=mysql", "/tutorial_ranking-0.0.1-SNAPSHOT.jar"]
CMD  java  -jar  -Dspring.profiles.active=mysql  ${JAR_NAME}
