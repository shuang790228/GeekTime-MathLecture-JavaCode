import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import java.util.Queue;
import java.util.LinkedList;


public class Lesson14_2 {
	
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
		public HashMap<Integer, Integer> degrees;		// 存放从不同用户出发，当前用户结点是第几度
		
		// 初始化结点
		public Node(int id) {
			user_id = id;
			friends = new HashSet<>();
			degree = 0;
			degrees = new HashMap<>();
			degrees.put(id, 0);
		}
		
	}
	
	/** 和之前一样，在讲座中可以省略
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
	
	
	/** 和之前一样，在讲座中可以省略
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
				user_nodes[friend_id].degree = user_nodes[current_user_id].degree + 1;		
													// 好友度数是当前结点的好友度数再加1
				System.out.println(String.format("\t%d度好友：%d",  user_nodes[friend_id].degree, friend_id));
			}
		}
		
	}
	
	/** 
    * @Description:	通过广度优先搜索，查找特定的好友
    * @param user_nodes-用户的结点；user_id_a-用户a的ID；user_id_b-用户b的ID
    * @return void
    */
	public static void bfs(Node[] user_nodes, int user_id_a, int user_id_b) {
		
		if (user_id_a > user_nodes.length) return;	// 防止数组越界的异常
		
		if (user_id_a == user_id_b) {
			System.out.println(String.format("%d和%d两者是%d度好友",  user_id_a, user_id_b, 0));
			return;
		}
		
		Queue<Integer> queue = new LinkedList<Integer>();	// 用于广度优先搜索的队列
		
		queue.offer(user_id_a);		// 放入初始结点
		HashSet<Integer> visited = new HashSet<>();	// 存放已经被访问过的结点，防止回路
		visited.add(user_id_a);
		
		boolean founded = false;
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
				// 发现特定的好友，输出，然后跳出循环并返回
				if (friend_id == user_id_b) {
					System.out.println(String.format("%d和%d两者是%d度好友",  user_id_a, user_id_b, user_nodes[friend_id].degree));
					founded = true;
					break;
				}
			}
			
			// 已经发现特定的好友，跳出循环并返回
			if (founded) break;
		}
		
	}
	
	
	/**
    * @Description:	通过双向广度优先搜索，查找两人之间最短通路的长度
    * @param user_nodes-用户的结点；user_id_a-用户a的ID；user_id_b-用户b的ID
    * @return void
    */
	public static int bi_bfs(Node[] user_nodes, int user_id_a, int user_id_b) {
		
		if (user_id_a > user_nodes.length || user_id_b > user_nodes.length) return -1;	// 防止数组越界的异常
		
		if (user_id_a == user_id_b) return 0;		// 两个用户是同一人，直接返回0
		
		Queue<Integer> queue_a = new LinkedList<Integer>();	// 队列a，用于从用户a出发的广度优先搜索
		Queue<Integer> queue_b = new LinkedList<Integer>();	// 队列b，用于从用户b出发的广度优先搜索
		
		queue_a.offer(user_id_a);		// 放入初始结点
		HashSet<Integer> visited_a = new HashSet<>();	// 存放已经被访问过的结点，防止回路
		visited_a.add(user_id_a);
		
		queue_b.offer(user_id_b);		// 放入初始结点
		HashSet<Integer> visited_b = new HashSet<>();	// 存放已经被访问过的结点，防止回路
		visited_b.add(user_id_b);
				
		int degree_a = 0, degree_b = 0, max_degree = 20;		// max_degree的设置，防止两者之间不存在通路的情况
		
		while ((degree_a + degree_b) < max_degree) {
			degree_a ++;
			getNextDegreeFriend(user_id_a, user_nodes, queue_a, visited_a, degree_a);
							// 沿着a出发的方向，继续广度优先搜索degree + 1的好友
			if (hasOverlap(visited_a, visited_b)) return (degree_a + degree_b);
							// 判断到目前为止，被发现的a的好友，和被发现的b的好友，两个集合是否存在交集
			
			degree_b ++;
			getNextDegreeFriend(user_id_b, user_nodes, queue_b, visited_b, degree_b);
							// 沿着b出发的方向，继续广度优先搜索degree + 1的好友
			if (hasOverlap(visited_a, visited_b)) return (degree_a + degree_b);
							// 判断到目前为止，被发现的a的好友，和被发现的b的好友，两个集合是否存在交集
			
		}
		
		return -1;			// 广度优先搜索超过max_degree之后，仍然没有发现a和b的重叠，认为没有通路
		
	}
	
	// 广度优先搜索和user_id相距度数为degree的所有好友
	public static void getNextDegreeFriend(int user_id, Node[] user_nodes, Queue<Integer> queue, HashSet<Integer> visited, int degree) {
		
		while (!queue.isEmpty()) {
			
			if (user_nodes[queue.peek()].degrees.get(user_id) >= degree) break;		
				// 首先看看，下一个从队列头部取出来的用户，他/她和出发点相距的度数是否超过了参数degree。如果超过了就跳出
			int current_user_id = queue.poll();		// 拿出队列头部的第一个结点
			if (user_nodes[current_user_id] == null) continue;
		
			for (int friend_id : user_nodes[current_user_id].friends) {
				if (user_nodes[friend_id] == null) continue;
				if (visited.contains(friend_id)) continue;
				queue.offer(friend_id);
				visited.add(friend_id);
				user_nodes[friend_id].degrees.put(user_id, user_nodes[current_user_id].degrees.get(user_id) + 1);		
					// 好友度数是当前结点的好友度数再加1
			}
		}
		
	}
	
	
	// 判断两个好友集合是否有交集
	public static boolean hasOverlap(HashSet<Integer> friends_from_a, HashSet<Integer> friends_from_b) {
		
		for (Integer f_a : friends_from_a) {
			if (friends_from_b.contains(f_a))
				return true;
		}
		
		return false;
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		System.out.println(String.format("lastModifiedTime>=%d", System.currentTimeMillis() - (6*30*24*3600*1000L)));
//		System.out.println(String.format("lastModifiedTime>=%d", System.currentTimeMillis()));
//		System.exit(0);
		
		Lesson14_2 l14_1 = new Lesson14_2();
		Node[] user_nodes = l14_1.generateGraph(50000, 320000);	//生成较大数量的结点和边，用于测试两种方法的性能差异
		
		long start = 0, end = 0;
		
		int a_id = 0, b_id = 1;
		
		start = System.currentTimeMillis();
		Lesson14_2.bfs(user_nodes, a_id, b_id);
		end = System.currentTimeMillis();
		System.out.println(String.format("耗时%d毫秒", end - start));
		
		System.out.println();
		
		start = System.currentTimeMillis();
		System.out.println(String.format("%d和%d两者是%d度好友", a_id, b_id, Lesson14_2.bi_bfs(user_nodes, a_id, b_id)));
		end = System.currentTimeMillis();
		System.out.println(String.format("耗时%d毫秒", end - start));
		
	}

}