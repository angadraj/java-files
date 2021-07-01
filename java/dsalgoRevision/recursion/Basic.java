import java.util.*;

class Basic {
    public static void main(String args[]){
        solve();
    }
    
    public static void solve(){
        // System.out.println(mazePaths(0,0,2,2,""));
        // System.out.println(mazePathMulti(0,0,2,2,""));
        // int[][] dirs={{-1,0},{0,1},{1,0},{0,-1}};
        // String[] dname={"T","R","D","L"};
        // boolean[][] vis=new boolean[3][3];
        // System.out.println(floodFill(0,0,2,2,dirs,dname,"",vis));
        // System.out.println(floodFillMulti(0,0,2,2,dirs,dname,"",vis));
        // String ques="abbc";
        // System.out.println(allPermutationsNoDups(ques,""));
        // String[] codes={";,","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
        String[] codes={".,/?", "@#$%", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz","*+-","&^!~"};
        String ques="123";
        // System.out.println(letterCombi2(codes,ques,""));
        ArrayList<String> ans=new ArrayList<>();
        System.out.println(letterCombi2(codes,ques,""));
        System.out.println(letterCombinations_set2(ques,0,codes,ans,""));
        System.out.println(ans);
    }
    public static int mazePaths(int sr,int sc,int dr,int dc,String psf){
        if(sr < 0 || sc < 0 || sr > dr || sc > dc) return 0;

        if(sr==dr && sc==dc){
            System.out.println(psf);
            return 1;
        }
        int count=0;
        if(sr+1 <= dr) count+=mazePaths(sr+1,sc,dr,dc,psf+"V"+" ");
        if(sc+1 <= dc) count+=mazePaths(sr,sc+1,dr,dc,psf+"H"+" ");
        return count;

    }

    public static int mazePathMulti(int sr,int sc,int dr,int dc,String psf){
        if(sr==dr && sc==dc) {
            System.out.println(psf);
            return 1;
        }
        int count=0;
        for(int i=1;i<=dr-sr;i++) count+=mazePathMulti(sr+i,sc,dr,dc,psf+"V"+i+" ");
        for(int j=1;j<=dc-sc;j++) count+=mazePathMulti(sr,sc+j,dr,dc,psf+"H"+j+" "); 
        return count;
    }

    public static int floodFill(int sr,int sc,int dr,int dc,int[][] dirs,String[] dname,String ans,boolean[][] vis){
        if(sr==dr && sc==dc) {
            System.out.println(ans);
            return 1;
        }
        int count=0;
        vis[sr][sc]=true;
        for(int i=0;i<4;i++){
            int x=sr+dirs[i][0];
            int y=sc+dirs[i][1];
            if(x>=0 && y>=0 && x<=dr && y<=dc && vis[x][y]==false){
                count+=floodFill(x,y,dr,dc,dirs,dname,ans+dname[i]+" ",vis);
            }
        }
        vis[sr][sc]=false;
        return count;
    }

    public static int floodFillMulti(int sr,int sc,int dr,int dc,int[][] dirs,String[] dname,String ans,boolean[][] vis){
        if(sr==dr && sc==dc){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        vis[sr][sc]=true;
        for(int jump=1;jump<=Math.max(dr,dc);jump++){
            for(int i=0;i<4;i++){
                int x=sr+dirs[i][0]*jump;
                int y=sc+dirs[i][1]*jump;
                if(x>=0 && y>=0 && x<=dr && y<=dc && !vis[x][y]){
                    count+=floodFillMulti(x,y,dr,dc,dirs,dname,ans+dname[i]+jump+" ",vis);
                }
            }
        }
        vis[sr][sc]=false;
        return count;
    }

    public static int allPermutations(String ques,String ans){
        if(ques.length()==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int i=0;i<ques.length();i++){
            char ch=ques.charAt(i);
            String roq=ques.substring(0,i)+ques.substring(i+1);
            count+=allPermutations(roq,ans+ch);
        }
        return count;
    }

    public static int allPermutationsNoDups(String ques,String ans){
        if(ques.length()==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        boolean[] vis=new boolean[26];
        for(int i=0;i<ques.length();i++){
            char ch=ques.charAt(i);
            vis[ch-'a']=true;
            if(!vis[ch-'a']){
                count+=allPermutationsNoDups(ques.substring(0,i)+ques.substring(i+1),ans+ch);
            }
        }
        return count;
    }

    public static int letterCombinations(String[] codes,String ques,String ans){
        if(ques.length()==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        int codeIdx=(int)(ques.charAt(0)-'0');
        String word=codes[codeIdx];
        for(int i=0;i<word.length();i++){
            char ch=word.charAt(i);
            count+=letterCombinations(codes,ques.substring(1),ans+ch);
        }
        return count;
    }

    public static int letterCombi2(String[] codes,String ques,String ans){
        if(ques.length()==0){
            System.out.print(ans+" ");
            return 1;
        }
        int count=0;
        int codeIdx=(int)(ques.charAt(0)-'0');
        String word=codes[codeIdx];
        for(int i=0;i<word.length();i++){
            char ch=word.charAt(i);
            count+=letterCombi2(codes,ques.substring(1),ans+ch);
        }

        if(ques.length()>=2){
            int codeIdx2=(int)(ques.charAt(1)-'0');
            int newIdx=codeIdx2*10 + codeIdx;
            if(newIdx<codes.length){
                String new_word=codes[newIdx];
                for(int i=0;i<new_word.length();i++){
                    char new_ch=new_word.charAt(i);
                    count+=letterCombi2(codes,ques.substring(2),ans+new_ch);
                }
            }
        }
        return count;

    }

    public static int letterCombinations_set2(String digits,int idx,String[] codes,List<String> ans,String res){
        if(idx==digits.length()){
            ans.add(res);
            return 1;
        }
        
        int cidx = (digits.charAt(idx)-'0');
        String code=codes[cidx];
        
        int count=0;
        for(int i=0;i<code.length();i++){
            count+=letterCombinations_set2(digits,idx+1,codes,ans,res+code.charAt(i));
        }

        if(idx<digits.length()-1){
            cidx = cidx*10 + (digits.charAt(idx+1)-'0');
            if(cidx>=10 && cidx<=11){
                code=codes[cidx];
                for(int i=0;i<code.length();i++){
                    count+=letterCombinations_set2(digits,idx+2,codes,ans,res+code.charAt(i));
                }
            } 
        }
        
        return count;
    }
}




