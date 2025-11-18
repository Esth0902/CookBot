### Launch docker

docker run --name postgres-cookbot -e POSTGRES_DB=<NOM_DB> -e POSTGRES_USER=<NOM_USER> -e POSTGRES_PASSWORD=<PASSWORD_BD> -p 5432:5432 -d postgres

### Setup environment variables

ENV=
DB_USERNAME=
DB_NAME=
DB_PASSWORD=
JWT_TOKEN=
JWT_EXPIRATION=

#### MONITORING ###

Prometheus => http://localhost:8080/actuator/prometheus
Grafana => http://localhost:3000


### LOGS ###

Logs => curl -s 'localhost:8080/actuator/metrics/http.client.requests'
Logs => curl -s 'http://localhost:8080/actuator/metrics/http.client.requests?tag=error%3Anone' (success)
Logs => curl -s 'http://localhost:8080/actuator/metrics/http.client.requests?tag=error%3ANonTransientAiException (error)
Logs => curl -s 'localhost:8080/actuator/metrics/spring.ai.chat.client' (ai metrics)

Get input tokens used:
curl -s 'localhost:8080/actuator/metrics/gen_ai.client.token.usage?tag=gen_ai.token.type%3Ainput'

Get output tokens used:
curl -s 'localhost:8080/actuator/metrics/gen_ai.client.token.usage?tag=gen_ai.token.type%3Aoutput'

Get total tokens used:
curl -s 'localhost:8080/actuator/metrics/gen_ai.client.token.usage?tag=gen_ai.token.type%3Atotal'