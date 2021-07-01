import java.util.*;
import java.lang.*;
class Max_Profit_Infinite
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int price[]=new int[n];
		for(int i=0;i<n;i++)
		{	price[i]=sc.nextInt();
		}
		//max profit with oo transactions
		
		//we will use running profit for previous to current day
		//when we buy it must be less than that of when we sell.
		
		int maxProfit=0;
		for(int i=1;i<n;i++)
		{	if(price[i]>price[i-1])
			{	maxProfit+=price[i]-price[i-1];
			}
		}
		System.out.println(maxProfit);
	}
	
}