# Clustered data warehouse:

Clustered data warehouse application reads csv file that the user have to upload through the exposed endpoint. The
endpoint to upload csv file is [upload csv](http://localhost:8080/api/v1/deal/upload). After upload is successful it
validates the file format to verify whether the file is csv or not.<br/>
**csv file must have header present in it since application contains logic to remove the header of csv file.**
Application will save records containing valid data in database and returns map of record that does not have valid data
with corresponding row number.
---

### Validations performed:

1. Blank data are checked for each row.
2. Only deals with unique id can be stored in database.
3. Deals with deal amount less than or equal to are not stored in database.

### Technologies used:

1. Spring Boot
2. MySql
3. JPA
4. Spring data JPA
5. JUnit
6. Docker

### Deliverables

1. Working spring boot project
2. Docker file

### How to run this?

$ git clone https://github.com/anilmaharjan87/clusus-task.git<br/>
$ mvn clean install<br/>
$ run docker-compose up<br/>
$ use postman to upload csv file to endpoint http://localhost:8080/api/v1/deal/upload

### Output

1. Clean data i.e without any violation is stored in mysql database.
2. Returns list of invalid data with row number, error message and property that is invalid

