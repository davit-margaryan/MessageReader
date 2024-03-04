FROM openjdk:17

WORKDIR /app

COPY target/message_reader.jar /app

ENTRYPOINT ["java","-jar","/app/message_reader.jar"]