import java.math.BigInteger;

public class Lesson1_2 {
	
	/**
    * @Description:	向左移位
    * @param num-等待移位的十进制数, m-向左移的位数
    * @return int-移位后的十进制数
    */
	public static int leftShift(int num, int m) {
		System.out.println(decimalToBinary(num << m));
		return num << m;
	}
	
	/**
    * @Description:	向右移位
    * @param num-等待移位的十进制数, m-向右移的位数
    * @return int-移位后的十进制数
    */
	
	 public static String decimalToBinary(int decimalSource) {
	    	BigInteger bi = new BigInteger(String.valueOf(decimalSource));	//转换成BigInteger类型，默认是十进制
	    	return bi.toString(2);	//参数2指定的是转化成二进制
	    }
	
	public static int binaryToDecimal(String binarySource) {
    	BigInteger bi = new BigInteger(binarySource, 2);	//转换为BigInteger类型，参数2指定的是二进制
    	return Integer.parseInt(bi.toString());		//默认转换成十进制
    }
	
	public static int rightShift(int num, int m) {
		System.out.println(decimalToBinary(num >>> m));
		return num >>> m;
	}
	
	public static int rightShift2(int num, int m) {
		System.out.println(decimalToBinary(num >> m));
		return num >> m;
	}
	
	
	public static void main(String[] args) {
		
		int num = -53;
		int m = 1;
		System.out.println(String.format("数字%d的二进制向左移%d位是%d", num, m, Lesson1_2.leftShift(num, m)));	//测试向左移位
		
		System.out.println(String.format("数字%d的二进制向右移%d位是%d", num, m, Lesson1_2.rightShift(num, m)));	//测试向右移位
		System.out.println(String.format("数字%d的二进制向右移2 %d位是%d", num, m, Lesson1_2.rightShift2(num, m)));	//测试向右移位
		
		System.out.println(Integer.MAX_VALUE);
		
		System.out.println();
		
		m = 3;
		System.out.println(String.format("数字%d的二进制向左移%d位是%d", num, m, Lesson1_2.leftShift(num, m)));	//测试向左移位
		System.out.println(String.format("数字%d的二进制向右移%d位是%d", num, m, Lesson1_2.rightShift(num, m)));	//测试向右移位
		
		System.out.println(decimalToBinary(1 << 31));
		System.out.println(1 << 31);
		System.out.println(1 << 30);
		System.out.println((1 << 31) - 1);
		
		System.out.println(64 >> 1);
		System.out.println(-64 >> 1);
		System.out.println(-64 >>> 1);
		System.out.println(Integer.MAX_VALUE);

	}

}
