# Documento de Requisitos Técnicos (TRD)
## Projeto Spring Boot - Virtual Threads

### Versão do Documento: 1.0
### Data: Janeiro 2025
### Autor: Documentação Técnica

---

## 1. Visão Geral do Projeto

### 1.1 Objetivo
Este projeto é uma aplicação web Java baseada no framework Spring Boot que demonstra o uso de Virtual Threads (Project Loom) para manipulação eficiente de requisições HTTP. O foco principal é demonstrar o gerenciamento de threads e otimização de performance utilizando Virtual Threads.

### 1.2 Escopo
A aplicação fornece endpoints REST para operações de thread, incluindo:
- Obtenção do nome da thread atual
- Configuração do nome da thread
- Endpoints para teste de carga e performance

### 1.3 Benefícios Esperados
- Demonstração prática do uso de Virtual Threads
- Melhor utilização de recursos do sistema
- Performance otimizada para operações concorrentes
- Arquitetura escalável para aplicações web

---

## 2. Requisitos Técnicos

### 2.1 Requisitos de Software

#### 2.1.1 Ambiente de Desenvolvimento
- **Java Development Kit (JDK)**: 21 ou superior
- **Maven**: 3.8 ou superior
- **IDE**: IntelliJ IDEA, Eclipse, ou VS Code com extensões Java
- **Git**: Para controle de versão

#### 2.1.2 Ambiente de Execução
- **Java Runtime Environment (JRE)**: 21 ou superior
- **Sistema Operacional**: Windows, Linux, ou macOS
- **Memória RAM**: Mínimo 512MB, recomendado 2GB
- **Espaço em Disco**: 100MB para aplicação + dependências

#### 2.1.3 Dependências Principais
```xml
- Spring Boot: 3.4.1
- Spring Boot Starter Web: Para desenvolvimento web
- Spring Boot Starter Data MongoDB: Para integração com MongoDB
- Spring Boot Starter Actuator: Para monitoramento
- Lombok: Para redução de código boilerplate
- JUnit 5: Para testes unitários
```

### 2.2 Requisitos de Infraestrutura

#### 2.2.1 Servidor de Aplicação
- **Servidor Embarcado**: Apache Tomcat (incluído no Spring Boot)
- **Porta Padrão**: 8080
- **Protocolo**: HTTP/HTTPS

#### 2.2.2 Banco de Dados (Opcional)
- **MongoDB**: Para persistência de dados (configurado mas não utilizado atualmente)
- **Versão**: 4.4 ou superior

---

## 3. Arquitetura e Design

### 3.1 Arquitetura Geral
A aplicação segue o padrão MVC (Model-View-Controller) do Spring Boot:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controllers   │    │  Configuration  │    │   Application   │
│  (REST Layer)   │────│   (Threading)   │────│     Main        │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 3.2 Componentes Principais

#### 3.2.1 ThreadsApplication.java
- **Responsabilidade**: Ponto de entrada principal da aplicação Spring Boot
- **Funcionalidade**: Inicialização e configuração da aplicação

#### 3.2.2 ThreadsController.java
- **Responsabilidade**: Controller REST para operações de thread
- **Endpoints**:
  - `GET /thread/name`: Retorna o nome da thread atual
  - `POST /thread/name/{name}`: Define o nome da thread atual

#### 3.2.3 ThreadsConfiguration.java
- **Responsabilidade**: Configuração de Virtual Threads
- **Funcionalidades**:
  - Configuração do executor de tarefas para Virtual Threads
  - Customização do protocolo handler do Tomcat
  - Habilitação condicional baseada em propriedades

#### 3.2.4 LoadTestController.java
- **Responsabilidade**: Controller para testes de carga
- **Funcionalidade**: Simula operações com delay para teste de performance

### 3.3 Configuração de Virtual Threads

#### 3.3.1 Ativação
```properties
spring.thread-executor=virtual
```

#### 3.3.2 Implementação
- Utiliza `Executors.newVirtualThreadPerTaskExecutor()`
- Configuração condicional via `@ConditionalOnProperty`
- Integração com protocolo handler do Tomcat

---

## 4. Especificações da API

### 4.1 Endpoints Disponíveis

#### 4.1.1 Thread Operations

**GET /thread/name**
- **Descrição**: Obtém informações da thread atual
- **Método**: GET
- **Parâmetros**: Nenhum
- **Resposta**: String com informações da thread
- **Exemplo de Resposta**: `"Thread[#23,virtual,VirtualThread-23,main]"`

**POST /thread/name/{name}**
- **Descrição**: Define o nome da thread atual
- **Método**: POST
- **Parâmetros**: 
  - `name` (Path Variable): Novo nome para a thread
- **Resposta**: Confirmação do novo nome
- **Exemplo de Resposta**: `"New name is: myThread"`

#### 4.1.2 Load Testing

**GET /load**
- **Descrição**: Endpoint para teste de carga
- **Método**: GET
- **Parâmetros**: Nenhum
- **Comportamento**: Simula delay de 1 segundo
- **Resposta**: Sem conteúdo (void)

