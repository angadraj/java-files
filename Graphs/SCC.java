import java.util.*;
class SCC{
    static int N;
    static ArrayList<Integer>[] graph;
    public static void addEdge(ArrayList<Integer>[] graph,int u,int v){
        graph[u].add(v);
    }
    public static void display(ArrayList<Integer>[] graph){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<graph.length;i++){
            sb.append(i+"->");
            for(int j=0;j<graph[i].size();j++){
                sb.append(graph[i].get(j));
                if(j!=graph[i].size()-1){
                    sb.append(",");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
    public static void topologicalSort(ArrayList<Integer>[] graph,int src){
        ArrayDeque<Integer> qu=new ArrayDeque<>();
        ArrayDeque<Integer> baseAns=new ArrayDeque<>();
        int[] indegeree=new int[graph.length];
        for(int i=0;i<graph.length;i++){
            for(int e:graph[i]){
                indegree[e]++;
            }
        }
        for(int i=0;i<indegeree.length;i++){
            if(indegree[i]==0) qu.add(i);
        }
        while(qu.size()!=0){
            int size=qu.size();
            while(size-- >0){
                int vtx=qu.remove();
                ans.add(vtx);
                for(int e:graph[vtx]){
                    if(--indegree[e]==0){
                        qu.add(e);
                    }
                }
            }
        }
    }
    public static void solve(){
        N=9;
        graph=new ArrayList[N];
        for(int i=0;i<N;i++){
            graph[i]=new ArrayList<>();
        }
        // ArrayList<Integer> ans=new ArrayList<>();
        addEdge(graph,0,1);
        addEdge(graph,0,7);
        addEdge(graph,1,2);
        addEdge(graph,1,7);
        addEdge(graph,2,8);
        addEdge(graph,2,5);
        addEdge(graph,2,3);
        addEdge(graph,8,6);
        addEdge(graph,8,7);
        addEdge(graph,6,5);
        addEdge(graph,5,3);
        addEdge(graph,5,4);
        addEdge(graph,3,4);
        addEdge(graph,7,6);
        display(graph);
    }
    public static void main(String args[]){
        solve();
    }
}