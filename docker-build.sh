# Compile project
mvn clean verify -DskipTests

# Build Docker image
docker build -t fpo/springai-rag-demo:latest .

# Stop all containers and start them up
docker-compose down
docker-compose up -d --force-recreate