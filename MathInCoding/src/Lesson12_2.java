import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Stack;

public class Lesson12_2 {
	
	/**
    * @Description:	前缀树的结点
    * 
    */
	
	public class TreeNode {
		
		public char label;		// 结点的名称，在前缀树里是单个字母
		public HashMap<Character, TreeNode> sons = null;	// 使用哈希映射存放子结点。哈希便于确认是否已经添加过某个字母对应的结点。
		public String prefix = null;			// 从树的根到当前结点这条通路上，全部字母所组成的前缀。例如通路b->o->y，对于字母o结点而言，前缀是b；对于字母y结点而言，前缀是bo
		public String explanation = null;		// 词条的解释
		
		// 初始化结点
		public TreeNode(char l, String pre, String exp) {
			label = l;
			prefix = pre; 
			explanation = exp;
			sons = new HashMap<>();
			
		}
		
	}
	
	/**
    * @Description:	通过递归的方式，构建前缀树的每个结点。
    * 每次调用，都拿出给定字符串的首字母，查找该字母的结点是否已经存在。
    * 不存在的话就建立一个。然后嵌套调用，重复这个步骤，直到等待处理的字符串为空
    * @param str-还未处理的字符串，pre-当前的前缀，parent-当前结点的父结点
    * @return void
    */
	public void processOneWord(String str, String pre, TreeNode parent) {
		
		// 如果没有剩下的字符，表示某个单词处理完毕，设置解释并返回
		if (str.length() == 0) {
			parent.explanation = String.format("The explanation of %s is ...", pre); 
			return;
		}
		
		// 处理当前字符串的第一个字母
		char c = str.toCharArray()[0];
		
		TreeNode found = null;
		
		// 如果字母结点已经存在于当前父结点之下，找出它。否则就新生成一个
		if (parent.sons.containsKey(c)) {
			found = parent.sons.get(c);
			
		} else {
			TreeNode son = new TreeNode(c, pre, "");
			parent.sons.put(c, son);
			found = son;
		}
		
		// 递归调用，处理剩下的字符串。记得传入当前的前缀和结点
		processOneWord(str.substring(1), pre + c, found);
		
	}
	
	
	// 前缀树根的初始化和构造前缀树的函数
	public TreeNode root = new TreeNode(' ', "", "");
	public void buildPrefixTree(String[] words) {
		
		for (int i = 0; i < words.length; i++) {
			processOneWord(words[i], "", root);
		}
		
	}
	
	
	/**
    * @Description:	通过递归的方式，查询前缀树的每个结点。
    * 每次调用，都拿出给定字符串的首字母，查找该字母的结点是否已经存在。不存在的话就返回“not found!”。
    * 如果存在，继续嵌套调用，重复这个步骤。直到等待处理的字符串为空，或者到达前缀树的叶子结点
    * @param str-还未处理的字符串，pre-当前的前缀，parent-当前结点的父结点
    * @return String-查找到的单词之解释，或者没有找到“not found!”
    */
	public String lookupAWord(String word, TreeNode parent) {
		
		// 等待处理的字符串为空
		if ("".equals(word)) {
			// 是否为一个合法的单词
			if ("".equals(parent.explanation)) {
				return "not found!";
			} else return parent.explanation;
		}
		
		// 到达叶子结点，仍然没有找到
		if (parent.sons.size() == 0) {
			return "not found!";
		}
		
		// 拿出待处理字符串的首字母，如果找到了该字母就继续递归查找下去，否则返回“not found!”
		char c = word.toCharArray()[0];
		if (parent.sons.containsKey(c)) {
			return lookupAWord(word.substring(1), parent.sons.get(c));
		} else return "not found!";
		
	}
	
	// 查询前缀树的函数
	public String lookup(String word) {
		return lookupAWord(word, root);
	}
	
	
		
	// 使用深度优先搜索，访问字典里所有的单词	
	public void dfs(TreeNode parent) {
		
		if (parent.sons.size() == 0) {
			// 已经到达叶子结点了，表明找到一个单词，输入
			System.out.println(parent.prefix + parent.label);
		} else {
			// 还不是叶子结点，遍历每个子结点，找出可能的通路
			Iterator<Entry<Character, TreeNode>> iter = parent.sons.entrySet().iterator();
			while (iter.hasNext()) {
				dfs(iter.next().getValue());
			}
		}
		
	}
	
	// 使用栈来实现深度优先搜索
	public void dfsByStack(TreeNode root) {
		
		Stack<TreeNode> stack = new Stack<TreeNode>(); 
			// 创建堆栈对象，其中每个元素都是TreeNode类型
		stack.push(root);		// 初始化的时候，压入根结点
		
		while (!stack.isEmpty()) {	// 只要栈里还有结点，就继续下去
			
			TreeNode node = stack.pop();	// 弹出栈顶的结点
			
			if (node.sons.size() == 0) {
				// 已经到达叶子结点了，输出
				System.out.println(node.prefix + node.label);
			} else {
				// 非叶子结点，遍历它的每个子结点
				Iterator<Entry<Character, TreeNode>> iter 
					= node.sons.entrySet().iterator();
				
				// 注意，这里使用了一个临时的栈stackTemp
				// 这样做是为了保持遍历的顺序，和递归遍历的顺序是一致的
				// 如果不要求一致，可以直接压入stack
				Stack<TreeNode> stackTemp = new Stack<TreeNode>();
				while (iter.hasNext()) {
					stackTemp.push(iter.next().getValue());
				}
				while (!stackTemp.isEmpty()) {
					stack.push(stackTemp.pop());
				}
			}
		}
		
	}	
	
	
	
	// 遍历所有单词的入口函数
	public void getAllWords() {
		dfs(root);
		System.out.println();
		
		dfsByStack(root);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lesson12_2 l12_2 = new Lesson12_2();
		String[] words = {"zoo", "geometry", "bat", "boy", "geek", "address", "zebra"};
		l12_2.buildPrefixTree(words);
		
		l12_2.getAllWords();
		
		
	}

}