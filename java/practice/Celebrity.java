import java.util.*;
import java.lang.*;
class Celebrity
{	public static void main(String args[])
	{	Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int arr[][]=new int[n][n];
		for(int i=0;i<n;i++)
		{	for(int j=0;j<n;j++)
			{	arr[i][j]=sc.nextInt();
			}
		}
		//we have to find a j which is known by every i..
		//we have to find a i that knows no j.

		//celeb=for any 1 ,i knows j
		//celeb=every one knows him but he does not knows anyone
		//if any i knows any j i is not a celeb
		//in any row 1 comes then that i is not a celeb
		//ie row contains 0 then he can be celeb
		//for ex:i=3 to be celeb then he should not know anyone row=0s
		//every i should know 3

		Stack<Integer> st=new Stack<>();
		for(int i=0;i<n;i++)
		{	st.push(i);
		}
		while(st.size()>1)
		{
			int p1=st.pop();
			int p2=st.pop();
			if(arr[p1][p2]==1)
			{	st.push(p2);
			}
			else
			{	st.push(p1);
			}
		}
		int pc=st.pop();
		boolean isCeleb=true;
		//check for actual celeb condition
		for(int i=0;i<n;i++)
		{	if(pc!=i && (arr[i][pc]==0||arr[pc][i]==1))
			{	isCeleb=false;
				break;
			}
		}
		if(isCeleb)
		{	System.out.println(pc);
		}
		else
		{	System.out.println("null");
		}	
	}
}	