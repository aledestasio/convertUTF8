/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.destasio.convertutf8;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream ;
import java.io.InputStreamReader ;
import java.io.BufferedReader ;
import java.io.BufferedWriter ;
import java.io.OutputStreamWriter ;
import java.io.FileOutputStream ;

import java.nio.charset.Charset;
/**
 *
 * @author adestasio
 */
public class Convert {
    
    static final String PATH = "/home/adestasio/fse-repository/gg1/src/main" ;
    static int n_files = 0 ;
    static int n_dir = 0 ;
    
    public static void main(String[] args) throws IOException  {
        File startDir = new File(PATH) ;
        readDirectory(startDir) ;
        System.out.println("n_files:"+n_files);
        System.out.println("n_dir:"+n_dir);
        n_files+=n_dir ;
        System.out.println("tot:"+n_files);
    }
    
    private static void convertFile(File file) throws IOException {
        n_files++ ;
        System.out.print(file.getCanonicalPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("ISO-8859-1")));
        StringBuilder content = new StringBuilder() ;
        while (true) {
            String line = reader.readLine();
            if (line == null) break ;
            content.append(line) ;
            content.append(System.lineSeparator()) ;
        }
        reader.close() ;
        BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(file, false), Charset.forName("UTF-8")));
        writer.write(content.toString());
        writer.flush();
        writer.close();
        System.out.println(" - converted");
    }
    
    private static void readDirectory(File dir) throws IOException {
        File[] files = dir.listFiles() ;
        for (int i = 0 ; i < files.length ; i++) {
            File file = files[i] ;
            if (file.isFile()) {
                convertFile(file) ;
            }
            else if (file.isDirectory()) {
                System.out.println("**** CD "+file.getName());
                readDirectory(file) ;
                n_dir++ ;
                System.out.println("**** CD ..");
            }
        }
    }
}

