# **DEMO**

## Introduction

Test task

## Prerequisites

The following technologies required:  

* Java 11  
* Postgres db  
* Spring boot 2  
* Ehcache  

## Building & setup 

Follow the steps below:

1. install docker  
2. cd /demo-user-project  
3. docker-compose up --build    * In this case, port **8081** will be used  
4. or run in your favorite ide:  
5. cd /demo-user-project/etc/docker/test_task_dev  
6. docker-compose up            * To create a database in docker  
7. The **prod** profile is used only for deploying a project in Docker. For local development, the profile does not need to be installed    
8. End Points:  
   To receive a token (user - **test1@User1.com**, password - **pass** (the password is the same for all users))  
   POST http://localhost:8085/api/v1/auth/login  
   Search for users  
   GET http://localhost:8085/api/v1/user/managed-user-data  
   Delete email or phone  
   DELETE http://localhost:8085/api/v1/user/managed-user-data  
   Add email or phone  
   POST http://localhost:8085/api/v1/user/managed-user-data  
   Change email or phone  
   PATCH http://localhost:8085/api/v1/user/managed-user-data  
   Transferring money from one user to another one  
   POST http://localhost:8085/api/v1/user/managed-user-amount  
9. enjoy

