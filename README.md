## RAG system demo in progress
This is a Spring AI-powered Retrieval-Augmented Generation (RAG) application that allows users to upload documents and query information using vector search. It supports PostgreSQL with pgvector for vector storage and can use **Ollama** for embeddings and model queries.

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
- Postgres pgvector latest
- Ollama latest

### Start the Application
simply running spring-boot:run command.

```bash
docker compose up -d
docker exec -it ollama ollama run mistral &
mvn spring-boot:run
```
##### Verify Service

Ensure the Ollama service is running:
  ```bash
  curl http://localhost:11434/models
  ```

##### Usage

1. **Upload a Document**:
   Use the `/upload` endpoint to upload a PDF document. This will tokenize the content and store embeddings in the `documents` table.

2. **Query Information**:
   Use the `/query` endpoint to ask questions based on the uploaded document.




