FROM amazoncorretto:11-alpine
ARG BOSE
COPY ${BOSE} bose.properties 
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 5432
ENTRYPOINT ["java","-jar","/app.jar"]
