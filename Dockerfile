FROM java:8
FROM maven:alpine
WORKDIR /app
COPY . /app
ADD ./target/warehouse-0.0.1-SNAPSHOT.jar warehouse-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","warehouse-0.0.1-SNAPSHOT.jar"]
