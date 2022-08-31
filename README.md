# Springboot-Cassandra CRUD 
In this project we will implement CRUD (Create, Read, Update, Delete) operations using spring data and cassandra DB.
## Tools/Technologies
Download and install the following.

* [Apache-Cassandra-3.11.13](https://cassandra.apache.org/_/download.html)
* [Python 2.7](https://www.python.org/downloads/release/python-2718/)
* [JDK 1.8](https://www.oracle.com/java/technologies/downloads/#jdk18-windows)
* [Apache Maven 3.8.6](https://maven.apache.org/download.cgi)
* [IDE - STS-4.15.1.RELEASE(Starters: Spring Data for Apache Cassandra, Spring Web MVC, Lombok)](https://spring.io/tools/)
* [Postman for API testing](https://www.postman.com/downloads/)

## Project Structure

![image](https://user-images.githubusercontent.com/65802767/183461296-d3edf992-4088-44ba-9805-f65cb2abcd54.png "Project structure")
* [`CassandraProjectApplication.java`](https://github.com/VamsiChelluri/Springboot-with-CassandraDB/blob/master/src/main/java/com/cassandra/CassandraProjectApplication.java) class is the Spring Boot application's main class that contains a public static void main() method that starts up the Spring ApplicationContext. 
By default, if the main class isn't explicitly specified, Spring will search for one in the classpath at compile time and fail to start if none or multiple of them are found.
* The details of remaining classes will be discussed in later sections.

## Create and Setup Springboot project
Create a Springboot project and add the following dependencies in `pom.xml` file.
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
		
<dependency>
  <groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-cassandra</artifactId>
</dependency>
		
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <scope>provided</scope>
</dependency>
```
## Configure Spring Data Cassandra
Open`application.properties` under src/main/resources folder and add the following lines.
```properties
spring.data.cassandra.keyspace-name= customer
spring.data.cassandra.contact-points= 127.0.0.1
spring.data.cassandra.port= 9042
spring.data.cassandra.local-datacenter= datacenter1
```

The attributes mentioned above are used by Spring Data to connect to the correct Cassandra cluster.
## Create Data Model
- The model class represents the table that we will store in Cassandra DB and perform CRUD operations.

- Create a [`Customer`](https://github.com/VamsiChelluri/Springboot-with-CassandraDB/blob/master/src/main/java/com/cassandra/entity/Customer.java) class within the sub-package or in the same package of main class.

## Create Repository Interface
- In order to support DB operations, we will create a Repositroy interface which will extend CassandraRepository.

- Create a [`CustomerRepository`](https://github.com/VamsiChelluri/Springboot-with-CassandraDB/blob/master/src/main/java/com/cassandra/repository/CustomerRepository.java) class within the sub-package or in the same package of main class.

   - Here [*@AllowFiltering*](https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/) annotation allows server-side filtering for `findByEmail()` method.
## Create Spring Rest APIs Controller
- We will create a Rest Controller to expose 6 endpoints for basic CRUD operarions.

- Create a [`CustomerController`](https://github.com/VamsiChelluri/Springboot-with-CassandraDB/blob/master/src/main/java/com/cassandra/controller/CustomerController.java) class within the sub-package or in the same package of main class.

- **CRUD Operations**

   - `@PostMapping("/register-customer")` - To Register the Customer.

   - `@GetMapping("/get-all-customers")` - To Retrieve all Customer details.

   - `@GetMapping("/get-by-id/{id}")` - To Retrieve Customer details based on Id.

   - `@GetMapping("/get-by-email/{email}")` - To Retrieve Customer details by Email Id.

   - `@PutMapping("/update-by-id/{id}")` - To Update the Customer details based on Id.

   - `@DeleteMapping("/delete-by-id/{id}")` - To Delete the Customer details based on Id.

## Set up Cassandra Database
- Open a command prompt (‘cmd’) window and navigate to the bin directory of your Cassandra installation (e.g. ‘C:\apache-cassandra-3.11.12\bin’). Now type `cassandra` to run it. Now observe the message **‘Starting Cassandra Server’** at the start or **‘startup complete’** at the bottom lines.
    - ```cmd
      C:\apache-cassandra-3.11.12\bin>cassandra
      ```

- **Create Keyspace and table in Cassandra DB**
   - In order to work with Apache Cassandra, we have to create a keyspace.

   - Open a new commadn prompt in the same bin folder and type `cqlsh`:
   
     -  `C:\apache-cassandra-3.11.12\bin>cqlsh`
   - Now create a keyspace by providing below command. The name of keyspace must match the keyspace-name that we specified in the properties file.
     -  ```cmd
        create keyspace customer with replication={'class':'SimpleStrategy', 'replication_factor':1};
        ```
   - We can use the created keyspace using below command.
     - ```cmd
       cqlsh> use customer;
       ```
- **Now create **_customer_** table in the keyspace**

```SQL
CREATE TABLE customer(
   id timeuuid PRIMARY KEY,
   name text,
   email text,
   address text
);
```

## Run and Test the application
Now run the Springboot application and we will use Postman to test the APIs that we created.

##### 1. Register Customer
![image](https://user-images.githubusercontent.com/65802767/183452194-c9bf1ce1-b4ca-4422-9e7f-87c438a8ca39.png "Post method")
* Check Cassandra databasse for results

![image](https://user-images.githubusercontent.com/65802767/183453838-c6be4663-e479-4747-a375-b643d6176453.png "Result in DB")

--Similarly we can register for other customers.


##### 2. Get all Customer details
![image](https://user-images.githubusercontent.com/65802767/183454610-1c4d6a0e-8070-41c7-bc5d-759eeae1583a.png "Retrieving all details")

##### 3. Get Customer details by ID
![image](https://user-images.githubusercontent.com/65802767/183454794-41a7f554-7602-49c7-bd9f-0b6dcde91d3d.png "Get by Id")

##### 4. Get Customer details by Email
![image](https://user-images.githubusercontent.com/65802767/183455136-5dbe2fcd-4ebe-44f1-a84d-5c8569718970.png "Get by Email")

##### 5. Update Customer details by ID
![image](https://user-images.githubusercontent.com/65802767/183455987-3cae7915-fcbf-466f-b016-b4c5c3c03b0c.png "Update details by ID")

* Check database for results
![image](https://user-images.githubusercontent.com/65802767/183456181-39775995-1509-4496-b6fd-3914667da05b.png "DB output")

##### 6. Delete Customer details by ID
![image](https://user-images.githubusercontent.com/65802767/183456330-fcc9591e-6463-428b-b994-a2abeb076f75.png "Delete method")

* Check database for results
![image](https://user-images.githubusercontent.com/65802767/183456478-b9caf2fd-cbf2-47e7-a7fc-ccbad0c673fe.png "DB output")






