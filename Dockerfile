FROM ustrd/oracle-jdk-phusion:8
WORKDIR /
ADD target/tutorial_ranking-0.0.1-SNAPSHOT.jar //
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/tutorial_ranking-0.0.1-SNAPSHOT.jar"]
