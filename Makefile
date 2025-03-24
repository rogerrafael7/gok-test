install:
	./mvnw clean install -DskipTests

database:
	docker-compose up -d postgres

fix-migrations:
	flyway repair -url=jdbc:postgresql://localhost:5436/gok -user=gok_user -password=gok_pass

test:
	./mvnw test -Dquarkus.args="--enable-preview"

build:
	./mvnw clean package -DskipTests

dev:
	./mvnw quarkus:dev -DskipTests -Dquarkus.args="--enable-preview"

prod:
	docker-compose up -d
