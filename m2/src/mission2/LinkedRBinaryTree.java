

import java.util.ArrayList;

/**
 *
 * @author Groupe 5.2
 */
public class LinkedRBinaryTree<E> implements RBinaryTree<E> {

    public RBinaryTree<E> right;
    public RBinaryTree<E> left;
    public Position<E> position;
    public E element;
   
    public LinkedRBinaryTree(E element) {
        this.element = element;
    }
    
    public LinkedRBinaryTree(RBinaryTree<E> right, RBinaryTree<E> left, E element) {
        this.right = right;
        this.left = left;
        this.element = element;
    }

    public LinkedRBinaryTree(RBinaryTree<E> right, RBinaryTree<E> left, E element, Position<E> position) {
        this.right = right;
        this.left = left;
        this.position = position;
        this.element = element;
    }

    @Override
    public boolean isEmpty() {

        return size() == 0;
    }

    @Override
    public int size() {
        if (isLeaf()) {
            return 1;
        } else {
            return 1 + left.size() + right.size();
        }
    }

    @Override
    public boolean isLeaf() {

        return this.left == null & this.right == null;
    }

    @Override
    public RBinaryTree<E> leftTree() {

        return this.left;
    }

    @Override
    public Position<E> root() {
        return position;

    }

    @Override
    public RBinaryTree<E> rightTree() {
        return this.right;
    }

    @Override
    public void setElement(E o) {
        this.element = o;

    }

    @Override
    public void setLeft(RBinaryTree<E> tree) {
        this.left = tree;

    }

    @Override
    public void setRight(RBinaryTree<E> tree) {
        this.right = tree;

    }

    /*public void throughTree(ArrayList<Position<E>> al) {
        if (isLeaf()) {
            al.add(this.position);
        } else {
            al.add(this.position);
            this.left.throughTree(al);
            this.right.throughTree(al);
        }
    }*/

    /*@Override
    public Iterable<Position<E>> positions() {

        ArrayList<Position<E>> al = new ArrayList<>();
        throughTree(al);
        return al;

    }*/

    @Override
    public Iterable<Position<E>> positions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
