import java.util.*;
class linklist {

    static class Node {
        Node next = null;
        Node bottom = null;
        int data;
        public Node(int data) {
            this.data = data;
        }
    }
    
    // Delete without head pointer 
    public static void deleWithoutHead(Node del) {
        Node temp = del.next;
        del.data = temp.data;
        del.next = temp.next;
        temp.next = null;
    }

    // first non repeating character in a stream (opti)
    public static String firstNonRepChar_opti(String s) {
        StringBuilder sb = new StringBuilder();
        boolean[] vis = new boolean[26];
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!vis[ch - 'a']) {
                // char is coming first time
                if (!list.contains(ch)) list.add(ch);
                else {
                    // this is it's second time
                    list.remove((Character)ch);
                    vis[ch - 'a'] = true;
                }
            }
            // if list size is not 0 then first ele is non rep
            if (list.size() != 0) sb.append(list.get(0));
            // if size is 0 then no char till now, add #
            else sb.append("#");
        }
        return sb.toString();
    }

    // Program for n’th node from the end of a Linked List
    public static int nthFromLast(Node head, int n) {
        Node p = head, q = head;
        for (int i = 0; i < n; i++) {
            if (q != null) q = q.next;
            else return -1;
            // this will handle the case then head is null, or n > size
        }
        while (q != null) {
            q = q.next;
            p = p.next;
        }
        return p.data;
    }

    // detect cycle and remove it
    public static void removeCycle(Node head) {
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        if (slow != fast) return;
        // find the start point of cyle
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        // start point 
        // return fast;
        // slow will be on start of cycle as well as fast
        // get the last node
        while (slow.next != fast) slow = slow.next;
        // slow is on last node so break the connection to remove cycle
        slow.next = null;
    }

    // Write a function to get the intersection point of two Linked Lists
    public static Node intersectioPoint(Node h1, Node h2) {
        if (h1 == null) {
	        Node temp = h1;
	        h1 = h2;
	        h2 = temp;
	    }
        
        Node tail = h1;
        while (tail.next != null) {
            tail = tail.next;
        }   
        tail.next = h2;
        // cycle has been created, now find out the start point
        Node slow = h1, fast = h1;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        if (slow != fast) return null;
        // we have the reference point
        slow = h1;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        // break the connection of tail and h2
        tail.next = null;
        return slow;
    }

    // Remove duplicates from an unsorted linked list
    public static Node removeDupsUnSorted(Node head) {
        if (head == null) return head;
        HashSet<Integer> set = new HashSet<>();
        Node p = head, q = null;
        while (p != null) {
            int val = p.data;
            if (!set.contains(val)) {
                q = p;
                p = p.next;
                set.add(val);
            } else {
                Node temp = p.next;
                q.next = temp;
                p.next = null;
                p = temp;
            }
        }
        return head;
    }

    // merge sort for linkedList
    // time: nlogn
    // space: o(1)
    public static Node mergeSort(Node head) {
        if (head == null || head.next == null) return head;
        Node midNode = mid(head);
        Node midNext = midNode.next;
        midNode.next = null;
        Node left = mergeSort(head);
        Node right = mergeSort(midNext);
        Node newHead = mergeLL(left, right);
        // correct the connections
        // midNode.next = midNext;
        return newHead;
    }

    public static Node mergeLL(Node left, Node right) {
        Node l = left, r = right;
        // safest approach to merge, by creating dummy node
        Node temp = new Node(-1);
        Node temphead = temp;
        while (l != null && r != null) {
            if (l.data < r.data) {
                temp.next = l;
                l = l.next;
            } else {
                temp.next = r;
                r = r.next;
            }
            temp = temp.next;
        }
        while (l != null) {
            temp.next = l;
            l = l.next;
            temp = temp.next;
        }
        while (r != null) {
            temp.next = r;
            r = r.next;
            temp = temp.next;
        }
        return temphead.next;
    }

    public static Node mid(Node head) {
        if (head == null) return head;
        // it will give first mid when size is even
        Node fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    // Delete nodes having greater value on right 
    public static Node removeNodesNgor(Node head) {
        if (head == null || head.next == null) return head;
        // faith: bring me a next node so that i can decide if i will be deleted or not
        Node nextNode = removeNodesNgor(head.next);
        // delete in o(1)
        if (head.data < nextNode.data) {
            Node nextToNext = nextNode.next;
            head.data = nextNode.data;
            head.next = nextToNext;
            nextNode.next = null;
        }
        return head;
    }

    // Segregate even and odd nodes in a Linked List
    // even values appear before odd values
    public static Node segregateEvenOddNodes(Node head) {
        if (head == null) return head;
        Node odd = new Node(-1);
        Node even = new Node(-1);
        Node o = odd, e = even, p = head, ansHead = null;
        boolean flag = true;
        while (flag) {
            // delete and retain the value of curr node
            int val = p.data;
            if (p.next != null) {
                Node next = p.next;
                p.data = next.data;
                p.next = next.next;
                next.next = null;
            } else {
                flag = false;
            }
            // now add the val in the correct list
            Node nn = new Node(val);
            if ((val & 1) == 0) {
                e.next = nn;
                e = e.next;
            } else {
                o.next = nn;
                o = o.next;
            }
        }
        // if there is no odds or even
        if (even.next != null && odd.next != null) {
            e.next = odd.next;
            ansHead = even.next;
        } else if (even.next == null && odd.next != null) {
            ansHead = odd.next;
        } else {
            ansHead = even.next;
        }
        return ansHead;
    }

    // add two linked list, no reverse allowed
    static Node addTwoLL_head = null;
    public static Node addTwoLL(Node h1, Node h2) {
        int n1 = getSize(h1);
        int n2 = getSize(h2);
        int carry = addTwoLL_rec(h1, h2, n1, n2);
        if (carry > 0) {
            addFirst(new Node(carry));
        }
        return addTwoLL_head;
    }

    public static int addTwoLL_rec(Node h1, Node h2, int pv1, int pv2) {
        if (h1 == null && h2 == null) return 0;
        int sum = 0, carry = 0;
        if (pv1 == pv2) {
            carry += addTwoLL_rec(h1.next, h2.next, pv1 - 1, pv2 - 1);
            sum += h1.data + h2.data + carry;

        } else if (pv1 > pv2) {
            carry += addTwoLL_rec(h1.next, h2, pv1 - 1, pv2);   
            sum += h1.data + carry;

        } else {
            carry += addTwoLL_rec(h1, h2.next, pv1, pv2 - 1);
            sum += h2.data + carry;
        }
        int val = sum % 10;
        carry = sum / 10;
        addFirst(new Node(val));
        return carry;
    }

    public static void addFirst(Node cn) {
        cn.next = addTwoLL_head;
        addTwoLL_head = cn;
    }

    public static int getSize(Node p) {
        int count = 0;
        while (p != null) {
            p = p.next;
            count++;
        }
        return count;
    }

    // Find a triplet from three linked lists with sum equal to a given number
    // keep a as it is, b in inc order, c in dec order
    public static void tripletSum(Node h1, Node h2, Node h3, int k) {
        Node n2 = mergeSort(h2);
        Node n3 = mergeSort(h3);
        n3 = reverse(n3);
        Node a = h1;
        while (a != null) {
            Node b = n2, c = n3;
            while (b != null && c != null) {
                int sum = a.data + b.data + c.data;
                if (sum == k) {
                    System.out.println(a.data + ", " + b.data + ", " + c.data);
                    return;
                } else if (sum < k) {
                    b = b.next;
                } else c = c.next;
            }
            a = a.next;
        }
    }

    public static Node reverse(Node head) {
        Node p = null, q = head;
        while (q.next != null) {
            Node temp = q.next;
            q.next = p;
            p = q;
            q = temp;
        }
        q.next = p;
        return q;
    }

    // Flattening a Linked List
    public static Node flattenLL(Node head) {
        if (head == null) return head;
        Node left = flattenLL(head.next);
        Node mergedNode = flattenLL_rec(head, left);
        return mergedNode;
    }

    public static Node flattenLL_rec(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;
        Node res;
        if (a.data < b.data) {
            res = a;
            res.bottom = flattenLL_rec(a.bottom, b);
        } else {
            res = b;
            res.bottom = flattenLL_rec(a, b.bottom);
        }
        res.next = null;
        return res;
    }

    // Sort a linked list of 0s, 1s and 2s
    public static Node sort012(Node head) {
        if (head == null) return head;
        Node zero = new Node(-1);
        Node one = new Node(-1);
        Node two = new Node(-1);
        Node z = zero, o = one, t = two;
        Node p = head;
        boolean flag = true;
        while (flag) {
            int val = p.data;
            // del curr node
            if (p.next != null) {
                Node pnext = p.next;
                p.data = pnext.data;
                p.next = pnext.next;
                pnext.next = null;
            } else {
                flag = false;
            }
            //
            if (val == 0) {
                z.next = new Node(val);
                z = z.next;
            } else if (val == 1) {
                o.next = new Node(val);
                o = o.next;
            } else if (val == 2) {
                t.next = new Node(val);
                t = t.next;
            }
        }
        Node ans = null;
        if (z.data == 0 && o.data == 1 && t.data == 2) {
            z.next = one.next;
            o.next = two.next;
            ans = zero.next;
        } else if (z.data == -1) {
            o.next = two.next;
            ans = one.next;
        } else if (o.data == -1) {
            z.next = two.next;
            ans = zero.next;
        } else if (t.data == -1) {
            z.next = one.next;
            ans = zero.next;
        }
        return ans;
    }

    // Flatten a multilevel linked list
    public static Node flattenLLChildPtr(Node head) {
        // bottom - child
        if (head == null) return null;
        Node curr = head, tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        Node temp = null;
        while (curr != tail) {
            if (curr.bottom != null) {
                // append child next to tail
                tail.next = curr.bottom;
                // find the last ele 
                temp = curr.bottom;
                while (temp.next != null) {
                    temp = temp.next;
                }
                // move tail to last ele
                tail = temp;
            }
            curr = curr.next;
        }
        return head;
    }

    // segregate nodes over pivot index
    public static Node segregateOverPivot(Node head, int pidx) {
        if (head == null || head.next == null) {
            return head;
        }
        int idx = 0;
        Node pivot = head;
        while (pivot != null) {
            if (idx == pidx) break;
            pivot = pivot.next;
            idx++;
        }
        Node small = new Node(-1);
        Node large = new Node(-1);
        Node s = small, l = large, curr = head;
        idx = 0;
        while (curr != null) {
            if (idx == pidx) {
              // pass, we don't need to do anything on pivot indx
              // we will skip this ele it will be a part of process and then it will become 
              // tough to handle links.
            } else if (curr.data <= pivot.data) {
                s.next = curr;
                s = s.next;
            } else {
                l.next = curr;
                l = l.next;
            }
            curr = curr.next;
            idx++;
        }
        s.next = pivot; 
        pivot.next = large.next;
        l.next = null;
        return s.next;
    }

    // Quick sort on singly linklist
    public static Node quickSort(Node head) {
        Node[] ans = quickSort_rec(head);
        return ans[0];
    }

    public static Node[] quickSort_rec(Node head) {
        if (head == null || head.next == null) return new Node[]{head, head};

        int len = getSize(head);
        int pidx = len / 2;
        Node[] ans = quickSort_seg(head, pidx);
        // ans = {startNode, pivotNode, nextToPivotNode}
        Node[] left = quickSort_rec(ans[0]);
        Node[] right = quickSort_rec(ans[2]);
        // left, right[] = {head, tail};
        return quickSort_merge(left, ans[1], right);
    }

    public static Node[] quickSort_seg(Node head, int pidx) {
        Node small = new Node(-1);
        Node big = new Node(-1);
        Node s = small, b = big, p = head, pivot = head;
        int idx = 0;
        while (idx != pidx) {
            pivot = pivot.next; 
            idx++;
        }
        while (p != null) {
            if (p != pivot) {
                if (p.data <= pivot.data) {
                    s.next = p;
                    s = s.next;
                } else {
                    b.next = p;
                    b = b.next;
                }
            }
            p = p.next;
        }
        // now isolate the three parts
        s.next = null; pivot.next = null; b.next = null;
        return new Node[]{small.next, pivot, big.next};
    }

    public static Node[] quickSort_merge(Node[] left, Node pivot, Node[] right) {
        Node head = null, tail = null;
        if (left[0] != null && right[0] != null) {
            left[1].next = pivot;
            pivot.next = right[0];
            head = left[0];
            tail = right[1];
        } else if (left[0] != null) {
            head = left[0];
            left[1].next = pivot;
            tail = pivot;
        } else if (right[0] != null) {
            head = pivot;
            pivot.next = right[0];
            tail = right[1];
        } else {
            // single node ie pivot
            head = tail = pivot;
        }
        return new Node[]{head, tail};
    }

    // Clone a linked list with next and random pointer  
    public static Node cloneWithRandomPtr(Node head) {
        if (head == null) return head;
        // 1. insert new nodes
        Node curr = head;
        while (curr != null) {
            Node nn = new Node(curr.data);
            Node cnext = curr.next;
            curr.next = nn;
            nn.next = cnext;
            curr = cnext;
        }
        // 2. set random pointers
        curr = head;
        while (curr != null) {
            if (curr.bottom != null) {
                Node cloned = curr.next;
                cloned.bottom = curr.bottom.next;
            }
            curr = curr.next.next;
        }
        // 3. extract old list
        Node dummy = new Node(-1);
        Node d = dummy;
        curr = head;
        while (curr != null) {
            // d to clone
            d.next = curr.next;
            // orginal node to original
            curr.next = curr.next.next;
            // next of clone
            d.next.next = null;
            // move d and curr
            d = d.next;
            curr = curr.next;
        }
        return dummy.next;
    }

    // Point to next higher value node in a linked list with an arbitrary pointer
    // we will use merge sort as we did to get inversions in array
    public static Node pointArbToNextHigher(Node head) {
        Node p = head;
        while (p != null) {
            p.bottom = p.next;
            p = p.next;
        }
        return pointArbToNextHigher_rec(head);
    }

    public static Node pointArbToNextHigher_rec(Node head) {
        if (head == null || head.bottom == null) return head;
        Node middle = pointArbToNextHigher_mid(head);
        Node midNext = middle.next;
        middle.next = null;
        Node left = pointArbToNextHigher_rec(head);
        Node right = pointArbToNextHigher_rec(midNext);
        return pointArbToNextHigher_merge(left, right);
    }

    public static Node pointArbToNextHigher_merge(Node a, Node b) {
        Node res = null;
        if (a == null) return b;
        else if (b == null) return a;
        if (a.data <= b.data) {
            res = a;
            res.bottom = pointArbToNextHigher_merge(a.bottom, b);
        } else {
            res = b;
            res.bottom = pointArbToNextHigher_merge(a, b.bottom);
        }
        return res;
    }

    public static Node pointArbToNextHigher_mid(Node head) {
        if (head == null) return head;
        Node fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    // Rearrange a given linked list in-place.
    // rearrange into spiral form 
    public static Node makeSpiral(Node head) {
        if (head == null || head.next == null) return head;
        Node midNode = mid(head);
        Node midNext = midNode.next;
        midNode.next = null;
        Node nh = reverse(midNext);
        Node p = head, q = nh;
        Node temp = new Node(-1);
        Node t = temp;
        while (p != null && q != null) {
            t.next = p;
            t = t.next;
            p = p.next;
            t.next = q;
            t = t.next;
            q = q.next;
        }
        if (p != null && q == null) {
            t.next = p;
            t = t.next;
            t.next = null;
        } else if (q != null && p == null) {
            t.next = q;
            t = t.next;
            t.next = null;
        }
        return temp.next;
    }

    // Select a Random Node from a Singly Linked List

    // Rearrange a Linked List in Zig-Zag fashion
    // a < b > c < d > e < f … where a, b, c… are consecutive data nodes of the linked list.
    public static Node zigZag(Node head) {
        if (head == null || head.next == null) return head;
        int idx = 0;
        Node p = head;
        while (p.next != null) {
            if ((idx & 1) == 0 && p.data > p.next.data) {
                // swap if a[i] > a[i + 1]
                swap(p);

            } else if ((idx & 1) != 0 && p.data < p.next.data) {
                // swap if a[i] < a[i + 1]
                swap(p);
            }
            p = p.next;
            idx++;
        }
        return head;
    }

    public static void swap(Node p) {
        int temp = p.data;
        p.data = p.next.data;
        p.next.data = temp;
    }

    // Sort linked list which is already sorted on absolute values
    public static Node sortLinkedListSortedOnAbs(Node head) {
       if (head == null || head.next == null) return head;
       Node p = head, q = head.next;
       while (q != null) {
           if (p.data > q.data) {
                Node t = q.next;
                p.next = t;
                q.next = head;
                head = q;
                q = t;
           } else {
               p = q;
               q = q.next;
           }
       }
       return head;
    }

    // Merge K sorted linked lists
    public static Node mergeKSortedLL(Node[] arr, int k) {
        return mergeKSortedLL_rec(arr, 0, arr.length - 1);
    }

    public static Node mergeKSortedLL_rec(Node[] arr, int si, int ei) {
        if (si == ei) return arr[si];
        int mid = (si + ei) >> 1;
        Node left = mergeKSortedLL_rec(arr, si, mid);
        Node right = mergeKSortedLL_rec(arr, mid + 1, ei);
        return mergeKSortedLL_merge(left, right);
    }

    public static Node mergeKSortedLL_merge(Node left, Node right) {
        Node temp = new Node(-1);
        Node t = temp;
        while (left != null && right != null) {
            if (left.data < right.data) {
                t.next = left;
                left = left.next;
            } else {
                t.next = right;
                right = right.next;
            }
            t = t.next;
        }
        while (left != null) {
            t.next = left;
            left = left.next;
            t = t.next;
        }
        while (right != null) {
            t.next = right;
            right = right.next;
            t = t.next;
        }
        return temp.next;
    }

    // Flatten a multi-level linked list | Set 2 (Depth wise)
    static Node last = null;
    public static Node flattenDepthWise(Node head) {
        if (head == null) return head;
        last = head;
        Node headNext = head.next;
        if (head.bottom != null) {
            head.next = flattenDepthWise(head.bottom);
        }
        if (headNext != null) {
            last.next = flattenDepthWise(headNext);
        }
        return head;
    }

    // Subtract Two Numbers represented as Linked Lists
    static Node diff = null;
    public static Node substractLL(Node h1, Node h2) {
        h1 = removeLeadingZeroes(h1);
        h2 = removeLeadingZeroes(h2);
        int n1 = getSize(h1);
        int n2 = getSize(h2);
        // we will prefer a - b, a > b
        if (n1 < n2) {
            Node temp = h1;
            h1 = h2;
            h2 = temp;
            int temp2 = n1;
            n1 = n2;
            n2 = temp2;
        }
        substractLL_rec(h1, n1, h2, n2);
        return diff;
    }
    // form -> 1 - 2
    public static boolean substractLL_rec(Node h1, int pv1, Node h2, int pv2) {
        if (h1 == null && h2 == null) return false;
        // no borrow required when h is null
        boolean borrow = false;
        int val1 = h1.data, val2 = h2.data;

        if (pv1 == pv2) {
            boolean borrow1 = substractLL_rec(h1.next, pv1 - 1, h2.next, pv2 - 1);
            if (borrow1) {
                val1 -= 1;
            }
            if (val1 < val2) {
                val1 += 10;
                borrow = true;
            }
            int d = val1 - val2;
            // make and add the new node
            Node nn = new Node(d);
            nn.next = diff;
            diff = nn;

        } else if (pv1 > pv2) {
            boolean borrow2 = substractLL_rec(h1.next, pv1 - 1, h2, pv2);
            if (borrow2) {
                val1 -= 1;
                if (val1 < 0) {
                    val1 += 10;
                    borrow = true;
                }
            }
            // at this point d = val1 - 0;
            Node nn = new Node(val1);
            nn.next = diff;
            diff = nn;
        }
        return borrow;
    }

    public static Node removeLeadingZeroes(Node head) {
        if (head == null) return head;
        Node p = head;
        boolean flag = false;
        while (p.next != null && p.data == 0 && p.next.data == 0) {
            flag = true;
            p = p.next;
        }
        if (!flag) return head;
        else return p.next;
    }

    // Find pair for given sum in a sorted singly linked without extra space
    public static void targetSumLL(Node head, int k) {
        if (head == null || head.next == null) return;
        Node midNode = mid(head);
        Node midNext = midNode.next;
        midNode.next = null;
        Node nh = reverse(midNext);
        Node p = head, q = nh;
        while (p != null && q != null) {
            int sum = p.data + q.data;
            if (sum == k) {
                System.out.println(p.data + ", " + q.data);
                p = p.next; q = q.next;
            } else if (sum < k) p = p.next;
            else q = q.next;
        }
    }

    // Multiply two linked lists 
    public static long multiplyLL(Node h1, Node h2) {
        long num1 = 0, num2 = 0;
        Node p = h1;
        while (p != null) {
            num1 = num1 * 10 + p.data;
            p = p.next;
        }
        p = h2;
        while (p != null) {
            num2 = num2 * 10 + p.data;
            p = p.next;
        }
        long ans = num1 * num2;
        return ans;
    }

    //  reverse a linked list in groups of k
    static Node th = null, tt = null;
    public static void reverseInGroupOfK_addFirst(Node cn) {
        if (th == null && tt == null) {
            th = tt = cn;
        } else {
            cn.next = th;
            th = cn;
        }
    }

    public static Node reverseInGroupOfK(Node head, int k) {
        if (head == null || head.next == null || k <= 0) return head;
        int n = getSize(head);
        Node curr = head, oh = null, ot = null;
        while (n >= k) {
            int tempk = k;
            while (tempk-- > 0) {
                Node fwd = curr.next;
                curr.next = null;
                reverseInGroupOfK_addFirst(curr);
                curr = fwd;
            }
            if (oh == null) {
                oh = th; ot = tt;
            } else {
                ot.next = th;
                ot = tt;
            }
            th = null;
            tt = null;
            n -= k;
        }
        ot.next = curr;
        return oh;
    }

    // rotate a linked list in anti clock wise dir or left rotate
    public static Node rotateLLAnti(Node head, int k) {
        if (head == null || head.next == null) return head;
        Node tail = head;
        while (tail.next != null) tail = tail.next;
        Node p = head;
        int idx = 0;
        while (idx < k - 1) {
            p = p.next;
            idx++;
        }
        // System.out.println(p.data);
        // if p reaches last node or k == size
        if (p.next == null) return head;
        Node newHead = p.next;
        p.next = null;
        tail.next = head;
        return newHead;
    }

    // rotate a ll k units right or clockwise
    public static Node rotateLLClock(Node head, int k) {
        // kth node from last
        int idx = 0;
        Node p = head, q = head, tail = head;
        while (tail.next != null) tail = tail.next;
        while (idx <= k) {
            idx++;
            q = q.next;
        }
        // check if q is at end ie: k == size
        if (q == null) return head;
        while (q != null) {
            p = p.next;
            q = q.next;
        }
        // p is on node before kth node from last
        Node nh = p.next;
        p.next = null;
        tail.next = head;
        return nh;
    }

    // rotate block wise
    // block size k, d = +ve right, d < 0 left side
    // time needed
    public static Node rotateBlockWise(Node head, int k, int d) {
        int n = getSize(head);
        Node oh = null, ot = null, th = null, tt = null;
        Node curr = head;
        while (n >= k) {
            int tempk = k;
            while (tempk-- > 0) {
                Node fwd = curr.next;
                curr.next = null;
                // add last
                if (th == null) {
                    th = curr;
                    tt = curr;
                } else {
                    tt.next = curr;
                    tt = curr;
                }
                curr = fwd;
            }
            Node rh = null;
            if (d > 0) {
                rh = rotateLLClock(th, 1);
            } else if (d < 0) {
                rh = rotateLLAnti(th, 1);
            }
            Node rt = getTail(rh);
            // adjust oh and ot
            if (oh == null) {
                oh = th; ot = tt;
            } else {
                ot.next = th;
                ot = tt;
            }
            th = null; tt = null;
            n -= k;
        }
        ot.next = curr;
        return oh;
    }

    public static Node getTail(Node p) {
        if (p == null || p.next == null) return p;
        while (p.next != null) p = p.next;
        return p;
    }

    // josephus problem
    // n -> 0 to n - 1
    public static int josephusProb(int n, int k) {
        if (n == 1) return 0;
        int recAns1 = josephusProb(n - 1, k);
        int ourValue = (recAns1 + k) % n;
        return ourValue;
    }

    // Count triplets in a sorted doubly linked list whose sum is equal to a given value x
    public static void countTripletsInDLL(Node head, int tar) {
        // bottom means prev ptr
        Node i = head, tail = getTail(head);
        while (i != null && i.next.next != null) {
            if (i.data == i.bottom.data) {
                i = i.next;
            }
            Node j = i.next, k = tail;
            while (j.next != k) {
                int sum = i.data + j.data + k.data;
                if (sum == tar) {
                    System.out.println(i.data + ", " + j.data + ", " + k.data);
                    j = j.next; k = k.bottom;
                    // while (j.next != k && j.data == j.bottom.data) j = j.next;
                } else if (sum < tar) {
                    j = j.next;
                } else {
                    k = k.bottom;
                }
            }
            i = i.next;
        }
    }

    // Sort the biotonic doubly linked list
    public static void addLastDll(Node th, Node tt, Node cn) {
        if (th == null) {
            th = tt = cn;
        } else {
            tt.next = cn;
            cn.bottom = tt;
            tt = cn;
        }
    }

    public static Node sortBitonicDll(Node head) {
        // bottom => prev
        Node i = head, j = getTail(head);
        Node th = null, tt = null;
        while (i != j) {
            if (i.data < j.data) {
                Node cn = i;
                i = i.next;
                if (i != null) i.bottom = null;
                cn.next = null;
                addLastDll(th, tt, cn);
            } else {
                Node cn = j;
                j = j.bottom;
                if (j != null) j.next = null;
                cn.bottom = null;
                addLastDll(th, tt, cn);
            }
        }
        return th;
    }

    public static void solve() {
        int[] a1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] a2 = {6};
        Node h1 = getHead(a1);
        Node h2 = getHead(a2);  
        Node nn = rotateBlockWise(h1, 3, 1);
        printLL(nn);
    }

    public static void printLL(Node head) {
        if (head == null) return;
        Node p = head;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
    }

    public static void printLL2(Node head) {
        if (head == null) return;
        Node p = head;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.bottom;
        }
    }

    public static Node getHead(int[] arr) {
        Node temp = new Node(-1);
        Node t = temp;
        for (int i = 0; i < arr.length; i++) {
            Node nn = new Node(arr[i]);
            t.next = nn;
            t = t.next;
        }
        return temp.next;
    }

    public static void main(String args[]) {
        solve();
    }
}