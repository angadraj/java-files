import java.util.*;
import java.lang.*;
class Rotate
{	public static void swap(int[] a,int i,int j)
	{	int temp;
		while(i<j)
		{	temp=a[i];
			a[i]=a[j];
			a[j]=temp;
			i++;j--;
		}
	}

	public static int[] left(int[] a,int d)
	{	swap(a,0,d-1);
		swap(a,d,a.length-1);
		swap(a,0,a.length-1);
		return a;
	}
		
	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int a[]=new int[n];
		for(int i=0;i<n;i++)
		{	a[i]=sc.nextInt();
		}
		int d=sc.nextInt();

		int ans[]=left(a,d);	
		for(int i=0;i<n;i++)
		{	
			System.out.print(ans[i]+" ");
		}
	}

}