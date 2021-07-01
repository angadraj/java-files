import java.util.*;
import java.lang.*;
class Histogram
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int[] arr=new int[n];
		for(int i=0;i<n;i++)
		{	arr[i]=sc.nextInt();
		}
		//--------------
		//to find the area we need to know that in how much area it covers
		Stack<Integer> st=new Stack<>();
		int[] sol=new int[n];
		for(int i=0;i<n;i++)
		{	while(st.size()>0 && arr[i]<=arr[st.peek()])
			{	st.pop();
			}
			if(st.size()==0)
			{	sol[i]=-1;
			}
			else
			{	sol[i]=st.peek();
			}
			st.push(i);		
		}
		st=new Stack<>();
		int sor[]=new int[n];
		for(int i=n-1;i>=0;i--)
		{	while(st.size()>0 && arr[i]<=arr[st.peek()])
			{	st.pop();
			}
			if(st.size()==0)
			{	sor[i]=n;
			}
			else
			{	sor[i]=st.peek();
			}
			st.push(i);
		}
		//w=rb-lb-1;
		int area=arr[0]*(sor[0]-sol[0]-1);
		for(int i=1;i<n;i++)	
		{	int h=arr[i];
			int w=sor[i]-sol[i]-1;
			int carea=h*w;
			if(carea>area)
			{	area=carea;
			}
		}
		System.out.println(area);
	}
}
	

