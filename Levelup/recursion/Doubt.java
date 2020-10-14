class Doubt{
    public static void main(String args[]){
        solve();
    }
    public static void solve(){
        String str="abba";
        System.out.println(permWithoutDups(str,"",0));
    }
    public static int permWithoutDups(String str,String ans,int count){
        if(str.length()==0){
            System.out.println(ans);
            return 1;
        }
        int num=0;
        boolean[] vis=new boolean[26];
        for(int i=0;i<str.length();i++){
            if(!vis[str.charAt(i)-'a']){
                num+=permWithoutDups(str.substring(0,i)+str.substring(i+1),ans+str.charAt(i),count+1);
                vis[str.charAt(i)-'a']=true;
            }
        }
        return num;
    }
}