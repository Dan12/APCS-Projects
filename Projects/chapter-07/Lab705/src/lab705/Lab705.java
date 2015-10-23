package lab705;

import java.util.Arrays;

/*
 * File Name: Lab705.java
 *   Created: Feb 10, 2015
 *    Author: You
 */


public class Lab705 extends Object
{
  
  public Lab705()
  {
    //This is a dummy constructor; no private data to initialize
  }
  
  public boolean contains(double[] nums, double d)
  {
    for(int i = 0; i < nums.length; i++){
      if(nums[i] == d)
        return true;
    }
    return false;
  }
  
  public int lastIndexOf(double[] nums, double d)
  {
    int ind = -1;
    for(int i = 0; i < nums.length; i++){
      if(nums[i] == d)
        ind = i;
    }
    return ind;
  }
  
  public String[] reverse(String[] words)
  {
    String[] temp = new String[words.length];
    int k = words.length-1;
    for(int i = 0; i < words.length; i++){
      temp[i] = words[k];
      k--;
    }
    return temp;
  }
  
  public double[] duplicateTarget(double[] nums, double tar)
  {
    int size = 0;
    for(int i =0; i < nums.length; i++){
      if(nums[i] == tar)
        size++;
      size++;
    }
    double[] temp = new double[size];
    int k = 0;
    for(int i = 0; i < nums.length; i++){
      temp[k] = nums[i];
      if(nums[i] == tar){
        k++;
        temp[k] = nums[i];
      }
      k++;
    }
    return temp;
  }
  
  public int[] trim(int[] nums)
  {
    int[] temp;
    if(nums.length > 0){
      boolean nonZero = false;
      int leadingIndex = 0;
      for(int i = 0; i < nums.length; i++){
        if(nums[i] != 0){
          leadingIndex = i;
          nonZero = true;
          break;
        }
      }
      if(nonZero){
        int trailingIndex = 0;
        for(int i = nums.length-1; i >= 0; i--){
          if(nums[i] != 0){
            trailingIndex = i;
            break;
          }
        }
        temp = new int[(trailingIndex-leadingIndex)+1];
        int k = 0;
        for(int i = leadingIndex; i <= trailingIndex; i++){
          temp[k] = nums[i];
          k++;
        }
      }
      else{
        temp = new int[0];
      }
    }
    else
      temp = new int[0];
    return temp;
  }
    
  public void start()
  {
    double[] a = new double[]{-3.14, 3, 11, 44, 44, 44, 4.5, 3.0, -2, 3.0, -1.5, 44, 44};
    //a = new double[]{};
    
    System.out.println("a = "+Arrays.toString(a));
    System.out.println("");
    
    //Test contains =============================================================================
    
    boolean b1 = this.contains(a, 17);
    System.out.println("this.contains(a, 17) = "+b1);
    
    boolean b2 = this.contains(a, 1.5);
    System.out.println("this.contains(a, 1.5) = "+b2);
    
    //Test lastIndexOf ==========================================================================
    
    int k1 = this.lastIndexOf(a, -3.14);
    System.out.println("this.lastIndexOf(a, -3.14) = "+k1);
    
    int k2 = this.lastIndexOf(a, 3);
    System.out.println("this.lastIndexOf(a, 3) = "+k2);
    
    int k3 = this.lastIndexOf(a, 99);
    System.out.println("this.lastIndexOf(a, 99) = "+k3);
    
    int k4 = this.lastIndexOf(a, 44);
    System.out.println("this.lastIndexOf(a, 44) = "+k4);
    
    //Test reverse ==============================================================================
    
    String[] s1 = {"ape", "boy", "cat", "dog", "elf", "fun"};
    String[] s2 = this.reverse(s1);
    System.out.println("this.reverse("+Arrays.toString(s1)+") = "+Arrays.toString(s2));
    
    String[] s3 = {"GBS HS"};
    String[] s4 = this.reverse(s3);
    System.out.println("this.reverse("+Arrays.toString(s3)+") = "+Arrays.toString(s4));
    
    String[] s5 = {"Glenbrook", "South", "High School"};
    String[] s6 = this.reverse(s5);
    System.out.println("this.reverse("+Arrays.toString(s5)+") = "+Arrays.toString(s6));
    
    //Test duplicate Target =====================================================================   
        
    double[] a1 = this.duplicateTarget(a, 3);
    System.out.println("this.duplicateTarget(a, 3) = "+Arrays.toString(a1));
    
    double[] a2 = this.duplicateTarget(a, 44);
    System.out.println("this.duplicateTarget(a, 44) = "+Arrays.toString(a2));
    
    double[] a3 = this.duplicateTarget(a, -3.14);
    System.out.println("this.duplicateTarget(a, -3.14) = "+Arrays.toString(a3));
    
    double[] a4 = this.duplicateTarget(a, 1999);
    System.out.println("this.duplicateTarget(a, 1999) = "+Arrays.toString(a4));
   
    //===========================================================================================  
    
    int[] x1 = {0, 0, 0, 0, 3, 0, 0};
    int[] y1 = this.trim(x1);
    System.out.println("this.trim("+Arrays.toString(x1)+") = "+Arrays.toString(y1));
    
    int[] x2 = {3, 7, 8, 5, 0, 0};
    int[] y2 = this.trim(x2);
    System.out.println("this.trim("+Arrays.toString(x2)+") = "+Arrays.toString(y2));
        
    int[] x3 = {0, 2, 4, 6, 8, 10};
    int[] y3 = this.trim(x3);
    System.out.println("this.trim("+Arrays.toString(x3)+") = "+Arrays.toString(y3));
    
    int[] x4 = {1, 2, 3, 3, 5, 5, 7, 7};
    int[] y4 = this.trim(x4);
    System.out.println("this.trim("+Arrays.toString(x4)+") = "+Arrays.toString(y4));    
    
    int[] x5 = {};
    int[] y5 = this.trim(x5);
    System.out.println("this.trim("+Arrays.toString(x5)+") = "+Arrays.toString(y5));
        
    int[] x6 = {0, 0, 0, 0, 0, 0, 0};
    int[] y6 = this.trim(x6);
    System.out.println("this.trim("+Arrays.toString(x6)+") = "+Arrays.toString(y6));
    
    int[] x7 = {0, 0, 0, 4, 3, 0, 2, 7, 0, 0};
    int[] y7 = this.trim(x7);
    System.out.println("this.trim("+Arrays.toString(x7)+") = "+Arrays.toString(y7));

    //===========================================================================================
  }

  public static void main(String[] args)
  {
    Lab705 app = new Lab705();
    app.start();
    
    Tester.test_contains_Method(app);
    Tester.test_LastIndexOf_Method(app);
    Tester.test_reverse_Method(app);
    Tester.test_duplicateTarget_Method(app);
    Tester.test_trim_Method(app);

  }
  
}
