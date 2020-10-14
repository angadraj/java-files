import java.util.*;
import java.lang.*;
class Max_Profit_TwoTr
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int price[]=new int[n];
		for(int i=0;i<n;i++)
		{	price[i]=sc.nextInt();
		}
		//transactions=2
		//---------------------------------
		//min pe buy and c[idx] pe sell
		int left[]=new int[n];

		int min=price[0];
		for(int i=1;i<n;i++)
		{	if(price[i]<min)
			{	min=price[i];
			}
			left[i]=Math.max(left[i-1],price[i]-min);
		}

		//buy at c[idx] and sell at max

		int right[]=new int[n];
		int max=price[n-1];
		for(int i=n-2;i>=0;i--)
		{	if(price[i]>max)
			{	max=price[i];
			}
			right[i]=Math.max(right[i+1],max-price[i]);
		}
	
		int profit=right[0]+left[0];
		for(int i=1;i<n;i++)
		{	if(profit<right[i]+left[i])
			{	profit=right[i]+left[i];
			}
		}
		System.out.println(profit);	
	}
}