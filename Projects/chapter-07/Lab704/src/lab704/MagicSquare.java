package lab704;

/*
 * File Name: MagicSquare.java
 *   Created: Dec 5, 2013
 *    Author: 
 */

public class MagicSquare extends Object
{
  //Do not include any static variables or instance variables...
   
  //Declaring all constructors private prevents other classes 
  //from instantiating a MagicSquare object.
  private MagicSquare()
  {
  }
  
  //return a string representation of the the 2D array mat
  //Use String.format("%4d", mat[r][c]) to reserve four spaces for a value
  //precondition: mat is square and not null
  public static String toString(int[][] mat)
  {
    String s = "";
    
    for(int i = 0; i < mat.length; i++){
      for(int k = 0; k < mat[0].length; k++){
        s += String.format("%4d", mat[i][k]);
      }
      s += "\n";
    }
    
    return s;
  }
  
  //return the sum of the elements of row r in the 2D array mat
  //precondition: mat is square and not null, 0 <= r < mat.length
  public static int rowSum(int[][] mat, int r)
  {
    int sum = 0;
    
    for(int i = 0; i < mat[0].length; i++){
      sum += mat[r][i];
    }
    
    return sum;
  }
  
  //return the sum of the elements of column c in the 2D array mat
  //precondition: mat is square and not null, 0 <= c < mat.length
  public static int columnSum(int[][] mat, int c)
  {
    int sum = 0;
    
    for(int i = 0; i < mat.length; i++){
      sum += mat[i][c];
    }
    
    return sum;
  }
  
  //return the sum of the elements on the top-left to bottom-right diagonal in the 2D array mat
  //precondition: mat is square and not null
  public static int majorDiagonalSum(int[][] mat)
  {
    int sum = 0;
    
    for(int i = 0; i < mat.length; i++){
      sum += mat[i][i];
    }
    
    return sum;
  }
  
  //return the sum of the elements on the top-right to bottom-left diagonal in the 2D array mat
  //precondition: mat is square and not null
  public static int minorDiagonalSum(int[][] mat)
  {
    int sum = 0;
    
    for(int i = 0; i < mat.length; i++){
      sum += mat[mat.length-(i+1)][i];
    }
    
    return sum;
  }
  
  //returns true if the 2D arraymat contains the natural numbers 
  //from 1 to n*n and each row, column, and diagonal has the same sum
  //precondition: mat is square and not null
  public static boolean isMagic(int[][] mat)
  {
    
    int mag = rowSum(mat, 0);
    
    for(int i = 0; i < mat[0].length; i++){
      if(rowSum(mat, i) != mag)
        return false;
    }
    
    for(int i = 0; i < mat.length; i++){
      if(columnSum(mat, i) != mag)
        return false;
    }
    
    if(majorDiagonalSum(mat) != mag)
        return false;
    
    if(minorDiagonalSum(mat) != mag)
        return false;
    
    String nums = "";
    for(int i = 1; i <= mat.length*mat.length; i++){
      nums += "*"+i+"*";
    }
    
    //System.out.println(nums);
    //needs fixing
    for(int i = mat.length*mat.length - 1; i >= 0; i--){
      if(nums.contains("*"+mat[i/mat.length][i%mat.length]+"*")){
        nums = nums.replace("*"+mat[i/mat.length][i%mat.length]+"*", "");
      }
      else
        return false;
      //System.out.println(nums);
    }
    
    return true;
  }
  
  
  //COMPLETE THIS METHOD LAST!
  //precondition: size is a poitive odd integer
  public static int[][] makeMagicSquare(int size)
  {
    if (size % 2 != 1) throw new IllegalArgumentException("Error: Invalid parameter: "+size);
    
    int[][] temp = new int[size][size];
    
    int r = 0;
    int c = (size/2);
    
    for(int i = 1; i <= size*size; i++){
      //System.out.println(r+","+c);
      
      temp[r][c] = i;
      
      c++;
      r--;
      
      if(c >= size)
        c = 0;
      if(r < 0)
        r = size - 1;
      
      if(temp[r][c] != 0){
        c--;
        r+=2;
      }
      
      if(r >= size)
        r -= size;
      if(c < 0)
        c = size - 1;
         
    }
    
    return temp;
  }
  
}
