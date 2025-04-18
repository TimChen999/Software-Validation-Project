public class BinarySearchTree {
    private Node root;

    private class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    public BinarySearchTree() {
        root = null;
    }

    // Insert a value into the BST
    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }

        if (value < root.data)
            root.left = insertRec(root.left, value);
        else if (value >= root.data)
            root.right = insertRec(root.right, value);

        return root;
    }

    // Search for a value in the BST
    public boolean search(int value) {
        return searchRec(root, value);
    }

    private boolean searchRec(Node root, int value) {
        if (root == null)
            return false;

        if (root.data == value)
            return true;

        if (value < root.data)
            return searchRec(root.left, value);
        else
            return searchRec(root.right, value);
    }

    // Delete a value from the BST
    public void delete(int value) {
        root = deleteRec(root, value);
    }

    private Node deleteRec(Node root, int value) {
        if (root == null)
            return root;

        if (value < root.data)
            root.left = deleteRec(root.left, value);
        else if (value > root.data)
            root.right = deleteRec(root.right, value);
        else {
            // Node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // Node with two children
            root.data = minValue(root.right);

            // Delete the successor
            root.right = deleteRec(root.right, root.data);
        }

        return root;
    }

    private int minValue(Node root) {
        int minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }

    // Traversals
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }

    public void preorder() {
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderRec(root.left);
            preorderRec(root.left);
        }
    }

    public void postorder() {
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(Node root) {
        if (root == null)
            return;
        
        postorderRec(root.left);
        postorderRec(root.right);
        System.out.print(root.data + " ");
    }

    // Height of the tree
    public int height() {
        return height(root);
    }

    private int height(Node root) {
        if (root == null)
            return -1;
        else {
            int leftHeight = height(root.left);
            int rightHeight = height(root.right);
            
            return (leftHeight > rightHeight) ? leftHeight : rightHeight;
        }
    }

    // Count nodes in the tree
    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(Node root) {
        if (root == null)
            return 0;
        else
            return 1 + countNodes(root.left) + countNodes(root.right);
    }

    // Main method for testing
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);
        
        System.out.println("Inorder traversal:");
        bst.inorder();
        
        System.out.println("Search for 40: " + bst.search(40));
        System.out.println("Search for 90: " + bst.search(90));
        
        bst.delete(20);
        System.out.println("Inorder after deleting 20:");
        bst.inorder();
        
        bst.delete(30);
        System.out.println("Inorder after deleting 30:");
        bst.inorder();
        
        bst.delete(50);
        System.out.println("Inorder after deleting 50:");
        bst.inorder();
    }
}