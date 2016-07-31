package de.phip1611.numeric_sort_algorithms;

/**
 * Heapsort-Implementierung in Java. Es kann entweder direkt eine Zahlenfolge sortiert werden,
 * oder aber Schritt für Schritt Operationen auf dem Heap ausgeführt werden, die mit
 * der print-Methode sichtbar gemacht werden können.
 */
public class Heapsort extends AbstractNumericSortAlgorithm {
    private HeapNode rootHeapNode;
    private Heap heapInstance;
    private int swapIndex;

    /**
     * Multiples, rekursives Sinkenlassen,
     * bis die Heap-Eigenschaft gilt.
     * Notwendig, da nach Sinkenlassen-Vertauschungen
     * weitere Sinkenlassen-Operationen am selben Knoten
     * notwendig sein können.
     */
    private void shiftDownAll() {
        while (canShiftDownAny()) {
            this.shiftDown();
        }
    }

    private boolean isAlreadySorted() {
        for (int i = 0; i < nums.length; i++) {
            if (i+1 < nums.length) {
                if (nums[i] > nums[i+1]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Einfaches Sinkenlassen über den ganzen Heap
     */
    private void shiftDown() {
        this.shiftDownAllR(this.rootHeapNode);
    }

    /**
     * Gibt an, ob man irgendein Element sinken lassen kann im Heap.
     * @return boolean
     */
    private boolean canShiftDownAny() {
        return canShiftDownAnyR(rootHeapNode);
    }

    /**
     * Rekursive Hilfsfunktion von canShiftDownAny()
     * @param node
     * @return
     */
    private boolean canShiftDownAnyR(HeapNode node) {
        if (node == null) return false;
        if (node.getLeft() != null)
            if (node.getLeft().getValue() > node.getValue()) {
                return true;
            }
        if (node.getRight() != null)
            if (node.getRight().getValue() > node.getValue()) {
                return true;
            }
        return canShiftDownAnyR(node.getLeft()) || canShiftDownAnyR(node.getRight());
        //return false;
    }

    /**
     * Rekursive Hilfsfunktion, die einmal im ganzen Heap Elemente sinkenlässt.
     * @param node
     */
    private void shiftDownAllR(HeapNode node) {
        if (node != null) {
            heapInstance.singleShiftDown(node);
            shiftDownAllR(node.getLeft());
            shiftDownAllR(node.getRight());
        }
    }

    private HeapNode getHeapNodeByIndex(double index) {
        return getHeapNodeByIndexRec(index, rootHeapNode);
    }

    private HeapNode getHeapNodeByIndexRec(double index, HeapNode curr) {
        if (curr == null) {
            return null;
        }
        if (curr.getIndex() == index) {
            return curr;
        } else {
            HeapNode x;
            x = getHeapNodeByIndexRec(index, curr.getLeft());
            if (x!=null) return x;
            x = getHeapNodeByIndexRec(index, curr.getRight());
            if (x!=null) return x;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Heapsort: Heap besteht aus %d Zahlen:",nums.length);
    }

    private void printHeap() {
        printHeapRecur(rootHeapNode);
        System.out.println("\n");
    }

    /**
     * Sortiert eine Liste an Zahlen mit Heapsort und gibt sie zurück.
     * @param nums
     * @return
     */
    @Override
    public double[] sortArray(double[] nums) {
        this.heapInstance = new Heap();
        this.nums = nums;
        if (isAlreadySorted()) {
            return this.nums;
        }
        this.swapIndex = nums.length;
        this.createHeap();
        //System.out.println("====== Anfangs-Heap =======");
        //this.printHeap();
        //System.out.println("====== /Anfangs-Heap =======");
        this.shiftDownAll();
        //this.printHeap();
        this.swapAndShiftDownAll();
        this.heapToArray();
        return this.nums;
    }

    /**
     * Wenn alle Elemente fertig gesunken sind, dann wird
     * diese Methode einen einzelnen Tauschvorgang sowie einen
     * Sinkenlassen-Vorgang ausführen.
     */
    private void singleSwapAndShiftDown() {
        HeapNode active = this.getHeapNodeByIndex(this.swapIndex);
        double tmpvalue = active.getValue();
        active.setValue(this.rootHeapNode.getValue());
        //System.out.printf("Tausch: %d(%d) --> %d(%d)\n", this.rootHeapNode.getIndex(), (int)this.rootHeapNode.getValue(), active.getIndex(), (int)tmpvalue);
        this.rootHeapNode.setValue(tmpvalue);
        this.swapIndex--;
        this.shiftDown();
    }

    private void swapAndShiftDownAll() {
        while (swapIndex > 1) {
            this.singleSwapAndShiftDown();
        }
    }

    /**
     * Erstellt den Heap aus der eingegebenen Zahlenfolge.
     */
    private void createHeap() {
        rootHeapNode = new HeapNode(nums[0],1);
        createHeapRecursively(rootHeapNode, 1, nums.length);
    }

    private void heapToArray() {
        this.heapToArrayR(this.rootHeapNode, 1);
    }

    private void heapToArrayR(HeapNode node, int index) {
        if (node == null) {
            return;
        }
        if (index > this.nums.length+1) {
            return;
        }
        this.nums[index-1] = node.getValue();
        this.heapToArrayR(node.getLeft(), index*2);
        this.heapToArrayR(node.getRight(), index*2+1);
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
        System.out.print("["+node.getIndex()+": "+node.getValue()+"]");
        if (node.getRight() != null) {
            System.out.print("-->(");
            printHeapRecur(node.getRight());
            System.out.print(")");
        }
    }

    protected class HeapNode implements Comparable<HeapNode> {
        private double value;
        private int index;
        private HeapNode left;
        private HeapNode right;
        public HeapNode(double key, int index) {
            this(key, index, null, null);
        }
        public HeapNode(double value, int index, HeapNode left, HeapNode right) {
            this.value = value;
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

        public double getValue() {
            return this.value;
        }

        public void setValue(double value) {
            this.value = value;
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

        public boolean hasRight() {
            return !(this.right == null);
        }

        public boolean hasLeft() {
            return !(this.left == null);
        }

        @Override
        public String toString() {
            return "HeapNode{" +
                    "index=" + index +
                    ", key=" + value +
                    '}';
        }

        @Override
        public int compareTo(HeapNode o) {
            return Double.compare(value, o.getValue());
        }
    }

    protected class Heap {
        /**
         * Lässt ein einzelnes Element von seiner Position maximal weit sinken
         * @param node
         */
        private void singleShiftDown(HeapNode node) {
            if (node == null) return;
            if (node.getIndex() > swapIndex) return;
            boolean shiftToRight = false, shiftToLeft = false;

            // gibt nur rechten Nachfolger
            if (node.hasRight() && !node.hasLeft()) {
                if (node.getRight().getIndex() > swapIndex) return;
                if (node.getRight().getValue() > node.getValue()) {
                    shiftToRight = true;
                }
            }
            // gibt zwei Nachfolger
            if (node.hasRight() && node.hasLeft()) {
                // rechter Nachfolger größer, linker nicht
                if (node.getRight().getValue() > node.getValue() &&
                        !(node.getLeft().getValue() > node.getValue())) {
                    if (node.getRight().getIndex() > swapIndex) return;
                    shiftToRight = true;
                }
                // linker Nachfolger größer, rechter nicht
                else if (node.getLeft().getValue() > node.getValue() &&
                        !(node.getRight().getValue() > node.getValue())) {
                    if (node.getLeft().getIndex() > swapIndex) return;
                    shiftToLeft = true;
                }
                // beide Nachfolger größer als aktueller Knoten
                else if (node.getRight().getValue() > node.getValue() &&
                        (node.getLeft().getValue() > node.getValue())) {
                    // rechter Nachfolger größer als der Linke
                    if (node.getRight().getValue() > node.getLeft().getValue()) {
                        if (node.getRight().getIndex() > swapIndex) return;
                        shiftToRight = true;
                    } else {
                        if (node.getLeft().getIndex() > swapIndex) return;
                        shiftToLeft = true;
                    }
                }
            // gibt nur einen Linken Nachfolger
            } else if (!node.hasRight() && node.hasLeft()) {
                if (node.getLeft().getValue() > node.getValue()) {
                    if (node.getLeft().getIndex() > swapIndex) return;
                    shiftToLeft = true;
                }
            }

            if (shiftToLeft) {
                double tmpvalue = node.getLeft().getValue();
                node.getLeft().setValue(node.getValue());
                node.setValue(tmpvalue);
                singleShiftDown(node.getLeft());
            } else if (shiftToRight) {
                double tmpvalue = node.getRight().getValue();
                node.getRight().setValue(node.getValue());
                node.setValue(tmpvalue);
                singleShiftDown(node.getRight());
            }
        }
    }
}
