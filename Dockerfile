FROM openjdk:21 AS builder
WORKDIR /build
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src ./src
RUN ./mvnw clean install -DskipTests

FROM openjdk:21 as runner
WORKDIR /app
COPY --from=builder /build/target/gok-api-runner.jar /app/gok-api-runner.jar
CMD ["java", "-Duser.timezone=UTC", "--enable-preview", "-jar", "gok-api-runner.jar"]
