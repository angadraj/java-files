import java.util.*;
import java.lang.*;
class Right_Greatest
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int[] arr=new int[n];
		int[] ans=new int[n];
		Stack<Integer> st=new Stack<>();
		for(int i=0;i<n;i++)
		{	arr[i]=sc.nextInt();
		}
		//----------------
		for(int i=arr.length-1;i>=0;i--)
		{	while(st.size()>0 && st.peek()<=arr[i])
			{	st.pop();
			}
			//for last element 
			if(st.size()==0)
			{	ans[i]=-1;
			}
			else
			{	ans[i]=st.peek();
			}
			st.push(arr[i]);
		}
		System.out.println(ans.length);
		
		for(int i=0;i<ans.length;i++)
		{	System.out.print(ans[i]+" ");
		}
	}
}