import java.util.*;
import java.lang.*;
class Max_Profit
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int price[]=new int[n];
		for(int i=0;i<n;i++)
		{	price[i]=sc.nextInt();
		}
		// 1 transaction allowed
		
		int min=price[0];
		int max_Profit=0,c_Profit=0;
		for(int i=0;i<n;i++)
		{	if(price[i]<min)
			{	min=price[i];
			}
			c_Profit=price[i]-min;
			max_Profit=Math.max(max_Profit,c_Profit);
		}
		System.out.println(max_Profit);	
	}

}