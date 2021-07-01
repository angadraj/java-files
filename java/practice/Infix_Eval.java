import java.util.*;
import java.lang.*;
class Infix_Eval{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String exp=sc.nextLine();
        System.out.println(eval(exp));
    }

    public static int eval(String exp){
        Stack<Integer> nums=new Stack<>();
        Stack<Character> optrs=new Stack<>();
        for(int i=0;i<exp.length();i++){
           char ch=exp.charAt(i); 
           if(ch>='0' && ch<='9'){
                nums.push(ch-'0');
           }
           else if(ch=='('){
                optrs.push(ch);
           }
           else if(ch==')'){
                while(optrs.peek()!='('){
                    int op2=nums.pop();
                    int op1=nums.pop();
                    char optr=optrs.pop();
                    int ans=solve(op2,op1,optr);
                    nums.push(ans);
                }
                optrs.pop();
           }
           else if(ch=='+'||ch=='*'||ch=='-'||ch=='/'){
                while(optrs.size()>0 && optrs.peek()!='(' && pred(optrs.peek())>=pred(ch)){
                    int op2=nums.pop();
                    int op1=nums.pop();
                    char optr=optrs.pop();
                    int ans=solve(op2,op1,optr);
                    nums.push(ans);
                }
                optrs.push(ch);
            }
        }
        while(optrs.size()>0){
            int op2=nums.pop();
            int op1=nums.pop();
            char optr=optrs.pop();
            int ans=solve(op2,op1,optr);
            nums.push(ans);
        }
        return nums.peek();
    }

    public static int solve(int op2,int op1,char optr){
        if(optr=='+'){
            return op1+op2;
        }
        else if(optr=='-'){
            return op1-op2;
        }
        else if(optr=='/'){
            return op1/op2;
        }
        else{
            return op1*op2;
        }
    }

    public static int pred(char optr){
        if(optr=='+'||optr=='-'){
            return 1;
        }
        else{
            return 2;
        }
    }
}