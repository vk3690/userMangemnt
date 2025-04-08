# userMangemnt


## Overview
This service handles user management operations with Kafka integration for event messaging.


## Server Configuration
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

--/register/user

Endpoint is available for all the users we can new users

request body

{
    "username":"Angel",
    "password":"vikas"
}

Response 


/login/user


/fetch/{username}

/update/user

/update/userRole


  /update/{action}/{username}
