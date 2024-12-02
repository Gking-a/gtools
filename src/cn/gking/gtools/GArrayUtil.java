package cn.gking.gtools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GArrayUtil {
    public static <T> List<T> insert(T[] s,int position,T[] a){
        if(position>s.length)throw new IllegalStateException();
        int p=0;
        ArrayList<T> r=new ArrayList<T>(s.length+a.length);
        for (int i = 0; i < s.length; i++) {
            if(i==position){
                for (T t : a) {
                    r.add(p, t);
                    p++;
                }
            }
            r.add(p,s[i]);
            p++;
        }
        if(position==s.length){
            for (T t : a) {
                r.add(p, t);
                p++;
            }
        }
        return r;
    }
    public static <T>boolean contains(Object[] s,Object o){
        for (Object e : s) {
            if (e.equals(o)) return true;
        }
        return false;
    }
    public static boolean contains(int[] s,int o){
        for (int e : s) {
            if (e==o) return true;
        }
        return false;
    }
 }
