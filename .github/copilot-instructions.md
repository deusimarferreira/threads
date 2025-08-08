# Spring Boot Virtual Threads Application

This is a Java Spring Boot application that demonstrates virtual threads functionality through REST APIs. The application showcases thread management and performance optimization using Java 21's virtual threads feature.

**CRITICAL**: Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## Working Effectively

## Working Effectively

### Environment Setup (REQUIRED - Java 21 is mandatory)
- Install Java 21: `sudo apt update && sudo apt install -y openjdk-21-jdk`
- Set Java 21 environment for current session:
  ```bash
  export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
  export PATH=$JAVA_HOME/bin:$PATH
  ```
- **CRITICAL**: Always set these environment variables before running any Maven commands
- Verify Java version: `java -version` (should show 21.x.x)
- Make Maven wrapper executable: `chmod +x mvnw` (only needed once)

### Build Commands (NEVER CANCEL - SET 5+ MINUTE TIMEOUTS)
- **Initial build with dependencies**: `./mvnw clean compile` -- takes 1-3 minutes on first run with downloads. NEVER CANCEL. Set timeout to 300+ seconds.
- **Full package build**: `./mvnw clean package` -- takes 10-60 seconds depending on dependencies. NEVER CANCEL. Set timeout to 120+ seconds.
- **Incremental compile**: `./mvnw compile` -- takes 2-5 seconds after initial build.

### Testing (NEVER CANCEL - SET 2+ MINUTE TIMEOUTS)
- **Run all tests**: `./mvnw test` -- takes 8-15 seconds. NEVER CANCEL. Set timeout to 120+ seconds.
- **Test with clean**: `./mvnw clean test` -- included in clean package build above. NEVER CANCEL. Set timeout to 180+ seconds.

### Running the Application
- **Development mode**: `./mvnw spring-boot:run` -- starts on port 8080, takes 2-5 seconds to start
- **JAR execution**: `java -jar target/threads-0.0.1-SNAPSHOT.jar` -- alternative way to run
- **Custom port**: Add `--server.port=8081` to run on different port

## Validation

### MANDATORY: Always validate functionality after changes
After making any code changes, ALWAYS run these validation scenarios:

#### 1. Basic Thread Operations
```bash
# Test GET thread name (should show VirtualThread)
curl -s "http://localhost:8080/thread/name"
# Expected output: VirtualThread[#XX]/runnable@ForkJoinPool-1-worker-X

# Test POST thread name
curl -s -X POST "http://localhost:8080/thread/name/TestThread"
# Expected output: New name is: TestThread
```

#### 2. Load Testing Endpoint
```bash
# Test load endpoint (simulates delay)
curl -s "http://localhost:8080/load"
# Should return empty body with 200 status
```

#### 3. Health Check
```bash
# Test actuator health endpoint
curl -s "http://localhost:8080/actuator/health"
# Should return JSON with status info (MongoDB will be DOWN if not running - this is normal)
```

#### 4. Build Verification
Always run these commands before considering changes complete:
```bash
./mvnw clean package  # Ensures clean build works
./mvnw test           # Ensures all tests pass
```

## Application Structure

### Key Components
- **ThreadsController.java**: Main REST controller with thread operation endpoints
- **ThreadsConfiguration.java**: Configures virtual threads for task execution
- **LoadTestController.java**: Provides load testing endpoint for performance testing
- **ThreadsApplication.java**: Main Spring Boot application entry point

### Important Directories
```
src/main/java/com/example/threads/    # Main application code
src/test/java/com/example/threads/    # Unit tests
src/main/resources/                   # Configuration files
target/                               # Build artifacts (excluded from git)
```

### Configuration Files
- **pom.xml**: Maven configuration with Java 21 requirement and Spring Boot dependencies
- **application.properties**: Spring configuration enabling virtual threads
- **.gitignore**: Standard Spring Boot exclusions including target/ directory

## Dependencies and Requirements

### Required Dependencies (auto-installed by Maven)
- Java 21 (REQUIRED - application uses virtual threads)
- Spring Boot 3.4.1
- Spring Boot Starter Web
- Spring Boot Starter MongoDB (optional - app runs without MongoDB server)
- Spring Boot Starter Actuator
- Lombok (build-time annotation processing)

### External Dependencies
- **MongoDB**: Optional - application starts without MongoDB server, but will log connection errors (this is normal and expected)

## Common Tasks

## Common Tasks

### Development Workflow
1. Set Java 21 environment: `export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 && export PATH=$JAVA_HOME/bin:$PATH`
2. Make code changes
3. Run `./mvnw compile` to verify compilation
4. Run `./mvnw test` to ensure tests pass
5. Start application with `./mvnw spring-boot:run`
6. Validate endpoints using curl commands above
7. Stop application with Ctrl+C

### Quick Reference Commands
```bash
# Complete build and validation cycle
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 && export PATH=$JAVA_HOME/bin:$PATH
./mvnw clean package  # Builds and runs tests
./mvnw spring-boot:run # Start application (in another terminal after build)

# Test all endpoints (while app is running)
curl -s "http://localhost:8080/thread/name"
curl -s -X POST "http://localhost:8080/thread/name/TestThread"  
curl -s "http://localhost:8080/load"
curl -s "http://localhost:8080/actuator/health"
```

### Troubleshooting
- **Java version issues**: Ensure JAVA_HOME points to Java 21 installation
- **Permission denied on mvnw**: Run `chmod +x mvnw`
- **MongoDB connection errors**: Normal if MongoDB not installed - application still functions
- **Port already in use**: Change port with `--server.port=XXXX` argument

### Project Features
- **Virtual Threads**: Demonstrates Java 21 virtual threads with Spring Boot
- **REST API**: Simple endpoints for thread name manipulation
- **Load Testing**: Basic load simulation endpoint
- **Health Monitoring**: Spring Actuator health checks
- **No Database Required**: Runs without external dependencies (MongoDB is optional)

## Time Expectations
- **NEVER CANCEL builds or tests** - Always wait for completion
- Initial build with downloads: 1-3 minutes
- Subsequent builds: 10-60 seconds  
- Test execution: 8-15 seconds
- Application startup: 2-5 seconds
- Thread operations: Instantaneous response

## Output Examples

### Successful Build Output
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX.XXX s
```

### Successful Test Output
```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Application Startup Confirmation
```
Started ThreadsApplication in X.XXX seconds
Tomcat started on port 8080 (http)
```

### Virtual Thread Endpoint Response
```
VirtualThread[#34]/runnable@ForkJoinPool-1-worker-1
```