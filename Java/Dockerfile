# Stage 1
# Build project
FROM openjdk:21-jdk-slim AS build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY application application
COPY domain domain
COPY boot boot
COPY infrastructure-api-rest infrastructure-api-rest
COPY infrastructure-authorize-server infrastructure-authorize-server
COPY infrastructure-database-mongodb infrastructure-database-mongodb

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /workspace/app/boot/target/*.jar)


# Stage 2
# Create volume
FROM openjdk:21-jdk-slim
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8180

ENTRYPOINT ["java","-cp","app:app/lib/*","io.github.javialc.java.labs.IntegrationTestApplication"]