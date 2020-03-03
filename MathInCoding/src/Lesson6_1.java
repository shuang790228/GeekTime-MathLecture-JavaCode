import java.util.Arrays;

public class Lesson6_1 {
	
	/**
    * @Description:	使用函数的递归（嵌套）调用，实现归并排序（从小到大）
    * @param to_sort-等待排序的数组
    * @return int[]-排序后的数组
    */
	
	public static int[] merge_sort(int[] to_sort) {
		
		if (to_sort == null) return new int[0];
		
		// 如果分解到只剩一个数，返回该数
		if (to_sort.length == 1) return to_sort;
		
		// 将数组分解成左右两半
		int mid = to_sort.length / 2;
		int[] left = Arrays.copyOfRange(to_sort, 0, mid);
		int[] right = Arrays.copyOfRange(to_sort, mid, to_sort.length);
		
		// 嵌套调用，对两半分别进行排序
		left = merge_sort(left);
		right = merge_sort(right);
		
		// 合并排序后的两半
		int[] merged = merge(left, right);
		
		return merged;
		
	}
   
	
	/**
    * @Description:	合并两个已经排序完毕的数组（从小到大）
    * @param a-第一个数组，b-第二个数组
    * @return int[]-合并后的数组
    */
    
    public static int[] merge(int[] a, int[] b) {
    	
    	if (a == null)  a = new int[0];
    	if (b == null) b = new int[0];
    	
    	int[] merged_one = new int[a.length + b.length];
    	
    	int mi = 0, ai = 0, bi = 0;
    	
    	// 轮流从两个数组中取出较小的值，放入合并后的数组中
    	while (ai < a.length && bi < b.length) {
    		
    		if (a[ai] <= b[bi]) {
    			merged_one[mi] = a[ai];
    			ai ++;
    		} else {
    			merged_one[mi] = b[bi];
    			bi ++;
    		}
    		
    		mi ++;
    		
    	}
    	
    	// 将某个数组内剩余的数字放入合并后的数组中
    	if (ai < a.length) {
    		for (int i = ai; i < a.length; i++) {
    			merged_one[mi] = a[i];
    			mi ++;
    		}
    	} else {
    		for (int i = bi; i < b.length; i++) {
    			merged_one[mi] = b[i];
    			mi ++;
    		}
    	}
    	
    	return merged_one;
    	
    }

	public static void main(String[] args) {
		
		int[] to_sort = {3434, 3356, 67, 12334, 878667, 387};
		int[] sorted = Lesson6_1.merge_sort(to_sort);
		
		for (int i = 0; i < sorted.length; i++) {
			System.out.println(sorted[i]);
		}
	}

}
