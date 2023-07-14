FROM maven:3.8.3-openjdk-17-slim
COPY src /home/app/src
COPY pom.xml /home/app

WORKDIR /home/app
# Run mvn package without running tests, maybe separate unit test from test containers and run only unit test here.
# docker-compose will run the actual tests with testcontainers.
RUN mvn clean package -DskipTests
