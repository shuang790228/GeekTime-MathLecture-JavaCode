class Result {
	public long wheatNum = 0;
	public long wheatTotalNum = 0;
}

public class Lesson4_2 {
	
	/**
    * @Description:	使用函数的递归（嵌套）调用，进行归纳法证明
    * @param k-放到第几格，result-保存当前格子的麦粒数和麦粒总数
    * @return boolean-放到第k格时是否成立
    */
	
    public static boolean prove(int k, Result result) {
    	
    	// 证明n = 1时，命题是否成立
    	if (k == 1) {
    		if ((Math.pow(2, 1) - 1) == 1) {
    			result.wheatNum = 1;
    			result.wheatTotalNum = 1;
    			return true;
    		} else return false;
    	}
    	// 如果n = (k-1)时命题成立，证明n = k时命题是否成立
    	else {
    		
    		boolean proveOfPreviousOne = prove(k - 1, result);
    		result.wheatNum *= 2;
    		result.wheatTotalNum += result.wheatNum;
    		boolean proveOfCurrentOne = false;
    		if (result.wheatTotalNum == (Math.pow(2, k) - 1)) proveOfCurrentOne = true; 
    		
    		if (proveOfPreviousOne && proveOfCurrentOne) return true;
    		else return false;
    		
    	}
    	
    }

	public static void main(String[] args) {
		
		int grid = 63;
		
		Result result = new Result();
		System.out.println(Lesson4_2.prove(grid, result));
		
	}

}
