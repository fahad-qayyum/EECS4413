/**
 * 
 */
package service;

/**
 * @author fq
 *
 */
public class User {

	/**
	 * 
	 */
	private String hash;
	private int count;
	private String salt;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	

	/**
	 * @param args
	 */
	
}
