# Spring Boot - Virtual Threads

## Overview
This project is a Java-based web application built using the Spring Boot framework. It leverages virtual threads to handle HTTP requests efficiently. The primary focus is on demonstrating thread management and performance optimization using virtual threads.

## 📚 Documentação Completa

Este projeto inclui documentação abrangente seguindo padrões corporativos:

### 📋 Documentos Principais
- **[BRD - Business Requirements Document](docs/BRD-Business-Requirements.md)**: Requisitos de negócio, justificativas e objetivos do projeto
- **[FD - Functional Design](docs/FD-Functional-Design.md)**: Especificações funcionais, APIs e casos de uso
- **[TRD - Technical Requirements Document](docs/TRD-Technical-Requirements.md)**: Arquitetura técnica, configurações e implementação

### 🎯 Para Diferentes Audiências
- **Gestores e Stakeholders**: Consultem o [BRD](docs/BRD-Business-Requirements.md) para entender valor de negócio
- **Analistas e Product Owners**: Consultem o [FD](docs/FD-Functional-Design.md) para especificações funcionais
- **Desenvolvedores e Arquitetos**: Consultem o [TRD](docs/TRD-Technical-Requirements.md) para detalhes técnicos

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

## Quick Start

### Pré-requisitos
- Java 21 (obrigatório para Virtual Threads)
- Maven 3.8+ ou usar o wrapper incluído (./mvnw)

### Executar a Aplicação
```bash
# Configurar Java 21
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Build e execução
./mvnw clean package
./mvnw spring-boot:run
```

### Testar Funcionalidades
```bash
# Verificar Virtual Threads
curl http://localhost:8080/thread/name

# Definir nome da thread
curl -X POST http://localhost:8080/thread/name/MinhaThread

# Teste de carga
curl http://localhost:8080/load

# Health check
curl http://localhost:8080/actuator/health
```

## Summary
This project showcases the use of virtual threads in a Spring Boot application to handle HTTP requests efficiently. It includes REST endpoints for thread operations, configuration for virtual threads, and unit tests to ensure functionality. The project is built using Maven and follows best practices for Spring Boot applications.

Para informações detalhadas sobre arquitetura, configuração e uso, consulte a [documentação completa](docs/).