CREATE DATABASE document_poc;

CREATE USER document_poc WITH PASSWORD 'document_password';

GRANT ALL PRIVILEGES ON DATABASE document_poc TO document_poc;

\c document_poc
CREATE EXTENSION IF NOT EXISTS vector;