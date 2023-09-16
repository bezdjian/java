# Build stage
#FROM maven:3.8.3-openjdk-17-slim AS build
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package -DskipTests

# To make it faster docker build, comment out the build stage and run mvn install locally first.
# Package stage
FROM openjdk:17.0.1-jdk-slim
#COPY --from=build /home/app/target/testcontainers2-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
COPY target/projects-0.0.1.jar /home/app/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/demo.jar"]