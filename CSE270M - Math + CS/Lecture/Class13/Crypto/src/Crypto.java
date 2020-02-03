// File to accompany the Crypto lab
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
public class Crypto {
  
  // Ciphertext from problem 4: PCPANHXH XH IWT PGI DU IPBXCV XCUXCXIN
  // Ciphertext from problem 8: ER UASZA PU JRSZALPHRS AZKJALEUR URZ VJLP XEALP JRSZALPHRS AZKJALEUR
  
  public static void main(String[] args) { 
	  String shift = "PCPANHXH XH IWT PGI DU IPBXCV XCUXCXIN";
	  for(int i = 0; i < 26; i++) {
		  String unshifted = decryptShift(shift, i);
		  System.out.println(unshifted);
	  }
	  String sub = "CATZ";
	  String flip = encryptSub(sub, "BAQCDEFGHIJKLMNOPRSTUVWXYZ");
	  System.out.println(flip);
	  String other = decryptSub(flip,"BAQCDEFGHIJKLMNOPRSTUVWXYZ");
	  System.out.println(other);
	  String needsDecrypted = "ER UASZA PU JRSZALPHRS AZKJALEUR URZ VJLP XEALP JRSZALPHRS AZKJALEUR";
	  System.out.println(bruteForceDecryptSub(needsDecrypted));
  }
  
  // Encrypts using shift cipher and a key ranging from
  // 0 through 25 inclusive.  Returns the encrypted text.
  public static String encryptShift(String plainText, int key) {
	char initialPosition = 'A';
	String newString = "";
	for(int i = 0; i < plainText.length(); i++) {
		if(plainText.charAt(i) == ' ') {
    		newString += " ";
    	}
		else {
			newString += (char)(initialPosition + (((int) plainText.charAt(i) % (int)(initialPosition) + key) % 26));
		}
	}
    return newString;
    
  }
  
  // Decrypts using shift cipher and a key ranging from
  // 0 through 25 inclusive.  Returns the original plain text.
  public static String decryptShift(String cipherText, int key) {
	return encryptShift(cipherText, 26 - key);
  } 
  
  // Encrypts using substitution cipher and a key consisting of a
  // 26-character String.  Returns the encrypted text.
  public static String encryptSub(String plainText, String key) {
	String encryptString = "";
    for(int i = 0; i < plainText.length(); i++) {
    	if(plainText.charAt(i) == ' ') {
    		encryptString += " ";
    	}
    	else {
    		encryptString += key.charAt(plainText.charAt(i) % 'A');
    	}
    }
    return encryptString;
    
  }
  
  // Decrypts using shift cipher and a key consisting of a
  // 26-character String.  Returns the original plain text.
  public static String decryptSub(String cipherText, String key) {
	  String decryptString = "";
	    for(int i = 0; i < cipherText.length(); i++) {
	    	if(cipherText.charAt(i) == ' ') {
	    		decryptString += " ";
	    	}  	
	    	else {
	    		decryptString += (char)('A' + key.indexOf(cipherText.charAt(i)));
	    	}
	    }
	    return decryptString;
  }
  
  // keys.txt contains one million different 26-character keys for substitution ciphers
  // words.txt contains all the legal words in Scrabble (all in lowercase...be careful).
  // One of the keys in keys.txt was used to encrypt some plain text.  The result is:
  // ER UASZA PU JRSZALPHRS AZKJALEUR URZ VJLP XEALP JRSZALPHRS AZKJALEUR
  // Use brute force to find the original text.  
  public static String bruteForceDecryptSub(String cipherText) {
	Scanner sc = new Scanner("bull.txt");
	try {
		sc = new Scanner(new File("words.txt"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HashSet<String> fullDict = new HashSet<String>();
	while(sc.hasNext()) {
		String word = sc.next();
		fullDict.add(word.toUpperCase());
	}
	try {
		sc = new Scanner(new File("keys.txt"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	boolean foundIt = false;
	while(sc.hasNext()) {
		String currentHash = sc.nextLine();
		String decryptTry = decryptSub(cipherText, currentHash);
		String[] wordsSplit = decryptTry.split(" ");
		for(int i = 0; i < wordsSplit.length; i++) {
			if(!fullDict.contains(wordsSplit[i])) {
				foundIt = false;
				break;
			}
			foundIt = true;
		}
		if(foundIt) {
			return decryptTry;
		}
		
	}
	return "All Invalid";

  }

  
}
  
  