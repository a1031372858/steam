package com.xu.bean.model.impl;

import com.xu.bean.model.SortModel;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class QuickSortModel implements SortModel {
    @Override
    public void sort(List<Integer> list) {

    }

    private int[] quickSort(int[] array, int left, int right) {
        int temp = array[left];
        int i = left, j = right;
        while(i<j){

            while (i < j && temp < array[j]) {
                j--;
            }
            if(i<j){
                array[i]=array[j];
                array[j]=temp;
                i++;
            }
            while(i<j&&temp>array[i]){
                i++;
            }
            if(i<j){
                array[j] =array[i];
                array[i] = temp;
                j--;
            }
        }
        if(i-1>left){
            quickSort(array,left,i-1);
        }
        if(j+1<right){
            quickSort(array,j+1,right);
        }
        return array;
    }
}
