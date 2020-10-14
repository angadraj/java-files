import java.util.Stack;
import java.util.Arrays;
class Questions{
    public static void main(String args[]){
        solve();
    }
    public static void solve(){
        int[] arr1={4,5,2,25};
        int[] arr2={13,7,6,12};
        int[] ans=ngor(arr1);
        // System.out.println(ans);
        display(ans);
    }
    public static void display(int[] arr){
        for(int i=0;i<arr.length;i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    // @Override
    // public String toString(){
    //     StringBuilder sb=new StringBuilder();
    //     sb.append("[");
    //     for(int i=0;i<this  .length;i++){
    //         sb.append(arr[i]);
    //         if(i!=0) sb.append(",");
    //     }
    //     sb.append("]");
    //     return sb.toString();
    // }
    public static int[] ngor(int[] arr){
        Stack<Integer> st=new Stack<>();
        int n=arr.length;
        int ans[]=new int[n];
        Arrays.fill(ans,n);
        for(int i=0;i<n;i++){
            while(st.size()!=0 && arr[st.peek()]<arr[i]){
                ans[st.pop()]=i;
            }
            st.push(i);
        }
        return ans;
    }
    public static int[] ngol(int[] arr){
        Stack<Integer> st=new Stack<>();
        int[] ans=new int[arr.length];
        Arrays.fill(ans,-1);
        for(int i=arr.length-1;i>=0;i--){
            while(st.size()!=0 && arr[st.peek()]<arr[i]){
                ans[st.pop()]=i;
            }
            st.push(i);
        }
        return ans;
    }
    public static int[] nsor(int[] arr){
        int n=arr.length;
        int[] ans=new int[n];
        Stack<Integer> st=new Stack<>();
        Arrays.fill(ans,n);
        for(int i=0;i<n;i++){
            while(st.size()!=0 && arr[st.peek()]>arr[i]){
                ans[st.pop()]=i;
            }
            st.push(i);
        }
        return ans;
    }
    public static int[] nsol(int[] arr){
        int n=arr.length;
        int[] ans=new int[n];
        Stack<Integer> st=new Stack<>();
        Arrays.fill(ans,-1);
        for(int i=n-1;i>=0;i--){
            while(st.size()!=0 && arr[st.peek()]>arr[i]){
                ans[st.pop()]=i;
            }
            st.push(i);
        }
        return ans;
    }
}