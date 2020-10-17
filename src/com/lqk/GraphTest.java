package com.lqk;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author lqk
 * @Date 2020/10/16
 * @Description
 */
public class GraphTest {

    private Graph graph;

    @Before
    public void before(){
        graph = new Graph(10);
        graph.insertVertex("V1");
        graph.insertVertex("V2");
        graph.insertVertex("V3");
        graph.insertVertex("V4");
        graph.insertVertex("V5");
        graph.insertVertex("V6");
        graph.insertVertex("V7");

        graph.insertEdge("V1", "V2", 2);
        graph.insertEdge("V1", "V4", 1);

        graph.insertEdge("V2", "V4", 3);
        graph.insertEdge("V2", "V5", 10);
        graph.insertEdge("V3", "V6", 5);
        graph.insertEdge("V3", "V1", 4);
        graph.insertEdge("V4", "V3", 2);
        graph.insertEdge("V4", "V5", 2);
        graph.insertEdge("V4", "V6", 8);
        graph.insertEdge("V4", "V7", 4);
        graph.insertEdge("V5", "V7", 6);
        graph.insertEdge("V7", "V6", 1);


    }

    @Test
    public void printEdgesTest(){
        System.out.println();
        System.out.println("=============邻接矩阵表开始=============");
        graph.printEdges();
        System.out.println("=============邻接矩阵表结束=============");
    }

    @Test
    public void dfsVertexTest(){
        System.out.println();
        System.out.println("=============深度优先搜索开始=============");
        List<String> list = graph.dfsVertex("V1");
        for (String string : list){
            System.out.print("\t" + string);
        }
        System.out.println();
        System.out.println("=============深度优先搜索结束=============");
    }

    @Test
    public void bfsVertexTest(){
        System.out.println();
        System.out.println("=============广度优先搜索开始=============");
        List<String> list = graph.bfsVertex("V3");
        for (String string : list){
            System.out.print("\t" + string);
        }
        System.out.println();
        System.out.println("=============广度优先搜索结束=============");
    }

    @Test
    public void topSortTest(){
        System.out.println();
        System.out.println("=============拓扑排序开始=============");
        List<String> list = graph.topSort();
        for (String string : list){
            System.out.print("\t" + string);
        }
        System.out.println();
        System.out.println("=============拓扑排序结束=============");
    }

    @Test
    public void unWeightPathLengthTest(){
        System.out.println();
        System.out.println("=============无权最短路径开始=============");
        graph.unWeightPathLength("V1", "V7");
        System.out.println("=============无权最短路径结束=============");
    }
}
