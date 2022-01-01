import java.util.*;
class bst {

    static class Node {
        int data;
        Node left = null, right = null, parent = null; 
        public Node(int data) {
            this.data = data;
        }
    }
    
    // binary tree to bst conversion
    public static Node btreeToBst(Node root) {
        ArrayList<Integer> list = new ArrayList<>();
        btreeToBst_fill(root, list);
        Collections.sort(list);
        int[] idx = new int[1];
        btreeToBst_swap(root, list, idx);
        return root;
    }

    public static void btreeToBst_swap(Node root, ArrayList<Integer> list, int[] idx) {
        if (root == null || idx[0] >= list.size()) return;
        btreeToBst_swap(root.left, list, idx);
        root.data = list.get(idx[0]++);
        btreeToBst_swap(root.right, list, idx);
    }

    public static void btreeToBst_fill(Node root, ArrayList<Integer> list) {
        if (root == null) return;
        list.add(root.data);
        btreeToBst_fill(root.left, list);
        btreeToBst_fill(root.right, list);
    }

    // in place conversion of bst to min heap
    public static void bstToMinHeap(Node root) {
        ArrayList<Integer> list = new ArrayList<>();
        bstToMinHeap_fill(root, list);
        int[] idx = new int[1];
        bstToMinHeap_swap(root, list, idx);
    }

    public static void bstToMinHeap_fill(Node root, ArrayList<Integer> list) {
        if (root == null) return;
        bstToMinHeap_fill(root.left, list);
        list.add(root.data);
        bstToMinHeap_fill(root.right, list);
    }

    public static void bstToMinHeap_swap(Node root, ArrayList<Integer> list, int[] idx) {
        if (root == null || idx[0] >= list.size()) return;
        root.data = list.get(idx[0]++);
        bstToMinHeap_swap(root.left, list, idx);
        bstToMinHeap_swap(root.right, list, idx);
    }

    // construct bst from levelorder
    // let each node find it's correct position in tree
    public static Node buildFromLevel(int[] arr) {
        Node root = new Node(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            buildFromLevel_helper(root, arr[i]);
        }
        return root;
    }

    public static Node buildFromLevel_helper(Node root, int val) {
        if (root == null) {
            return new Node(val);
        }
        if (val < root.data) {
            root.left = buildFromLevel_helper(root.left, val);
        } else {
            root.right = buildFromLevel_helper(root.right, val);
        }
        return root;
    }

    static class level_pair {
        Node p = null;
        int lr, rr;
        public level_pair(Node p, int lr, int rr) {
            this.p = p;
            this.lr = lr;
            this.rr = rr;
        }
    }

