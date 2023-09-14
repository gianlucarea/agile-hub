# agile-hub
University Project for the course of Agile Developing Methodologies at the University of L'Aquila for the Master's Degree in Advanced Software Engineering.

## Description
The project consists of an application made for a sporting centre. The centre can have different sports and kinds of pitch which user can book for themselves and their friends. The user can also book a sports teacher. During the course development, we divided the tasks based on spring and created working deliverables.

## Getting Started

### Dependencies

- Maven
- JDK 17
- SQL Server

### Execution
1. Import the project as a Maven project
2. Import the MySQL database in the server. You can use the file Testing_DB.sql that can be found under resources/
3. Go to the utility.java class under the utility package and insert your database root username and password.
4. You can run the application and try it out.

### Testing
1. Import the Test MySQL database in the server. You can use the file Production\_DB.sql that can be found under SQL\_FILES/
2. Go to the DaoFactory.java class under the dao package and insert your database root username and password inside the readScript() method.
3. You can run the now run the test.

## Authors
- Gianluca Rea - [gianlucarea](https://gianlucarea.github.io)
- Francesco Falone - [FrancescoFalone01](https://github.com/FrancescoFalone01)
- Antonio Devastatore Ranaldi -  [Odradek9](https://github.com/Odradek9)
- Usama Labzouzi - [usama-lb](https://github.com/usama-lb)

## Version History

- Final
    * The project was delivered as the final exam of the course

## License

This project is licensed under the MIT License - see the LICENSE.md file for details
