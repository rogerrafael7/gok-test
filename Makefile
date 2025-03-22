install:
	./mvnw clean install -DskipTests

tests:
	./mvnw test -Dquarkus.args="--enable-preview"

dev:
	./mvnw quarkus:dev -DskipTests -Dquarkus.args="--enable-preview"

prod:
	docker-compose up -d
