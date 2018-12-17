FROM ustrd/oracle-jdk-phusion:8
RUN mkdir -p /usr/src/app
COPY . /usr/src/app
WORKDIR /usr/src/app
ADD target/tutorial_ranking-0.0.1-SNAPSHOT.jar //
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/tutorial_ranking-0.0.1-SNAPSHOT.jar"]

