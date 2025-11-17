# Employee service
This project provides simple service for managing employees using **REST** API calls.

## Description
With Employee service you can do next operations with employees: create, update, find by id, find by filter, delete by id, delete by filter.

REST endpoints defined in su.aleksokol3.employeeservice.controller.EmployeeController.

Port is 8080.

Employee service uses Postgresql database to store the data.

## Getting Started

### Dependencies
For building and running the application you need:
- [JDK 17](https://openjdk.org/projects/jdk/17/) or higher
- [Maven 3](https://maven.apache.org)

### Installing
Clone the repository

### Executing program
- Execute the `main` method in the `su.aleksokol3.employeeservice.EmployeeServiceApplication` class from your IDE.

- Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
    ```shell
    mvn spring-boot:run
    ```

### Usage
Do the REST calls to: `http://localhost:8080/api/v1/employees` to use the application.

#### Examples of requests:

- ###### Create employee
    ```
    POST http://localhost:8080/api/v1/employees
    
    Content-Type: application/json
    {
    "firstName": "John",
    "patronymic": "W",
    "lastName": "Johnson",
    "age": 50,
    "salary": 123.45,
    "hiringDate": "2009-11-30"
    };
    
    Response: 201 CREATED
    Content: id of created employee
    ```

- ###### Update employee
    ```
    PATCH http://localhost:8080/api/v1/employees/e30ceaea-c89b-4029-a044-3f64454d8e35
    
    Content-Type: application/json
    {
      "patronymic": null,
      "age": 51,
      "salary": 223.45
    };
    
    Response: 200 OK
    Content: updated employee
    ```
    
- ###### Get employee by id
    ```
    GET http://localhost:8080/api/v1/employees/e30ceaea-c89b-4029-a044-3f64454d8e35
    
    Response: 200 OK
    Content: employee
    ```
    
- ###### Get employees by filter with pageable (page, size, sort by entity field)
    ```
    GET http://localhost:8080/api/v1/employees?ageFrom=40&ageTo=111&firstName=Jo&page=2&size=3&sort=salary
    
    Response: 200 OK
    Content: paginated list of employees with meta-information
    ```
    
- ###### Delete employee by id
    ```
    DELETE http://localhost:8080/api/v1/employees/e30ceaea-c89b-4029-a044-3f64454d8e35
    
    Response: 204 NO CONTENT
    ```
    
- ###### Delete employees by filter
    ```
    DELETE http://localhost:8080/api/v1/employees/e30ceaea-c89b-4029-a044-3f64454d8e35
    
    Response: 204 NO CONTENT
    ```

### Swagger
The below link will launch the swagger of the Employee service.
[Swagger UI](http://localhost:8080/employeeservice/swagger-ui.html)

## Contact
[Email](malito:aleksokol3@yandex.ru)