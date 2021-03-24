FROM bellsoft/liberica-openjdk-debian:11

EXPOSE 5432

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","de.chris.apps.bose.bin.BoseControllerCli"]
