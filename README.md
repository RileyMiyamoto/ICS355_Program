# ICS355_Program
Hosting for ICS355 Programing assignment


Riley Miyamoto


ICS 355


11/18/17


##Program 2 Documentation


	Program 2 is a financial program that allows a user to withdraw or deposit money into a bank account. It allows for multiple user usage. An admin account can log in to allow for adding and deleting accounts. Users are able to login to withdraw, deposit, and transfer money. Installing the program is easy. There are 5 files that are needed. ICS355_Program.java, Account.java, Users.java, users.csv, and test.csv. The ICS355_Program.java file is the file that contains the main method and does most of the work. Account.java is an object class used to create the account object that stores the information from the CSV file. Users.java is an object class used to create the user accounts. Test.csv is the file that contains the information on the bank accounts. Users.csv is the file that contains the username, salt, and the hashed value of the password. All that you have to do to make the program run is have all three files in the same folder or path. After that all you do is compile the program and then run it. This program does not depend on any dependencies. 
	As for example runs, everything seems to work fine. Users are given 3 tries to login, if they are unable, the program terminates. Admins are able to login with the username “admin” and password “password”. When they enter into admin mode they are given 2 options. When adding a user information for the account is needed (i.e. username, first name, last name, etc.) The reason for this is because an account is created with the user. When a user logs in, they are given 3 options. If they input a value that the program does not like it will give an error message and terminate the program. The prompt from start to finish is, login screen. If admin logs in it’s either add or delete user. If  a user logs in and wants to withdraw, deposit, or transfer money. After that it asks the user what form of money they are depositing (i.e. USD, YEN, UK). When the user enters that it will convert the money being stored in the account to the appropriate amount (not sure if this is what is suppose to happen, but if it’s different, changes can easily be done. I also assumed that money being stored is in USD). The user is then asked to enter the amount they want to withdraw or subtract. The money is dealt with respectively and the new amount is written to the csv file. If transfer is chosen, the user is asked to input the account number of the corresponding account. After all this the program is terminated. 
	After each execution, if everything previous was a success then the new values will be written to the file. Every time the program is ran, it will show the value that was set from the previous execution. 
