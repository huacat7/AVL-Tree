package assn06;

public class AVLTree<T extends Comparable<T>> implements SelfBalancingBST<T> {
    // Fields
    private T _value;
    private AVLTree<T> _left;
    private AVLTree<T> _right;
    private int _height;
    private int _size;

    static boolean _rdone = false;

    public AVLTree() {
        _value = null;
        _left = null;
        _right = null;
        _height = -1;
        _size = 0;
    }

    /**
     *
     * Rotates the tree left and returns
     * AVLTree root for rotated result.
     */

    private AVLTree<T> rotateLeft() {
        // You should implement left rotation and then use this
        // method as needed when fixing imbalances.
        // TODO
        AVLTree<T> tree = _right;
        AVLTree<T> secondTree = tree._left;
        tree._left = this;
        this._right = secondTree;
        return tree;
    }

    /**
     *
     * Rotates the tree right and returns
     * AVLTree root for rotated result.
     */

    private AVLTree<T> rotateRight() {
        // You should implement right rotation and then use this
        // method as needed when fixing imbalances.
        // TODO
        AVLTree<T> tree = _left;
        AVLTree<T> secondTree = tree._right;
        tree._right = this;
        this._left = secondTree;
        return tree;
    }

    @Override
    public boolean isEmpty() { return size() == 0;
    }

