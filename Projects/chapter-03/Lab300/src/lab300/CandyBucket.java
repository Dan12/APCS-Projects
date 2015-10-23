package lab300;

/*
 * File Name: CandyBucket.java
 *   Created: Oct 21, 2014
 *    Author: 
 */

public class CandyBucket extends Object
{
  private String ownerName;
  private int piecesOfCandy = 0;
  private String candyList="";
  
  public CandyBucket(String n){
    ownerName = n;
  }
  
  public String toString(){
    String n = ownerName;
    if(piecesOfCandy == 0)
      n = n+"'s candy bucket is empty.";
    else
      n = n+"'s candy bucket contains these "+piecesOfCandy+" items: "+candyList;
    return n;
  }
  
  public String add(String candy){
    if(candy == null){
      return "Nothing was added to "+ownerName+"'s candy bucket.\n";
    }
    else{
      candyList += candy+" ";
      piecesOfCandy++;
      return candy+" added to "+ownerName+"'s candy bucket.\n";
    }
  }
  
  public boolean contains(String c){
    return candyList.contains(c);
  }
  
  public int remove(String r){
    int numRem = candyList.length();
    if(r == null || r.length() == 0)
      return 0;
    else{
      candyList = candyList.replace(r, "");
      numRem -= candyList.length();
      numRem = numRem/r.length();
      piecesOfCandy-=numRem;
      return numRem;
    }
  }
  
  public CandyBucket emptyBucket(String n){
    CandyBucket temp = new CandyBucket(n);
    
    temp.candyList = this.candyList;
    temp.piecesOfCandy = this.piecesOfCandy;
    
    this.piecesOfCandy = 0;
    this.candyList = "";
    
    return temp;
  }
  
  public void transferTo(CandyBucket other, String c){
    if(other != this){
      int numToAdd = this.remove(c);
      for(int i = 0; i < numToAdd; i++){
        other.add(c);
      }
    }
  }
}
