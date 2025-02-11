# Receipt Processor

Receipt Processor is a Spring Boot application that processes receipts by applying business rules to calculate points. It provides a RESTful API for submitting receipts, processing them, and retrieving the awarded points.

## Table of Contents

- [Accessing the code](#Accessing-the-code)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Using Docker](#using-docker)
- [API Documentation](#api-documentation)
- [Testing](#testing)

## Accessing the code
The source code of this application can be found in this repository within the following path: src/main/java/com/example/ReceiptProcessor
Within this directory, you'll find several key packages:
- **Controller:**
  This package contains the REST controllers that handle HTTP requests and responses. It defines the API endpoints for submitting receipts for processing and retrieving the awarded points.

- **Service:** 
  This package contains the business logic of the application. It includes service interfaces and their implementations that process receipts by applying the defined rules to calculate points.

- **Model:**  
This package contains the domain models (POJOs or Java records) that represent the structure of receipts and items. These classes define the schema used for JSON serialization and deserialization.

- **Exceptions:**  
This package contains custom exception classes and global exception handlers that manage error conditions. These classes ensure that meaningful error responses are returned when issues occur during receipt processing.

## Features

- **Receipt Processing:**  
  The application calculates points based on the following rules:
  - **Retailer Name:** One point for every alphanumeric character in the retailer name.
  - **Round Dollar Total:** 50 points if the total is a round dollar amount with no cents.
  - **Multiple of 0.25:** 25 points if the total is a multiple of 0.25.
  - **Item Pairing:** 5 points for every two items on the receipt.
  - **Item Bonus:** If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
  - **Odd Day:** 6 points if the day in the purchase date is odd.
  - **Time Bonus:** 10 points if the time of purchase is after 2:00pm and before 4:00pm.

- **RESTful API Endpoints:**
  - **POST** `/receipts/process` – Submits a receipt for processing and returns the assigned receipt ID.
  - **GET** `/receipts/{id}/points` – Retrieves the points awarded for a given receipt ID.

- **Swagger UI Integration:**  
  This project uses Swagger/OpenAPI to interact with the endpoints.

- **Dockerized Application:**  
  This application has been dockerized and can be pulled directly from Docker Hub.

## Prerequisites

- Docker (for containerized deployment)

*Note: All dependencies are packaged within the Docker container. You do not need to install Java or Maven locally to run the application using Docker.*

## Using Docker

You can pull and run the Docker container directly from Docker Hub. This allows you to test the application without having to run it locally.

### Pull the Docker Image

Pull the latest image from Docker Hub using the following command:

```bash
docker pull csalazar24/receipt-processor:latest
```

### Run the Docker Container.

```bash
docker run -p 8080:8080 csalazar24/receipt-processor:latest
```

Once running, your application will be accessible at http://localhost:8080.

## API Documentation

Open your browser and navigate to `http://localhost:8080/swagger-ui/index.html` to view the interactive API documentation.

## Testing

While the Docker container is the primary way to run and test the application, the project also includes unit and integration tests. To run tests locally (if needed), you can execute:

```bash
mvn test
```

This will run all tests and report any failures.


