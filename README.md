# DataExtractionService

DataExtractionService is a Spring Boot-based microservice responsible for extracting transaction details from messages. It consumes message notifications from Kafka, processes them using a Mistral API-based LLM (Large Language Model) to extract merchant, amount, and currency details, and then publishes the extracted data to ExpenseService via Kafka.

## Features

- Consumes message notifications from Kafka.
- Sends message details to Mistral API for extraction.
- Extracts merchant, amount, and currency information.
- Publishes extracted data to ExpenseService via Kafka.

## Tech Stack

- **Spring Boot**
- **Kafka**
- **Mistral API (LLM-based processing)**
- **Spring Data JPA**