    public static Node buildFromLevel_2(int[] arr) {
        Queue<level_pair> qu = new LinkedList<>();
        int idx = 0;
        qu.add(new level_pair(null, -(int)(1e8), (int)(1e8)));
        Node root = null;
        boolean flag = true;

        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                level_pair cp = qu.remove();
                if (idx == arr.length) {
                    flag = false;
                    break;
                }
                int ele = arr[idx];
                if (ele < cp.lr || ele > cp.rr) continue;

                Node cn = new Node(ele);
                // unless you create a node, don't increment idx
                idx++;
                // 
                if (cp.p == null) {
                    root = cn;
                } else {
                    if (ele < cp.p.data) cp.p.left = cn;
                    else cp.p.right = cn; 
                }
                /// adjust ranges and add children
                qu.add(new level_pair(cn, cp.lr, ele));
                qu.add(new level_pair(cn, ele, cp.rr));
            }
            if (!flag) break;
        }
        return root;
    }

    // Check for Identical BSTs without building the trees
    // for each ele left subtree elements & right subtree elements must lie after it
    public static boolean isBstIdentical(int[] a, int[] b) {
        return isBstIdentical_rec(a, b, 0, 0, -(int)(1e8), (int)(1e8));
    }

    public static boolean isBstIdentical_rec(int[] a, int[] b, int i, int j, int min, int max) {
        int p, q;
        for (p = i; p < a.length; p++) {
            if (a[p] > min && a[p] < max) break;
        }
        for (q = j; q < b.length; q++) {
            if (b[q] > min && b[q] < max) break;
        }
        if (p == a.length && q == b.length) return true;
        if (p == a.length || q == b.length || a[p] != b[q]) return false;
        // pass the range
        return isBstIdentical_rec(a, b, p + 1, q + 1, min, a[p]) && isBstIdentical_rec(a, b, p + 1, q + 1, a[p], max);
    }

    // K’th Largest Element in BST when modification to BST is not allowed
    // apply reverse inorder that is same as traversing from last in sorted arr
    public static int kthLargestBst(Node root, int k) {
        int[] ans = new int[2];
        // ans = {k, ansNodeData}
        ans[0] = k;
        boolean res = kthLargestBst_rec(root, ans);
        return ans[1];
    }

    public static boolean kthLargestBst_rec(Node root, int[] ans) {
        if (root == null) return false;
        boolean right = kthLargestBst_rec(root.right, ans);
        if (right) return true;
        ans[0] -= 1;   
        if (ans[0] == 0) {
            ans[1] = root.data;
            return true;
        } 
        boolean left = kthLargestBst_rec(root.left, ans);
        return left;
    }

    // K’th smallest element in BST using O(1) Extra Space
    public static int kthSmallestBst(Node root, int k) {
        int[] ans = new int[2];
        ans[0] = k;
        kthSmallestBst_rec(root, ans);
        return ans[1];
    }

    public static boolean kthSmallestBst_rec(Node root, int[] ans) {
        if (root == null) return false;
        boolean left = kthSmallestBst_rec(root.left, ans);
        if (left) return true;
        ans[0] -= 1;
        if (ans[0] == 0) {
            ans[1] = root.data;
            return true;
        }
        boolean right = kthSmallestBst_rec(root.right, ans);
        return right;
    }

    // Check whether BST contains Dead End or not
    // Given a Binary search Tree that contains positive integer values greater than 0. The task is to check whether the BST contains a dead end or not. Here Dead End means, we are not able to insert any element after that node.
    public static boolean deadEnd(Node root) {
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> leaves = new HashSet<>();
        deadEnd_fill(root, set, leaves);
        for (Integer val: leaves) {
            if (set.contains(val + 1) || set.contains(val - 1)) return true;
        }
        return false;
    }

    public static void deadEnd_fill(Node root, HashSet<Integer> set, HashSet<Integer> leaves) {
        if (root == null) return;
        set.add(root.data);
        deadEnd_fill(root.left, set, leaves);
        deadEnd_fill(root.right, set, leaves);
        if (root.left == null && root.right == null) leaves.add(root.data);
    }

    // *********************** MERGE BST ************************************

    // Merge Two Balanced Binary Search Trees
    public static Node mergeBst(Node r1, Node r2) {
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> ans = new ArrayList<>();
        mergeBst_fill(r1, list1);
        mergeBst_fill(r2, list2);
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) < list2.get(j)) {
                ans.add(list1.get(i));
                i++;
            } else {
                ans.add(list2.get(j));
                j++;
            }
        }
        while (i < list1.size()) {
            ans.add(list1.get(i));
            i++;
        }
        while (j < list2.size()) {
            ans.add(list2.get(j));
            j++;
        }
        Node root = mergeBst_makeTree(ans, 0, ans.size() - 1);
        return root;
    }

    public static void mergeBst_fill(Node root, ArrayList<Integer> list) {
        if (root == null) return;
        mergeBst_fill(root.left, list);
        list.add(root.data);
        mergeBst_fill(root.right, list);
    }

    public static Node mergeBst_makeTree(ArrayList<Integer> ans, int si, int ei) {
        if (si > ei) return null;
        int mid = (si + ei) >> 1;
        Node root = new Node(ans.get(mid));
        root.left = mergeBst_makeTree(ans, si, mid - 1);
        root.right = mergeBst_makeTree(ans, mid + 1, ei);
        return root;
    }

    // merge optimized (space)
    public static Node mergeBst_opti(Node r1, Node r2) {
        // Node[] a1 = {null, null};
        // Node[] a2 = {null, null};
        // bstToDll(r1, a1);
        // bstToDll(r2, a2);
        Node a1 = bstToDll_opti(r1);
        Node a2 = bstToDll_opti(r2);
        Node mergeRoot = mergeDll(a1, a2);
        Node root = buildTreeFromDll(mergeRoot);
        return root;
    }

    public static void bstToDll(Node root, Node[] a) {
        if (root == null) return;
        Node left = root.left;
        Node right = root.right;
        //
        bstToDll(root.left, a);
        if (a[0] == null) {
            a[0] = a[1] = root;
        } else {
            a[1].right = root;
            root.left = a[1];
            a[1] = a[1].right;
        }
        bstToDll(root.right, a);
    }

    public static Node rightMost(Node c, Node n) {
        while (n.right != null && n.right != c) {
            n = n.right;
        }
        return n;
    }

    public static Node bstToDll_opti(Node root) {
        Node dummy = new Node(-1);
        Node cn = root, prev = dummy;
        while (cn != null) {
            Node next = cn.left;
            if (next != null) {
                Node rm = rightMost(cn, next);
                if (rm.right == null) {
                    rm.right = cn;
                    cn = cn.left;
                } else {
                    // second visit
                    rm.right = null; 
                    prev.right = cn;
                    cn.left = prev;
                    prev = cn;
                    cn = cn.right;
                }
            } else {
                // first visit
                prev.right = cn;
                cn.left = prev;
                prev = cn;
                cn = cn.right;
            }
        }
        Node head = dummy.right;
        dummy.right = dummy.left = null;
        return head;
    }

    public static Node mergeDll(Node p, Node q) {
        Node dummy = new Node(-1);
        Node d = dummy;
        while (p != null && q != null) {
            if (p.data < q.data) {
                d.right = p;
                p.left = d; 
                p = p.right;
            } else {
                d.right = q;
                q.left = d;
                q = q.right;
            } 
            d = d.right;
        }
        while (p != null) {
            d.right = p;
            p.left = d; 
            p = p.right;
            d = d.right;
        }
        while (q != null) {
            d.right = q;
            q.left = d;
            q = q.right;
            d = d.right;
        }
        Node ans = dummy.right;
        ans.left = null;
        dummy.right = null;
        return ans;
    }

    public static Node buildTreeFromDll(Node head) {
        if (head == null || head.right == null) return head;
        Node mid = getMid(head);
        Node next = mid.right, prev = mid.left;
        mid.right = mid.left = null;
        next.left = null;
        if (prev != null) prev.right = null; 
        else head = null;
        /// major mistake: in case of 50 60 
        // head is on 50 and prev is null, but when calling left, we are passing the same head again
        mid.left = buildTreeFromDll(head);
        mid.right = buildTreeFromDll(next);
        return mid;
    }

    public static Node getMid(Node h) {
        if (h == null || h.right == null) return h;
        Node s = h, f = h;
        while (f.right != null && f.right.right != null) {
            s = s.right;
            f = f.right.right;
        }
        return s;
    }

    // ***************************************************************************

    // Two nodes of a BST are swapped, correct the BST
    public static void correctTheBst(Node root) {
        Node[] arr = {null, null, null};
        correctTheBst_rec(root, arr);
        System.out.print(arr[1].data + " " + arr[2].data);
    }

    public static void correctTheBst_rec(Node root, Node[] arr) {
        if (root == null) return;
        correctTheBst_rec(root.left, arr);
        if (arr[0] == null) {
            arr[0] = root;
        } else {
            if (arr[0].data > root.data) {
                if (arr[1] == null) arr[1] = arr[0];
                else arr[2] = root;
            }
            arr[0] = root;
        }
        correctTheBst_rec(root.right, arr);
    }

    // Find if there is a triplet in a Balanced BST that adds to zero
    // k equals 0
    // one method is to convert it into bst 
    public static void tripletSum(Node root, int k) {
        HashSet<Integer> set = new HashSet<>();
        tripletSum_fill(root, set);
        tripletSum_rec(root, set, k);
    }

    public static void tripletSum_fill(Node root, HashSet<Integer> set) {
        if (root == null) return;
        set.add(root.data);
        tripletSum_fill(root.left, set);
        tripletSum_fill(root.right, set);
    }

    public static void tripletSum_rec(Node root, HashSet<Integer> set, int k) {
        if (root == null) return;
        tripletSum_rec(root.left, set, k);
        pairSum(root, root, set, k, root.data);
        tripletSum_rec(root.right, set, k);
    }

    public static void pairSum(Node root, Node orgRoot, HashSet<Integer> set, int k, int a) {
        if (root == null) return;
        pairSum(root.left, orgRoot, set, k, a);
        if (root != orgRoot) {
            int remaining = k - root.data - a;
            if (set.contains(remaining)) {
                System.out.println(a + ", " + root.data + ", " + remaining);
            }
        }
        pairSum(root.right, orgRoot, set, k, a);
    }

    // find a pair in bst sums up to k
    // space : o(n), optimized -> space o(lgn)
    public static void targetPairSum(Node root, int k) {
        Node head = bstToDll_opti(root);
        Node tail = head;
        while (tail.right != null) {
            tail = tail.right;
        }
        while (head != tail) {
            int sum = head.data + tail.data;
            if (sum == k) {
                System.out.print(head.data + ", " + tail.data);
                head = head.right; tail = tail.left;
            } else if (sum < k) {
                head = head.right;
            } else tail = tail.left;
        }
    }

    // Remove BST keys outside the given range
    public static Node removeOutOfRange(Node root, int min, int max) {
        if (root == null) return null;
        root.left = removeOutOfRange(root.left, min, max);
        root.right = removeOutOfRange(root.right, min, max);
        if (root.data < min) {
            // then return root's right tree
            Node rightChild = root.right;
            root = null;
            return rightChild;
        } else if (root.data > max) {
            // then return root's left tree
            Node leftChild = root.left;
            root = null;
            return leftChild;
        }
        return root;
    }

    // Add all greater values to every node in a given BST
    public static void addBigValsToAllNodes(Node root) {
        int[] ans = new int[1];
        addBigValsToAllNodes_rec(root, ans);
    }

    public static void addBigValsToAllNodes_rec(Node root, int[] ans) {
        if (root == null) return;
        addBigValsToAllNodes_rec(root.right, ans);
        int temp = root.data;
        root.data += ans[0];
        ans[0] += temp;
        addBigValsToAllNodes_rec(root.left, ans);
    }

    // Inorder predecessor and successor for a given key in BST
    public static void inorderPreSucc(Node root, int data) {
        Node pre = null, succ = null;
        Node temp = root;
        while (temp != null) {
            if (temp.data == data) {
                break;
            } else if (temp.data < data) {
                pre = temp;
                temp = temp.right;
            } else {
                succ = temp;
                temp = temp.left;
            }
        }
        if (pre == null) {
            System.out.print("null" + ", " + succ.data);
        } else if (succ == null) {
            System.out.print(pre.data + ", " + "null");
        } else {
            System.out.print(pre.data + ", " + succ.data);
        }
    }

     public static void inorderPreSucc_2(Node root, int data) {
        Node pre = null, succ = null;
        Node temp = root;
        // set p first
        while (temp != null) {
            if (temp.data >= data) {
                temp = temp.left;
            } else {
                pre = temp;
                temp = temp.right;
            }
        }
        temp = root;
        while (temp != null) {
            if (temp.data <= data) {
                temp = temp.right;
            } else {
                succ = temp;
                temp = temp.left;
            }
        }
    }

    // https://www.geeksforgeeks.org/given-n-appointments-find-conflicting-appointments/
    // interval tree problem

    // Data Structure for a single resource reservations
    public static Node insertReservations(Node root, int k, int time) {
        if (root == null) return new Node(time);
        // check if the current time does not conflicts with any time frame
        if (time - k < root.data && time + k > root.data)  {
            // curr = 5, new 3, k = 4 then 3 - 4 = -1 and -1 < 5 ie
            return root;
        }
        if (time < root.data) root.left = insertReservations(root.left, k, time);
        else root.right = insertReservations(root.right, k, time);
        return root;
    }

    // Count BST subtrees that lie in given range
    public static int bstSubTreeInRange(Node root, int l, int h) {
        int[] ans = new int[1];
        bstSubTreeInRange_rec(root, l, h, ans);
        return ans[0];
    }

    public static boolean bstSubTreeInRange_rec(Node root, int l, int h, int[] ans) {
        if (root == null) return true;
        boolean left = bstSubTreeInRange_rec(root.left, l, h, ans);
        boolean right = bstSubTreeInRange_rec(root.right, l, h, ans);
        // now check that if curr root lies in the range 
        boolean self = false;
        if (root.data >= l && root.data <= h && left && right) {
            ans[0]++;
            self = true;
        } 
        return self;
    }

    // Replace every element with the least greater element on its right
    public static void findLeastGreaterOnRight(int[] arr) { 
        // as we have to find next least greater on right, we start from end
        int n = arr.length;
        Node root = null;
        Node[] succ = {null};
        for (int i = n - 1; i >= 0; i--) {
            succ[0] = null;
            root = findLeastGreaterOnRight_insert(root, arr[i], succ);
            if (succ[0] == null) arr[i] = -1;
            else arr[i] = succ[0].data;
        }
        for (int val: arr) System.out.print(val + " ");
    }

    public static Node findLeastGreaterOnRight_insert(Node root, int val, Node[] succ) {
        if (root == null) {
            return new Node(val);
        }
        if (val < root.data) {
            succ[0] = root;
            root.left = findLeastGreaterOnRight_insert(root.left, val, succ);
        } else if (val > root.data) {
            root.right = findLeastGreaterOnRight_insert(root.right, val, succ);
        }       
        return root;
    }

    // Find the closest element in Binary Search Tree
    static class closestEle_pair {
        Node n;
        int diff = (int)(1e8);
        public closestEle_pair(Node n) {
            this.n = n;
        }
    }

    public static void closestElement(Node root, int k) {
        closestEle_pair ans = new closestEle_pair(null);
        closestElement_rec(root, k, ans);
        System.out.println(ans.n.data);
    }

    public static void closestElement_rec(Node root, int k, closestEle_pair ans) {
        if (root == null) return;
        if (ans.diff > Math.abs(root.data - k)) {
            ans.diff = Math.abs(root.data - k);
            ans.n = root;
        }
        if (k < root.data) closestElement_rec(root.left, k, ans);
        else closestElement_rec(root.right, k, ans);
    }

    // sum of all elements <= kth smallest element
    public static int sumOfKSmallest(Node root, int k) {
        int[] ans = new int[2];
        // k, sum
        ans[0] = k;
        sumOfKSmallest_rec(root, ans);
        return ans[1];
    }

    public static boolean sumOfKSmallest_rec(Node root, int[] ans) {
        if (root == null) return false;
        boolean left = sumOfKSmallest_rec(root.left, ans);
        if (left) return true;
        if (ans[0] > 0) {
            ans[1] += root.data;
            ans[0] -= 1;
            if (ans[0] == 0) return true;
        }
        boolean right = sumOfKSmallest_rec(root.right, ans);
        return right;
    }

    // Maximum element between two nodes of BST
    public static int maxEleBtwNodes(int[] arr, int a, int b) {
        Node root = null;
        for (int i = 0; i < arr.length; i++) {
            root = maxEleBtwNodes_insert(root, arr[i]);
        }   
        // val, a, b
        // int[] ans = {-(int)(1e8), -1, -1};
        // maxEleBtwNodes_rec(root, a, b, ans);
        // return ans[0];
        
        // more optimized soln
        // time: o(h)
        Node lca = LCA(root, a, b);
        int ans1 = maxInRootToEle(lca, a);
        int ans2 = maxInRootToEle(lca, b);
        return Math.max(ans1, ans2);
    }

    public static Node LCA(Node root, int a, int b) {
        while ((root.data > a && root.data > b) || (root.data < a && root.data < b)) {
            if (a > root.data && b > root.data) {
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return root;
    }

    public static int maxInRootToEle(Node root, int x) {
        Node p = root;
        int ans = -1;
        while (p.data != x) {
            ans = Math.max(ans, p.data);
            if (p.data < x) {
                p = p.right;
            } else {
                p = p.left;
            }
        }
        return Math.max(ans, x);
    }

    public static boolean maxEleBtwNodes_rec(Node root, int a, int b, int[] ans) {
        if (root == null) return false;
        //
        if (ans[1] == -1 && root.data == a) {
            ans[1] = root.data;
            ans[0] = Math.max(ans[0], root.data);

        } else if (ans[2] == -1 && root.data == b) {
            ans[2] = root.data;
            ans[0] = Math.max(ans[0], root.data);
            return true;

        } else if (ans[1] != -1 && ans[2] == -1) {
            ans[0] = Math.max(ans[0], root.data);
        }
        //
        boolean left = maxEleBtwNodes_rec(root.left, a, b, ans);
        if (left) return true;
        boolean right = maxEleBtwNodes_rec(root.right, a, b, ans);
        return right;
    }

    public static Node maxEleBtwNodes_insert(Node root, int val) {
        if (root == null) return new Node(val);
        if (val < root.data) {
            root.left = maxEleBtwNodes_insert(root.left, val);
        } else if (val > root.data) {
            root.right = maxEleBtwNodes_insert(root.right, val);
        } 
        return root;
    }

    // insert with parent Nodes
    public static Node insertWithParent(Node root, int val) {
        if (root == null) return new Node(val);
        if (val < root.data) {
            Node left = insertWithParent(root.left, val);
            root.left = left;
            left.parent = root;
        } else if (val > root.data) {
            Node right = insertWithParent(root.right, val);
            root.right = right;
            right.parent = root;
        }
        return root;
    }
    
    // Largest BST in a Binary Tree
    // return the size of largest subtree which is also a binary search tree
    // single nodes are bst, so default size will be 1 not 0;
    static class bstPair {
        int max = -(int)(1e8), min = (int)(1e8), size = 0;
        boolean flag = true;
        public bstPair() {
        }
    }

    public static int largestBst(Node root) {
        bstPair ans = largestBst_rec(root);
        // is ques is about is valid bst then return 
        // ans.flag 
        return ans.size;
    }

    public static bstPair largestBst_rec(Node root) {
        if (root == null) {
            return new bstPair();
        }
        bstPair left = largestBst_rec(root.left);
        bstPair right = largestBst_rec(root.right);
        bstPair self = new bstPair();
        //
        self.min = Math.min(left.min, Math.min(right.min, root.data));
        self.max = Math.max(left.max, Math.max(right.max, root.data));
        //
        if (left.flag && right.flag && root.data > left.max && root.data < right.min) {
            self.size = left.size + right.size + 1;
            self.flag = true;

        } else {
            // caculate size from left and right
            self.size = Math.max(left.size, right.size);
            // as default is true, so set it to false if bst is not formed
            self.flag = false;
        }
        return self;
    }

    // Leaf nodes from Preorder of a Binary Search Tree
    // the task is to print leaf nodes from preorder of bst
    public static void printLeafFromPre(int[] arr) {
        int[] idx = new int[1];
        int lr = -(int)(1e8), rr = (int)(1e8);
        printLeafFromPre_rec(arr, idx, lr, rr);
    }

    public static Node printLeafFromPre_rec(int[] arr, int[] idx, int lr, int rr) {
        if (idx[0] >= arr.length || arr[idx[0]] > rr || arr[idx[0]] < lr) {
            return null;
        }
        Node root = new Node(arr[idx[0]++]);
        root.left = printLeafFromPre_rec(arr, idx, lr, root.data);
        root.right = printLeafFromPre_rec(arr, idx, root.data, rr); 
        if (root.left == null && root.right == null) {
            System.out.print(root.data + " ");
        }
        return root;
    }

    // Find median of BST in O(n) time and O(1) space
    public static int median(Node root) {
        int n = findSizeMorris(root);
        int k = (n + 1) / 2;
        int ans = kthSmallMorris(root, k);
        return ans;
    }

    public static int findSizeMorris(Node root) {
        Node cn = root;
        int n = 0;
        while (cn != null) {
            Node next = cn.left;
            if (next == null) {
                cn = cn.right;
                n++;
            } else {
                Node rm = rightMost(cn, next);
                if (rm.right == null) {
                    rm.right = cn;
                    cn = cn.left;
                } else {
                    rm.right = null;
                    cn = cn.right;
                    // inc size
                    n++;
                }
            }
        }
        return n;
    }

    public static int kthSmallMorris(Node root, int k) {
        Node cn = root;
        int ans = -1;
        while (cn != null) {
            Node next = cn.left;
            if (next == null) {
                if (k > 0) {
                    ans = cn.data;
                    k -= 1;
                }
                cn = cn.right;
            } else {
                Node rm = rightMost(cn, next);
                if (rm.right == null) {
                    rm.right = cn;
                    cn = cn.left;
                } else {
                    rm.right = null;
                    //
                    if (k > 0) {
                        ans = cn.data;
                        k -= 1;
                    }
                    //
                    cn = cn.right;
                }
            }   
        }
        return ans;
    }

    public static void solve() {
        int[] arr = {20, 8, 4, 12, 10, 14, 22};
        Node root = buildFromPre(arr);
        int ans = median(root);
        System.out.println(ans);
    }

    public static void printDll(Node h) {
        while (h != null) {
            System.out.print(h.data + " ");
            h = h.right;
        }
    }

    public static void inorder(Node root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    public static void preOrder(Node root) {
        if (root == null) return;
        System.out.print(root.data + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

      // Construct BST from given preorder traversal | Set 1
    public static Node buildFromPre(int[] arr) {
        int[] idx = new int[1];
        int lr = -(int)(1e8), rr = (int)(1e8);
        Node root = buildFromPre_rec(arr, idx, lr, rr);
        return root;
    }

    public static Node buildFromPre_rec(int[] arr, int[] idx, int lr, int rr) {
        if (idx[0] >= arr.length || arr[idx[0]] < lr || arr[idx[0]] > rr) {
            return null;
        } 
        Node root = new Node(arr[idx[0]++]);
        root.left = buildFromPre_rec(arr, idx, lr, root.data);
        root.right = buildFromPre_rec(arr, idx, root.data, rr);
        return root;
    }

    public static Node buildBinaryTree(int[] arr) {
        int[] idx = new int[1];
        return buildBinaryTree_rec(arr, idx);
    }

    public static Node buildBinaryTree_rec(int[] arr, int[] idx) {
        if (idx[0] >= arr.length || arr[idx[0]] == -1) {
            idx[0]++;
            return null;
        }
        Node root = new Node(arr[idx[0]++]);
        root.left = buildBinaryTree_rec(arr, idx);
        root.right = buildBinaryTree_rec(arr, idx);
        return root;
    }
    
    public static void main(String args[]) {
        solve();
    }
}