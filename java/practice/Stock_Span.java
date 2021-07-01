import java.util.*;
import java.lang.*;
class Stock_Span
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int arr[]=new int[n];
		Stack<Integer> st=new Stack<>();s
		for(int i=0;i<arr.length;i++)
		{	arr[i]=sc.nextInt();
		}
		int[] ans=new int[n];
		//-------------
		for(int i=0;i<arr.length;i++)
		{	while(st.size()>0 && arr[i]>=arr[st.peek()])
			{	st.pop();
			}
			if(st.size()==0)
			{	ans[i]=i+1;
			}
			else
			{	ans[i]=i-st.peek();
			}
			st.push(i);
		}
		for(int i=0;i<ans.length;i++)
		{	System.out.print(ans[i]+" ");
		}
	}
}