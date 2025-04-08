# userMangemnt


## Overview
This service handles user management operations with Kafka integration for event messaging.


- **Application Name**: UserManagement

## Authentication
This service uses Spring Security (as indicated by the security debug logging configuration)

## Database Configuration
- **Type**: PostgreSQL
- **Database Name**: user_management

## Message Broker
- **Platform**: Apache Kafka
- **Bootstrap Servers**: localhost:9092
- **Consumer Group ID**: userid

## API Endpoints

### [Endpoint Name]

## POST - /register/user

Endpoint is available for all the users we can register new users

request body

{
    "username":"Angel",
    "password":"vikas"
}

Response 

User has ben added successfully


## POST - /login/user

request body

{
    "username":"Angel",
    "password":"vikas"
}

Response 

Response will be JWT token

## GET - fetch/{username} ( accesible for ADMIN user)


request body: with JWT token and username

Response 

{
    "username":"Angel",
    "password":"vikas"
}
## PUT - /update/user

User password can be updated


request body

{
    "username":"Angel",
    "password":"vikas"
}

Response 
The 
password has been updated

## PUT - /update/userRole ( accesible for ADMIN user)

request body

{
    "username":"Angel",
    "roles":["ADMIN"]
}
Response

Roles have been updated

## Delete - /remove/user 

request body

{
    "username":"Angel",
}
Response

User have been removed

## GET /update/{action}/{username}  ( accesible for ADMIN user)

Response User events such as update, register, login and 

response :

[
{
"username":"ram",
"action":"update",
"createdAt":"2024-04-23 21:21:123"

}
]



