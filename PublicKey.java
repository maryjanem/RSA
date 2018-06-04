package rsa;

public class PublicKey {
	public int e;
	public int n;
	
	/**
	 * Used by Sender
	 * @param e
	 * @param n
	 */
	public PublicKey(int e, int n){
		this.e = e;
		this.n = n;
	}
}
