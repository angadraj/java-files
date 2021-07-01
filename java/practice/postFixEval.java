import java.util.*;
import java.lang.*;
public class postFixEval {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String exp=sc.nextLine();
        conversion_Eval(exp);
    }
    public static void conversion_Eval(String exp){
        Stack<String> prefix=new Stack<>();
        Stack<String> infix=new Stack<>();
        Stack<Integer> eval=new Stack<>();
        for(int i=0;i<exp.length();i++){
            char ch=exp.charAt(i);
            if(ch>='0' && ch<='9'){
                infix.push(""+ch);
                prefix.push(""+ch);
                eval.push(ch-'0');
            }
            else{
                String inop2=infix.pop();
                String inop1=infix.pop();
                infix.push("("+inop1+ch+inop2+")");

                String preop2=prefix.pop();
                String preop1=prefix.pop();
                prefix.push(ch+preop1+preop2);

                int n2=eval.pop();
                int n1=eval.pop();
                eval.push(solve(n1,n2,ch));
            }
        }
        System.out.println(eval.peek());
        System.out.println(infix.peek());
        System.out.println(prefix.peek());
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