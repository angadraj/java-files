import java.util.*;
import java.lang.*;
class Dictionary
{	public static void WordBreak(String ques,String Line,String ans)
	{	for(int i=0;i<ques.length();i++)
		{	for(int j=i+1;j<=ques.length();j++)
			{	ans=ques.substring(i,j);
				//System.out.println(ans);
				
				if(Line.contains(ans))
				{	System.out.println(1);
				}
				else
				{	System.out.println(0);
				}
				
			}
		}
	}	
	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		String Sentence=sc.nextLine();
		String str=sc.nextLine();
		WordBreak(str,Sentence,"");
	}
}
	