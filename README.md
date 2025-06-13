# MarketAuction Application

This is a Spring Boot application for calculating equipment costs based on yearly ratios and default values, using an H2 in-memory database. The project includes a REST API and integration tests.

---

## Prerequisites

- Java 21
- Maven 3.8+ (or use the included `mvnw` wrapper)
- Git

---

## How to Run the Application

1. **Clone the repository:**
   ```sh
   git clone https://github.com/diegoLom/MarketAuction
   cd marketauction
   ```

2. **Build and start the application:**
   ```sh
   ./mvnw spring-boot:run
   ```
   The application will start on [http://localhost:8080](http://localhost:8080).

3. **Access the H2 Console (optional):**
   - Go to [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - JDBC URL: `jdbc:h2:mem:testdb`
   - User: `sa`
   - Password: *(leave blank)*

---

## How to Call the REST API

You can use `curl`, Postman, or your browser. Example:

```sh
curl "http://localhost:8080/cost?id=67352&year=2007"
```

---

## How to Run the Integration Test

1. **Run all tests:**
   ```sh
   ./mvnw test
   ```

2. **Run only the integration test:**
   ```sh
   ./mvnw -Dtest=CostCalculationApiIntegrationTest test
   ```

   The integration test will call the `/cost` endpoint with sample parameters and print the results to the console.

---

## Troubleshooting

- If you see a "table not found" error, ensure you have `spring.jpa.defer-datasource-initialization=true` in your `application.properties`.
- Make sure your database and entity mappings match the sample data in `src/main/resources/data.sql`.

---

## Project Structure

- `src/main/java/com/smartgroup/marketauction` - Main application code
- `src/main/resources/data.sql` - Sample data loaded into H2 at startup
- `src/test/java/com/smartgroup/marketauction/integration/CostCalculationApiIntegrationTest.java` - Integration test for the REST API

---

## Contact
For questions or help, contact the diegosantana019@gmail.com