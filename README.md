### Launch docker

docker run --name postgres-cookbot -e POSTGRES_DB=<NOM_DB> -e POSTGRES_USER=<NOM_USER> -e POSTGRES_PASSWORD=<PASSWORD_BD> -p 5432:5432 -d postgres

### Setup environment variables

ENV=
DB_USERNAME=
DB_NAME=
DB_PASSWORD=
JWT_TOKEN=
JWT_EXPIRATION=

#### GRAFANA ###

Prometheus => http://localhost:8080/actuator/prometheus

Grafana => http://localhost:3000