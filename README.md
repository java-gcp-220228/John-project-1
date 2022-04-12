# PROJECT NAME

## Project Description

The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used

* Javalin
* JDBC
* Angular
* Postgresql

## Features

List of features ready and TODOs for future development
* Stateless authentication using JWTs
* Auto-login functionality reuses valid JWT
* State managment in Redux pattern using NGRX
* Filter by status functionality minimizes requests sent from frontend

To-do list:
* Finish the UserManager module with Create, Delete and Update User functionality
* Allow for employees to change password

## Getting Started
   
- `git clone https://github.com/java-gcp-220228/John-project-1.git`
- Have a Postgres server running at `localhost:5432`
- Set enviroment variables `POSTGRES_HOST=localhost`, `POSTGRES_PORT=5432`, `p1_url=jdbc:postgresql://localhost:5432/postgres?currentSchema=p1ers`, and `p1_username` + `p1_password` to appropriate postgres credentials
- Install python from the Windows Store on Windows or on Ubuntu: `sudo apt-get install python3.6`
- Change working directory to project root
- Run the seedDB script with `python3 seedDB.py`
- Build the jar file with `./gradlew build`

## Usage

- Run the jar file with `java -jar ./app/build/libs/app-1.1.jar`
- Navigate to `http://localhost:8080/` to launch app
- ![Login Image](/images/login.jpg)
- Log in as admin user with `admin:admin`
- ![Login Box](/images/login2.jpg)
- Navigate to UserManager module and copy an Employee username
- ![Manager Image](/images/manager.jpg)
- In a seperate browser session, launch app and log in as employee user with `${username}:default`
- Create a new reimbursement ticket with an optional image
- ![New Ticket 1](/images/newticket1.jpg)
- ![New Ticket 2](/images/newticket2.jpg)
- In admin session, navigate to Dashboard in order to view tickets and filter by status
- Approve or Deny pending tickets
- ![Dashboard](/images/dashboard.jpg)

## License

This project uses the following license: [MIT](LICENSE).