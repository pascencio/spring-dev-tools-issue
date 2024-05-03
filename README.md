# Spring Dev Tools Issue

## Requirements

Java 17
Docker 25.0.3

## Start database

In a terminal

```shell
docker-compose up
```

## Start application

In a new terminal

```shell
./mvnw spring-boot:run
```

## Rest API

In a new terminal

```shell
curl http://localhost:8080/demo/greetings
```

## Trigger reload

Make a change in the code and then in a new terminal execute

```shell
./mvnw compile
```

