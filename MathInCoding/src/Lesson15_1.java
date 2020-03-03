import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;


public class Lesson15_1 {
	
	/**
    * @Description:	图的结点
    * 
    */
	
	public static Random rand = new Random();
	
	public class Node {
		
		public int geo_id;		// 结点的名称，这里使用地点的id
		public HashMap<Integer, Float> closeby_geo_ids = null;	
				// 哈希便于确认和某个地点是否相连，并获取相应的权重
				// 由于是有向边，我们假设边的方向都是从geo_id到closeby_geo_id
		
		// 初始化结点
		public Node(int id) {
			geo_id = id;
			closeby_geo_ids = new HashMap<>();
		}
		
	}
	
	/**
    * @Description:	生成图的结点和边
    * @param geo_num-地点的数量，也就是结点的数量；closeby_num-地点邻近关系的数量，也就是边的数量
    * @return Node[]-图的所有结点
    */
	
	public Node[] generateGraph(int geo_num, int closeby_num) {
		
		Node[] geo_nodes = new Node[geo_num];
		
		// 生成所有表示地点的结点
		for (int i = 0; i < geo_num; i++) {
			geo_nodes[i] = new Node(i);
		}
		
		// 生成所有表示地点邻近关系的边
		for (int i = 0; i < closeby_num; i++) {
			int geo_a_id = rand.nextInt(geo_num);
			int geo_b_id = rand.nextInt(geo_num);
			if (geo_a_id == geo_b_id) continue;	// 自己不能和自己邻近。如果生成的两个地点id相同，跳过
			Node geo_a = geo_nodes[geo_a_id];
			
			// 由于是有向边，我们假设生成的边是从a到b
			// 这里为了简化起见，暂时不考虑重复的邻近地点id。如果有重复，跳过
			if (!geo_a.closeby_geo_ids.containsKey(geo_b_id)) {
				geo_a.closeby_geo_ids.put(geo_b_id, rand.nextFloat());
			}
			
		}
		
		return geo_nodes;
		
	}
	
	public Node[] craftedGraph() {
		Node[] geo_nodes = new Node[9]; 
		geo_nodes[0] = new Node(0);
		geo_nodes[1] = new Node(1);
		geo_nodes[2] = new Node(2);
		geo_nodes[3] = new Node(3);
		geo_nodes[4] = new Node(4);
		geo_nodes[5] = new Node(5);
		geo_nodes[6] = new Node(6);
		geo_nodes[7] = new Node(7);
		geo_nodes[8] = new Node(8);
		
		geo_nodes[0].closeby_geo_ids.put(1, 0.5f);
		geo_nodes[0].closeby_geo_ids.put(2, 0.3f);
		geo_nodes[0].closeby_geo_ids.put(3, 0.2f);
		geo_nodes[0].closeby_geo_ids.put(4, 0.4f);
		geo_nodes[1].closeby_geo_ids.put(5, 0.3f);
		geo_nodes[2].closeby_geo_ids.put(1, 0.2f);
		geo_nodes[2].closeby_geo_ids.put(6, 0.1f);
		geo_nodes[3].closeby_geo_ids.put(6, 0.4f);
		geo_nodes[3].closeby_geo_ids.put(8, 0.8f);
		geo_nodes[4].closeby_geo_ids.put(3, 0.1f);
		geo_nodes[4].closeby_geo_ids.put(8, 0.6f);
		geo_nodes[5].closeby_geo_ids.put(7, 0.1f);
		geo_nodes[6].closeby_geo_ids.put(5, 0.1f);
		geo_nodes[6].closeby_geo_ids.put(8, 0.2f);
		geo_nodes[8].closeby_geo_ids.put(8, 0.4f);
		
		return geo_nodes;
	}
	
	
	/**
    * @Description:	给定起始点，通过Dijkstra算法，计算这个起始点到其他任意结点的最小权重通路
    * @param geo_nodes-包含所有地点的结点；source_geo_id-给定的起始点ID，我们要从这个地点出发
    * @return void
    */
	
	public static void doDijkstra(Node[] geo_nodes, int source_geo_id) {
		
		// 初始化步骤
		HashSet<Integer> F = new HashSet<>();	//Finish集合
		F.add(source_geo_id);		// 往集合F里添加结点s，F包含且仅包含s
		float[] mw = new float[geo_nodes.length];
		for (int i = 0; i < mw.length; i++) {
			if (i == source_geo_id) mw[i] = 0;	// 把起始点s的最小权重赋为0，也就是mw[s] = 0
			else mw[i] = Float.MAX_VALUE;		
				// 对于所有s不能直接到达的结点，将通路的权重设为无穷大或者可能的最大值
		}
		
		updateWeight(geo_nodes, source_geo_id, mw);	// 假设结点s能直接到达的边集合为M，对于其中的每一条边m，则把mw[m]设为w[s, m]
		
		while (F.size() < geo_nodes.length) {
			int geo_with_min_weight = findGeoWithMinWeight(mw, F);		// 从mw数组选择最小值，并获得对应的结点
			if (geo_with_min_weight == -1 || geo_with_min_weight >= mw.length) break;
			
			F.add(geo_with_min_weight);		// 把这个点加入到F中
			updateWeight(geo_nodes, geo_with_min_weight, mw);	
				// 新加入F的结点x是不是可以直接到达其他结点。
				// 如果是，看看通过x到达其他点的通路权重是否比这些点当前的mw更小，如果是，那么就替换这些点在mw中的值
		}
		
		for (float w : mw) System.out.print(w + "  ");	// 输出最终的结果
		
	}
	
	/**
    * @Description:	每次发现最小的mw及对应的结点x之后，更新和x相邻结点的权重
    * @param geo_nodes-包含所有地点的结点；geo_id-拥有最小mw的结点x；mw-目前所有结点的mw值
    * @return void
    */
	
	public static void updateWeight(Node[] geo_nodes, int geo_id, float[] mw) {
		
		for (int closeby_node : geo_nodes[geo_id].closeby_geo_ids.keySet()) {
			float new_weight = mw[geo_id] + geo_nodes[geo_id].closeby_geo_ids.get(closeby_node);
				// 计算通过x到达其这个点的通路权重
			if (mw[closeby_node] > new_weight) mw[closeby_node] = new_weight;
				// 如果新的通路权重比这个点当前的mw更小，那么就替换它的mw值
		}
		
	}
	
	/**
    * @Description:	在当前还未完成的mw中，查找最小值，及其对应的结点x
    * @param mw-目前所有结点的mw值；F-已完成的结点
    * @return int-最小mw值所对应的结点ID
    */
	
	public static int findGeoWithMinWeight(float[] mw, HashSet<Integer> F) {
		
		int geo_with_min_weight = -1;
		float min_weight = Float.MAX_VALUE; 
		for (int i = 0; i < mw.length; i++) {
			if (F.contains(i)) continue;		// 跳过已经完成的结点
			if (mw[i] < min_weight) {
				// 同时记录最小值和对应的结点ID
				min_weight = mw[i];
				geo_with_min_weight = i;
			}
		}
		
		return geo_with_min_weight;
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lesson15_1 l16_1 = new Lesson15_1();
		
		int source_geo_id = 0;
		
		// 随机生成图
		Node[] geo_nodes_1 = l16_1.generateGraph(5, 12);
		Lesson15_1.doDijkstra(geo_nodes_1, source_geo_id);
		
		System.out.println();
		
		// 手动构建正文中图解的例子，便于你加深理解
		Node[] geo_nodes_2 = l16_1.craftedGraph();
		Lesson15_1.doDijkstra(geo_nodes_2, source_geo_id);
		
		
		
	}

}