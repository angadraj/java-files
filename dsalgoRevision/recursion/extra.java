import java.util.*;
class extra {
    public static void main(String args[]){
        solve();
    }    
    public static void wordBreak(){
        String ques = "ilikesamsungandmango";
        String words[]= {"mobile", " samsung", "sam", "sung", "man", "mango", "icecream", "and", "go", "i", "like", "ice", "cream", "ilike"};
        HashSet<String> set = new HashSet<>();
        int maxlen = 0;
        for(String word : words){
            if(!set.contains(word)){
                set.add(word);
                maxlen = Math.max(maxlen, word.length());
            }
        }
        wordBreakHelper(set, ques, 0,"",maxlen);
    }

    public static int wordBreakHelper(HashSet<String> set, String ques, int idx, String asf, int maxlen){
        if(idx == ques.length()){
            System.out.println(asf);
            return 1;
        }
        int count = 0;
        for(int i = idx; i < (idx + maxlen + 1) && i < ques.length(); i++){
            String validWord = ques.substring(idx, i + 1);
            if(set.contains(validWord)){
                count += wordBreakHelper(set, ques, i + 1, asf + validWord + " ", maxlen);
            }
        }
        return count;
    }
    public static void solve(){
        wordBreak();
    }
}
