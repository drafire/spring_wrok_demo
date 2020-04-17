package com.teligen.demo.utils;

import java.util.Comparator;

public class FileNameComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        int range1 = Integer.parseInt(o1.substring(o1.lastIndexOf("_") + 1));
        int range2 = Integer.parseInt(o2.substring(o2.lastIndexOf("_") + 1));
        if(range1 - range2 == 0){
            return 0;
        }else if(range1 - range2 > 0){
            return 1;
        }else{
            return -1;
        }
    }
}
