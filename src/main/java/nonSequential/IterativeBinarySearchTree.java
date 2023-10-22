package nonSequential;

import sequential.ArrayList;
import sequential.LinkedListQueue;
import sequential.List;
import util.TreeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class IterativeBinarySearchTree<T extends Comparable<T>> implements BinaryTree<T>  {

    protected TreeNode<T> root;

    public IterativeBinarySearchTree(T data) {
        this.root = new TreeNode<>(data);
    }

    public IterativeBinarySearchTree() {
        this.root = null;
    }

    @Override
    public boolean search(T data) {
        return this.search(this.root, data) != null;
    }

    @Override
    public void insert(T data) {
        this.root = this.insert(this.root, data);
    }

    @Override
    public void delete(T data) {
        this.root = this.delete(this.root, data);
    }

    @Override
    public void inOrder() {
        this.inOrder(this.root);
    }

    @Override
    public void preOrder() {
        this.preOrder(this.root);
    }

    @Override
    public void postOrder() {
        this.postOrder(this.root);
    }

    @Override
    public int height() {
        return this.height(this.root);
    }

    @Override
    public int size() {
        return this.size(root);
    }

    @Override
    public T minValue() {
        return this.minValue(this.root);
    }

    @Override
    public T maxValue() {
        return this.maxValue(this.root);
    }

    @Override
    public boolean isBalanced() {
        return this.isBalanced(root);
    }

    @Override
    public void balance() {
        List<T> values = new ArrayList<>();
        this.inOrder(root, values);
        this.buildTree(values);
    }

    public String toString() {
        return this.toString(this.root);
    }

    private T search(TreeNode<T> root, T data){
        
        TreeNode<T> currentNode = root;

        while (currentNode != null) {

            int comparableValue = data.compareTo(currentNode.getKey());

            if (comparableValue < 0) {
                currentNode = currentNode.getLeft();
            } else if (comparableValue > 0) {
                currentNode = currentNode.getRight();
            } else {
                return currentNode.getKey();
            }
        }
        return null;
    }

    private TreeNode<T> insert(TreeNode<T> root, T data) {
        if (root == null) return new TreeNode<>(data);

        TreeNode<T> currentNode = root;
        TreeNode<T> parentNode = null;

        while (currentNode != null) {
            parentNode = currentNode;

            int comparableValue = data.compareTo(currentNode.getKey());

            if (comparableValue < 0) {
                currentNode = currentNode.getLeft();
            } else if (comparableValue > 0) {
                currentNode = currentNode.getRight();
            } else {
                return root;
            }
        }

        // Una vez que encontramos la posición, creamos el nuevo nodo
        TreeNode<T> newNode = new TreeNode<>(data);

        // Ahora, conectamos el nuevo nodo al nodo padre
        if (data.compareTo(parentNode.getKey()) < 0) {
            parentNode.setLeft(newNode);
        } else {
            parentNode.setRight(newNode);
        }

        return root;
    }

    private TreeNode<T> delete(TreeNode<T> root, T data){
        if (root == null) return null;

        TreeNode<T> parent = null;
        TreeNode<T> currentNode = root;

        int comparableValue;

        while (currentNode != null) {

            comparableValue = data.compareTo(currentNode.getKey());

            if (comparableValue < 0) {
                parent = currentNode;
                currentNode = currentNode.getLeft();
            } else if (comparableValue > 0) {
                parent = currentNode;
                currentNode = currentNode.getRight();
            } else break;
        }

        if (currentNode == null) return root;

        // Case 1
        if (currentNode.getLeft() == null && currentNode.getRight() == null) {
            if (parent == null) return null;

            if (parent.getLeft() == currentNode) parent.setLeft(null);
            else parent.setRight(null);
        }

        // Case 2
        else if (currentNode.getLeft() == null || currentNode.getRight() == null) {

            TreeNode<T> child;

            if (currentNode.getLeft() != null) child = currentNode.getLeft();
            else child = currentNode.getRight();

            if (parent == null) return child;

            if (parent.getLeft() == currentNode) parent.setLeft(child);
            else parent.setRight(child);
        }

        // Case 3
        else {
            TreeNode<T> minValueNode = currentNode.getRight();
            TreeNode<T> parentOfMinValueNode = currentNode;

            while (minValueNode.getLeft() != null) {
                parentOfMinValueNode = minValueNode;
                minValueNode = minValueNode.getLeft();
            }

            currentNode.setKey(minValueNode.getKey());
            if (parentOfMinValueNode.getLeft() == minValueNode) parentOfMinValueNode.setLeft(minValueNode.getRight());
            else parentOfMinValueNode.setRight(minValueNode.getRight());
        }
        return root;
    }

    private void inOrder(TreeNode<T> root){
        if (root == null) return;

        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> currentNode = root;

        while (currentNode != null || !stack.isEmpty()) {

            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.getLeft();
            }

            currentNode = stack.pop();
            System.out.print(currentNode.getKey() + " ");

            currentNode = currentNode.getRight();
        }
    }

    private void inOrder(TreeNode<T> root, List<T> values) {
        if(root == null) return;
        this.inOrder(root.getLeft(), values);
        values.add(root.getKey());
        this.inOrder(root.getRight(), values);
    }

    private void preOrder(TreeNode<T> root){
        if (root == null) {
            return;
        }

        Stack<TreeNode<T>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {

            TreeNode<T> currentNode = stack.pop();

            System.out.print(currentNode.getKey() + " ");

            if (currentNode.getRight() != null) stack.push(currentNode.getRight());

            if (currentNode.getLeft() != null) stack.push(currentNode.getLeft());
        }
    }

    private void postOrder(TreeNode<T> root){
        if (root == null) return;

        Stack<TreeNode<T>> stack1 = new Stack<>();
        Stack<TreeNode<T>> stack2 = new Stack<>();

        stack1.push(root);

        while (!stack1.isEmpty()) {

            TreeNode<T> currentNode = stack1.pop();

            stack2.push(currentNode);

            if (currentNode.getLeft() != null) stack1.push(currentNode.getLeft());

            if (currentNode.getRight() != null) stack1.push(currentNode.getRight());
        }

        while (!stack2.isEmpty()) {
            TreeNode<T> currentNode = stack2.pop();
            System.out.print(currentNode.getKey() + " ");
        }
    }

    protected int height(TreeNode<T> root){
        if (root == null) return 0;

        Queue<TreeNode<T>> queue = new LinkedList<>();

        queue.add(root);

        int height = 0;

        while (true) {
            int nodeCount = queue.size();
            if (nodeCount == 0) return height;

            height++;

            while (nodeCount > 0) {

                TreeNode<T> currentNode = queue.poll();

                if (currentNode.getLeft() != null) queue.add(currentNode.getLeft());

                if (currentNode.getRight() != null) queue.add(currentNode.getRight());

                nodeCount--;
            }
        }
    }

    private int size(TreeNode<T> root){
        if (root == null) return 0;

        Queue<TreeNode<T>> queue = new LinkedList<>();

        queue.add(root);

        int count = 0;

        while (!queue.isEmpty()) {

            TreeNode<T> currentNode = queue.poll();

            count++;

            if (currentNode.getLeft() != null) queue.add(currentNode.getLeft());

            if (currentNode.getRight() != null) queue.add(currentNode.getRight());
        }
        return count;
    }

    protected T minValue(TreeNode<T> root){
        if (root == null) return null;

        TreeNode<T> currentNode = root;
        while (currentNode.getLeft() != null) {
            currentNode = currentNode.getLeft();
        }
        return currentNode.getKey();
    }

    private T maxValue(TreeNode<T> root){
        if (root == null) return null;

        TreeNode<T> currentNode = root;
        while (currentNode.getRight() != null) {
            currentNode = currentNode.getRight();
        }
        return currentNode.getKey();
    }

    protected boolean isBalanced(TreeNode<T> root){
        if (root == null) return true;

        Queue<TreeNode<T>> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> currentNode = queue.poll();

            int lh = height(currentNode.getLeft());
            int rh = height(currentNode.getRight());

            if (Math.abs(lh - rh) > 1) return false;

            if (currentNode.getLeft() != null) queue.add(currentNode.getLeft());
            if (currentNode.getRight() != null) queue.add(currentNode.getRight());
        }
        return true;
    }

    private String toString(TreeNode<T> root) {
        sequential.Queue<TreeNode<T>> queue = new LinkedListQueue<>();
        sequential.Queue<Integer> level = new LinkedListQueue<>();
        if(root == null) return "";
        queue.add(root);
        level.add(0);
        StringBuilder sb = new StringBuilder();
        int preLevel = 0;
        while (!queue.empty()) {
            TreeNode<T> temp = queue.poll();
            int l = level.poll();
            if(preLevel != l) {
                sb.append("\n");
                preLevel = l;
            }
            sb.append(temp.getKey() + " ");
            if(temp.getLeft() != null) {
                queue.add(temp.getLeft());
                level.add(l + 1);
            }
            if(temp.getRight() != null) {
                queue.add(temp.getRight());
                level.add(l + 1);
            }
        }
        return sb.toString();
    }

    private void buildTree(List<T> values) {
        this.root = buildTreeUtil(0, values.size() - 1, values);
    }

    private TreeNode<T> buildTreeUtil(int start, int end, List<T> values) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        TreeNode<T> root = new TreeNode<>(values.get(mid));
        root.setLeft(this.buildTreeUtil(start, mid-1, values));
        root.setRight(this.buildTreeUtil(mid+1,end,values));
        return root;
    }
}
