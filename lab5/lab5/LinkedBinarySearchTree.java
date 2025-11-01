/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 * LinkedBinarySearchTree for tree traversal lab
 * @author Lachlan Plant
 * @param <E>
 */
public class LinkedBinarySearchTree<E extends Comparable> implements Iterable<E>{
    
    private class Node<E>{
        E elem;
        Node<E> parent;
        Node<E> left;
        Node<E> right;
        public Node(E e,Node<E> p, Node<E> l, Node<E> r){
            elem = e;
            parent= p;
            left = l;
            right = r;
        }
    }
    
    private Node<E> root;
    private int size;
    
    /**
     *
     */
    public LinkedBinarySearchTree(){
        root = null;
        size = 0;
    }
    
    /**
     * Adds elem into BST
     * @param elem
     * @return
     */
    public boolean add(E elem){
        if(root == null){
            root = new Node<>(elem, null, null, null);
            size ++;
            return true;
        }
        else{
            root = insert(elem, root, null);
            return true;
        }
    }
    
    /**
     * Recursive BST insertion
     * @param elem
     * @param curr
     * @param from
     * @return
     */
    private Node<E> insert(E elem, Node<E> curr, Node<E> from){
        if(curr == null){
            curr = new Node<>(elem, from, null, null);
            size ++;
            return curr;
        }
        if( elem.compareTo(curr.elem)<0){
            curr.left = insert(elem, curr.left, curr);
        }
        if( elem.compareTo(curr.elem)>0){
            curr.right = insert(elem, curr.right, curr);
        }
        return curr;
    }
    

    
    /*****************************************************************
     *
     * Recursive Printing Functions
     *
     *
     *****************************************************************/
    
    /**
     * Caller method for preorder recursive printing
     */
    public void printPreorderRecursive(){
        System.out.print("Recursive Preorder Printing: ");
        preorderRecursive(root);
    }
    
    /**
     * preorder tree traversal, prints(curr.elem + ", ")
     * @param curr
     */
    private void preorderRecursive(Node<E> curr){
        //Implement Here
        if(curr != null){
            System.out.print(curr.elem + ", ");
            preorderRecursive(curr.left);
            preorderRecursive(curr.right);
        }
    }
    
    /**
     * Caller method for inorder recursive printing
     */
    public void printInorderRecursive(){
        System.out.print("Recursive Inorder Printing: ");
        inorderRecursive(root);
    }
    
    /**
     * inorder tree traversal, prints(curr.elem + ", ")
     * @param curr
     */
    private void inorderRecursive(Node<E> curr){
        //Implement Here
        if(curr != null){
            inorderRecursive(curr.left);
            System.out.print(curr.elem + ", ");
            inorderRecursive(curr.right);
        }
    }
    
    
    /**
     * Caller method for postorder recursive printing
     */
    public void printPostorderRecursive(){
        System.out.print("Recursive Postorder Printing: ");
        postorderRecursive(root);
    }
    
    /**
     * postorder tree traversal, prints(curr.elem + ", ")
     * @param curr
     */
    private void postorderRecursive(Node<E> curr){
        //Implement Here       
        if(curr != null){
            postorderRecursive(curr.left);
            postorderRecursive(curr.right);
            System.out.print(curr.elem + ", ");
        }
    }

    
    
     /*****************************************************************
     *
     * Iterator Functions
     *
     *
     *****************************************************************/
    
    
    public Iterator iterator(){
        return new InorderIterator();
    }
    
    public Iterator inorderIterator(){
        return new InorderIterator();
    }
    
    public Iterator preorderIterator(){
        return new PreorderIterator();
    }
    

    
     /*****************************************************************
     *
     * Iterators 
     *
     *
     *****************************************************************/ 
    
    
    
    
    /**
     * Tree Iterator using preorder traversal for ordering
     */
    private class PreorderIterator implements Iterator<E>{
        Node<E> next;
        
        public PreorderIterator(){
            //Implement Here
            next = root;
        }
        
        public boolean hasNext(){
            return this.next != null;
        }
        
        public E next(){
            //Implement Here
            E elem = next.elem; 
            // Move to the next element in preorder
            if(next.left != null){
                next = next.left;
            } else if(next.right != null){
                next = next.right;
            } else {
                Node<E> parent = next.parent;
                while(parent != null && (next == parent.right || parent.right == null)){
                    next = parent;
                    parent = parent.parent;
                }
                if(parent == null){
                    next = null;
                } else {
                    next = parent.right;
                }
            }
            return elem;
        }
        
        public void remove(){
            // not implemented
            if(next == null){
                throw new IllegalStateException();
            }
            if(next.left == null && next.right == null){
                if(next.parent.left == next){
                    next.parent.left = null;
                } else {
                    next.parent.right = null;
                }
            } else if(next.left != null || next.right != null){
                Node<E> child = (next.left != null) ? next.left : next.right;
                if(next.parent.left == next){
                    next.parent.left = child;
                } else {
                    next.parent.right = child;
                }
                child.parent = next.parent;
            } else {
                Node<E> successor = next.right;
                while(successor.left != null){
                    successor = successor.left;
                }
                next.elem = successor.elem;
                if(successor.parent.left == successor){
                    successor.parent.left = successor.right;
                } else {
                    successor.parent.right = successor.right;
                }
                if(successor.right != null){
                    successor.right.parent = successor.parent;
                }
            }
        }
    }
    
    /**
     * Tree Iterator using inorder traversal for ordering
     */
    private class InorderIterator implements Iterator<E>{
        
        Node<E> next;
        
        public InorderIterator(){
            //Implement Here
            next = root;
            if(next != null){
                while(next.left != null){
                    next = next.left;
                }
            }
        }
        
        public boolean hasNext(){
            return next != null;
            //Implement Here
        }
        
        public E next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            E elem = next.elem;
            // Move to the next element in inorder
            if(next.right != null){
                next = next.right;
                while(next.left != null){
                    next = next.left;
                }
            } else {
                Node<E> parent = next.parent;
                while(parent != null && next == parent.right){
                    next = parent;
                    parent = parent.parent;
                }
                next = parent;
            }
            return elem;
        }
        
        public void remove(){
            // not implemented
            if(next == null){
                throw new IllegalStateException();
            }   
            if(next.left == null && next.right == null){
                if(next.parent.left == next){
                    next.parent.left = null;
                } else {
                    next.parent.right = null;
                }
            } else if(next.left != null || next.right != null){
                Node<E> child = (next.left != null) ? next.left : next.right;
                if(next.parent.left == next){
                    next.parent.left = child;
                } else {
                    next.parent.right = child;
                }
                child.parent = next.parent;
            } else {
                Node<E> successor = next.right;
                while(successor.left != null){
                    successor = successor.left;
                }
                next.elem = successor.elem;
                if(successor.parent.left == successor){
                    successor.parent.left = successor.right;
                } else {
                    successor.parent.right = successor.right;
                }
                if(successor.right != null){
                    successor.right.parent = successor.parent;
                }
            }
        }
    }
}
