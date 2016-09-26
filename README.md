SpringBoot tutorial practical exercise
Build a SpringBoot application.

Requirements.

1.  You must used either Gradle or Maven to do the build
2.  The applicaiton is responsible for creating, updating, and reviewing a customer.  You need to have a RESTful end point for:

/customers - POST
/customers - PUT
/customers/ - GET
/customers/<id> - GET
/customers/email/<email> - GET

3.  The customer should have a first name, last name, email, username, and password
4.  Store the customer in a static hash map or an in-memory database.
5.  Develop with TDD in mind.  You should write the test cases for the application before you code it.
6.  The application should have a mimimum of 3 layers.  A controller, service, and DAO.

Application available at Pivotal Cloud Foundry at
http://kaushikbekal-springboot.cfapps.io

Operations available:
GET http://kaushikbekal-springboot.cfapps.io/customers
GET http://kaushikbekal-springboot.cfapps.io/customers/1
GET http://kaushikbekal-springboot.cfapps.io/customers/2
POST http://kaushikbekal-springboot.cfapps.io/customers with json body as follows:
{"firstName":"Kaushik3", "lastName":"Bekal3","email":"kaushik.bekal1@gmail.com","username":"bekalk23","password":"abcdefg"}

PUT http://kaushikbekal-springboot.cfapps.io/customers/1 with json body as follows:
{"firstName":"Kaushik3", "lastName":"Bekal3","email":"kaushik.bekal1@gmail.com","username":"bekalk23","password":"abcdefg"}
If customer with id 1 exists, the PUT request will update that customer's data with the one provided in json body. Otherwise a new customer is created with the same data.

GET http://kaushikbekal-springboot.cfapps.io/customers/email/kaushik.bekal1@gmail.com

