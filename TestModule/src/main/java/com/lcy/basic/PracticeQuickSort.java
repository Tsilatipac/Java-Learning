package com.lcy.basic;

import java.util.Arrays;
import java.util.Random;

public class PracticeQuickSort {
    public static void main(String[] args) {
        int[] array = new int[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Random().nextInt(100);
        }
        System.out.println(Arrays.toString(array));
        practiceQuickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    private static void practiceQuickSort(int[] array, int left, int right) {
        int l = left;
        int r = right;
        int temp = 0;
        int pivot = array[(left + right) / 2];
        while (l < r) {
            while (array[l] < pivot) {
                l++;
            }
            while (array[r] > pivot) {
                r--;
            }
            if (l == r) {
                break;
            }
            temp = array[l];
            array[l] = array[r];
            array[r] = temp;

            if (array[l] == pivot) {
                r--;
            }
            if (array[r] == pivot) {
                l++;
            }
        }
        if (l == r) {
            l++;
            r--;
        }
        if (left < r) {
            practiceQuickSort(array, left, r);
        }
        if (right > l) {
            practiceQuickSort(array, l, right);
        }

    }
}
