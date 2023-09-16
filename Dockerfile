FROM maven:3.8.3-openjdk-17-slim
COPY src /home/app/src
COPY pom.xml /home/app

WORKDIR /home/app

RUN mvn clean package -Punit-tests
