import java.util.*;
class main2 {
    static class Node {
        Node left = null;
        Node right = null;
        int data;   
        public Node(int data) {
            this.data = data;
        }
    }

    public static Node construct(int[] arr, int si, int ei) {
        if(si > ei) return null;
        int mid = (si + ei) / 2;
        Node nn = new Node(arr[mid]);
        nn.left = construct(arr, si, mid - 1);
        nn.right = construct(arr, mid + 1, ei);
        return nn;
    }
    static String pre = "", in = "", post = "";
    public static void display(Node root) {
        if(root == null) return;
        pre += root.data + " ";
        display(root.left);
        in += root.data + " ";
        display(root.right);
        post += root.data + " ";
    }

    public static int[] diameter(Node root) {
        if(root == null) {
            return new int[]{-1, 0};
        }
        int lans[] = diameter(root.left);
        int rans[] = diameter(root.right);
        int myans[] = new int[2];
        myans[1] = Math.max(lans[0] + rans[0] + 2, Math.max(lans[1], rans[1]));
        myans[0] = Math.max(lans[0], rans[0]) + 1;
        return myans;
    }

    public static boolean find(Node root, int val) {
        if(root == null) return false;
        if(root.data == val) return true;
        else if(root.data > val) {
            boolean lans = find(root.left, val);
            if(lans) return true;
        } else if(root.data < val) {
            boolean rans = find(root.right, val);
            if(rans) return true;
        }
        return false;
    }

    public static void minMax(Node root) {
        Node h1 = root, h2 = root;
        while(h1.left != null) {
            h1 = h1.left;
        }
        while(h2.right != null) {
            h2 = h2.right;
        }
        System.out.print("min " + h1.data + " max " + h2.data);
    }

    static int idx = 0;
    public static Node bstFromPre(int[] arr, int lr, int rr) {
        if(idx == arr.length || arr[idx] > rr) return null;
        Node root = new Node(arr[idx]);
        idx++;
        root.left = bstFromPre(arr, lr, root.data);
        root.right = bstFromPre(arr, root.data, rr);
        return root;
    }

    public static void print(Node root) {
        if(root == null) return;
        String ans = "";
        ans += root.data + "-> " ;
        if(root.left != null) ans += root.left.data + " , ";
        if(root.right != null) ans += root.right.data + " ";
        System.out.println(ans);
        print(root.left);
        print(root.right);
    }

    // given arr find the height using preorder
    public static int heightFromPreOrder(int[] arr, int lr, int rr) {
        if(idx == arr.length || arr[idx] > rr) return -1;
        int node_data = arr[idx];
        idx++;
        int lans = heightFromPreOrder(arr, lr, node_data);
        int rans = heightFromPreOrder(arr, node_data, rr);
        return Math.max(lans, rans) + 1;
    }

    static Node pred = null, succ = null;
    public static void predSucc(Node root, int val) {
        if(root == null) return;
        if(root.data == val) {
            if(root.left != null) {
                pred = root.left;
                while(pred.right != null) {
                    pred = pred.right;
                }
            } if(root.right != null) {
                succ = root.right;
                while(succ.left != null) {
                    succ = succ.left;
                }
            }
        } else if(root.data > val) {
            succ = root;    
            predSucc(root.left, val); 
        } else {
            pred = root;
            predSucc(root.right, val);
        }
    }

       // ceil and floor are same 

    public static Node add(Node root, int val) {
        if(root == null) {
            Node nn = new Node(val);
            return nn;
        }
        if(root.data > val) {
            root.left = add(root.left, val);
        } else {
            root.right = add(root.right, val);
        }
        return root;
    }

    public static Node rightMostInTheLeft(Node root) {
        while(root.right != null) root = root.right;
        return root;
    }

    public static Node remove(Node root, int val) {
        if(root == null) return null;
        if(root.data == val) {
            if(root.left == null && root.right == null) {
                return null;
            } else if(root.left == null) {
                return root.right;
            } else if(root.right == null) {
                return root.left;
            } else {
                Node potentialCandidate = rightMostInTheLeft(root.left);
                root.data = potentialCandidate.data;
                remove(root.left, potentialCandidate.data);
                return root;
            }
        }
        else if(root.data > val) {
            root.left = remove(root.left, val);
        } else {
            root.right = remove(root.right, val);
        }
        return root;
    }

    public static Node buildTree_1(int[] preArr, int psi, int pei, int[] inArr, int isi, int iei) {
        if(psi > pei) return null;
        Node root = new Node(preArr[psi]);
        int rootIdx = -1, len = 0;
        while(rootIdx == -1 || inArr[rootIdx] != root.data) {
            rootIdx++;
        }
        len = rootIdx - isi;
        root.left = buildTree_1(preArr, psi + 1, psi + len, inArr, isi, rootIdx - 1);
        root.right =  buildTree_1(preArr, psi + len + 1, pei, inArr, rootIdx + 1, iei);
        return root;
    }
    
