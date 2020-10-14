import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
class Lecture1{
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
    static int N=7;
    static ArrayList<Edge>[] graph=new ArrayList[N];

    public static void addEdge(int u,int v,int w){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }
    public static void display(){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<N;i++){
            sb.append(i+"->");
            for(Edge e:graph[i]){
                sb.append("( "+e.v+", "+e.w+") ");
            }
            sb.append("\n");
        }
        // sb.append("\n");
        System.out.print(sb.toString());
    }
    public static int findEdge(int u,int v){
        int idx=-1;
        for(int i=0;i<graph[u].size();i++){
            Edge e=graph[u].get(i);
            if(e.v==v) {
                idx=i;
                break;
            }
        }
        return idx;
    }
    public static void removeEdge(int u,int v){
        int idx=findEdge(u,v);
        graph[u].remove(idx);
        idx=findEdge(v,u);
        graph[v].remove(idx);
    }
    public static void removeVertex(int u){
        for(int i=graph[u].size()-1;i>=0;i--){
            Edge e=graph[u].get(i);
            removeEdge(u,e.v);
        }
    }
    public static void construct(){
        for(int i=0;i<N;i++){
            graph[i]=new ArrayList<>();
        }
        addEdge(0,1,10);
        addEdge(0,3,10);
        addEdge(1,2,10);
        addEdge(2,3,40);
        addEdge(3,4,2);
        addEdge(4,5,2);
        addEdge(5,6,3);
        addEdge(4,6,8);
        addEdge(0,6,1);
        addEdge(2,5,1);
    }
    public static boolean hasPath(int src,int dest,boolean[] vis,String ans){
        if(src==dest){
            System.out.println(ans+src);
            return true;
        }
        vis[src]=true;
        boolean res=false;
        int count=0;
        for(Edge e:graph[src]){
            if(!vis[e.v]){
                res=res || hasPath(e.v,dest,vis,ans+src);
            }
        }
        return res;
    }
    public static int allPaths(int src,int des,boolean vis[],String ans,int count){
        if(src==des){
            System.out.println(ans+src+"@"+count);
            return 1;
        }
        int res=0;
        vis[src]=true;
        for(Edge e:graph[src]){
            if(!vis[e.v]){
                res+=allPaths(e.v,des,vis,ans+src,count+e.w);
            }
        }
        vis[src]=false;
        return res;
    }
    static class pair{
        int cost=0;
        String path="";
        public pair(int cost,String path){
            this.cost=cost;
            this.path=path;
        }
    }
    public static pair heavyPathHelper(int src,int dest,boolean[] vis){
        if(src==dest){
            return new pair(0,""+src);
        }
        vis[src]=true;
        pair myans=new pair(0,"");
        for(Edge e:graph[src]){
            if(!vis[e.v]){
                pair cp=heavyPathHelper(e.v,dest,vis);
                if(myans.cost<cp.cost+e.w){
                    myans.cost=cp.cost+e.w;
                    myans.path=src+cp.path;
                }
            }
        }
        vis[src]=false;
        return myans;
    }
    public static void heavyPath(int src,int des,boolean[] vis){
        pair cp=heavyPathHelper(src,des,vis);
        System.out.println(cp.cost +"\n" + cp.path);
    }
    public static void hamiltonian(int src,int osrc,boolean[] vis,int count,String ans){
        if(N==count){
            int idx=-1;
            for(int i=0;i<graph[src].size();i++){
                Edge e=graph[src].get(i);
                if(e.v==osrc) idx=i;
            }
            if(idx!=-1){
                System.out.println("cycle "+ans+src+"@"+osrc);
            }
            else {
                System.out.println("path "+ans+src);
            }
        }
        vis[src]=true;
        for(Edge e:graph[src]){
            if(!vis[e.v]){
                hamiltonian(e.v,osrc,vis,count+1,ans+src);
            }
        }
        vis[src]=false;
    }
    public static int gcc(boolean[] vis){
        int count=0;
        for(int i=0;i<N;i++){
            if(!vis[i]){
                dfs(i,vis);
                count++;
            }
        }
        return count;
    }
    public static void dfs(int src,boolean[] vis){
        vis[src]=true;
        for(Edge e:graph[src]){
            if(!vis[e.v]){
                dfs(e.v,vis);
            }
        }
    }
    ////////////BFS
    public static void bfs_01(int src,boolean[] vis){
        Queue<Integer> qu=new LinkedList<>();
        qu.add(src);
        while(qu.size()!=0){
            int size=qu.size();
            while(size-->0){
                int vtx=qu.remove();
                System.out.print(vtx+" ");
                vis[vtx]=true;
                for(Edge e:graph[vtx]){
                    if(!vis[e.v]){
                        qu.add(e.v);
                    }
                }
            }
        }
    }
    public static void bfs_no_dups(int src,boolean[] vis){
        Queue<Integer> qu=new LinkedList<>();
        qu.add(src);
        while(qu.size()!=0){
            int size=qu.size();
            while(size-->0){
                int vtx=qu.remove();
                if(vis[vtx]) continue;
                System.out.print(vtx+" ");
                vis[vtx]=true;
                for(Edge e:graph[vtx]){
                    if(!vis[e.v]){
                        qu.add(e.v);
                    }
                }
            }
        }
    }
    public static void bfs_cycle(int src,boolean[] vis){
        Queue<Integer> qu=new LinkedList<>();
        qu.add(src);
        while(qu.size()!=0){
            int size=qu.size();
            while(size-->0){
                int vtx=qu.remove();
                if(vis[vtx]){
                    System.out.println("cycle@ "+vtx);
                    continue;
                }
                System.out.print(vtx+" ");
                vis[vtx]=true;
                for(Edge e:graph[vtx]){
                    if(!vis[e.v]){
                        qu.add(e.v);
                    }
                }
            }
        }
    }
    public static void bfs_edges(int src,int des,boolean[] vis){
        Queue<Integer> qu=new LinkedList<>();
        int level=0;
        qu.add(src);
        while(qu.size()!=0){
            int size=qu.size();
            while(size-->0){
                int vtx=qu.remove();
                if(vis[vtx]) continue;
                // System.out.print(vtx+" ");
                if(vtx==des){
                    System.out.println(level);
                    break;
                }
                vis[vtx]=true;
                for(Edge e:graph[vtx]){
                    if(!vis[e.v]){
                        qu.add(e.v);
                    }
                }
            }
            //when a batch is processed
            level++;
        }
    }
    //in above all qu has all dups but we are not printing them ie:continue
    public static void bfs_final(int src,boolean[] vis){
        Queue<Integer> qu=new LinkedList<>();
        qu.add(src);
        vis[src]=true;
        while(qu.size()!=0){
            int size=qu.size();
            while(size-->0){
                int vtx=qu.remove();
                System.out.print(vtx+" ");
                for(Edge e:graph[vtx]){
                    if(!vis[e.v]){
                        qu.add(e.v);
                        vis[e.v]=true;
                    }
                }
            }
        }
    }
    //Articulation point
    static int low[];
    static int dis[]; 
    static boolean[] ap;
    static boolean[] vis;
    static int rootcount=0;
    static int timecount=0;
    public static void articulationPoint(int src,int par){
        vis[src]=true;
        low[src]=dis[src]=timecount++;
        for(Edge e:graph[src]){
            if(!vis[e.v]){
                if(par==-1) rootcount++;
                articulationPoint(e.v,src);
                if(dis[src]<=low[e.v]){
                    System.out.println("ap"+src);
                    ap[src]=true;
                }
                if(dis[src]<low[e.v]){
                    System.out.println("bridge point "+src+"->"+e.v);
                }
                low[src]=Math.min(low[src],low[e.v]);
            }
            else if(e.v!=par){
                low[src]=Math.min(low[src],dis[e.v]);
            }
        }
    }
    public static void artiHelper(){
        low=new int[N];
        dis=new int[N];
        ap=new boolean[N];
        vis=new boolean[N];
        for(int i=0;i<N;i++){
            if(!vis[i]){
                articulationPoint(i,-1);
                if(rootcount==1) ap[i]=false;
                rootcount=0;
            }
        }
    }
    //in above you can't detect a cycle because we are marking them while we adding them
    //so next occurance does not enters into qu;
    public static void solve(){
        construct();
        display();
        boolean vis[]=new boolean[N];
        // System.out.println(hasPath(0,6,vis,""));
        // System.out.println(allPaths(0,6,vis,"",0));
        // heavyPath(0,6,vis);
        // hamiltonian(0,0,vis,1,"");
        // System.out.println(gcc(vis));
        // bfs_final(0,vis);
        bfs_cycle(0,vis);
    }
}