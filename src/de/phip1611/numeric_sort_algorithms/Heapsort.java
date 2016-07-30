package de.phip1611.numeric_sort_algorithms;

/**
 * Created by phip1611 on 27.07.16.
 */
public class Heapsort extends AbstractNumericSortAlgorithm {
    private HeapNode rootHeapNode;
    private Heap heapInstance;

    public Heapsort() {
        this.heapInstance = new Heap();
    }

    public void setNums(double[] nums) {
        this.nums = nums;
    }

    // ein komplettes Sinkenlassen eines einzelnen Elementes
    // kann danach mit print visualisiert werden
    public void singleShiftDown(double key) {
        HeapNode x = getHeapNode(key);
        heapInstance.singleShiftDown(x);
    }

    public void shiftDownAll() {
        // wenn man es so mehrfach durchlaufen lässt funktioniert es auf alle Fälle
        // für relativ kleine Zahlenfolgen
        while (canShiftDownRootNode()) {
            this.shiftDownAllR(this.rootHeapNode);
        }
    }

    public boolean canShiftDownRootNode() {
        if (rootHeapNode != null) {
            if (rootHeapNode.getLeft().getKey() > rootHeapNode.getKey()
                    || rootHeapNode.getLeft().getKey() > rootHeapNode.getKey()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void shiftDownAllR(HeapNode node) {
        if (node != null) {
            heapInstance.singleShiftDown(node);
            shiftDownAllR(node.getLeft());
            shiftDownAllR(node.getRight());
        }
    }

    private HeapNode getHeapNode(double key) {
        return getHeapNodeRec(key, rootHeapNode);
    }

    private HeapNode getHeapNodeRec(double key, HeapNode curr) {
        if (curr == null) {
            return null;
        }
        if (curr.getKey() == key) {
            return curr;
        }
        else {
            HeapNode x;
            x = getHeapNodeRec(key, curr.getLeft());
            if (x!=null) return x;
            x = getHeapNodeRec(key, curr.getRight());
            if (x!=null) return x;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Heapsort: Heap besteht aus %d Zahlen:",nums.length);
    }

    public void printHeap() {
        printHeapRecur(rootHeapNode);
        System.out.println("\n");
    }

    @Override
    public double[] sortArray() {

        createHeap();

        return this.nums;
    }

    public void createHeap() {
        rootHeapNode = new HeapNode(nums[0],1);
        createHeapRecursively(rootHeapNode, 1, nums.length);
    }

    private void createHeapRecursively(HeapNode node, int binaryTreeNodeIndex, int nodeCount) {
        // vorgehen wie bei einem vollständigen, balancierten Binären Baum
        // linker Folgeknoten hat Index key*2, rechter Folgeknoten hat Index key*2+1
        if (binaryTreeNodeIndex*2 <= nodeCount) {
            HeapNode n = new HeapNode(nums[binaryTreeNodeIndex*2-1],binaryTreeNodeIndex*2);
            node.setLeft(n);
            createHeapRecursively(n, binaryTreeNodeIndex*2, nodeCount);
        }
        if (binaryTreeNodeIndex*2+1 <= nodeCount) {
            HeapNode n = new HeapNode(nums[binaryTreeNodeIndex*2+1-1],binaryTreeNodeIndex*2+1);
            node.setRight(n);
            createHeapRecursively(n, binaryTreeNodeIndex*2+1, nodeCount);
        }
    }

    private void printHeapRecur(HeapNode node) {
        if (node.getLeft() != null) {
            System.out.print("(");
            printHeapRecur(node.getLeft());
            System.out.print(")<--");
        }
        System.out.print("["+node.getIndex()+": "+node.getKey()+"]");
        if (node.getRight() != null) {
            System.out.print("-->(");
            printHeapRecur(node.getRight());
            System.out.print(")");
        }
    }

    protected class HeapNode implements Comparable<HeapNode> {
        private double key;
        private int index;
        private HeapNode left;
        private HeapNode right;
        public HeapNode(double key, int index) {
            this(key, index, null, null);
        }
        public HeapNode(double key, int index, HeapNode left, HeapNode right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public double getKey() {
            return this.key;
        }

        public void setKey(double key) {
            this.key = key;
        }

        public void setLeft(HeapNode left) {
            this.left = left;
        }
        public void setRight(HeapNode right) {
            this.right = right;
        }

        public HeapNode getLeft() {
            return left;
        }

        public HeapNode getRight() {
            return right;
        }

        @Override
        public String toString() {
            return "HeapNode{" +
                    "index=" + index +
                    ", key=" + key +
                    '}';
        }

        @Override
        public int compareTo(HeapNode o) {
            return Double.compare(key, o.getKey());
        }
    }

    protected class Heap {
        /**
         * Lässt ein einzelnes Element von seiner Position maximal weit sinken
         * @param node
         */
        private void singleShiftDown(HeapNode node) {
            System.out.printf("Debug: Sinken lassen von %f\n",node.getKey());
            if (node == null) {
                System.err.println("Node is null du Pappnase!");
                return;
            }
            else {
                HeapNode nextNode = null; // Knoten wohin sinken gelassen wurde
                if (node.getRight() != null && node.getLeft() != null) {
                    System.out.println("shiftdown: es gibt rechten und linken nachfolger");
                    if (node.getLeft().getKey() > node.getKey()) {
                        if (node.getRight().getKey() > node.getLeft().getKey()) {
                            System.out.println("rechter Nachfolger ist größer als der linke und größer als der key");
                            double tmpkey = node.getRight().getKey();
                            node.getRight().setKey(node.getKey());
                            node.setKey(tmpkey);
                            nextNode = node.getRight();
                        } else {
                            System.out.println("linker Nachfolger ist größer als der rechte");
                            double tmpkey = node.getLeft().getKey();
                            node.getLeft().setKey(node.getKey());
                            node.setKey(tmpkey);
                            nextNode = node.getLeft();
                        }
                    } else if (node.getRight().getKey() > node.getKey()) {
                        System.out.println("rechter Knoten ist größer als der key, der linke aber nicht");
                        double tmpkey = node.getRight().getKey();
                        node.getRight().setKey(node.getKey());
                        node.setKey(tmpkey);
                        nextNode = node.getRight();
                    }
                } else if (node.getRight() != null) {
                    if (node.getRight().getKey() > node.getKey()) {
                        double tmpkey = node.getKey();
                        node.setKey(node.getRight().getKey());
                        node.getRight().setKey(tmpkey);
                        nextNode = node.getRight();
                    }
                } else if (node.getLeft() != null) {
                    if (node.getLeft().getKey() > node.getKey()) {
                        double tmpkey = node.getKey();
                        node.setKey(node.getLeft().getKey());
                        node.getLeft().setKey(tmpkey);
                        nextNode = node.getLeft();
                    }
                }
                // Element weiter sinken lassen
                if (nextNode != null) {
                    singleShiftDown(nextNode);
                }
            }
        }
    }
}
