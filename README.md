### Correr con docker-compose

./mvnw clean package -DskipTests
docker image rm backend
docker build -t backend .
docker-compose up
