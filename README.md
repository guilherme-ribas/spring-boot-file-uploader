# Spring Boot File Storage Example

This is a simple Spring Boot application that allows file upload, listing, and download functionalities.

## Prerequisites

- Java 17 or higher
- Maven

## Getting Started

### Clone the Repository

Clone this repository to your local machine:

```bash
git clone https://github.com/seu-usuario/spring-boot-file-storage.git
cd spring-boot-file-storage
```
Configure Upload Directory
In src/main/resources/application.properties, specify the directory where uploaded files will be stored:

```
file.upload-dir=/path/to/your/upload/directory
```

### Build and Run the Application
You can build and run the application using Maven:

```bash
mvn spring-boot:run
```

Alternatively, you can build a JAR file and run it:

```bash
mvn clean package
java -jar target/spring-boot-file-storage-0.0.1-SNAPSHOT.jar
```
The application will start on port 8080 by default.

### Usage
Endpoints
POST /files/upload
Upload a file to the server. Use multipart/form-data with key file.

### Example with cURL:
```bash
curl -X POST -F "file=@/path/to/your/file.txt" http://localhost:8080/files/upload
```

### GET /files/list
List all files uploaded to the server.

### Example with cURL:
```bash
curl http://localhost:8080/files/list
```
### GET /files/download/{fileName}
Download a specific file from the server.

#### Example with cURL:
```bash
curl -OJ http://localhost:8080/files/download/{fileName}
```
Replace {fileName} with the actual filename you want to download.

### Dependencies
- Spring Boot Starter Web
  
This dependency are managed via Maven and are included in the pom.xml file.

