/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BplusTree;

import java.util.Scanner;
import java.io.*;
/**
 *
 * @author Noah
 */
public class TreeImplementation {

    private int sen;
    
    BplusTree BpTree;
    
    public static void main(String[] args) throws FileNotFoundException, FileNotFoundException 
    {
        int sen = 0;
        int element;
        String file;
        String line;
        BplusTree BpTree = new BplusTree();
        System.out.println("BpTreeConstructed");
        System.out.println(BpTree.toString()); 
        

        file = "TestCase.txt";
        line = null;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) 
                {
                 //System.out.println(line);
                 element = Integer.parseInt(line);
                 BpTree.elementInsert(element);   
                }   
             System.out.println(BpTree.toString());
             bufferedReader.close();         
            } 
        
        catch(FileNotFoundException ex) 
            {
             System.out.println("Can't open file '" + file + "'");                
            }
        
        catch(IOException ex) 
            {
             System.out.println("Error in reading the file '" + file + "'");       
            }
        
        /*while(sen != 2)
             {
              Scanner s = new Scanner(System.in);
              System.out.println("Choose 0 / 1");
              sen = s.nextInt();
              System.out.println("Insert/ Remove element: ");
              if (sen == 0)
                 {  
                  element = s.nextInt();
                  BpTree.elementInsert(element);
                  System.out.println(BpTree.toString()); 
                 }
              else if (sen == 1)
                 {  
                  BpTree.Remove();
                  System.out.println(BpTree.toString()); 
                 } 
             }*/
    }
    
}
