public class AVLTree {
    class Node{
        int key;
        int height;

        Node left,right;

        Node(int value){
            key = value;
            height = 1;
            left = null;
            right = null;
        }
    }
    private Node root;

    public int height(Node node){
        if(node == null){
            return 0;
        }
        return node.height;
    }

    public int getBalance(Node node){
        if (node == null){
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    public Node rightRotate(Node y){
        Node x = y.left;
        Node tempSubtree = x.right;

        x.right = y;
        y.left = tempSubtree;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    public Node leftRotate(Node x){
        Node y = x.right;
        Node tempSubtree = y.left;

        y.left = x;
        x.right = tempSubtree;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public Node insertNode(Node node, int key){
        if (node == null){
            return new Node(key);
        }
        if (key < node.key){
            node.left = insertNode(node.left, key);
        }
        else if (key > node.key){
            node.right = insertNode(node.right, key);
        }
        else{
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && key < node.left.key){
            return rightRotate(node);
        }
        if (balance < -1 && key > node.right.key){
            return leftRotate(node);
        }
        if (balance > 1 && key > node.left.key){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && key < node.right.key){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public void insert(int key){
        root = insertNode(root, key);
    }

    public Node minNode(Node node){
        while (node.left != null){
            node = node.left;
        }
        return node;
    }

    public Node deleteNode(Node node, int key){
        if(node == null) {
            return null;
        }
        if (key < node.key) {
            node.left = deleteNode(node.left, key);
        }
        else if (key > node.key){
            node.right = deleteNode(node.right, key);
        }
        else{
            if (node.left == null && node.right == null){
                node = null;
            }
            else if (node.left == null){
                node = node.right;
            }
            else if (node.right == null){
                node = node.left;
            }
            else{
                Node temp = minNode(node.right);
                node.key = temp.key;
                node.right = deleteNode(node.right, temp.key);
            }
        }

        if (node == null){
            return null;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0){
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.left) < 0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && getBalance(node.right) <= 0){
            return leftRotate(node);
        }

        if (balance < -1 && getBalance(node.right) > 0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void delete(int key){
        root = deleteNode(root, key);
    }

    public String serializing(Node node){
        if (node == null){
            return "nil,";
        }
        return node.key + "," + serializing(node.left) + serializing(node.right);
    }

    public String serialize(){
        return serializing(root);
    }

    public static void main(String[] args){
        AVLTree tree = new AVLTree();

        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);

        System.out.println("Serialized AVL Tree: " + tree.serialize());

        tree.delete(6);

        System.out.println("Serialized AVL Tree after deletion: " + tree.serialize());
    }
}