    @Override
    public int height() {
        return _height;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public SelfBalancingBST<T> insert(T element) {
        // TODO
        cleanUp();
        if (_value == null) {
            //Check if if statement is needed
            _value = element;
            _size = 1;
            _height = 0;
            this.change();
            //System.out.print("Element: ");
            //System.out.println(element);
        }
        else {
            if (contains(element))
            {
                return this;
            }
            this.insertNode(element);
            cleanUp();
            this._height = updateHeight();
            this._size = updateSize();
            //System.out.print("Element: ");
            //System.out.println(element);
            AVLTree<T> node = this;
            node = rotation();
            //cleanUp();
            node._height = node.updateHeight();
            node._size = node.updateSize();
            AVLTree._rdone = false;
            node.change();
            return node;
        }
        return this;
    }

    public void insertNode(T element) {
        this._size++;
        if (_value == null) {
            _value = element;
            _size = 1;
            _height = 0;
        }
        else {
            if(_value.compareTo(element) < 0) {
                if (this._right == null) {
                    this._right = new AVLTree<>();
                    this._right._value = element;
                    this._right._size = 1;
                    this._right._height = 0;
                    //this._right._right = new AVLTree<>();
                    //this._right._left = new AVLTree<>();
                }
                else {
                    this._right.insertNode(element);
                }
            }
            else  if (_value.compareTo(element) > 0){
                if (this._left == null) {
                    this._left = new AVLTree<>();
                    this._left._value = element;
                    this._left._size = 1;
                    this._left._height = 0;
                    //this._left._left = new AVLTree<>();
                    //this._left._right = new AVLTree<>();
                }
                else {
                    this._left.insertNode(element);
                }
            }
        }
    }

    public AVLTree<T> rotation() {
        int leftHeight = 0;
        if (_left != null) {
            leftHeight = _left.height() + 1;
        }
        int rightHeight = 0;
        if (_right != null) {
            rightHeight = _right.height() + 1;
        }
        //System.out.print("Left height:");
        //System.out.println(leftHeight);
        //System.out.print("Right height: ");
        //System.out.println(rightHeight);
        if (leftHeight - rightHeight > 1) {
            AVLTree <T> lTree = this._left.rotation();
            if (lTree != this._left) {
                this._left = lTree;
                return this;
            }
            else if (AVLTree._rdone) {
                return this;
            }
            int leftChildHeight = 0;
            if (_left.getLeft() != null) {
                leftChildHeight = _left.getLeft().height() + 1;
            }
            int rightChildHeight = 0;
            if (_left.getRight() != null) {
                rightChildHeight = _left.getRight().height() + 1;
            }
            if (leftChildHeight >= rightChildHeight) {
                AVLTree<T> node = this;
                node = rotateRight();
                AVLTree._rdone = true;
                return node;
            }
            else {
                AVLTree<T> node = this;
                AVLTree<T> nodeTwo = this._left;
                node._left = nodeTwo.rotateLeft();
                this._height = updateHeight();
                node = rotateRight();
                AVLTree._rdone = true;
                return node;
            }
        }
        else if (rightHeight - leftHeight > 1) {
            AVLTree <T> rTree = this._right.rotation();
            if (rTree != this._right) {
                this._right = rTree;
                return this;
            }
            else if (AVLTree._rdone) {
                return this;
            }
            int leftChildHeight = 0;
            if (_right.getLeft() != null) {
                leftChildHeight = _right.getLeft().height() + 1;
            }
            int rightChildHeight = 0;
            if (_right.getRight() != null) {
                rightChildHeight = _right.getRight().height() + 1;
            }
            if (rightChildHeight >= leftChildHeight) {
                AVLTree<T> node = this;
                node = rotateLeft();
                AVLTree._rdone = true;
                return node;
            }
            else {
                AVLTree<T> node = this;
                AVLTree<T> nodeTwo = this._right;
                node._right = nodeTwo.rotateRight();
                this._height = updateHeight();
                node = rotateLeft();
                AVLTree._rdone = true;
                return node;
            }
        }
        else {
            if (_left != null) {
                _left = _left.rotation();
            }
            if (_right != null) {
                _right = _right.rotation();
            }
            return this;
        }
    }

    public int updateHeight() {
        if (_left == null && _right == null) {
            _height = 0;
            return 0;
        }
        else if (_left != null && _right == null) {
            if (_left._value != null) {
                _height =  _left.updateHeight() + 1;
            }
            else {
                return 0;
            }
        }
        else if (_right != null && _left == null) {
            if (_right._value != null) {
                _height = _right.updateHeight() + 1;
            }
            else {
                return 0;
            }
        }
        else {
            _height = Math.max(_left.updateHeight(), _right.updateHeight()) + 1;
        }
        return _height;
    }

    public int updateSize() {
        if (_left == null && _right == null) {
            if (_value == null) {
                _size = 0;
            }
            else {
                _size = 1;
                return 1;
            }
        }
        else if (_left != null && _right == null) {
            if (_left._value != null) {
                _size = _left.updateSize() + 1;
            }
            else {
                return 1;
            }
        }
        else if (_right != null && _left == null) {
            if (_right._value != null) {
                _size =  _right.updateSize() + 1;
            }
            else {
                return 1;
            }
        }
        else {
            _size = (_left.updateSize() + _right.updateSize()) + 1;
        }
        return _size;

    }

    @Override
    public SelfBalancingBST<T> remove(T element) {
        // TODO
        cleanUp();
        if (_value == null) {
            _size = updateSize();
            return this;
        }
        if (!contains(element)) {
            _size = updateSize();
            return this;
        }
        else {
            if (_value == null) {
                _size = updateSize();
                return this;
            }
            else if(this._value.compareTo(element) == 0 && this.getLeft() == null && this.getRight() == null) {
                this._value = null;
                _size = updateSize();
                return this;
            }
            else {
                this.removeNode(element);
            }
            cleanUp();
            this._height = updateHeight();
            AVLTree<T> node = this;
            node = rotation();
            node._height = node.updateHeight();
            node._size = node.updateSize();
            AVLTree._rdone = false;
            node.change();
            return node;
        }
    }

    public void removeNode(T element) {
        if(this._value == null) {
            return;
        }
        if(this._value.compareTo(element) < 0){
            if(this.getRight() != null) {
                ((AVLTree)this.getRight()).removeNode(element);
            }
        }
        else if(this._value.compareTo(element) > 0){
            if(this.getLeft() != null) {
                ((AVLTree<T>)this.getLeft()).removeNode(element);
            }
        }
        else{
            if(this.getLeft() == null && this.getRight() == null){
                this._value = null;
            }
            else if(this.getRight() != null){
                this._value = this._right.findMin();
                ((AVLTree<T>)this.getRight()).removeNode(this._value);
            }
            else{
                this._value = this._left.findMax();
                ((AVLTree<T>)this.getLeft()).removeNode(this._value);
            }
        }
    }

    private void cleanUp() {
        if (this._value == null) {
            this._left = null;
            this._right = null;
            _size = 0;
            _height = 0;
            return;
        } else {
            if (this.getLeft() != null) {
                if (this.getLeft().getValue() == null) {
                    this._left = null;
                } else {
                    ((AVLTree<T>)this.getLeft()).cleanUp();
                }
            }
            if (this.getRight() != null) {
                if (this.getRight().getValue() == null) {
                    this._right = null;
                } else {
                    ((AVLTree<T>)this.getRight()).cleanUp();
                }
            }
        }
    }

    private T successor(AVLTree<T> T){
        AVLTree t = (AVLTree) T.getRight();
        while(t.getLeft() != null){
            t = (AVLTree) t.getLeft();
        }
        T value = (T)t.getValue();
        t = null;
        return value;
    }

    private T predecessor(AVLTree<T> T){
        AVLTree t = (AVLTree) T.getLeft();
        while(t.getRight() != null){
            t = (AVLTree) t.getRight();
        }
        T value = (T)t.getValue();
        t = null;
        return value;
    }

    public void change() {
        if (_left == null && _right == null) {
            _left = new AVLTree<>();
            _right = new AVLTree<>();
        }
        else if (_left != null & _right == null) {
            _right = new AVLTree<>();
            _left.change();
        }
        else if (_right != null && _left == null) {
            _left = new AVLTree<>();
            _right.change();
        }
        else {
            _right.change();
            _left.change();
        }
    }

    @Override
    public T findMin() {
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        if (_left == null) {
            return _value;
        } else {
            if (_left._value == null) {
                return _value;
            }
            return _left.findMin();
        }
    }

    @Override
    public T findMax() {
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        if (_right == null) {
            return _value;
        } else {
            if (_right._value == null) {
                return _value;
            }
            return _right.findMax();
        }
    }

    @Override
    public boolean contains(T element) {
        // TODO
        if (_value == element) {
            return true;
        }
        else if (_value == null) {
            return false;
        }
        else if (element.compareTo(_value) > 0) {
            if (_right != null) {
                return _right.contains(element);
            }
            else {
                return false;
            }
        }
        else {
            if (_left != null) {
                return _left.contains(element);
            }
            else {
                return false;
            }
        }
    }

    @Override
    public T getValue() {
        return _value;
    }

    @Override
    public SelfBalancingBST<T> getLeft() {
        if (isEmpty()) {
            return null;
        }
        return _left;
    }

    @Override
    public SelfBalancingBST<T> getRight() {
        if (isEmpty()) {
            return null;
        }

        return _right;
    }

}

