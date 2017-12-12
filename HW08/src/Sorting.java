import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Nate Knauf
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     * <p>
     * See the PDF for more info on this sort.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Null array!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Null comparator!");
        }
        boolean swapped = true;
        int n = arr.length;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                    swapped = true;
                }
            }
            n--;
        }
    }

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     * <p>
     * See the PDF for more info on this sort.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Null array!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Null comparator!");
        }
        for (int i = 1; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(arr[j], temp) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     * <p>
     * int pivotIndex = r.nextInt(b - a) + a;
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Note that there may be duplicates in the array.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws IllegalArgumentException if the array or comparator or rand is
     *                                  null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Null Array!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Null Comparator!");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Null Random!");
        }
        quickHelp(arr, comparator, rand, 0, arr.length - 1);
    }


    /**
     *  Helper method which is called recursively to quicksort the array arr
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @param l          the left index
     * @param r          the right index
     */
    public static <T> void quickHelp(T[] arr, Comparator<T> comparator,
                                     Random rand, int l, int r) {
        if (r > l) {
            int pivotIndex = rand.nextInt(r - l) + l;
            T temp = arr[pivotIndex];
            arr[pivotIndex] = arr[l];
            arr[l] = temp;
            int li = l + 1;
            int ri = r;
            while (li <= ri && li < arr.length) {
                while (li < arr.length
                        && comparator.compare(arr[li], arr[l]) <= 0) {
                    li++;
                }
                while (ri >= li && comparator.compare(arr[ri], arr[l]) > 0) {
                    ri--;
                }
                if (li < arr.length && ri > 0 && li < ri) {
                    temp = arr[li];
                    arr[li] = arr[ri];
                    arr[ri] = temp;
                    li++;
                    ri--;
                }
            }
            temp = arr[l];
            arr[l] = arr[ri];
            arr[ri] = temp;
            quickHelp(arr, comparator, rand, l, ri - 1);
            quickHelp(arr, comparator, rand, ri + 1, r);
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     * <p>
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Null Array!");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Null Comparator!");
        }
        int len = arr.length;
        if (len <= 1) {
            return;
        }
        int midIndex = len / 2;
        T[] larr = (T[]) new Object[midIndex];
        for (int i = 0; i < midIndex; i++) {
            larr[i] = arr[i];
        }
        mergeSort(larr, comparator);
        T[] rarr = (T[]) new Object[len - midIndex];
        for (int i = 0; i < len - midIndex; i++) {
            rarr[i] = arr[midIndex + i];
        }
        mergeSort(rarr, comparator);
        int li = 0;
        int ri = 0;
        int ci = 0;
        while (li < midIndex && ri < len - midIndex) {
            if (comparator.compare(rarr[ri], larr[li]) >= 0) {
                arr[ci++] = larr[li++];
            } else {
                arr[ci++] = rarr[ri++];
            }
        }
        while (li < midIndex) {
            arr[ci++] = larr[li++];
        }
        while (ri < len - midIndex) {
            arr[ci++] = rarr[ri++];
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     * <p>
     * It should be:
     * stable
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     * <p>
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     * <p>
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @param arr the array to be sorted
     * @return the sorted array
     * @throws IllegalArgumentException if the array is null
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Null Array!");
        }
        ArrayList[] buckets = new ArrayList[19];
        for (int b = 0; b < buckets.length; b++) {
            buckets[b] = new ArrayList();
        }
        int digits = maxDig(arr);
        int exp = 0;
        int bucket = 0;
        int index = 0;
        while (digits + 1 > 0) {
            for (int j = 0; j < arr.length; j++) {
                bucket = (arr[j] / pow(10, exp)) % 10;
                buckets[bucket + 9].add(arr[j]);
            }
            index = 0;
            for (bucket = 0; bucket < buckets.length; bucket++) {
                while (!buckets[bucket].isEmpty()) {
                    arr[index++] = (int) buckets[bucket].remove(0);
                }
            }
            exp++;
            digits--;
        }
        return arr;
    }

    /**
     * Helper method to get the max number of digits in an array
     *
     * @param arr the array to be sorted
     * @return the maximum number of digits in a number in arr
     */
    private static int maxDig(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }
        int count = 0;
        while (max > 0) {
            max /= 10;
            count++;
        }
        return count;
    }

    /**
     * Implement MSD (most significant digit) radix sort.
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     * <p>
     * It should:
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     * <p>
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @param arr the array to be sorted
     * @return the sorted array
     * @throws IllegalArgumentException if the array is null
     */
    public static int[] msdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array!");
        }
        int maxl = maxDig(arr);
        return msdHelp(arr, maxl);
    }

    /**
     * Recursive helper method for the MSD sorting method
     * @param arr the array to be sorted
     * @param maxl max number of digits in arr
     * @return the sorted array
     */
    public static int[] msdHelp(int[] arr, int maxl) {
        if (maxl <= 0) {
            return arr;
        }
        ArrayList<Integer>[] buckets = new ArrayList[19];
        for (int b = 0; b < buckets.length; b++) {
            buckets[b] = new ArrayList<Integer>();
        }
        int len = arr.length;
        int bucket;
        int index;
        for (int j = 0; j < len; j++) {
            bucket = (arr[j] % pow(10, maxl)) / pow((int) 10, maxl - 1);
            buckets[bucket + 9].add(arr[j]);
        }
        index = 0;
        for (bucket = 0; bucket < buckets.length; bucket++) {
            if (buckets[bucket].size() > 1 && maxl > 1) {
                int[] intarr = new int[buckets[bucket].size()];
                for (int b = 0; b < intarr.length; b++) {
                    intarr[b] = (int) buckets[bucket].get(b);
                }
                intarr = msdHelp(intarr, maxl - 1);
                buckets[bucket] = new ArrayList<Integer>();
                for (int x : intarr) {
                    buckets[bucket].add(x);
                }
            }
            while (!buckets[bucket].isEmpty()) {
                arr[index] = buckets[bucket].remove(0);
                index++;
            }
        }
        return arr;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     * <p>
     * DO NOT MODIFY THIS METHOD.
     *
     * @param base base of the number
     * @param exp  power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     *                                  0
     * @throws IllegalArgumentException if {@code exp} is negative
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * base;
        }
    }
}
