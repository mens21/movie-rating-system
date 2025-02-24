# Movie Rating System (Console App)
A simple **Spring Boot console-based** Movie Rating System implementing **SOLID principles**, **design patterns**, and **unit testing**.

## 📌 Features
- **Add Movies** (via console input)
- **List Movies**
- **Rate Movies**
- **Switch Rating Strategies** (Strategy Pattern)
  - **Average Rating**
  - **Weighted Rating**
  - **Median Rating**
- **Persistence with H2 Database**
- **Unit Tests with JUnit & Mockito**

## 🎯 Design Patterns Used
- **Singleton** (Spring Beans)
- **Factory** (Movie object creation)
- **Strategy** (Rating calculation methods)
- **Repository** (Spring Data JPA abstraction)
- **Service Layer** (Separation of concerns)

## 🚀 How to Run
1. **Extract the ZIP file**
2. Open a terminal and navigate to the project directory.
3. Run:  
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
4. Follow console instructions to interact with the system.

## 🛠 Dependencies
- Java 17+
- Spring Boot
- H2 Database
- JUnit 5 & Mockito (for tests)

## 📌 License
MIT License
