# Fandango

## Summary 
  
Fandango is a [Micronaut](http://micronaut.io/)-based microservice providing a REST API that allows to store and recover images (jpeg, jpg, png) or documents like PDF, and also allowing to generate thumbnails, modify and retrieve images, using a NoSQL database storage via HTTP-based API.  
 
## Benefits 
  
1. Uncouple the image or documents storage management from the rest of any application.  
2. Improve efficiency and simplicity when dealing with images or documents.  
3. Micro Service Architecture oriented using Micronaut framework

## Api & Doc

You can found the swagger ui on project itself in the path :

http://localhost:8585/swagger-ui/

Also you can find a basic dashboard to test the API functions via browser on :

http://localhost:8585/

## Build & Deploy 

Fandango require a MongoDB installed instance in the host machine to store the image and files.

You can clone the project and generate your own jar :

```console
./gradlew shadowJar
```

Or you can use the provided docker image :

```console
$ docker pull vettonum/fandango
$ docker run --net host vettonum/fandango
```
