public class DllToBst {
 
    private ListNode head = null;
    private ListNode tail = null;
 
    public void addToList(int data) {
        ListNode n = new ListNode(data);
        if (head == null) {
            head = n;
            tail = n;
        } else {
            n.prev = tail;
            tail.next = n;
            tail = n;
        }
    }

    public void convertDllToBST() {
        int len = getListLength();
        head = convertDllToBST(len);
        tail = null;
    }
 
    private int getListLength() {
        int len = 0;
        ListNode curr = head;
        while (curr != null) {
            len++;
            curr = curr.next;
        }
        return len;
    }
 
    private ListNode convertDllToBST(int len) {
        if (len == 0) {
            return null;
        }
 
        ListNode left = convertDllToBST(len / 2);
        ListNode root = head;
        root.prev = left;
        head = head.next;
        ListNode right = convertDllToBST(len - (len / 2) - 1);
        root.next = right;
        return root;
    }
 
    public void printInorderOrderTraversal() {
        printInorderOrderTraversalHelper(head);
    }
 
    private void printInorderOrderTraversalHelper(ListNode root) {
        if (root == null) {
            return;
        }
 
        printInorderOrderTraversalHelper(root.prev);
        System.out.print(root.data + " ");
        printInorderOrderTraversalHelper(root.next);
    }
 
    public static void main(String args[]) {
        DllToBst dll = new DllToBst();
        for (int i = 1; i < 8; i++) {
            dll.addToList(i);
        }
        dll.convertDllToBST();
        dll.printInorderOrderTraversal();
    }
}
 
class ListNode {
    int data;
    ListNode prev;
    ListNode next;
 
    public ListNode(int data) {
        this.data = data;
    }
}