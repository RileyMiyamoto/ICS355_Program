/********************************
*Riley Miyamoto
*ICS 355
*
*Financial program that allows a  user to 
*edit the balance of their account.
*
********************************/

import java.util.Scanner;
import java.util.Currency;
import java.util.Locale;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.lang.NullPointerException;

public class ICS355_Program {
	public static void main(String[] args) throws FileNotFoundException{
		Scanner scan = new Scanner(System.in);
		Account account = null;
		Account[] multiAccount = new Account[5];
		Users[] multiUsers = new Users[5];
		//Locale is used for the symbol of each currency
		Locale localeUS = Locale.US;
		Locale localeYen = new Locale("ja", "JP");
		Locale localeUK = Locale.UK;
		Currency currency = null;
		String csvFile = "test.csv", userFile = "users.csv", line = "", symbol = "", splitBy = ",", username = "", password = "", salt = "";
		int choice, currType,tries = 0;
		double conversion, addBalance, subBalance;
		byte[] hashBytes = new byte[1024];
		//reads in csv file that has information about the account
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			int counter = 0;
			while ((line = br.readLine()) != null) {
				String[] input = line.split(splitBy);
				multiAccount[counter] = new Account(Integer.parseInt(input[0]), input[1], input[2], input[3], Double.parseDouble(input[4]));
				counter++;
			}	
		}catch (FileNotFoundException e){
			System.out.println("File not found");
		}catch (IOException e){
		
		}
		try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
			int counter = 0;
			while ((line = br.readLine()) != null) {
				String[] input = line.split(splitBy);
				multiUsers[counter] = new Users(input[0],input[1],input[2]);
				counter++;
			}	
		}catch (FileNotFoundException e){
			System.out.println("File not found");
		}catch (IOException e){
		
		}
		try {
			while (tries < 3){
			System.out.println("Please Login");
			System.out.print("Username: ");
			username = scan.nextLine();
			System.out.print("\nPassword: ");
			password = scan.nextLine();
			if(loginFunction(username, password)) {
				if(username.equals("admin")){
					System.out.println("\n1. Add user\n2. Delete user");
					System.out.print("Enter Option: ");
					choice = scan.nextInt();
					scan.nextLine();
					switch(choice){
					case 2:		System.out.print("\nEnter username: ");
							username = scan.nextLine();
							try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
								int counter = 0;
								while((line = br.readLine()) != null) {
									String[] input = line.split(splitBy);
										multiUsers[counter] = new Users(input[0],input[1],input[2]);
										counter++;
								}
								
								counter--;
								PrintWriter pw = new PrintWriter(userFile);
								pw.write("");
								pw.close();
								while(counter >=0) {
									if(!(multiUsers[counter].getUsername().equals(username))) {
										FileWriter fw = new FileWriter(userFile, true);
										StringBuilder sb = new StringBuilder();
										sb.append(multiUsers[counter].getUsername() + ",");
										sb.append(multiUsers[counter].getSalt() + ',');
										sb.append(multiUsers[counter].getHash());
										sb.append('\n');
										fw.write(sb.toString());
										fw.close();
									}
									counter--;
								}
							}catch (IOException e){
								System.out.println("Error");
							}
							try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
								int counter = 0;
								while((line = br.readLine()) != null) {
									String[] input = line.split(splitBy);
										multiAccount[counter] = new Account(Integer.parseInt(input[0]),input[1],input[2],input[3],Double.parseDouble(input[4]));
										counter++;
								}
								
								counter--;
								PrintWriter pw = new PrintWriter(csvFile);
								pw.write("");
								pw.close();
								while(counter >=0) {
									if(!(multiAccount[counter].getUsername().equals(username))) {
										FileWriter fw = new FileWriter(csvFile, true);
										StringBuilder sb = new StringBuilder();
										sb.append(multiAccount[counter].getAccNum() + ",");
										sb.append(multiAccount[counter].getUsername() + ',');
										sb.append(multiAccount[counter].getFName() + ",");
										sb.append(multiAccount[counter].getLName() + ',');
										sb.append(multiAccount[counter].getBalance());
										sb.append('\n');
										fw.write(sb.toString());
										fw.close();
									}
									counter--;
								}
							}catch (IOException e){
								System.out.println("Error");
							}						
							break;
					
					case 1:		System.out.print("\nEnter username: ");
							username = scan.nextLine();
							System.out.print("\nEnter password: ");
							password = scan.nextLine();
							salt = generateSalt();
							MessageDigest hashFunc = MessageDigest.getInstance("MD5");
							hashFunc.update((salt + password).getBytes());
							hashBytes = hashFunc.digest();
							StringBuffer buffer = new StringBuffer();
							for(int i = 0; i < hashBytes.length; i++){
								buffer.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1)); 
							}
							Users user = new Users(username, salt, buffer.toString());
							System.out.println();
							try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
									while ((line = br.readLine()) != null) {
									
									String[] input = line.split(splitBy);
									FileWriter fw = new FileWriter(userFile, true);
									StringBuilder sb = new StringBuilder();
									sb.append(user.getUsername() + ",");
									sb.append(user.getSalt() + ',');
									sb.append(user.getHash());
									sb.append('\n');
					
									fw.write(sb.toString());
									fw.close();
									break;
									
									}
							}catch (FileNotFoundException e){
								System.out.println("File not found");
							}catch (IOException e){
							System.out.println("Error: Wrong input");
							}
							System.out.println("Enter Account Number: ");
							int accNum = scan.nextInt();
							scan.nextLine();
							System.out.println("First Name: ");
							String fName = scan.nextLine();
							System.out.println("Last Name: ");
							String lName = scan.nextLine();
							System.out.println("Amount: ");
							double amount = scan.nextDouble();
							try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
									while ((line = br.readLine()) != null) {
									
									String[] input = line.split(splitBy);
									FileWriter fw = new FileWriter(csvFile, true);
									StringBuilder sb = new StringBuilder();
									sb.append(accNum + ",");
									sb.append(user.getUsername() + ",");
									sb.append(fName + ",");
									sb.append(lName + ',');
									sb.append(amount);
									sb.append('\n');
					
									fw.write(sb.toString());
									fw.close();
									break;
									
									}
							}catch (FileNotFoundException e){
								System.out.println("File not found");
							}catch (IOException e){
							System.out.println("Error: Wrong input");
							}
							break;
					}
					System.exit(0);
				}else if(loginFunction(username, password)){;
						for( int i = 0;i < multiUsers.length; i++) {
							if(multiAccount[i].getUsername().equals(username)){
								account = new Account(multiAccount[i].getAccNum(), multiAccount[i].getUsername(), multiAccount[i].getFName(), 										              multiAccount[i].getLName(), multiAccount[i].getBalance());
								break;
							}
						}
						System.out.print("\n1: Withdraw\n2: Deposit\n3: Transfer\nEnter Option: ");
						choice = scan.nextInt();
							if (choice == 1) {
								System.out.print("\n1: USD\n2: YEN\n3: POUND\nEnter currency type: ");
								currType = scan.nextInt();
									if (currType == 1 || currType == 2 || currType == 3) {
										System.out.print("\nEnter conversion rate: ");
										conversion = scan.nextDouble();
										/**********************************
										*This acts as the withdraw function,
										*it allows a user to subtract money
										*from their account
										***********************************/
										switch(currType){
											case 3: System.out.print("Enter withdraw amount: ");
												subBalance = scan.nextDouble();	
												//does the conversion of the money in the account as well as
												//subtracting the amount inputted
												subBalance = account.getBalance() * conversion - subBalance;
												account.setBalance(subBalance);
												currency = Currency.getInstance(localeUK);
												symbol = currency.getSymbol(localeUK);
												System.out.println("\nNew Balance: " + symbol + subBalance);	 
											break;
											case 2: System.out.print("Enter withdraw amount: ");
												subBalance = scan.nextDouble();	
												subBalance = account.getBalance() * conversion - subBalance;
												account.setBalance(subBalance);
												currency = Currency.getInstance(localeYen);
												symbol = currency.getSymbol(localeYen);
												System.out.println("\nNew Balance: " + symbol + subBalance);	 
											break;
											case 1:System.out.print("Enter withdraw amount: ");
												subBalance = scan.nextDouble();	
												subBalance = account.getBalance() * conversion - subBalance;
												account.setBalance(subBalance);
												currency = Currency.getInstance(localeUS);
												symbol = currency.getSymbol(localeUS);
												System.out.println("\nNew Balance: " + symbol + subBalance);	 
											break;
											default: System.out.println("Please enter 1, 2 or 3");
										}
								} else {
									System.out.println("Please enter 1, 2 or 3");
							} 
					} else if (choice == 2) {
						System.out.print("\n1: USD\n2: YEN\n3: POUND\nEnter currency type: ");
						currType = scan.nextInt();
						if ( currType == 1 || currType == 2 || currType == 3) {
						System.out.print("\nEnter conversion rate: ");
						conversion = scan.nextDouble();
						/**********************************
						*This acts as the deposit function,
						*it allows a user to add money
						*to their account
						***********************************/
						switch(currType){
						case 3: System.out.print("Enter Deposit amount: ");
							addBalance = scan.nextDouble();	
							//does the conversion of the money in the account as well as
							//adding the amount inputted
							addBalance += account.getBalance() * conversion;
							account.setBalance(addBalance);
							currency = Currency.getInstance(localeUK);
							symbol = currency.getSymbol(localeUK);
							System.out.println("\nNew Balance: " + symbol + addBalance);	 
						break;
						case 2: System.out.print("Enter Deposit amount: ");
							addBalance = scan.nextDouble();	
							addBalance += account.getBalance() * conversion;
							account.setBalance(addBalance);
							currency = Currency.getInstance(localeUK);
							symbol = currency.getSymbol(localeUK);
							System.out.println("\nNew Balance: " + symbol + addBalance);	 
						break;
						case 1:System.out.print("Enter Deposit amount: ");
							addBalance = scan.nextDouble();	
							addBalance += account.getBalance() * conversion;
							account.setBalance(addBalance);
							currency = Currency.getInstance(localeUK);
							symbol = currency.getSymbol(localeUK);
							System.out.println("\nNew Balance: " + symbol + addBalance);	 
						break;
						default: System.out.println("Please enter 1, 2 or 3");
						}
					} else {
						System.out.println("Please enter 1, 2 or 3");
					}
				} else if (choice == 3) {
						System.out.print("Enter Account Number: ");
						int accNumb = scan.nextInt();
						System.out.print("\nEnter Transfer Amount: ");
						double transfer = scan.nextDouble();
						if(account.getBalance() > transfer) {
							for( int i = 0;i < multiUsers.length-1; i++) {
								if(multiAccount[i].getAccNum() == accNumb){
									multiAccount[i].setBalance(multiAccount[i].getBalance() + transfer);
									break;
								}	
							}
						}
						account.setBalance(account.getBalance() - transfer);
					} else {
					System.out.println("Please enter 1, 2 or 3");
				}
			} 
		}  else {
				System.out.println("Invalid username and/or password\n");
			}
			tries++;
		}
		} catch (NullPointerException e) {
			e.printStackTrace();	
		}
		catch (NoSuchAlgorithmException e){
			System.out.println("Error");
		} catch (InputMismatchException e) {
			System.out.println("Error: Wrong input");
		}
		
		for( int i = 0;i < multiUsers.length-1; i++) {
			if(multiAccount[i].getUsername().equals(username)){
				multiAccount[i].setAccNum(account.getAccNum());
				multiAccount[i].setUsername(account.getUsername());
				multiAccount[i].setFName(account.getFName());
				multiAccount[i].setLName(account.getLName());
				multiAccount[i].setBalance(account.getBalance());
				break;
			}
		}
		/*****************************
		*Takes the information that was
		*changed by the user and updates
		*it in the csv file
		******************************/
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			int counter = 0;
			PrintWriter pw = new PrintWriter(csvFile);
			pw.write("");
			pw.close();
			while(!(multiAccount[counter].getUsername() == null)) {
				FileWriter fw = new FileWriter(csvFile, true);
				StringBuilder sb = new StringBuilder();
				sb.append(multiAccount[counter].getAccNum() + ",");
				sb.append(multiAccount[counter].getUsername() + ',');
				sb.append(multiAccount[counter].getFName() + ",");
				sb.append(multiAccount[counter].getLName() + ',');
				sb.append(multiAccount[counter].getBalance());
				sb.append('\n');
				fw.write(sb.toString());
				fw.close();
				counter++;
			}
		}catch (FileNotFoundException e){
			System.out.println("File not found");
		}catch (IOException e){
			System.out.println("Error: Wrong input");
		}catch (NullPointerException e) {
			System.out.println("Done");
		}
	}

	public static boolean loginFunction(String username, String password){
		String userFile = "users.csv", line = "", splitBy = ",";
		Users user = null;
		boolean loginResults = false;
		try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
			while ((line = br.readLine()) != null) {
				String[] input = line.split(splitBy);
				user = new Users(input[0], input[1], input[2]);
				byte[] hashBytes = new byte[1024];
				MessageDigest hashFunc = MessageDigest.getInstance("MD5");
				hashFunc.update((user.getSalt() + password).getBytes());
				hashBytes = hashFunc.digest();
				StringBuffer buffer = new StringBuffer();
				for(int i = 0; i < hashBytes.length; i++){
					buffer.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1)); 
				}
				if(user.getUsername().equals(username) && user.getHash().equals(buffer.toString())){
					loginResults = true;
					break;
				} else{
					loginResults = false;
				}
			}	
		}catch (FileNotFoundException e){
			System.out.println("File not found");
		}catch (IOException e){
		
		}catch (NoSuchAlgorithmException e) {
			System.out.println("Error");
		}
		return loginResults;
	}
	public static String generateSalt(){
		String salt = "";
		char character;
		int randomNum;
		for(int begin = 0; begin < 6; begin++){
			randomNum = 32 + (int)(Math.random() * ((128 - 32) + 1));
			character = (char) randomNum;
			salt += character;
		}
		return salt;
	}
}
