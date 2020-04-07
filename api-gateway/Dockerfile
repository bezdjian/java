FROM adoptopenjdk/openjdk11

ARG JAR_FILE=target/api-gateway.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]