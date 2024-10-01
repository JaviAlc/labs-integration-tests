# labs-integration-tests

## Docker compose

We use Docker Compose to run the environment required by the application. The environment consists of the following
containers:

* MongoDB
* MongoDB Express

The Docker Compose configuration is in the `docker-compose.yml` file.

```bash
docker-compose up -d
```

## Database

The application use a MongoDB database. The database is running in a Docker container.
The database is created automatically when the application starts.

It is possible to access the database using a MongoDB client. The connection information is as follows:

* Host: localhost
* Port: 27117
* Database: integrationtest-db
* Username: admin
* Password: password

### MongoDB Express

MongoDB Express is enabled by default. You can access MongoDB Express at the following URL:

* http://127.0.0.1:8181

## Running the application (Java)

The application is a Spring Boot application. You can run the application using Maven or your favorite IDE.
The application is in the `Java` directory.

### Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Integration tests

We use JUnit 5 with TestContainers to run the integration tests.
The integration tests are in the `src/test/java` directory at module `boot`.

```bash
mvn clean verify
```

### Swagger

Swagger UI is enabled by default. You can access the Swagger UI at the following URL:

* http://127.0.0.1:8180/swagger-ui/index.html#

## Newman

We use Newman to run the Postman collection. The Postman collection is in the `postman` directory.

Newman Url:

* https://learning.postman.com/docs/collections/using-newman-cli/installing-running-newman/

### Install Newman

```bash
npm install -g newman
```

### Run Postman collection

```bash
newman run Postman/smoke-test.postman_collection.json -e Postman/local-java.postman_environment.json
```


