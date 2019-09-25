# x86_64
FROM adoptopenjdk/openjdk8:alpine-slim

# Copy the emobion app
COPY build/libs/fandango-*-all.jar fandango.jar

# Command to start the client
CMD java -jar fandango.jar