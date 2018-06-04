package rsa;

public class PrivateKey {
	public int d;
	public int n;
	/**
	 * Used by Receiver
	 * @param d
	 * @param n
	 */
	public PrivateKey(int d, int n){
		this.d = d;
		this.n = n;
	}
}