#### 4.1.3 Health Check

**GET /actuator/health**
- **Descrição**: Verificação de saúde da aplicação
- **Método**: GET
- **Resposta**: Status da aplicação
- **Configuração**: Detalhes sempre visíveis

---

## 5. Configuração e Propriedades

### 5.1 application.properties
```properties
# Nome da aplicação
spring.application.name=threads

# Habilitar Virtual Threads
spring.thread-executor=virtual

# Configurações do Actuator
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=always
```

### 5.2 Configurações de Build (pom.xml)

#### 5.2.1 Propriedades do Projeto
- **Group ID**: com.example
- **Artifact ID**: threads
- **Versão**: 0.0.1-SNAPSHOT
- **Java Version**: 21

#### 5.2.2 Plugins de Build
- **maven-compiler-plugin**: Configurado para Lombok
- **spring-boot-maven-plugin**: Para build da aplicação Spring Boot

---

## 6. Build e Deployment

### 6.1 Comandos de Build

#### 6.1.1 Compilação
```bash
mvn clean compile
```

#### 6.1.2 Execução de Testes
```bash
mvn test
```

#### 6.1.3 Geração do JAR
```bash
mvn clean package
```

#### 6.1.4 Execução da Aplicação
```bash
mvn spring-boot:run
```

### 6.2 Deployment

#### 6.2.1 JAR Executável
```bash
java -jar target/threads-0.0.1-SNAPSHOT.jar
```

#### 6.2.2 Configurações de Ambiente
- Pode ser configurado via variáveis de ambiente
- Profiles do Spring para diferentes ambientes
- Configurações externalizadas via application.properties

---

## 7. Testes

### 7.1 Estratégia de Testes

#### 7.1.1 Testes Unitários
- **Framework**: JUnit 5
- **Cobertura**: Testes de contexto Spring Boot
- **Localização**: `src/test/java`

#### 7.1.2 Testes de Integração
- Testes dos endpoints REST
- Validação da configuração de Virtual Threads
- Testes de performance com LoadTestController

#### 7.1.3 Testes de Carga
- Utilização do endpoint `/load` para simulação
- Monitoramento via Spring Boot Actuator
- Análise de performance de Virtual Threads vs. Platform Threads

---

## 8. Performance e Escalabilidade

### 8.1 Virtual Threads

#### 8.1.1 Benefícios
- Menor uso de memória por thread
- Maior concorrência sem overhead de thread switching
- Melhor performance em operações I/O bound

#### 8.1.2 Considerações
- Ideal para aplicações com muitas operações I/O
- Não recomendado para operações CPU-intensive
- Compatível com código blocking tradicional

### 8.2 Métricas de Performance

#### 8.2.1 Monitoramento
- Health checks via Spring Boot Actuator
- Logging de operações via SLF4J
- Métricas de thread utilization

---

## 9. Segurança

### 9.1 Considerações de Segurança

#### 9.1.1 Endpoints Expostos
- Endpoints públicos sem autenticação
- Health check exposto para monitoramento
- Considerações para ambiente de produção

#### 9.1.2 Recomendações
- Implementar autenticação para produção
- Configurar HTTPS para comunicação segura
- Validação de entrada nos endpoints

---

## 10. Monitoramento e Observabilidade

### 10.1 Spring Boot Actuator

#### 10.1.1 Endpoints Disponíveis
- `/actuator/health`: Status da aplicação
- Configuração para exposição de métricas adicionais

#### 10.1.2 Logging
- SLF4J com Logback (padrão Spring Boot)
- Logging de operações no LoadTestController
- Configurável via application.properties

---

## 11. Troubleshooting

### 11.1 Problemas Comuns

#### 11.1.1 Versão do Java
- **Problema**: Erro de compilação com Java < 21
- **Solução**: Atualizar para Java 21 ou superior

#### 11.1.2 Virtual Threads não Ativo
- **Problema**: Virtual Threads não sendo utilizados
- **Solução**: Verificar propriedade `spring.thread-executor=virtual`

#### 11.1.3 Porta em Uso
- **Problema**: Porta 8080 já está em uso
- **Solução**: Configurar porta alternativa via `server.port`

---

## 12. Roadmap e Melhorias Futuras

### 12.1 Melhorias Propostas
- Implementação de autenticação e autorização
- Integração efetiva com MongoDB
- Métricas avançadas de performance
- Testes de carga automatizados
- Documentação de API com Swagger/OpenAPI

### 12.2 Considerações Técnicas
- Migração para Spring Boot 3.x features
- Implementação de observabilidade com Micrometer
- Containerização com Docker
- CI/CD pipeline setup

---

## 13. Referências

### 13.1 Documentação Oficial
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Virtual Threads (JEP 444)](https://openjdk.org/jeps/444)
- [Project Loom](https://openjdk.org/projects/loom/)

### 13.2 Recursos Adicionais
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Maven Documentation](https://maven.apache.org/guides/)

---

**Fim do Documento**

*Este documento deve ser atualizado conforme evolução do projeto e mudanças nos requisitos técnicos.*