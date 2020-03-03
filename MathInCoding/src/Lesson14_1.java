import java.util.HashSet;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;


public class Lesson14_1 {
	
	/**
    * @Description:	图的结点
    * 
    */
	
	public static Random rand = new Random();
	
	public class Node {
		
		public int user_id;		// 结点的名称，这里使用用户id
		public HashSet<Integer> friends = null;	
				// 使用哈希映射存放相连的朋友结点。哈希便于确认和某个用户是否相连。
		public int degree;		// 用于存放和给定的用户结点，是几度好友
		
		// 初始化结点
		public Node(int id) {
			user_id = id;
			friends = new HashSet<>();
			degree = 0;
			
		}
		
	}
	
	/**
    * @Description:	生成图的结点和边
    * @param user_num-用户的数量，也就是结点的数量；relation_num-好友关系的数量，也就是边的数量
    * @return Node[]-图的所有结点
    */
	
	public Node[] generateGraph(int user_num, int relation_num) {
		
		Node[] user_nodes = new Node[user_num];
		
		// 生成所有表示用户的结点
		for (int i = 0; i < user_num; i++) {
			user_nodes[i] = new Node(i);
		}
		
		// 生成所有表示好友关系的边
		for (int i = 0; i < relation_num; i++) {
			int friend_a_id = rand.nextInt(user_num);
			int friend_b_id = rand.nextInt(user_num);
			if (friend_a_id == friend_b_id) continue;	// 自己不能是自己的好友。如果生成的两个好友id相同，跳过
			Node friend_a = user_nodes[friend_a_id];
			Node friend_b = user_nodes[friend_b_id];
			
			// 这里为了简化起见，暂时不考虑重复的好友id。如果有重复，跳过
			if (!friend_a.friends.contains(friend_b_id)) {
				friend_a.friends.add(friend_b_id);	
			}
			if (!friend_b.friends.contains(friend_a_id)) {
				friend_b.friends.add(friend_a_id);	
			}
		}
		
		return user_nodes;
		
	}
	
	
	/**
    * @Description:	通过广度优先搜索，查找好友
    * @param user_nodes-用户的结点；user_id-给定的用户ID，我们要为这个用户查找好友
    * @return void
    */
	
	public static void bfs(Node[] user_nodes, int user_id) {
		
		if (user_id > user_nodes.length) return;	// 防止数组越界的异常
		
		Queue<Integer> queue = new LinkedList<Integer>();	// 用于广度优先搜索的队列
		
		queue.offer(user_id);		// 放入初始结点
		HashSet<Integer> visited = new HashSet<>();	// 存放已经被访问过的结点，防止回路
		visited.add(user_id);
		
		while (!queue.isEmpty()) {
			int current_user_id = queue.poll();		// 拿出队列头部的第一个结点
			if (user_nodes[current_user_id] == null) continue;
			
			// 遍历刚刚拿出的这个结点的所有直接连接结点，并加入队列尾部
			for (int friend_id : user_nodes[current_user_id].friends) {
				if (user_nodes[friend_id] == null) continue;
				if (visited.contains(friend_id)) continue;
				queue.offer(friend_id);
				visited.add(friend_id);	// 记录已经访问过的结点
				user_nodes[friend_id].degree = user_nodes[current_user_id].degree + 1;		// 好友度数是当前结点的好友度数再加1
				System.out.println(String.format("\t%d度好友：%d",  user_nodes[friend_id].degree, friend_id));
			}
		}
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lesson14_1 l14_1 = new Lesson14_1();
		Node[] user_nodes = l14_1.generateGraph(50, 80);
		int given_user_id = 0;
		System.out.println(String.format("广度优先搜索%d的好友：", given_user_id));
		Lesson14_1.bfs(user_nodes, given_user_id);
	}

}