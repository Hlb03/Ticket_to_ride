        TICKET TO RIDE WEB APPLICAITON

This application consists of four endpoints (two public and two private).

Swagger documentation is available via URL:

        http://localhost:8080/api/v1/swagger-ui/index.html

--- 
Public endpoints:

GET **/api/v1/calculate** - takes request parameters 'departure' and 'arrival' which are town names.
Returns response with amount of segments, price and currency

POST **/api/v1/users/registration** - takes request body which consists of
two fields 'username' and 'password'. Registers client as a new user in the application

---
Private endpoints (require basic authentication):

POST **/api/v1/tickets** - takes request body with information about desired ticket to buy. Stores user
bought ticket

GET **/api/v1/tickets** - returns list of all bought user tickets

--- 
Technology stack:

* Java21
* Maven
* Spring Boot, Data, Security, Test
* JUnit 5, Mockito
* PostgreSQL

Additional tools:

* Flyway
* Docker
* OpenAPI/Swagger

---
Endpoints request examples:

1. GET **/api/v1/calculate?departure=London&arrival=Coventry**:

Response body example:
{
    "segments": 4,
    "price": 15,
    "currency": "GPB"
}

2. POST **/api/v1/users/registration**

Payload example:
{
    "fullName": "Jane Smith",
    "password": "password"
}

3. POST **/api/v1/tickets**

Payload example:
{
    "departure": "London",
    "arrival": "Coventry",
    "segments": 4,
    "price": 15,
    "currency": "GBP",
    "travellerAmount": 20,
    "traveller": "Oliver Smart"
}

Success response body example:
{
    "result": "success",
    "currency": "GBP",
    "change": 5
}

Failure response body example (in case user provided less money than ticket costs, for example 11):
{
    "result": "failure",
    "currency": "GBP",
    "lackOf": -4
}

4. GET **/api/v1/tickets**

Response body example:
[
    {
        "id": 6,
        "departure": "London",
        "arrival": "Coventry",
        "segments": 4,
        "price": 15.00,
        "currency": "GBP",
        "travellerAmount": 20.00,
        "traveller": "Oliver Smart"
    }
]



