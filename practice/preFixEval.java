import java.util.*;
import java.lang.*;
public class preFixEval {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String exp=sc.nextLine();
        conversion_Eval(exp);
    }
    public static void conversion_Eval(String exp){
        Stack<String> postfix=new Stack<>();
        Stack<String> infix=new Stack<>();
        Stack<Integer> eval=new Stack<>();
        for(int i=exp.length()-1;i>=0;i--){
            char ch=exp.charAt(i);
            if(ch>='0' && ch<='9'){
                infix.push(""+ch);
                postfix.push(""+ch);
                eval.push(ch-'0');
            }
            else{
                String postop1=postfix.pop();
                String postop2=postfix.pop();
                postfix.push(postop1+postop2+ch);

                String inop1=infix.pop();
                String inop2=infix.pop();
                infix.push("("+inop1+ch+inop2+")");

                int n1=eval.pop();
                int n2=eval.pop();
                eval.push(solve(n1,n2,ch));
            }
        }
        System.out.println(eval.peek());
        System.out.println(infix.peek());
        System.out.println(postfix.peek());
    }
    public static int solve(int n1,int n2,char ch){
        if(ch=='+'){
            return n1+n2;
        }
        else if(ch=='-'){
            return n1-n2;
        }
        else if(ch=='*'){
            return n1*n2;
        }
        else{
            return n1/n2;
        }
    }
}