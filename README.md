# Receipt Processor

Receipt Processor is a Spring Boot application that processes receipts by applying business rules to calculate points. It provides a RESTful API for submitting receipts, processing them, and retrieving the awarded points.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Using Docker](#using-docker)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [License](#license)

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
  Easily deploy the application using Docker for consistent testing across environments.

## Prerequisites

- Docker (for containerized deployment)

*Note: All dependencies are packaged within the Docker container. You do not need to install Java or Maven locally to run the application using Docker.*

## Using Docker

You can pull and run the Docker container directly from Docker Hub. This allows you to test the application without having to run it locally.

## Pull the Docker Image

Pull the latest image from Docker Hub using the following command:

```bash
docker pull csalazar24/receipt-processor:latest
```

## Run the Docker Container.

```bash
docker run -p 8080:8080 csalazar24/receipt-processor:latest
```

Once running, your application will be accessible at http://localhost:8080.

## Verifying the Application

### API Endpoints

- **Submit a Receipt:**  
  Send a POST request to `http://localhost:8080/receipts/process` with a valid receipt JSON.

- **Retrieve Points:**  
  Send a GET request to `http://localhost:8080/receipts/{id}/points` to get the points for the processed receipt.

### API Documentation

Open your browser and navigate to `http://localhost:8080/swagger-ui/index.html` to view the interactive API documentation.

### Testing

While the Docker container is the primary way to run and test the application, the project also includes unit and integration tests. To run tests locally (if needed), you can execute:

```bash
mvn test
```

This will run all tests and report any failures.


