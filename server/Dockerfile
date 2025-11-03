FROM eclipse-temurin:21-jre-jammy
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Europe/Belgrade
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]