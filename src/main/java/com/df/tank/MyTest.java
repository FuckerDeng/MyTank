package com.df.tank;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MyTest {
    public static void main(String[] args) {
        File f = new File("F:\\study\\Java\\RabbitMq");
        listFile(f,0);
    }

    private static void listFile(File f, int i) {
        for(int j = 0;j<i;j++){
            System.out.print("  ");
        }
        if(i>0){
            System.out.print(i);
            System.out.println("-"+f.getName());
        }else {
            System.out.println(f.getName());
        }
        if(f.isDirectory()){
            File[] files = f.listFiles();
            List<File> files1 = Arrays.asList(files);
            files1.sort((o1,o2)->{
                return o1.isDirectory()?1:-1;
            });
            files = files1.toArray(new File[]{});
            for (int i1 = 0; i1 < files.length; i1++) {
                listFile(files[i1],i+1);;
            }
        }

    }
}
