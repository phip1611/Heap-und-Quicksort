package de.phip1611.numeric_sort_algorithms;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Heapsort: Gib eine beliebige Menge an Zahlen durch \",\"-getrennt ein");
        System.out.print("Input: ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = scanner.nextLine();
        String[] nums = input.split(",");
        double[] numsd = new double[nums.length];
        int i = 0;
        for (String num : nums) {
            numsd[i] = Double.parseDouble(num.trim());
            i++;
        }

        Heapsort hs = new Heapsort();
        numsd = hs.sortArray(numsd);
        System.out.println("Sortiere Zahlenfolge");
        System.out.println(Arrays.toString(numsd));
    }
}
