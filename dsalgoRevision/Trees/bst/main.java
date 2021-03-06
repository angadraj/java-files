import java.util.ArrayList;
import java.util.Stack;
class main {
    private static class Node {
        Node left;
        Node right;
        int data;
        public Node(int data, Node left, Node right) {
            this.left = left;
            this.right = right;
            this.data = data;
        }
    }

    public static Node construct(int[] arr, int lo, int hi) {
        if(lo > hi) return null;
        int mid = (lo + hi)/2;
        Node root = new Node(arr[mid], null, null);
        root.left = construct(arr, lo, mid - 1);
        root.right = construct(arr, mid + 1, hi);
        return root;
    }

    public static void display(Node root) {
        if(root == null) {
            return;
        }
        display(root.left);
        System.out.print(root.data + " ");
        display(root.right);
    }

    public static int[] sizeSum(Node root) {
        if(root == null) {
            return new int[]{0, 0};
        }
        int lans[] = sizeSum(root.left);
        int rans[] = sizeSum(root.right);
        int myans[] = new int[2];
        myans[0] = lans[0] + rans[0] + 1;
        myans[1] = lans[1] + rans[1] + root.data;
        return myans;
    }

    public static int min(Node root) {
        int ans = (int)(1e8);
        while(root != null) {
            ans = Math.min(root.data, ans);
            root = root.left;
        }   
        return ans;
    }

    public static int max(Node root) {
        int ans = -(int)(1e8);
        while(root != null) {
            ans = Math.max(root.data, ans);
            root = root.right;
        }   
        return ans;
    }

    public static boolean find(Node root, int val) {
        if(root == null) return false;
        if(root.data == val) return true;
        if(root.data > val) {
            boolean lans = find(root.left, val);
            if(lans) return true;
        } else if(root.data < val) {
            boolean rans = find(root.right, val);
            if(rans) return true;
        } 
        return false;
    }

    public static Node add(Node root, int data) {
        if(root == null) {
            Node n = new Node(data, null, null);
            return n;
        }
        if(data < root.data) {
            root.left = add(root.left, data);
        } else if(data > root.data) {
            root.right = add(root.right, data);
        } else {
            // it's equal
        }
        return root;
    }

    public static int getLeftMax(Node root) {
        if(root.right != null) {
            int ans = getLeftMax(root.right);
            return ans;
        }
        return root.data;
    }

    public static Node remove(Node root, int val) {
        if(val < root.data) {
            root.left = remove(root.left, val);
        } else if(val > root.data) {
            root.right = remove(root.right, val);
        } else {
            if(root.left == null && root.right == null) {
                return null;
            } else if(root.left == null && root.right != null) {
                return root.right;
            } else if(root.right == null && root.left != null ) {
                return root.left;
            } else if(root.left != null && root.right != null) {
                //find max in the left
                int lmax = getLeftMax(root.left);
                root.data = lmax;
                root.left = remove(root.left, lmax);
                return root;
            }
        }
        return root;
    }

    static int sum = 0;
    public static void rwsol(Node root) {
        if(root == null) return;
        rwsol(root.right);
        int temp = root.data;
        root.data = sum;
        sum += temp;
        rwsol(root.left);
    }

    public static int lca(Node root, int d1, int d2) {
        // write your code here
        Node ans = helper(root, d1, d2);
        return ans.data;
    }

    public static Node helper(Node root, int val1, int val2) {
        if(root == null) return null;
        Node lans = null, rans = null;
        if(root.data > val1 && root.data > val2) {
            lans = helper(root.left, val1, val2);
        } else if(root.data < val1 && root.data < val2) {
            rans = helper(root.right, val1, val2);
        } else {
            return root;
        }
        return lans == null ? rans : lans;
    }

    public static void pir(Node root, int d1, int d2) {
        if(root == null) return;
        if(root.data > d1 && root.data > d2) {
            pir(root.left, d1, d2);
        } else if(root.data < d1 && root.data < d2) {
            pir(root.right, d1, d2);
        } else {
            pir(root.left, d1, d2);
            if(root.data >= d1 && root.data <= d2) System.out.println(root.data);
            pir(root.right, d1, d2);
        }
    }

    public static boolean helper(Node root, int val) {
        if(root == null) return false;
        if(root.data > val) return helper(root.left, val);
        else if(root.data < val) return helper(root.right, val);
        else return true;
    }
    public static void tsp(Node parent, Node root, int tar) {
          if(root == null) return;
          tsp(parent, root.left, tar);
          int comp = tar - root.data;
          if(root.data < comp) {
              if(helper(parent, comp)){
                  System.out.println(root.data + " " + comp);
              }
          }
          tsp(parent, root.right, tar);
    }

    public static void fill(Node root, ArrayList<Integer> ans) {
        if(root == null) return;
        fill(root.left, ans);
        ans.add(root.data);
        fill(root.right, ans);
    }
  
    public static void app_2(Node root, int tar) {
        ArrayList<Integer> ans = new ArrayList<>();
        fill(root, ans);
        int i = 0, j = ans.size() -1;
        while(i < j) {
            int left = ans.get(i);
            int right = ans.get(j);
            if(left + right == tar) {
                System.out.println(left + " " + right);
                i++; j--;
            } else if(left + right < tar) {
                i++;
            } else {
                j--;
            }
        }
    }

    public static void app_3(Node root, int tar) {
        Stack<pair> ls = new Stack<>();
        Stack<pair> rs = new Stack<>();
        
        ls.push(new pair(root, -1));
        rs.push(new pair(root, -1));
        
        pair left = getLeft(ls);
        pair right = getRight(rs);
        
        while(left.node.data < right.node.data) {
            
            if(left.node.data + right.node.data == tar) {
                System.out.println(left.node.data + " " + right.node.data);
                left = getLeft(ls);
                right = getRight(rs);
            } else if(left.node.data + right.node.data < tar) {
                left = getLeft(ls);
            } else {
                right = getRight(rs);
            }
        }
    }
    
    static class pair {
        Node node;
        int state;
        public pair(Node node, int state) {
            this.node = node;
            this.state = state;
        }
    }
    
    public static pair getLeft(Stack<pair> ls) {
        while(ls.size() > 0) {
            pair cp = ls.peek();
            if(cp.state == -1) {
                cp.state++;
                if(cp.node.left != null) {
                    ls.push(new pair(cp.node.left, -1));
                }
            } else if(cp.state == 0) {
                cp.state++;
                if(cp.node.right != null) {
                    ls.push(new pair(cp.node.right, -1));
                }
                return cp;
            } else if(cp.state == 1) {
                ls.pop();
            }
        }
        return null;
    }
    
    public static pair getRight(Stack<pair> rs) {
        while(rs.size() > 0) {
            pair cp = rs.peek();
            if(cp.state == -1) {
                cp.state++;
                if(cp.node.right != null) {
                    rs.push(new pair(cp.node.right, -1));
                }
            } else if(cp.state == 0) {
                cp.state++;
                if(cp.node.left != null) {
                    rs.push(new pair(cp.node.left, -1));
                }
                return cp;
            } else if(cp.state == 1) {
                rs.pop();
            }
        }
        return null;
    }

    public static void solve() {
        int[] arr = {12, 25, 30, 37, 50, 62, 70, 75, 87};
        Node root = construct(arr, 0, arr.length - 1);
        app_3(root, 100);
    }

    public static void main(String args[]) {
        solve();
    }
}
