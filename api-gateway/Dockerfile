FROM adoptopenjdk/openjdk11

ARG JAR_FILE=target/api-gateway.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Denv.eureka.url=192.168.99.100", "-jar","app.jar"]