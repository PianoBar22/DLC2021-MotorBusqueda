/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.soporte;

import java.util.ArrayList;

/**
 *
 * @author CC31899077
 */
public class QuickSort<T extends Comparable> {
    public void Sort(ArrayList<T> arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            Sort(arr, begin, partitionIndex-1);
            Sort(arr, partitionIndex+1, end);
        }
    }
    
    private int partition(ArrayList<T> arr, int begin, int end) {
        T pivot = arr.get(end);
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j).compareTo(pivot) > 0) {
                i++;

                T swapTemp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, swapTemp);
            }
        }

        T swapTemp = arr.get(i+1);
        arr.set(i+1, arr.get(end));
        arr.set(end, swapTemp);

        return i+1;
    }
}
