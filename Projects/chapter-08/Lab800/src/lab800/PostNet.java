package lab800;

/*
 * File Name: PostNet.java
 *   Created: Jan 20, 2014
 *    Author: 
 */

public class PostNet extends Object
{
    
   static String[] postnetAlpha = {"||...", "...||", "..|.|","..||.",".|..|",".|.|.",".||..","|...|","|..|.","|.|.."};
    
  //Prevent other classes from ever instantiating a PostNet object...
  private PostNet()
  {
  }
 
  
  public static String encode(String s){
    if(s == null)
        return "null zip code";
    if(s.length() != 5)
        return "invalid length";
    int sum = 0;
    String out = "|";
    for(int i = 0; i < s.length(); i++){
        String num = s.substring(i, i+1);
        //System.out.println(num);
        int intToEncode = 0;
        if(num.equals("0") || num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5") || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9"))
            intToEncode = Integer.parseInt(num);
        else
            return "invalid character";
        out+=postnetAlpha[intToEncode];
        sum+=intToEncode;
    }
    int checkSum = 0;
    if(sum%10 != 0)
        checkSum = 10-(sum%10);
      //System.out.println(sum+","+checkSum);
    out+=postnetAlpha[checkSum]+"|";
    return out;
  }
  
  
  
  public static String decode(String s)
  {
    if(s == null)
        return "null barcode";
    if(s.length() != 32)
        return "invalid length";
    if(!(s.startsWith("|") && s.endsWith("|")))
        return "invalid frame";
    String actual = s.substring(1,s.length()-1);
    //System.out.println(actual);
    String out = "";
    int sum = 0;
    for(int i = 0; i < 5; i++){
        String toCheck = actual.substring(i*5, (i+1)*5);
        //System.out.println(toCheck);
        String add = decodeSingle(toCheck);
        if(add.equals(""))
            return "invalid barcode";
        out+=add;
        sum+=Integer.parseInt(add);
    }
    String checkSum = decodeSingle(actual.substring(25,30));
    int checkS = 0;
    if(sum%10 != 0)
        checkS = 10-(sum%10);
    if(Integer.parseInt(checkSum) != checkS)
        return "invalid check digit";
    return out;
  }
  
  private static String decodeSingle(String check){
    String out = "";
    for(int k = 0; k < postnetAlpha.length; k++){
      if(check.equals(postnetAlpha[k]))
        out = k+"";
    }
    return out;
  }
  
}
