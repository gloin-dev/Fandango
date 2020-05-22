#!/bin/bash
docker build -f Dockerfile.graalvm -t vettonum/fandango:graalvm
echo
echo "To run the docker container execute:"
echo "$ docker run --net host vettonum/fandango:graalvm"
echo
echo "To push the generated image to the docker image repository:"
echo "$ docker push vettonum/fandango:graalvm"
