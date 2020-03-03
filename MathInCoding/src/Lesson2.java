

import  java.util.*;

public class Lesson2 {
	
	public static int gcd(int m,int n)
	{
		 return n==0?m:gcd(n,m%n);
	}


	
	public static void main(String[] args) {
		
		System.out.println( (1) % 7 );
		System.out.println( (101) % 7 );
		
		System.out.println( (1 + 590127) % 7 );
		System.out.println( (101 + 590127) % 7 );
		System.out.println( (201 + 590127) % 7 );
//		System.out.println( (21 + 21 * 590127) % 100 );
		System.out.println( (2 + 590127) % 7 );
		System.out.println( (102 + 590127) % 7 );
//		System.out.println( (3 + 3 * 590127) % 100 );
		
		System.out.println( (1 + 0) % 100 );
		System.out.println( (21 + 0) % 100 );
		System.out.println( (2 + 0) % 100 );
		
		
		
		
//		System.out.println( (54 + 590127) % 53 );
//		System.out.println( (54 + 0) % 53 );
		
		System.out.println( (101 + 590127) % 14 );
		System.out.println( (101 + 0) % 14 );
		
		System.out.println( (201 + 590127) % 14 );
		System.out.println( (201 + 0) % 14 );
		
//		System.out.println(gcd(590128, 590226));

	}
}
