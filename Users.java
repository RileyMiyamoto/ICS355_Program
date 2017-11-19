class Users {
	public String username, salt, hash;
	public Users(String myUsername, String mySalt, String myHash){
		username = myUsername;
		salt = mySalt;
		hash = myHash;
	}
	public String getUsername(){
		return this.username;
	}
	public void setUsername(String newUsername){
		this.username = newUsername;
	}
	public String getSalt(){
		return this.salt;
	}
	public void setSalt(String newSalt){
		this.salt = newSalt;
	}
	public String getHash(){
		return this.hash;
	}
	public void setHash(String newHash){
		this.hash = newHash;
	}
	public String toString(){
		String output = getUsername() + "," + getSalt() + "," + getHash() + "\n";
		return output;
	}
}

