public class Lesson10_1 {
	
	/**
    * @Description:	使用状态转移方程，计算两个字符串之间的编辑距离
    * @param a-第一个字符串，b-第二个字符串
    * @return int-两者之间的编辑距离
    */
	
	public static int getStrDistance(String a, String b) {
		
		if (a == null || b == null) return -1;
		
		// 初始用于记录化状态转移的二维表
		int[][] d = new int[a.length() + 1][b.length() + 1];
		
		// 如果i为0，且j大于等于0，那么d[i, j]为j
		for (int j = 0; j <= b.length(); j++) {
			d[0][j] = j;
		}
		
		// 如果i大于等于0，且j为0，那么d[i, j]为i
		for (int i = 0; i <= a.length(); i++) {
			d[i][0] = i;
		}
		
		// 实现状态转移方程
		// 请注意由于Java语言实现的关系，代码里的状态转移是从d[i, j]到d[i+1, j+1]，而不是从d[i-1, j-1]到d[i, j]。本质上是一样的。
		for (int i = 0; i < a.length(); i++) {
			for (int j = 0; j < b.length(); j++) {
				
				int r = 0;
				if (a.charAt(i) != b.charAt(j)) {
					r = 1;
				} 
				
				int first_append = d[i][j + 1] + 1;
				int second_append = d[i + 1][j] + 1;
				int replace = d[i][j] + r;
				
				int min = Math.min(first_append, second_append);
				min = Math.min(min, replace);
				d[i + 1][j + 1] = min;
				
			}
		}
		
		return d[a.length()][b.length()];
				
	}
	
	public static int getStrDistanceByThreshold(String a, String b, int threshold) {
		
		if (a == null || b == null) return -1;
		
		int minStrDist = Integer.MAX_VALUE;
		
		// 初始用于记录化状态转移的二维表
		int[][] d = new int[a.length() + 1][b.length() + 1];
		
		// 如果i为0，且j大于等于0，那么d[i, j]为j
		for (int j = 0; j <= b.length(); j++) {
			d[0][j] = j;
		}
		
		// 如果i大于等于0，且j为0，那么d[i, j]为i
		for (int i = 0; i <= a.length(); i++) {
			d[i][0] = i;
		}
		
		// 实现状态转移方程
		// 请注意由于Java语言实现的关系，代码里的状态转移是从d[i, j]到d[i+1, j+1]，而不是从d[i-1, j-1]到d[i, j]。本质上是一样的。
		for (int i = 0; i < a.length(); i++) {
			for (int j = 0; j < b.length(); j++) {
				
				int r = 0;
				if (a.charAt(i) != b.charAt(j)) {
					r = 1;
				} 
				
				int first_append = d[i][j + 1] + 1;
				int second_append = d[i + 1][j] + 1;
				int replace = d[i][j] + r;
				
				int min = Math.min(first_append, second_append);
				min = Math.min(min, replace);
				d[i + 1][j + 1] = min;
				
				if (min < minStrDist) minStrDist = min;
				if (minStrDist > threshold) return -1;
				
			}
		}
		
		return d[a.length()][b.length()];
				
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Lesson10_1.getStrDistance("mouse", "mouuse"));
		System.out.println(Lesson10_1.getStrDistanceByThreshold("mouse", "mouuse", 0));

	}

}