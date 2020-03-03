public class Lesson1_append1 {
	
	public static void main(String[] args) {
		
		int even_cnt = 0, odd_cnt = 0;
		long start = 0, end = 0;
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			
			if((i & 1) == 0){
			    even_cnt ++;
			}else{
			    odd_cnt ++;
			}
			
		}
		end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println(even_cnt + " " + odd_cnt);
		
		even_cnt = 0;
		odd_cnt = 0;
		start = 0;
		end = 0;
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			
			if((i % 2) == 0){
			    even_cnt ++;
			}else{
			    odd_cnt ++;
			}
			
		}
		end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println(even_cnt + " " + odd_cnt);
		
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			int x = 3, y = 5;
			x = (x ^ y);
			y = x ^ y;
			x = x ^ y;
		}
		end = System.currentTimeMillis();
		System.out.println(end - start);
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			int x = 3, y = 5, t = 0;
			t = y;
			y = x;
			x = t;
		}
		end = System.currentTimeMillis();
		System.out.println(end - start);
		
//		System.out.println(x + " " + y);

	}

}
