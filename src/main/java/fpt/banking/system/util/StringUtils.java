package fpt.banking.system.util;

public class StringUtils {

	public static String reverseString(String s) {
		StringBuilder input1 = new StringBuilder(); 
		  
        // append a string into StringBuilder input1 
        input1.append(s); 
  
        // reverse StringBuilder input1 
        input1 = input1.reverse(); 
        
        return input1.toString();
	}
}
