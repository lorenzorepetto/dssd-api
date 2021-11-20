### Correr con docker-compose

./mvnw clean package -DskipTests
docker image rm backend
docker build -t backend .
docker-compose up

El archivo resources/data-postgres.sql es un script que se puede ejecutar desde la QueryTool de PgAdmin, son algunos datos de prueba para agilizar. 
