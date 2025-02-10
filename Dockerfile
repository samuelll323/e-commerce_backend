FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/BackEnd.jar BackEnd.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/BackEnd.jar"]
