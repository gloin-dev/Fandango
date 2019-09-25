#!/bin/bash
cd "$(dirname "$0")"
./gradlew clean shadowJar
docker build -t vettonum/fandango:latest .
echo
echo "To run the docker container execute:"
echo "$ docker run --net host vettonum/fandango:latest"
echo
echo "To push the generated image to the docker image repository:"
echo "$ docker push vettonum/fandango:latest"
