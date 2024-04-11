FROM amazoncorretto:21-alpine
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]