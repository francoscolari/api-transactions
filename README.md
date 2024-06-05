# API Transactions
this api is in charge of storing and returning information about transactions.
## Requirements
- Java 11
- Gradle
- Docker (optional)

### Locally
1. Clone this repository.
2. Run the following command to compile the project and execute the tests:
   ```sh
   ./gradlew clean build
    ```
### With Docker (optional)

1. Build the Docker image by running the following command in the root of the project:
    ```
    docker build --platform linux/amd64 -t api-transactions  --build-arg appName=api-transactions  --build-arg version=0.0.1  .
    ```
2.  Run the Docker image:
    ```
    docker run -d -p 8080:8080 --platform linux/amd64 api-transactions
    ```
3. The application will be available at http://localhost:8080.

# Endpoints

### `PUT /transactions/{transactionId}`
Create or update an existing transaction.

#### Parameters
- `transactionId`: The ID of the transaction.
- Request body in JSON format.

#### Example Request
```json
{
  "type": "type",
  "amount": 100.0,
  "parent_id": 1
}
```
"parent_id" is optional

#### Example Response
```json
{
  "status": "OK"
}
```
---
### `GET /transactions/types/{type}`
Gets all transactions ID of a specific type.

#### Parameters
- `type`: The type of transaction.

#### Example Response
```json
[
  10
]
```
---
### `GET /transactions/sum/{transactionId}`
Gets the sum of all transactions related to a specific transaction.

#### Parameters
- `transactionId`: The ID of the transaction.

#### Example Response
```json
{
  "sum": 300.0
}
```

## Documentation

You can find the API documentation in Swagger at the following URL:

    ```
    http://localhost:8080/swagger-ui/index.html
    ```

## Technical Debt

1. Improper Use of HTTP Methods:

    It's considered a bad practice to use the HTTP PUT method both for creating and updating a resource. Instead, adhering to RESTful principles, it's recommended to use the HTTP POST method for resource creation and the HTTP PUT method exclusively for updates. This ensures better clarity and consistency in your API design, making it easier for developers to understand and maintain.

2. Performance Enhancement with Redis:
    
    Currently, our application relies on Hibernate and JPA for data persistence. While these technologies are reliable and widely used, they may not always offer the optimal performance, especially when dealing with complex queries or large datasets. As a strategy to mitigate technical debt and improve performance, we're considering incorporating Redis into our stack.
    Redis is an in-memory data store known for its speed and flexibility. By leveraging Redis, we can implement caching mechanisms to store frequently accessed data, such as transaction children, and reduce the overhead of querying the database. This approach can significantly enhance the responsiveness and scalability of our application, providing a better experience for our users.

