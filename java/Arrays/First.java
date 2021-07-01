class First{
    public static void main(String args[]){
        solve();
    }
    public static int kadaneAlgo(int[] arr){
        int max_so_far=-(int)(1e8);
        int max_till_here=0;
        for(int i=0;i<arr.length;i++){
            max_till_here+=arr[i];
            if(max_till_here>max_so_far){
                max_so_far=max_till_here;
            }
            if(max_till_here<0){
                max_till_here=0;
            }
        }
        return max_so_far;
    }
    public static void solve(){
        int arr[]={-2, -3, 4, -1, -2, 1, 5, -3};
        System.out.print(kadaneAlgo(arr));
    }
}