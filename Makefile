install:
	./mvnw clean install -DskipTests

tests:
	./mvnw test -Dquarkus.args="--enable-preview"

dev:
	./mvnw quarkus:dev -Dquarkus.args="--enable-preview"