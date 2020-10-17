package com.lqk;

import java.util.*;

/**
 * 有向有权图
 *
 * @author LQKLXR
 */
public class Graph {

    /**
     * 保存各个顶点的列表
     */
    private List<String> vertexes;
    /**
     * 存储边的邻接矩阵表
     */
    private int[][] edges;
    /**
     * 该图可以存储的最大顶点数
     */
    private int capacity;
    /**
     * 存储当前的顶点数
     */
    private int size;


    /**
     * 根据图的最大容量建图
     *
     * @param capacity
     */
    public Graph(int capacity) {
        this.capacity = capacity;
        this.vertexes = new ArrayList<>(capacity);
        this.edges = new int[capacity][capacity];
        this.size = 0;
    }


    /**
     * 插入一个新的顶点
     *
     * @param name
     */
    public void insertVertex(String name) {
        if (size >= capacity) {
            throw new RuntimeException("图已被填满，不可插入新的顶点");
        }
        if (vertexes.contains(name)) {
            throw new RuntimeException("此顶点已经存在");
        }
        vertexes.add(name);
        size++;
    }

    /**
     * 插入一条新的边
     *
     * @param from   起始顶点
     * @param to     指向顶点
     * @param weight 权重
     */
    public void insertEdge(String from, String to, int weight) {

        int fromIndex = vertexes.indexOf(from);
        int toIndex = vertexes.indexOf(to);
        if (fromIndex == -1) {
            throw new RuntimeException("顶点 " + from + " 不存在");
        }
        if (toIndex == -1) {
            throw new RuntimeException("顶点 " + toIndex + " 不存在");
        }
        edges[fromIndex][toIndex] = weight;
        //edges[toIndex][fromIndex] = weight;
    }


    /**
     * 把邻接矩阵输出
     */
    public void printEdges() {
        for (int[] array : edges) {
            for (int x : array) {
                System.out.print("\t" + x);
            }
            System.out.println();
        }
    }

    /**
     * 深度优先搜索一个顶点
     *
     * @param start 出发顶点的名称
     * @return 返回的字符串链表
     */
    public List<String> dfsVertex(String start) {
        int startIndex = vertexes.indexOf(start);
        if (startIndex == -1) {
            throw new RuntimeException(start + " 顶点不存在");
        }
        // 记录各个点是否被访问过的数组
        boolean[] visited = new boolean[size];
        // 存储结果
        List<String> result = new ArrayList<>();
        // 执行深度优先搜索
        doDfsVertex(startIndex, visited, result);
        return result;
    }

    /**
     * 执行深度优先搜索
     *
     * @param startIndex 当前起始顶点索引
     * @param visited    标记是否访问过的数组
     * @param result     返回的字符串链表
     */
    private void doDfsVertex(int startIndex, boolean[] visited, List<String> result) {

        result.add(vertexes.get(startIndex));
        visited[startIndex] = true;

        for (int x : getLinkedVertexes(startIndex)) {
            if (!visited[x]) {
                doDfsVertex(x, visited, result);
            }
        }


    }

