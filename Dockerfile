# x86_64 Alpine Linux With OpenJDK 8
FROM adoptopenjdk/openjdk8:alpine-slim

# Copy the emobion app
COPY build/libs/fandango-*-all.jar fandango.jar

# Expose port
EXPOSE 8585/tcp

# Command to start the client
CMD java -jar fandango.jar