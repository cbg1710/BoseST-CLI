FROM bellsoft/liberica-openjdk-debian:11

ARG BOSE
COPY ${BOSE} bose.properties 

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

EXPOSE 5432

ENTRYPOINT ["java","-jar","/app.jar"]
