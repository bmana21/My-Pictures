# MyPictures Web Application

MyPictures is a Java web application developed using Spring Boot that allows users to manage their photo albums. Users can register, log in, create albums, add photos, and manage their uploaded content. The application securely stores photos on Google Cloud Storage and ensures privacy for users' albums and images. It provides a seamless user experience with features like photo viewing, deletion, downloading, and full-screen mode.

## Features

- **User Authentication:**
  - Users can register and log in securely, with the option to use the "Remember Me" function that utilizes cookies to remember their login status for one month.

- **Album Management:**
  - Users can create personal albums and add photos to them.
  - Secure storage of photos on Google Cloud Storage.

- **Privacy and Security:**
  - The application ensures privacy for users' albums and photos.

- **User Interface (UI):**
  - The UI is designed for a seamless experience, allowing users to view their photos during the upload process.
  - Users can delete photos individually or entire albums, with deleted items saved in a trash can for easy recovery.

- **Enhanced Photo Viewing:**
  - Users can download uploaded photos or view them in full-screen mode for a better viewing experience.

## Requirements

- **Java:** Java 20+
- **Spring Boot:** Spring Boot 2.5.x
- **Database:** MySQL 8.0.25+
- **Docker:** Latest version with Docker Compose support

## Setup Guide

### Bucket Setup

1. Create a Google Cloud Storage Bucket:
   - Go to [Google Cloud Console](https://console.cloud.google.com/).
   - Navigate to `More -> Cloud Storage -> Buckets`.
   - Click `Create Bucket`.
   - Set the bucket name, enable public access, and set protection to object versioning.
   
2. Update Constants in `GoogleCloudConstants.java`:
   - In `src/main/java/com/example/constants/GoogleCloudConstants.java`, update the `BUCKET_NAME` field with your bucket's name.
   
3. Create a Service Account and Obtain JSON Key:
   - Go to `More -> IAM & ADMIN -> Service accounts`.
   - Click `Create Service Account`.
   - Click `Edit -> Key Management', create a new JSON key, and download that JSON file.
   - Save the JSON key file in `src/main/resources/credentials`.
   - Update `JSON_KEY_NAME` in `GoogleCloudConstants.java` with your JSON key file name.

### Database Setup

1. Create Database:
   - Run the SQL schema provided in `src/main/resources/sql/schema.sql` to create the necessary database tables.

2. Configure Database Credentials:
   - In `src/main/resources/application.properties`, set your database username and password in the `spring.datasource.username` and `spring.datasource.password` fields.

### Running the Application

1. Build and Run:
   - Open a terminal and navigate to the project directory.
   - Run `mvn clean install` to build the project.
   - Run `mvn spring-boot:run` to start the application.
   - Or import your project in IntelliJ, which will automatically build, run, and deploy the application

2. Access the Application:
   - Visit `http://localhost:8080` in your web browser to access the MyPictures web application.

## Docker Support

Dockerized setup files are provided to run the application in a Docker container. Refer to the `docker-compose.yml` file for configuration. Run the following command to start the application with Docker:

```bash
docker-compose up
```
---

Feel free to modify and customize the application according to your needs! If you encounter any issues or have questions, please don't hesitate to reach out. Happy coding!
