# File Processor Server

The backend service for the File Processor application, built with Spring Boot. This service handles file processing, user authentication, and query execution.

## Architecture

### Core Components

```
src/main/java/com/example/fileprocessor/
├── config/                 # Configuration classes
│   ├── ApplicationConfiguration.java
│   ├── RabbitMqConfiguration.java
│   └── SecurityConfiguration.java
├── controller/            # REST API endpoints
│   ├── AuthenticationController.java
│   ├── FileUploadController.java
│   ├── JobController.java
│   └── UserController.java
├── service/              # Business logic
│   ├── AuthenticationService.java
│   ├── FileJobService.java
│   ├── FileMetadataService.java
│   ├── FileProcessingService.java
│   ├── JobFailureService.java
│   ├── JobService.java
│   └── JwtService.java
├── repository/           # Data access layer
│   ├── FileMetadataRepository.java
│   ├── JobRepository.java
│   └── UserRepository.java
├── entity/              # Database entities
│   ├── FileMetadata.java
│   ├── Job.java
│   └── User.java
├── queue/               # Message queue handlers
│   ├── MessageConsumer.java
│   ├── MessageProducer.java
│   └── event/
└── engine/             # Query processing engine
    ├── QueryEngine.java
    ├── command/
    ├── grammar/
    └── parser/
```

## Component Details

### File Processing Pipeline

1. **File Upload**

   - Handled by `FileUploadController`
   - Files are stored using `FileSystemStorageService`
   - Metadata is saved through `FileMetadataService`

2. **File Processing**

   - `FileProcessingService` orchestrates the processing
   - File format detection and deserialization:
     ```
     deserializer/
     ├── FileDeserializerFactory.java  # Factory for creating appropriate deserializer
     ├── CsvDeserializer.java
     ├── JsonDeserializer.java
     └── XmlDeserializer.java
     ```

3. **Query Engine**

   - Custom query processing engine for data manipulation
   - Components:
     - `QueryEngine.java`: Main entry point for query execution
     - `command/`: Query command implementations
     - `grammar/`: Query language grammar definitions
     - `parser/`: Query parsing logic

   #### ANTLR Integration

   ANTLR (ANother Tool for Language Recognition) plays a crucial role in our query engine:

   - **Grammar Definition**:

     - Located in `grammar/` directory
     - Defines the syntax rules for our custom query language
     - Supports operations like SELECT, SET, REMOVE, DELETE WHERE, FILTER WHERE, INSERT

   - **Parser Generation**:

     - ANTLR automatically generates Java parser code from our grammar
     - Creates lexer for tokenizing input
     - Generates parser for syntax tree construction

   - **Query Processing Flow**:

     1. Query text → ANTLR Lexer → Tokens
     2. Tokens → ANTLR Parser → Parse Tree
     3. Parse Tree → Custom Visitor → Command Objects
     4. Command Objects → QueryEngine → Results

   - **Benefits**:
     - Robust error detection and reporting
     - Handle complex nested queries
     - Easy grammar modifications and maintenance
     - Industry-standard parsing solution

4. **Job Management**
   - Asynchronous job processing using RabbitMQ
   - Components:
     - `JobService`: Manages job lifecycle
     - `MessageProducer`: Queues jobs for processing
     - `MessageConsumer`: Processes queued jobs
     - `JobFailureService`: Handles failed jobs

### Security

1. **Authentication & Authorization**

   - JWT-based authentication
   - Components:
     - `SecurityConfiguration.java`: Security settings
     - `JwtAuthFilter.java`: JWT validation
     - `JwtService.java`: Token management
     - `AuthenticationService.java`: User authentication

2. **Error Handling**
   - Global exception handling through `GlobalExceptionHandler`
   - Custom exceptions for business logic
   - Syntax validation for queries

## Database Schema

The application uses Flyway for database migrations. Key tables:

1. **Users**

   - User authentication and profile data
   - Managed by `UserRepository`

2. **Files**

   - Stores file metadata and references
   - Managed by `FileMetadataRepository`

3. **Jobs**
   - Tracks processing jobs and their status
   - Managed by `JobRepository`

## Configuration

Key configuration files:

1. `application.yml`

   - Database configuration
   - RabbitMQ settings
   - File storage paths
   - Security parameters

2. Database Migrations
   ```
   resources/db/migration/
   ```

## Build and Deployment

The project uses Maven for build management. Key commands:

```bash
# Build the project
./mvnw clean package

# Run the application
./mvnw spring-boot:run

# Run tests
./mvnw test
```

## Dependencies

- Spring Boot
- Spring Security
- Spring Data JPA
- RabbitMQ
- PostgreSQL
- Flyway
- JWT
- ANTLR (for query parsing)
