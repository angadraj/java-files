class l001{
    public static void main(String args[]){
        solve();
    }
    public static int binary_search(int[] arr,int ele){
        int si=0,ei=arr.length-1;
        while(si<=ei){
            int mid=(si+ei)>>1;
            if(arr[mid]==ele){
                return mid;
            }
            else if(arr[mid]>ele){
                ei=mid-1;
            }
            else{
                si=mid+1;
            }
        }
        return -1;
    }
    public static int first_occ(int[] arr,int ele){
        int si=0,ei=arr.length-1,mid=0;
        while(si<=ei){
            mid=(si+ei)>>1;
            if(arr[mid]==ele){
                if(mid-1>=0 && arr[mid-1]==arr[mid]) ei=mid-1;
                else return mid;
            }
            else if(arr[mid]>ele){
                ei=mid-1;
            }
            else{
                si=mid+1;
            }
        }
        return mid;
    }
    public static int last_occ(int[] arr,int ele){
        int si=0,ei=arr.length-1,mid=0;
        while(si<=ei){
            mid=(si+ei)>>1;
            if(arr[mid]==ele){
                if(mid+1<arr.length && arr[mid+1]==arr[mid]) si=mid+1;
                else return mid;
            }
            else if(arr[mid]>ele){
                ei=mid-1;
            }
            else{
                si=mid+1;
            }
        }
        return mid;
    }
    public static int nearestElement(int[] arr,int ele){
        int si=0,ei=arr.length-1,mid=0;
        while(si<=ei){
            mid=(si+ei)>>1;
            if(arr[mid]==ele) return mid;
            else if(arr[mid]>ele){
                ei=mid-1;
            }
            else si=mid+1;
        }
        if(ei<0) return si;
        else if(si>=arr.length) return ei;
        else{
            return (ele-arr[ei] < arr[si]-ele)?ei:si;
        }
    }
    public static int findPos(int[] arr,int ele){
        int si=0,ei=arr.length;
        while(si<ei){
            int mid=(si+ei)>>1;
            if(arr[mid]<ele) si=mid+1;
            else ei=mid;
        }
        return ei;
    }
    public static void solve(){
        int[] arr={10,12,14,16,20,30,40,48};
        // System.out.println(binary_search(arr,18));
        // System.out.println(first_occ(arr,56));
        // System.out.println(last_occ(arr,56));
        // System.out.println(nearestElement(arr,13));
        System.out.println(findPos(arr,50));
    }
    
}