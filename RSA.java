package rsa;

import java.util.Arrays;


public class RSA {
	
	//Letter encoding to numbers...
	private char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
			'P','Q','R','S','T','U','V','W','X','Y','Z',' '};
	private String[] numbers = {"01","02","03","04","05","06","07","08","09","10","11","12",
			"13","14","15","16","17","18","19","20","21","22","23","24","25","26","27"};
	
	private KeyGenerator keygen;
	
	/**
	 * RSA Constructor - generates public and private keys
	 */
	public RSA(){
		keygen = new KeyGenerator();
		keygen.generate();
		keygen.getPrivateKey();
	}
	
	/**
	 * Pass an int array that was previously encrypted to 
	 * decrypt back into a String containing the original plaintext
	 * 
	 * @param int[] cryptotext
	 * @return String plaintext
	 */
	public String decrypt(int[] cryptotext){
		return this.decrypt(cryptotext, keygen.privatekey);
	}
	
	/**
	 * Pass a String containing only alphabet characters to encrypt and
	 * an int array containing the encrypted plaintext will be returned
	 * 
	 * @param String plaintext
	 * @return int[] cryptotext
	 */
	public int[] encrypt(String plaintext){
		return this.encrypt(plaintext.toUpperCase(), keygen.publickey);
	}
	
	/**
	 * Private Method:
	 * 
	 * Pass an int array that was previously encrypted to 
	 * decrypt back into a String containing the original plaintext
	 * 
	 * @param int[] cryptotext
	 * @param PrivateKey key
	 * @return String plaintext
	 */
	private String decrypt(int[] cryptotext, PrivateKey key) {
		//M = C^d (mod n)
		
		char[] eBinary = Integer.toBinaryString(key.d).toCharArray();
		
		int[] decryptedMessage = new int[cryptotext.length];
		int i = 0;
		for(int c: cryptotext){
			int m = 1;
			for(char bit: eBinary){
				m = (m*m) % key.n;
				if(bit == '1'){
					m = m*c % key.n;
				}
			}
			decryptedMessage[i] = m;
			i++;
		}
		
		System.out.print("Decrypted -> " + Arrays.toString(decryptedMessage));
		System.out.println();
		
		return convertABPairsToText(decryptedMessage);
	}
	
	/**
	 * Private Method:
	 * 
	 * Pass a String containing only alphabet characters to encrypt and
	 * an int array containing the encrypted plaintext will be returned
	 * 
	 * @param String plaintext
	 * @param PublicKey key
	 * @return int[] cryptotext
	 */
	private int[] encrypt(String plaintext, PublicKey key) {
		int[]AB = convertTextToABPairs(plaintext);
		//C = Me (mod n)
		
		char[] eBinary = Integer.toBinaryString(key.e).toCharArray();
		
		int[] encryptedMessage = new int[AB.length];
		int i = 0;
		for(int m: AB){
			int c = 1;
			for(char bit: eBinary){
				c = (c*c) % key.n;
				if(bit == '1'){
					c = c*m % key.n;
				}
			}
			encryptedMessage[i] = c;
			i++;
		}
		
		System.out.print(plaintext + " Encrypted -> " + Arrays.toString(encryptedMessage));
		System.out.println();
		
		return encryptedMessage;
	}
	
	/**
	 * Private Method:
	 * 
	 * Pass a String containing alphabet only text and it 
	 * will be processed into AB numerical pairs that will
	 * be returned as an int array
	 * 
	 * @param String text
	 * @return int[] ABPairs
	 */
	private int[] convertTextToABPairs(String text){
		int count = 0;
		String[] encoded = new String[text.length()];
		for(char c: text.toCharArray()){
			for(int i = 0; i < letters.length; i++){
				if(letters[i] == c){
					encoded[count] = numbers[i];
					count++;
					break;
				}
			}
		}
		count = 0;
		int size;
		if(encoded.length % 2 == 0){
			size = encoded.length/2;
		}
		else {
			size = (encoded.length/2) + 1;
		}
		int[] AB = new int[size];
		for(int i = 0; i < encoded.length; i+=2){
			if(i < encoded.length-1){
				AB[count] = Integer.parseInt(encoded[i] + encoded[i+1]);
			} else {
				AB[count] = Integer.parseInt(encoded[i]);
			}
			count++;
		}
		System.out.print(text + " Encoded -> " + Arrays.toString(AB));
		System.out.println();
		
		return AB;
	}
	
	/**
	 * Private Method:
	 * 
	 * Pass an int array that was the result of a String being 
	 * processed into AB numerical pairs and it will be returned
	 * as its original String text
	 * 
	 * @param int[] ABPairs
	 * @return String text
	 */
	private String convertABPairsToText(int[] AB){
		String text = "";
		for(int ab: AB){
			String substring = Integer.toString(ab);
			
			while(substring.length() < 4){
				substring = "0" + substring;
			}
			
			int count = 0;
			String a = substring.substring(0,2);
			String b = substring.substring(2);
			for(int i = 0; i < numbers.length; i++){
				if(numbers[i].equals(a)){
					text += letters[i];
					count++;
					break;
				}
			}
			count = 0;
			for(int i = 0; i < numbers.length; i++){
				if(numbers[i].equals(b)){
					text += letters[i];
					count++;
					break;
				}
			}
		}
		
		System.out.print(Arrays.toString(AB) + " Decoded -> " + text);
		System.out.println();
		
		return text;
	}
}