    /**
     * 广度优先搜索一个顶点
     *
     * @param start 起始顶点名
     * @return 返回的字符串链表
     */
    public List<String> bfsVertex(String start) {

        int startIndex = vertexes.indexOf(start);
        if (startIndex == -1) {
            throw new RuntimeException(start + "顶点不存在");
        }
        // 存储结果
        List<String> result = new ArrayList<>();
        result.add(start);
        // 广度优先搜索的辅助队列
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startIndex);
        // 标记是否访问过的数组
        boolean[] visited = new boolean[vertexes.size()];
        visited[startIndex] = true;
        while (!queue.isEmpty()) {
            int curStart = queue.poll();
            for (int x : getLinkedVertexes(curStart)) {
                if (!visited[x]) {
                    result.add(vertexes.get(x));
                    visited[x] = true;
                    queue.add(x);
                }
            }
        }
        return result;
    }

    /**
     * 返回一个节点的指向的邻居节点
     *
     * @param startIndex 起始顶点索引
     * @return 列表
     */
    private List<Integer> getLinkedVertexes(int startIndex) {

        List<Integer> list = new ArrayList<>();
        for (int x = 0; x < edges[startIndex].length; x++) {
            if (edges[startIndex][x] > 0) {
                list.add(x);
            }
        }
        return list;
    }

    /**
     * 拓扑排序
     */
    public List<String> topSort() {
        // 存储结果
        List<String> result = new ArrayList<>();
        // 存储各个点的入度数值的数组
        int[] inDegree = new int[vertexes.size()];
        // 存储各个点有没有被访问过
        boolean[] visited = new boolean[vertexes.size()];
        // 存储一个点到相邻结点的数组
        Map<Integer, List<Integer>> map = new HashMap<>(vertexes.size());
        // 构造入度数组
        for (int i = 0; i < vertexes.size(); i++) {
            int count = 0;
            for (int j = 0; j < vertexes.size(); j++) {
                if (edges[j][i] > 0) {
                    count++;
                }
            }
            inDegree[i] = count;
            List<Integer> linkedVertexes = getLinkedVertexes(i);
            map.put(i, linkedVertexes);
        }

        // 开始排序
        for (int i = 0; i < vertexes.size(); i++) {
            int V = findNewVertexOfInDegreeZero(inDegree, visited);
            if (V == -1) {
                throw new RuntimeException("图中有环，无法拓扑排序");
            }
            result.add(vertexes.get(V));
            for (int x : map.get(V)) {
                inDegree[x]--;
            }
        }
        return result;
    }

    /**
     * 查找下一个入度为 0 的顶点
     *
     * @param inDegree 统计入度的数组
     * @param visited  标记是否访问过的数组
     * @return 入度值
     */
    private int findNewVertexOfInDegreeZero(int[] inDegree, boolean[] visited) {
        for (int i = 0; i < inDegree.length; i++) {
            // 入度为 0 且没有被访问过
            if (inDegree[i] == 0 && !visited[i]) {
                visited[i] = true;
                return i;
            }
        }
        return -1;
    }

    /**
     * 求无权最短路径
     *
     * @param start 起始顶点名称
     * @param end   目标顶点名称
     */
    public void unWeightPathLength(String start, String end) {

        int startIndex = vertexes.indexOf(start);
        if (startIndex == -1) {
            throw new RuntimeException(start + " 顶点不存在");
        }
        int endIndex = vertexes.indexOf(end);
        if (endIndex == -1) {
            throw new RuntimeException(end + " 顶点不存在");
        }

        // 储存结果的列表
        List<String> result = new ArrayList<>();
        // 记录到各个顶点的最短距离
        int[] dis = new int[vertexes.size()];
        // -1表示不可达
        Arrays.fill(dis, -1);
        int curDis = 0;
        dis[startIndex] = curDis;
        // 记录每一个节点是从哪个节点来的
        String[] pre = new String[vertexes.size()];

        // 广度优先搜索开始
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startIndex);
        while (!queue.isEmpty()) {
            // 取出当前顶点
            int curIndex = queue.poll();
            // 拿到所有当前顶点指向的邻接顶点
            for (int x : getLinkedVertexes(curIndex)) {
                // 如果这个顶点还没有更新距离
                if (dis[x] == -1) {
                    // 更新距离、路径信息
                    dis[x] = (curDis++) + 1;
                    pre[x] = vertexes.get(curIndex);
                    queue.offer(x);
                }
            }
        }
        // 至此获得全部的最短路径信息

        // 开始输出信息, 可以封装成另一个函数
        System.out.println("从 " + start + " 到 " + end + " 的最短路径长为 " + dis[endIndex]);
        System.out.println("路径为：");
        Stack<String> path = new Stack<>();
        path.push(end);
        int curIndex = endIndex;
        for (; ; ) {
            String preVertex = pre[curIndex];
            path.push(preVertex);
            if (preVertex.equals(start)) {
                break;
            }
            curIndex = vertexes.indexOf(preVertex);
        }
        while(!path.isEmpty()){
            System.out.print("\t" + path.pop());
        }
        System.out.println();
    }

}
