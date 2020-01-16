# Gradle with JDK 8 to compile JAR
FROM gradle:6.0.1-jdk8 as TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --chown=gradle:gradle . $APP_HOME
RUN gradle build -Denv=prod -x test --no-daemon

# x86_64 Alpine Linux With OpenJDK 8 to run JAR
FROM adoptopenjdk/openjdk8:alpine-slim
ARG ARG_ARTIFACT_NAME=fandango.jar
ENV ARTIFACT_NAME=${ARG_ARTIFACT_NAME} APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8585
CMD ["sh", "-c", "java -jar ${ARTIFACT_NAME}"]