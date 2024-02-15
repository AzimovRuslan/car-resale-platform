# car-resale-platform
My car resale platform project

TECHNOLOGY STACK USED
Spring Boot Spring Data (JpaRepository) Hibernate Slf4j REST PostgreSQL Gradle JUnit JWT 

STARTING INSTRUCTIONS
Before running the application, you need to create an empty database called CarSalePlatform without tables. Then go to the resources/application.properties file, change the connection string to the database (spring.datasource.url=jdbc:postgresql://localhost:5432/CarSalePlatform - my connection string). You also need to change the port number on which the server will be located or leave it the same. After all this, you can launch the application. After launching the application or tests, all the tables with fields we need will be created automatically and will be ready for use. Integration tests were written with which you can test all APIs

DESCRIPTION OF ENDPOINTS
/auth/signin [POST] - authorization of the registered user /auth/signup [POST] - user registration

/api/cars/ [GET] - output of all stored cars /api/cars/{id} [GET] - output of a car by ID /api/cars [POST] - adding a new car /api/cars/{id} [DELETE ] - deleting a car by ID /api/cars/{id} [PUT] - updating a car by ID

/api/sale-requests/ [GET] - output of all stored sales requests /api/sale-requests/{id} [GET] - output of a sales request by ID /api/sale-requests [POST] - adding a new request about sale /api/sale-requests/{id} [DELETE] - deleting a sales request by ID /api/sale-requests/{id} [PUT] - updating a sales request by ID

/api/sale-announcements/ [GET] - display all stored announcements /api/sale-announcements/{id} [GET] - display an announcement by ID /api/sale-announcements [POST] - add a new announcement /api/sale-announcements/{id} [DELETE] - deleting an ad by ID /api/sale-announcements/{id} [PUT] - updating an ad by ID
