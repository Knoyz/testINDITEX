FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/price-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9081

ENTRYPOINT ["java", "-jar", "app.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
