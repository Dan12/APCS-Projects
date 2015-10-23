package lab706;

import java.util.Arrays;

/*
 * File Name: Lab705.java
 *   Created: Feb 10, 2015
 *    Author: 
 */


public class Lab706 extends Object
{
  public Lab706()
  {
    //This is a dummy constructor; no private data to initialize
  }
  
  public double productAll(double[][] mat)
  {
    double product = 1;
    
    for(int i = 0; i < mat.length; i++){
      for(int k = 0; k < mat[0].length; k++)
        product*=mat[i][k];
    }
    
    return product;
  }
  
  public double[][] transpose(double[][] mat)
  {
    double[][] trans = new double[mat[0].length][mat.length];
    
    for(int i = 0; i < mat[0].length; i++){
      for(int k = 0; k < mat.length; k++)
        trans[i][k] = mat[k][i];
    }
    
    return trans;
  }
  
  public String getLexicographicLast(String[][] mat)
  {
    String ls = "";
    
    for(int i = 0; i < mat.length; i++){
      for(int k = 0; k < mat[0].length; k++){
        if(mat[i][k].compareTo(ls) > 0){
          ls = mat[i][k];
        }
      }
    }
    
    return ls;
  }
  
  public String locationSmallest(double[][] mat)
  {
    int r = 0;
    int c = 0;
    double min = mat[0][0];
    
    for(int i = 0; i < mat.length; i++){
      for(int k = 0; k < mat[0].length; k++){
        if(mat[i][k] < min){
          min = mat[i][k];
          r = i;
          c = k;
        }
      }
    }
    
    return "("+r+", "+c+")";
  }
  
  public void start()
  {
    double[][] a = new double[][]{{3, 2, 5}, {6, 1, -4}, {0.5, 8, -2}, {-4, 1, 5}};
    double[][] b = new double[][]{{3, 1, 4, 1, 5, 9}};
    double[][] c = new double[][]{{11, 4.5}, {2, 8}, {3, 7.75}, {9, 5.25}, {2.5, 2.5}, {6, 1.25}};
    
    System.out.println("a = "+Arrays.deepToString(a));
    System.out.println("b = "+Arrays.deepToString(b));
    System.out.println("c = "+Arrays.deepToString(c));
    System.out.println("");
    
    //===========================================================================================
   
    double k1 = this.productAll(a);
    System.out.println("this.productAll(a) = "+k1);
        
    double k2 = this.productAll(b);
    System.out.println("this.productAll(b) = "+k2);
        
    double k3 = this.productAll(c);
    System.out.println("this.productAll(c) = "+k3);
   
    System.out.println("");
    
    //===========================================================================================
    
    double[][] aT = this.transpose(a);
    System.out.println("this.transpose(a) = "+Arrays.deepToString(aT));
    
    double[][] bT = this.transpose(b);
    System.out.println("this.transpose(b) = "+Arrays.deepToString(bT));
    
    double[][] cT = this.transpose(c);
    System.out.println("this.transpose(c) = "+Arrays.deepToString(cT));
    
    System.out.println("");
        
    //===========================================================================================   
    
    String aLoc = this.locationSmallest(a);
    System.out.println("this.locationSmallest(a) = "+aLoc);
    
    String bLoc = this.locationSmallest(b);
    System.out.println("this.locationSmallest(b) = "+bLoc);
    
    String cLoc = this.locationSmallest(c);
    System.out.println("this.locationSmallest(c) = "+cLoc);
    
    System.out.println("");
    
    //===========================================================================================  
    
    String[][] s1 = {{"ape", "zoo", "cat"}, {"dog", "zap", "fun"}};
    String[][] s2 = {{"reggie", "bill", "mark", "gerald", "gustav"}};
    String[][] s3 = {{"jake", "finn"}, {"princess bubblegum", "tree trunks"}, {"starchy", "lady unicorn"}, {"lsp", "ice king"}};
      
    System.out.println("s1 = "+Arrays.deepToString(s1));
    System.out.println("s2 = "+Arrays.deepToString(s2));
    System.out.println("s3 = "+Arrays.deepToString(s3));
       
    System.out.println("");
    
    String s1Last = this.getLexicographicLast(s1);
    System.out.println("this.getLexicographicLast(s1) = "+s1Last);
    
    String s2Last = this.getLexicographicLast(s2);
    System.out.println("this.getLexicographicLast(s2) = "+s2Last);
   
    String s3Last = this.getLexicographicLast(s3);
    System.out.println("this.getLexicographicLast(s3) = "+s3Last);
    
    System.out.println("");
    
    //===========================================================================================
  }

  public static void main(String[] args)
  {
    Lab706 app = new Lab706();
    app.start();
    
    Tester.test_productAll_Method(app);
    Tester.test_transpose_Method(app);
    Tester.test_locationSmallest_Method(app);
    Tester.test_getLexicographicMax_Method(app);   
  }
  
}
