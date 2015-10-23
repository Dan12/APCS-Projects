package survive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LineCounter {
    
    public LineCounter(){
        File directory = new File(new File("").getAbsolutePath().concat("/src/survive"));
        File[] tempFiles = directory.listFiles();
        int numLines = -2;
        for(File f : tempFiles){
            if(f.getName().contains(".java") && !f.getName().contains("LineCounter")){
                Scanner inLine = null;
                try {inLine = new Scanner(f);}catch (FileNotFoundException ex) {System.out.println(ex);}
                while(inLine.hasNext()){
                    inLine.nextLine();
                    numLines++;
                }
            }   
        }
        System.out.println("Total Project Lines: "+numLines);
    }
}
