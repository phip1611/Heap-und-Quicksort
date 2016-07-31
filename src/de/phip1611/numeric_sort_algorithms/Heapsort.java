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

    public Heapsort() {
        this.heapInstance = new Heap();
    }

    public void setNums(double[] nums) {
        this.nums = nums;
        this.swapIndex = nums.length;
    }

    /**
     * Multiples, rekursives Sinkenlassen,
     * bis die Heap-Eigenschaft gilt.
     * Notwendig, da nach Sinkenlassen-Vertauschungen
     * weitere Sinkenlassen-Operationen am selben Knoten
     * notwendig sein können.
     */
    public void shiftDownAll() {
        while (canShiftDownAny()) {
            this.shiftDown();
        }
    }

    /**
     * Einfaches Sinkenlassen über den ganzen Heap
     */
    public void shiftDown() {
        this.shiftDownAllR(this.rootHeapNode);
    }

    /**
     * Gibt an, ob man irgendein Element sinken lassen kann im Heap.
     * @return boolean
     */
    public boolean canShiftDownAny() {
        return canShiftDownAnyR(rootHeapNode);
    }

    /**
     * Rekursive Hilfsfunktion von canShiftDownAny()
     * @param node
     * @return
     */
    private boolean canShiftDownAnyR(HeapNode node) {
        if (node != null) {
            if (node.getLeft() != null && node.getLeft().getValue() > node.getValue()
                    || node.getRight() != null && node.getLeft().getValue() > node.getValue()) {
                return true ;
            } else {
                return canShiftDownAnyR(node.getRight()) || canShiftDownAnyR(node.getLeft());
            }
        } else {
            return false;
        }
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

    public void printHeap() {
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
        this.nums = nums;
        this.swapIndex = nums.length;
        this.createHeap();
        this.shiftDownAll();
        this.swapAndShiftDownAll();

        return this.nums;
    }

    /**
     * Wenn alle Elemente fertig gesunken sind, dann wird
     * diese Methode einen einzelnen Tauschvorgang sowie einen
     * Sinkenlassen-Vorgang ausführen.
     */
    public void singleSwapAndShiftDown() {
        if (canShiftDownAny()) {
            System.err.println("Es muss erst alles was geht sinkengelassen werden");
        }
        HeapNode active = this.getHeapNodeByIndex(this.swapIndex);
        double tmpvalue = active.getValue();
        System.out.printf("SWAP: %d --> %d\n", (int)this.rootHeapNode.getValue(), (int)tmpvalue);
        active.setValue(this.rootHeapNode.getValue());
        this.rootHeapNode.setValue(tmpvalue);
        this.swapIndex--;
        this.shiftDown();
    }

    public void swapAndShiftDownAll() {
        if (canShiftDownAny()) {
            System.err.println("Es muss erst alles was geht sinkengelassen werden");
        }
        while (swapIndex > 1) {
            this.singleSwapAndShiftDown();
            this.printHeap();
        }
    }

    /**
     * Wenn man manuell auf dem Heap arbeiten möchte, kann man
     * nach dem man die Liste an Zahlen übergeben hat hiermit den Heap erstellen.
     */
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
            if (node == null) {
                System.err.println("Node is null du Pappnase!");
                return;
            }
            else {
                HeapNode nextNode = null; // Knoten wohin sinken gelassen wurde
                if (node.getRight() != null && node.getLeft() != null) {
                    if (node.getLeft().getValue() > node.getValue()) {
                        if (node.getRight().getValue() > node.getLeft().getValue()) {
                            if (!(node.getRight().getIndex() < swapIndex)) {
                                return;
                            }
                            double tmpvalue = node.getRight().getValue();
                            node.getRight().setValue(node.getValue());
                            node.setValue(tmpvalue);
                            nextNode = node.getRight();
                        } else {
                            if (!(node.getLeft().getIndex() < swapIndex)) {
                                return;
                            }
                            double tmpvalue = node.getLeft().getValue();
                            node.getLeft().setValue(node.getValue());
                            node.setValue(tmpvalue);
                            nextNode = node.getLeft();
                        }
                    } else if (node.getRight().getValue() > node.getValue()) {
                        if (!(node.getRight().getIndex() < swapIndex)) {
                            return;
                        }
                        double tmpvalue = node.getRight().getValue();
                        node.getRight().setValue(node.getValue());
                        node.setValue(tmpvalue);
                        nextNode = node.getRight();
                    }
                } else if (node.getRight() != null) {
                    if (node.getRight().getValue() > node.getValue()) {
                        if (!(node.getRight().getIndex() < swapIndex)) {
                            return;
                        }
                        double tmpvalue = node.getValue();
                        node.setValue(node.getRight().getValue());
                        node.getRight().setValue(tmpvalue);
                        nextNode = node.getRight();
                    }
                } else if (node.getLeft() != null) {
                    if (node.getLeft().getValue() > node.getValue()) {
                        if (!(node.getLeft().getIndex() < swapIndex)) {
                            return;
                        }
                        double tmpvalue = node.getValue();
                        node.setValue(node.getLeft().getValue());
                        node.getLeft().setValue(tmpvalue);
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
