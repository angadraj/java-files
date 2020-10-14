import java.util.*;
class Prims{
    public static void main(String args[]){
        solve();
    }
    public static class Edge{
        int v;
        int w;
        public Edge(int v,int w){
            this.v=v;
            this.w=w;
        }
    }
    public static void addEdge(int u,int v,int w,ArrayList<Edge>[] graph){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }
    public static void display(int N,ArrayList<Edge>[] graph){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<N;i++){
            sb.append(i+"->");
            for(Edge e:graph[i]){
                sb.append("("+e.v+","+e.w+")");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
    public static class primPair implements Comparable<primPair>{
        int src=0;
        int weight=0;
        int par=0;
        public primPair(int src,int par,int weight){
            this.src=src;
            this.par=par;
            this.weight=weight;
        }
        @Override
        public int compareTo(primPair other){
            return this.weight-other.weight;
        }
    }
    public static void Prims_algo(int src,int N,ArrayList<Edge>[] graph){
        ArrayList<Edge>[] prims_graph=new ArrayList[N];
        for(int i=0;i<N;i++) prims_graph[i]=new ArrayList<>();
        PriorityQueue<primPair> pq=new PriorityQueue<>();
        pq.add(new primPair(0,-1,0));
        boolean vis[]=new boolean[N];
        int edge_count=0;
        while(edge_count!=N-1){
            primPair cp=pq.remove();
            if(vis[cp.src]) continue;
            if(cp.par!=-1){
                addEdge(cp.src,cp.par,cp.weight,prims_graph);
                edge_count++;
            }
            vis[cp.src]=true;
            for(Edge e:graph[cp.src]){
                pq.add(new primPair(e.v,cp.src,e.w));
            }
        }
        display(N,prims_graph);
    }
    public static void prims_algo_02(int src,int N,ArrayList<Edge>[] graph){
        ArrayList<Edge>[] mygraph=new ArrayList[N];
        for(int i=0;i<N;i++) mygraph[i]=new ArrayList<>();
        int cost[]=new int[N];
        Arrays.fill(cost,(int)1e8);
        boolean[] vis=new boolean[N];
        int edge_count=0;
        PriorityQueue<primPair> pq=new PriorityQueue<>();
        pq.add(new primPair(src,-1,0));
        cost[src]=0;
        while(edge_count!=N-1){
            primPair cp=pq.remove();
            if(vis[cp.src]) continue;
            if(cp.par!=-1){
                addEdge(cp.src,cp.par,cp.weight,mygraph);
                edge_count++;
            }
            vis[cp.src]=true;
            for(Edge e:graph[cp.src]){
                if(!vis[e.v] && e.w<cost[e.v]){
                    cost[e.v]=e.w;
                    pq.add(new primPair(e.v,cp.src,e.w));
                }
            }
        }
        display(N,mygraph);
    }
    /////////////////////////////////////
    public static class Diji implements Comparable<Diji>{
        int src=0;
        int par=0;
        int weight=0;
        int wsf=0;
        public Diji(int src,int par,int weight,int wsf){
            this.src=src;
            this.par=par;
            this.weight=weight;
            this.wsf=wsf;
        }
        @Override
        public int compareTo(Diji other){
            return this.wsf-other.wsf;
        }
    }
    public static void Dijkistras_02(int src,int N,ArrayList<Edge>[] graph){
        ArrayList<Edge>[] mygraph=new ArrayList[N];
        for(int i=0;i<N;i++) mygraph[i]=new ArrayList<>();
        PriorityQueue<Diji> pq=new PriorityQueue<>();
        boolean vis[]=new boolean[N];
        int edge_count=0;
        pq.add(new Diji(src,-1,0,0));
        while(pq.size()!=0){
            Diji cp=pq.remove();
            
            if(vis[cp.src]) continue;

            if(cp.par!=-1){
                addEdge(cp.src,cp.par,cp.weight,mygraph);
                edge_count++;
            }
            vis[cp.src]=true;
            // System.out.println(cp.src +"via"+cp.par+"@"+cp.wsf);
            for(Edge e:graph[cp.src]){
                if(!vis[e.v]){
                    pq.add(new Diji(e.v,cp.src,e.w,cp.wsf+e.w));
                }
            }
        }
        display(N,mygraph);
    }
    public static void Dijkistras(int src,int N,ArrayList<Edge>[] graph){
        ArrayList<Edge>[] mygraph=new ArrayList[N];
        for(int i=0;i<N;i++) mygraph[i]=new ArrayList<>();
        int edge_count=0;
        boolean vis[]=new boolean[N];
        int cost[]=new int[N];
        Arrays.fill(cost,(int)1e8);
        cost[src]=0;
        PriorityQueue<Diji> pq=new PriorityQueue<>();
        pq.add(new Diji(src,-1,0,0));
        while(edge_count!=N-1){
            Diji cp=pq.remove();
            if(vis[cp.src]) continue;
            if(cp.par!=-1){
                addEdge(cp.src,cp.par,cp.weight,mygraph);
                edge_count++;
            }
            vis[cp.src]=true;
            for(Edge e:graph[cp.src]){
                if(!vis[e.v] && cp.wsf+e.w<cost[e.v]){
                    cost[e.v]=e.w+cp.wsf;
                    pq.add(new Diji(e.v,cp.src,e.w,cp.wsf+e.w));
                }
            }
        }
        display(N,mygraph);
    }
    public static void solve(){
        int N=9;
        ArrayList<Edge>[] graph=new ArrayList[N];
        for(int i=0;i<N;i++) graph[i]=new ArrayList<>();
        addEdge(0,1,4,graph);
        addEdge(0,7,8,graph);
        addEdge(1,2,8,graph);
        addEdge(1,7,11,graph);
        addEdge(2,8,2,graph);
        addEdge(2,5,4,graph);
        addEdge(2,3,7,graph);
        addEdge(8,6,6,graph);
        addEdge(8,7,7,graph);
        addEdge(6,5,2,graph);
        addEdge(5,3,14,graph);
        addEdge(5,4,10,graph);
        addEdge(3,4,9,graph);
        addEdge(7,6,1,graph);
        display(N,graph);
        Prims_algo(0,N,graph);
        // prims_algo_02(0,N,graph);
        Dijkistras_02(0,N,graph);
        Dijkistras(0,N,graph);
    }
}