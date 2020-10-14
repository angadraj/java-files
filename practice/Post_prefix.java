import java.util.*;
import java.lang.*;
public class Post_prefix {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String exp=sc.nextLine();
        conversion(exp);
    }

    public static void conversion(String exp){
        Stack<String> postfix=new Stack<>();
        Stack<String> prefix=new Stack<>();
        Stack<Character> optrs=new Stack<>();

        for(int i=0;i<exp.length();i++){
            char ch=exp.charAt(i);
            if(isDigit(ch)){
                postfix.push(""+ch);
                prefix.push(""+ch);
            }
            else if(ch=='('){
                optrs.push(ch);
            }
            else if(ch==')'){
                while(optrs.peek()!='('){
                    //prefix
                    char optr=optrs.pop();
                    String preop2=prefix.pop();
                    String preop1=prefix.pop();
                    prefix.push(optr+preop1+preop2);
                    //postfix
                    String postop2=postfix.pop();
                    String postop1=postfix.pop();
                    postfix.push(postop1+postop2+optr);
                }
                optrs.pop();
            }
            else if(ch=='+'||ch=='-'||ch=='*'||ch=='/'){
                while(optrs.size()>0 && optrs.peek()!='(' && pred(optrs.peek())>=pred(ch)){
                    char optr=optrs.pop();
                    String preop2=prefix.pop();
                    String preop1=prefix.pop();
                    prefix.push(optr+preop1+preop2);

                    String postop2=postfix.pop();
                    String postop1=postfix.pop();
                    postfix.push(postop1+postop2+optr);
                }
                optrs.push(ch);
            }
        }
            //when string is exhausted
        while(optrs.size()>0){
            char optr=optrs.pop();
            String preop2=prefix.pop();
            String preop1=prefix.pop();
            prefix.push(optr+preop1+preop2);
            
            String postop2=postfix.pop();
            String postop1=postfix.pop();
            postfix.push(postop1+postop2+optr);
        }      
        System.out.println(postfix.peek());
        System.out.println(prefix.peek());
    }
    public static int pred(char optr){
        if(optr=='+'||optr=='-'){
            return 1;
        }
        else{
            return 2;
        }
    }
    public static boolean isDigit(char ch){
        if((ch>='0'&& ch<='9')||(ch>='a' && ch<='z')||(ch>='A' && ch<='Z')){
            return true;
        }
        else{
            return false;
        }
    }
    
}