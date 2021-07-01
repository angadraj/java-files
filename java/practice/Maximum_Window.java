import java.util.*;
import java.lang.*;
class Maximum_Window
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int[] arr=new int[n];
		int[] nge=new int[n];
		for(int i=0;i<n;i++)
		{	arr[i]=sc.nextInt();
		}
		// int k=sc.nextInt();
		// int ans[]=new int[n-k+1];
		//brute force 
		Stack<Integer> st=new Stack<>();
		for(int i=arr.length-1;i>=0;i--){
			while(st.size()>0 && arr[i]>=arr[st.peek()]){
				st.pop();
			}
			if(st.size()==0){
				nge[i]=arr.length;
			}
			else{
				nge[i]=st.peek();
			}
			st.push(i);
		}

		for(int i=0;i<nge.length;i++)
		{	System.out.print(nge[i]+" ");
		}
		 int k=sc.nextInt();
		 for(int i=0;i<arr.length-k;i++){
			 int j=i;
			 while(nge[j]<i+k){
				 j=nge[j];
			 }
			 System.out.print(arr[j]+" ");
		 }

	}
}