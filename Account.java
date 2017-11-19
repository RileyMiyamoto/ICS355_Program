
class Account {
	public int accNum;
	public String fName, lName, username;
	public double balance;
	public Account(int myAccNum, String myUsername, String myFName, String myLName, double myBalance){
		accNum = myAccNum;
		username = myUsername;
		fName = myFName;
		lName = myLName;
		balance = myBalance;
	}
	public int getAccNum(){
		return this.accNum;
	}
	public void setAccNum(int newAccNum){
		this.accNum = newAccNum;
	}
	public String getUsername(){
		return this.username;
	}
	public void setUsername(String newUsername){
		this.username = newUsername;
	}
	public String getFName(){
		return this.fName;
	}
	public void setFName(String newFName){
		this.fName = newFName;
	}
	public String getLName(){
		return this.lName;
	}
	public void setLName(String newLName){
		this.lName = newLName;
	}
	public double getBalance(){
		return this.balance;
	}
	public void setBalance(double newBalance){
		this.balance = newBalance;
	}
	public String toString(){
		String output = getUsername() + " " + getFName() + " " + getLName() + " " + getBalance();
		return output;
	}
}
	
