import java.util.*;
import java.lang.*;
class Overlapping
{	
	static class time implements Comparable<time>
	{	int st;
		int et;
		time(int s,int e)
		{	st=s;
			et=e;
		}
		
		public int compareTo(time other)
		{	return this.st-other.st;
		}
	
	}
	public static void mergeMeetings(int[][] arr)
	{	time[] intervals=new time[arr.length];	
		for(int i=0;i<arr.length;i++)
		{	time ct=new time(arr[i][0],arr[i][1]);
			intervals[i]=ct;
		}
		Arrays.sort(intervals);
		Stack<time> st1=new Stack<>();
		st1.push(intervals[0]);//1-12
		for(int i=1;i<intervals.length;i++)
		{	time ct=intervals[i];//10-18,5-8,13-18
			if(st1.peek().et>=ct.st)
			{	st1.peek().et=Math.max(ct.et,st1.peek().et);
			}
			else
			{	st1.push(ct);
			}
		}
		Stack<time> st2=new Stack<>();
		while(st1.size()>0)
		{	st2.push(st1.pop());
		}
		while(st2.size()>0)
		{	time ct=st2.pop();
			System.out.println("-----------------");
			System.out.println(ct.st +" " + ct.et);
		}

	}

	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int arr[][]=new int[n][2];
		for(int i=0;i<n;i++)
		{	for(int j=0;j<2;j++)
			{	arr[i][j]=sc.nextInt();
			}
		}
		mergeMeetings(arr);
	}
}