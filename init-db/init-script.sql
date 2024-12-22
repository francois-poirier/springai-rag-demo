CREATE DATABASE documentgpt;

CREATE USER documentgpt WITH PASSWORD 'documentgptpassword';

GRANT ALL PRIVILEGES ON DATABASE documentgpt TO documentgpt;

\c documentgpt
CREATE EXTENSION IF NOT EXISTS vector;