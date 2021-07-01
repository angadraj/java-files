import java.util.*;
import java.lang.*;
class Prime
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		for(int i=2;i<=n;i++)
		{	int count=0;
			for(int div=2;div*div<=n;div++)
			{	if(i%div==0)
				{	count++;
					break;
				}
			}
			if(count==0)
			{
				System.out.println(i);
			}
		}
	}
}
