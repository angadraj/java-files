import java.util.*;
import java.lang.*;
class K_Transactions
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int price[]=new int[n];
		for(int i=0;i<n;i++)
		{	price[i]=sc.nextInt();
		}
		int k=sc.nextInt();

		//-------------------------------------------------
		int[][] dp=new int[k+1][n];
		for(int i=1;i<=k;i++)
		{	if(i==1)
			{	int min=price[0];
				for(int j=1;j<n;j++)
				{	if(price[j]<min)
					{	min=price[j];
					}
					dp[i][j]=Math.max(price[j]-min,dp[i][j-1]);
	       		        }
			
			}
			else
			{	for(int j=1;j<n;j++)
				{	int max=0;
					for(int p=0;p<j;p++)
					{	int profit=price[j]-price[p]+dp[i-1][p];
						if(profit>max)
						{	max=profit;
						}
						// call NO
					}
					dp[i][j]=Math.max(max,dp[i][j-1]);		
				}			
			}
		}
		System.out.println(dp[k][n-1]);
	}
}