    public static Node buildTree_1b(int[] in, int[] pre, int isi, int iei, int psi, int pei) {
        if(psi > pei) return null;
        Node root = new Node(pre[psi]);
        int rootIdx = -1;
        for(int i = isi; i <= iei; i++) {
            if(root.data == in[i]) {
                rootIdx = i;
                break;
            }
        }
        int lpsi = psi + 1;
        int lisi = isi;
        int liei = rootIdx - 1;
        int rpei = pei;
        int risi = rootIdx + 1;
        int riei = iei;
        int lpei = liei - lisi + lpsi;
        int rpsi = lpei + 1;
        root.left = buildTree_1b(in, pre, lisi, liei, lpsi, lpei);
        root.right = buildTree_1b(in, pre, risi, riei, rpsi, rpei);
        return root;
    }

    public static Node buildTree2(int[] post, int[] in, int isi, int iei, int psi, int pei) {
        if(psi > pei) return null;
        Node root = new Node(post[pei]);
        int ridx = -1;
        for(int i = isi; i <= iei; i++) {
            if(root.data == in[i]) {
                ridx = i;
                break;
            }
        }
        int lisi = isi;
        int liei = ridx - 1;
        int risi = ridx + 1;
        int riei = iei;
        int lpsi = psi;
        int rpei = pei - 1;
        int lpei = liei - lisi + lpsi;
        int rpsi = lpei + 1;
        root.left = buildTree2(post, in, lisi, liei, lpsi, lpei);
        root.right = buildTree2(post, in, risi, riei, rpsi, rpei);
        return root;
    }

    public static Node buildTree3(int[] pre, int preSi, int preEi, int[] post, int postSi, int postEi) {
        if(postSi > postEi) return null;
        //above case will fire on right side 
        Node root = new Node(pre[preSi]);
        int idx = -1;
        for(int i = postSi; i <= postEi; i++) {
           if(preSi + 1 < pre.length && pre[preSi + 1] == post[i]) {
               idx = i;
               break;
           }
       }
       int len = idx - postSi;
       if(postSi == postEi) return root;
       //above case will fire on left side 
       int lptSi = postSi;
       int lptEi = postSi + (idx - postSi);
       int rptSi = lptEi + 1;
       int rptEi = postEi - 1;
       int lprSi = preSi + 1;
       int lprEi = preSi + (idx - postSi) + 1;
       int rprSi = lprEi + 1;
       int rprEi = preEi;
       root.left = buildTree3(pre, lprSi, lprEi, post, lptSi, lptEi);
       root.right = buildTree3(pre, rprSi, rprEi, post, rptSi, rptEi);
       return root;
    }

    //bst iterator
    static class bstIterator {
        Stack<Node> st = new Stack<>();

        public bstIterator(Node root) {
            pushAllEle(root);
        }

        private void pushAllEle(Node root) {
            while(root != null) {
                st.push(root);
                root = root.left;
            }
        }

        public int next() {
            Node rv = st.pop();
            pushAllEle(rv.right);
            return rv.data;
        }
        public boolean hasNext() {
            return st.size() > 0;
        }
    }

    public static void testBstIterator(Node root) {
        bstIterator bs = new bstIterator(root);
        System.out.println(bs.next());
        System.out.println(bs.next());
        System.out.println(bs.hasNext());
    }

    static Node a = null, b = null, c = null;

    public static void recoverTreeHelper(Node root) {
        if(root == null) return;
        recoverTreeHelper(root.left);
        if(a != null) {
            if(root.data < a.data) {
                if(b == null) b = a;
                c = root;
            }
        }
        a = root;
        recoverTreeHelper(root.right);
    }

    public static Node recoverTree(Node root) {
       recoverTreeHelper(root);
       if(b != null && c != null) {
           int temp = b.data;
           b.data = c.data;
           c.data = temp;
       }
       return root;
    }
    
    static int K = 0;
    public static Node kthSmallestHelper(Node root) {
        if(root == null) return null;
        Node lans = kthSmallestHelper(root.left);
        if(lans != null) return lans;
        K -= 1;
        if(K == 0) return root;
        Node rans = kthSmallestHelper(root.right);
        if(rans != null) return rans;
        return null;
    }

    public static int KthSmallestInBst(Node root, int k) {
        K = k;
        Node ans = kthSmallestHelper(root);
        return ans.data;
    }

    public static void solve() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Node root = construct(arr, 0, arr.length - 1);
        display(root);
        System.out.println(in);
        System.out.println(pre);
    }

    public static void main(String args[]) {
        solve();
    }
}