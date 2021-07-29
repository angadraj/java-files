import java.util.*;
class ques {

    // remove putermost parenth, but it removes all braces
    public String removeOuterParentheses(String s) {
        int open = 0;
        int close = 0;
        int bf_old = -1;
        String ans = "";
        
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int bf_new = -1;
            
            if (ch == '(') open++;
            else if (ch == ')') {
                close++;
                bf_new = open - close;
                if (bf_new > bf_old || bf_new == bf_old) {
                    ans += "()";
                }
            }
            bf_old = bf_new;
        }
        return ans;
    }

    public void solve() {

    }

    public static void main(String args[]) {
        solve();
    }
}