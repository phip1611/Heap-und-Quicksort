package de.phip1611.numeric_sort_algorithms;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by phip1611 on 31.07.16.
 */
public class HeapsortTest {
    private Heapsort heapsort;

    @Before
    public void setUp() {
        this.heapsort = new Heapsort();
    }

    @Test
    public void sortArrayTest1() throws Exception {
        double[] nums = new double[10];
        for (int i = 0; i <10; i++) {
            nums[i] = i;
        }
        nums = heapsort.sortArray(nums);
        for (int i = 0; i <10; i++) {
            assertEquals("Falsch sortiert", (int)i, (int)nums[i]);
        }
    }

    @Test
    public void sortArrayTest2() throws Exception {
        double[] nums = new double[10];
        for (int i = 9; i >= 0; i--) {
            nums[9-i] = i;
        }
        nums = heapsort.sortArray(nums);
        for (int i = 0; i <10; i++) {
            assertEquals("Falsch sortiert", (int)i, (int)nums[i]);
        }
    }

    @Test
    public void sortArrayTest3() throws Exception {
        double[] nums = new double[10];
        nums[0] = 7;
        nums[1] = 8;
        nums[2] = 9;
        nums[3] = 4;
        nums[4] = 5;
        nums[5] = 6;
        nums[6] = 3;
        nums[7] = 0;
        nums[8] = 2;
        nums[9] = 1;
        nums = heapsort.sortArray(nums);
        for (int i = 0; i <10; i++) {
            assertEquals("Falsch sortiert", (int)i, (int)nums[i]);
        }
    }

}