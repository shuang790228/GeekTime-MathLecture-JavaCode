import java.math.BigInteger;

public class Lesson1_3 {
	
	/**
    * @Description:	十进制转换成二进制
    * @param decimalSource
    * @return String
    */
    public static String decimalToBinary(int decimalSource) {
    	BigInteger bi = new BigInteger(String.valueOf(decimalSource));	//转换成BigInteger类型，默认是十进制
    	return bi.toString(2);	//参数2指定的是转化成二进制
    }
	
	/**
    * @Description:	二进制按位“或”的操作
    * @param num1-第一个数字，num2-第二个数字
    * @return 二进制按位“或”的结果
    */
	public static int or(int num1, int num2) {
			
		return (num1 | num2);
		
	}
	
	/**
    * @Description:	二进制按位“与”的操作
    * @param num1-第一个数字，num2-第二个数字
    * @return 二进制按位“与”的结果
    */
	public static int and(int num1, int num2) {
		
		return (num1 & num2);
		
	}
	
	/**
    * @Description:	二进制按位“异或”的操作
    * @param num1-第一个数字，num2-第二个数字
    * @return 二进制按位“异或”的结果
    */
	public static int xor(int num1, int num2) {
		
		return (num1 ^ num2);
		
	}
	
	
	public static void main(String[] args) {
		
		int a = 53;
		int b = 35;
		
		System.out.println(String.format("数字%d(%s)和数字%d(%s)的按位‘或’结果是%d(%s)", 
				a, decimalToBinary(a), b, decimalToBinary(b), Lesson1_3.or(a, b), decimalToBinary(Lesson1_3.or(a, b))));	//获取十进制数53和35的按位“或”
		
		System.out.println(String.format("数字%d(%s)和数字%d(%s)的按位‘与’结果是%d(%s)", 
				a, decimalToBinary(a), b, decimalToBinary(b), Lesson1_3.and(a, b), decimalToBinary(Lesson1_3.and(a, b))));	//获取十进制数53和35的按位“与”
		
		System.out.println(String.format("数字%d(%s)和数字%d(%s)的按位‘异或’结果是%d(%s)", 
				a, decimalToBinary(a), a, decimalToBinary(a), Lesson1_3.xor(a, a), decimalToBinary(Lesson1_3.xor(a, a))));	//获取十进制数53和35的按位“异或”
		
	}

}
