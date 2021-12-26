.PHONY: down up build restart
down:
	docker-compose down

up:
	docker-compose up -d

build:
	./mvnw compile jib:dockerBuild

restart: down up
