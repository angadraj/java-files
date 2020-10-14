import java.util.*;
import java.lang.*;
class Balance_Brackets
{	public static boolean isBal(String str)
	{	Stack<Character> st=new Stack<>();
		for(int i=0;i<str.length();i++)
		{	char ch=str.charAt(i);
			if(ch=='(' || ch=='{'||ch=='[')
			{	st.push(ch);
			}
			else if(ch==')')
			{	if(st.size()==0 || st.peek()!='(')
				{	return false;
				}
				else	
				{	st.pop();
				}
			}
			else if(ch=='}')
			{	if(st.size()==0 || st.peek()!='{')
				{	return false;
				}
				else	
				{	st.pop();
				}
			}
			else if(ch==']')
			{	if(st.size()==0 || st.peek()!='[')
				{	return false;
				}
				else	
				{	st.pop();
				}
			}
		}
	//still there is any element means no match and string got empty
	return st.size()==0;
	}
	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		String str=sc.nextLine();
		System.out.println(isBal(str));
	}
}