package rsa;

import java.util.ArrayList;

/**
 * Generates two mathematically linked keys.
 * Public Key for use of the Sender
 * Private Key for use of the Receiver
 * 
 * Generated using Extended Euclid's Algorithm
 * 
 * @author Mary-Jane McBride
 *
 */
public class KeyGenerator {

	PrivateKey privatekey;
	PublicKey publickey;
	/**
	 * Returns the Private Key result of key generation
	 * @return PrivateKey
	 */
	public PrivateKey getPrivateKey(){
		return this.privatekey;
	}
	/**
	 * Returns the Public Key result of key generation
	 * @return
	 */
	public PublicKey getPublicKey(){
		return this.publickey;
	}
	
	/**
	 * Uses the Extended Euclid's Algorithm.
	 * Generates a Private Key and Public Key based on p = 41, q = 67, and e = 87
	 */
	public void generate(){
		//Key Generation
		int p = 41, q = 67; //two hard-coded prime numbers
		int n = p * q;
		int w = (p-1) * (q-1);
		int d = 83; //hard-coded number relatively prime number to w
		
		this.privatekey = new PrivateKey(d,n); //public key generation completed
		
		//Extended Euclid's algorithm
		int 	a = w, 
				b = d,
				v = a/b;
		ArrayList<Integer> 	x = new ArrayList<Integer>(), 
					y = new ArrayList<Integer>(),
					r = new ArrayList<Integer>();
		
		
		//Iteration 0
		int i = 0;
		x.add(1);
		y.add(0);
		r.add(a);
		i++;
		//Iteration 1
		x.add(0);
		y.add(1);
		r.add(b);
		i++;
		//Iteration 2
		 //iteration counter
		int e = y.get(i-1);
		v = r.get(i-2) / r.get(i-1);
		x.add(x.get(i-2)-v*x.get(i-1));
		y.add(y.get(i-2) - v*y.get(i-1));
		r.add(a*(x.get(i-2)-v*x.get(i-1))
				+b*(y.get(i-2)-v*y.get(i-1))); 
		i++;
		
		//Iterate until r == 0, then get the previous iteration's value of d
		while(r.get(i-1) > 0){
			e = y.get(i-1);
			v = r.get(i-2) / r.get(i-1);
			x.add(x.get(i-2)-v*x.get(i-1));
			y.add(y.get(i-2) - v*y.get(i-1));
			r.add(a*(x.get(i-2)-v*x.get(i-1))
					+b*(y.get(i-2) -(v*y.get(i-1))));
			i++;
		}
		
		//if number is negative, add w to keep positive
		if (e < 0){
			e = w+e;
		}
		this.publickey = new PublicKey(e,n); //private key generation completed
		
		//print values to console
		System.out.println("Value of public key: " + e + ", private key: " + d + ", modulus: " + n);
		System.out.println();
	}
}
