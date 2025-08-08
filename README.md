# Spring Boot - Virtual Threads

## Overview
This project is a Java-based web application built using the Spring Boot framework. It leverages virtual threads to handle HTTP requests efficiently. The primary focus is on demonstrating thread management and performance optimization using virtual threads.

## Key Components

### ThreadsController.java
A REST controller that provides endpoints for thread operations.

- **Endpoints**:
  - `GET /thread/name`: Returns the current thread's name.
  - `POST /thread/name/{name}`: Sets the current thread's name to the provided value.

- **Example usage**:
  - `GET /thread/name`: "Thread[main,5,main]"
  - `POST /thread/name/myThread`: "New name is: myThread"

### ThreadsConfiguration.java
Configures the application to use virtual threads for task execution.

- Uses `Executors.newVirtualThreadPerTaskExecutor()` to create a virtual thread executor.
- Customizes the Tomcat protocol handler to use virtual threads.

### LoadTestController.java
A REST controller for simulating load and testing the performance of virtual threads.

- **Endpoint**:
  - `GET /load`: Logs a message and simulates a delay to test thread handling.

### ThreadsControllerTest.java
Unit tests for `ThreadsController` using JUnit 5 and Spring Boot's testing support.

- Tests the `GET /thread/name` and `POST /thread/name/{name}` endpoints to ensure they function correctly.

### ThreadsApplication.java
The main entry point for the Spring Boot application.

- Configures and runs the application.

## Configuration
- **application.properties**:
  - Configures the application name and enables the use of virtual threads.
  - `spring.application.name=threads`
  - `spring.thread-executor=virtual`

## Build and Dependency Management
- **pom.xml**:
  - Maven configuration file specifying project dependencies and build settings.
  - Key dependencies include Spring Boot, Lombok, and Spring Boot Starter for MongoDB and Web.

## Project Structure
- **src/main/java**: Contains the main application code.
- **src/test/java**: Contains unit tests.
- **target**: Directory for compiled classes and resources.

## Summary
This project showcases the use of virtual threads in a Spring Boot application to handle HTTP requests efficiently. It includes REST endpoints for thread operations, configuration for virtual threads, and unit tests to ensure functionality. The project is built using Maven and follows best practices for Spring Boot applications.

## Documentation
For comprehensive business requirements, stakeholder analysis, and project objectives, see the [Business Requirements Document (BRD)](docs/Business_Requirements_Document.md).

### Quick Links
- [Full Documentation Index](docs/README.md)
- [Business Requirements Document](docs/Business_Requirements_Document.md)
- [Business Objectives & Success Criteria](docs/Business_Requirements_Document.md#2-business-objectives)