# Expense Reimbursement System JS
The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Employess may also update their email adresses and passwords. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

# Reimbursement Types
Employees must select the type of reimbursement as: LODGING, TRAVEL, FOOD, or OTHER.

# Technical Features
- The application employs the DAO design pattern, and properly separates code into the appropriate layers
- The back-end system uses JDBC to connect to an Oracle 12c EE database through Amazon Web Services Relational Database
- The application deploys onto Tomcat Server ( v9.0 )
- The middle tier uses Servlet technology for dynamic Web application development through the Servlet API
- The front-end view uses JavaScript to mimic a single page application that uses AJAX to call server-side components
- The back-end system uses Log4J to log errors and any information into a log file
- Authentication is handled through the use of Json Web Tokens ( JWT's )
- A list of dependencies used can be found in the pom.xml file

# Important!
- Connecting to an Amazon Web Service hosted RDS, the application requires an app.properties file to be created in the src/main/resources path. With this file, you will need to detail the driver, driver type with RDS endpoint provided by AWS, the username and password to the database
- Log4j requires a log4j.properties file to be configured in order to allow for logging functionality
- A .sql script is available in the src/main/resources folder that can be used to create the database that the application is using
