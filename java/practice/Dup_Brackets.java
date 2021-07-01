import java.util.*;
import java.lang.*;
class Dup_Brackets
{	public static boolean Dup(String str)
	{	
		Stack<Character> st=new Stack<>();
		// we have return t/f for duplicate braces
		//assumption is braces are even
		for(int i=0;i<str.length();i++)
		{	char ch=str.charAt(i);
			if(ch==')')
			{	if(st.peek()=='(')
				{	return true;
				}
				else
				{	while(st.peek()!='(')
					{	st.pop();
					}
					st.pop();
				}
			}
			else
			{	st.push(ch);
			}
		}
		return false;	
	}
	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		String str=sc.nextLine();
		System.out.println(Dup(str));
	}	
}
