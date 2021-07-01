class Crypto{
    public static void main(String args[]){
        solve();
    }
    public static void solve(){
        Extr_gen_unique();
    }
    static String a="send";  
    static String b="more";   
    static String c="money";
    static int[] charToInt=new int[26];
    static boolean[] used=new boolean[10];
    public static void Extr_gen_unique(){
        String parent=a+b+c;
        //note their freq
        int[] freq=new int[26];
        for(int i=0;i<parent.length();i++){
            char ch=parent.charAt(i);
            freq[ch-'a']++;
        }
        String parent2="";
        //extract all chars appearing once ie: freq>0
        for(int i=0;i<freq.length;i++){
            if(freq[i]>0){
                parent2+=(char)(i+'a');
            }
        }
        // System.out.println(parent2);
        System.out.println(generate_Nos(parent2,0));
    }  
    //generate nos for parent string
    public static int generate_Nos(String parent,int idx){
        if(idx==parent.length()){
            int x=stringToNumber(a);
            int y=stringToNumber(b);
            int z=stringToNumber(c);
            if(x+y==z && charToInt[a.charAt(0)-'a']!=0 && charToInt[c.charAt(0)-'a']!=0 && charToInt[b.charAt(0)-'a']!=0){
                System.out.print(x + "\n+" + y + "\n"  + z);
                return 1;
            }
            return 0;
        }
        int count=0;
        for(int num=0;num<=9;num++){
            if(!used[num]){
                charToInt[parent.charAt(idx)-'a']=num;
                used[num]=true;
                count+=generate_Nos(parent,idx+1);
                charToInt[parent.charAt(idx)-'a']=0;
                used[num]=false;
            }
        }
        return count;
    }
    public static int stringToNumber(String child){
        int num=0;
        for(int i=0;i<child.length();i++){
            char ch=child.charAt(i);
            int val=charToInt[ch-'a'];
            num=val+num*10;
        }
        return num;
    }
}