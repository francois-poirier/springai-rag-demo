## RAG system demo
This is a demo RAG system, which is basically a QA bot which uses solid data to answer questions, rather than relying solely on it's own LLM knowledge.

#### Architecture
The System is based on the following components:
1. Code - spring boot framework with spring ai.
2. Local models deployment, including chat and embeddings generation: ollama.
3. Vector Database, for storing embeddings and querying them: postgres pgvector.
   All components used are free and open source.

#### Running the system
##### Prerequisites
- Docker
- Docker Compose
- Maven
- Java 17
- Spring Boot 3.3.5
- Spring AI 1.0.0-M3
- Postgres pgvector 0.7.0-pg16
- Ollama

##### Running
simply running spring-boot:run command.

```bash
docker compose up -d
docker exec -it ollama ollama run mistral &
mvn spring-boot:run
```

##### Executing int other terminal

```bash
curl --location 'http://localhost:8082/rag?query=%22Can%20you%20give%20me%20a%20short%20summary%20of%20the%20thoughtworks%20technology%20radar%3F%22'
```