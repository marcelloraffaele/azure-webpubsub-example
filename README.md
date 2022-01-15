# azure-webpubsub-example
A Java (Quarkus) application that shows example of how to use the Azure Web PubSub API

The example uses an instance of Azure Web PubSub service that you can create from Azure portal.

# Quarkus
To work locally, you can execute this:
```
./mvnw compile quarkus:dev
```

# How to use
/webpubsub
- GET /webpubsub/send/{text}
- GET /webpubsub/sendtoall
- GET /webpubsub/token

Or visit: http://localhost:8080 and choose an option from the menu.

# Build docker image
Using the following command you can rebuild the image:
```
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t rmarcello/azure-webpubsub-example .
```

# Test using Docker image
I pushed the image on my [docker hub](https://hub.docker.com/repository/docker/rmarcello/azure-webpubsub-example) and you can use it to play with it.
```
docker run -i --rm -p 8080:8080 -e webpubsub.accesskey=changeme -e webpubsub.endpoint=https://<RESOURCE_NAME>.webpubsub.azure.com rmarcello/azure-webpubsub-example
```