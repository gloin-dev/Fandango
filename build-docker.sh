#!/bin/bash
cd "$(dirname "$0")"
./gradlew clean shadowJar
docker build -t vettonum/fandango:latest .
echo
echo
echo "To run the docker container execute:"
echo "$ docker run --net host vettonum/fandango:latest"