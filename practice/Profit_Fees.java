import java.util.*;
import java.lang.*;
class Profit_Fees
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int price[]=new int[n];
		for(int i=0;i<n;i++)
		{	price[i]=sc.nextInt();
		}
		int fee=sc.nextInt();
		//--------------------------
		
		int sell=0;
		int buy=0-price[0];
		for(int i=1;i<n;i++)
		{	int nbuy=Math.max(sell-price[i],buy);
			int nsell=Math.max(price[i]+buy-fee,sell);
		
			buy=nbuy;	
			sell=nsell;
		}
		System.out.println(sell);
	}

}