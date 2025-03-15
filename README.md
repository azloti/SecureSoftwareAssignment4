# Assignment 4 - Secure Notes App

This is my implementation of Assignment 4. I created a simple Spring Boot app that lets you create and manage notes securely. The main focus was on making it secure against SQL injection and making sure all data is transmitted safely using HTTPS.

## Features

I implemented three main security features:

### 1. HTTPS Setup
- Created a keystore file (`keystore.p12`) to enable HTTPS
- Enforced all traffic through HTTPS
- Set everything up in the `application.properties` file

### 2. Database Security
- Used SQLite with Spring JPA/Hibernate
- Protected against SQL injection by using JPA's built-in security features
- Made sure users can only see their own notes
- Database updates automatically when you change the models

### 3. Security Features
- Added login requirement for secure data
- Used BCrypt to encrypt passwords
- Added a test public endpoint and notes endpoints that require login
- Set up basic session management

## How It Works

The code is split into a Entity Repository controller model:

1. **Note Storage** (`SecureNote.kt`)
   - Stores note info: ID, title, content, who created it, when it was created/modified

2. **Database Access** (`SecureNoteRepository.kt`)
   - Handles all database operations
   - Uses Spring's automatic query generation, protecting against SQL Injection

3. **API Endpoints** (`SecureNoteController.kt`)
   - Handles all the web requests
   - Makes sure users can only access their own notes

4. **Security Setup** (`SecurityConfig.kt`)
   - Sets up all the security features
   - Handles user login and HTTPS code

## Testing

### Required libraries
- JDK 21
- Gradle

### Running the app
1. Clone this repo
2. Go to the project folder
3. Run it:
   ```bash
   ./gradlew bootRun
   ```
4. The app will be at `https://localhost:8443`

### Testing the API
Here are some commands to test it:

1. Create a new note:
   ```bash
   curl -k -X POST https://localhost:8443/api/notes \
     -u user:password \
     -H "Content-Type: application/json" \
     -d '{"title":"Test Note","content":"This is a test"}'
   ```

2. Get your notes:
   ```bash
   curl -k https://localhost:8443/api/notes -u user:password
   ```

3. Get one specific note:
   ```bash
   curl -k https://localhost:8443/api/notes/1 -u user:password
   ```

### Login Info
To test it out, use:
- Username: `user`
- Password: `password`

## Security Features
- Everything goes through HTTPS
- Passwords are encrypted
- Protected against SQL injection
- Need to log in to do anything important
- Users can only see their own notes
- Basic session management

## Technical Details

### Database Setup
```properties
spring.datasource.url=jdbc:sqlite:notes.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
```

### HTTPS Setup
```properties
server.port=8443
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-type=PKCS12
server.ssl.enabled=true
```

### Main Dependencies Used
- spring-boot-starter-web
- spring-boot-starter-security
- spring-boot-starter-data-jpa
- sqlite-jdbc
- hibernate-community-dialects 