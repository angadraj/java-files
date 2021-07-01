import java.util.*;
import java.lang.*;
class KLargest
{	public static void KL(int[] arr ,int idx,int k)
	{	if(k==0)
		{	
			return;
		}
		
		System.out.println(arr[idx]);
		KL(arr,idx-1,k-1);
	}
	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int k=sc.nextInt();
		int arr[]=new int[n];
		for(int i=0;i<n;i++)
		{	arr[i]=sc.nextInt();
		}
		Arrays.sort(arr);
		KL(arr,arr.length-1,k);
	}
}