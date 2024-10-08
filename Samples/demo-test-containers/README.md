# Demo Test Containers

This is a demo project to show how to use [TestContainers](https://www.testcontainers.org/) in a Node.js project.

## Description

This project is a simple example of how to use TestContainers to run a PostgreSQL container in a Node.js project. The
project has a simple function that connects to the database and returns the version of the PostgreSQL server.

## Dependencies

- [NPM](https://www.npmjs.com/)
- [Node.js](https://nodejs.org/en/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [TestContainers](https://www.testcontainers.org/)
- [Jest](https://jestjs.io/)
- [PG](https://node-postgres.com/)

## Setup

### Installed dependencies on package.json

```bash
  npm install --save-dev jest pg testcontainers @testcontainers/postgresql
```

### Install dependencies

```bash
  npm install
```

### Run tests

```bash
  npm test
```


