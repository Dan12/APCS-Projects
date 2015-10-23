package alienlanguage;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class AlienLanguage{
    
    String filePath = new File("").getAbsolutePath();
    String readFilePath = filePath.concat("/src/alienlanguage/text.in");
    String writeFilePath= filePath.concat("/src/alienlanguage/text.out");
    
    String[] dataInLines;
    
    String[] dataOutLines;
    
    public AlienLanguage(){
        System.out.println("New Main Object");
    }
    
    public static void main(String[] args){
        AlienLanguage m = new AlienLanguage();
        m.Go();
    }
    
    public void Go(){
        System.out.println("Starting");
        readData();
        solve();
        writeData(dataOutLines);
    }
    
    //solve problem using algorithm and store answer to each test case as a string in string array dataOutLines
    public void solve(){
        System.out.println("Solving");
        
        String[] firstLine = dataInLines[0].split(" ");
        
        int testCases = Integer.parseInt(firstLine[2]);
        int dictNum = Integer.parseInt(firstLine[1]);
        int tokenNum = Integer.parseInt(firstLine[0]);
        
        dataOutLines = new String[testCases];
        
        for(int i = 0; i < dataOutLines.length; i++){
            //algorithm here
            int answer;
            
            answer = i*i;
            dataOutLines[i] = ""+answer;
        }
    }
    
    //method to read data into string array dataInLines
    public void readData(){
        try {
            ReadFile file = new ReadFile(readFilePath);
            dataInLines = file.OpenFile();
            System.out.println("Data Read");
        }
        catch (IOException e){System.out.println(e.getMessage());}
    }
    
    //method to write data in string array data to out file
    public void writeData(String[] data){
        String dataOut = "";
        System.out.println("Printing Answers");
        for(int i = 0; i < data.length; i++){
            dataOut+="Case #"+i+": "+data[i];
            if(i < data.length-1)
                dataOut+="\n";
            System.out.println(data[i]);
        }
        try{
            WriteFile file = new WriteFile(writeFilePath, true);
            file.writeToFile(dataOut);
        }
        catch (IOException e){System.out.println(e.getMessage());}
        System.out.println("Done Printing Answers");
    }
    
    //object that can return string array of all data in file
    public class ReadFile {
        
        private String path;
        
        public ReadFile(String file_path){
                path = file_path;
        }
        
        public String[] OpenFile() throws IOException {
            FileReader fr = new FileReader(path);
            BufferedReader textReader = new BufferedReader(fr);
            
            int numberOfLines = readLines();
            String[] textData = new String[numberOfLines];
            
            int i;
            
            for (i=0; i<numberOfLines; i++){
                textData[i] = textReader.readLine();
            }
            
            textReader.close();
            return textData;
        }
            
        int readLines() throws IOException {
            FileReader file_to_read = new FileReader(path);
            BufferedReader bf = new BufferedReader(file_to_read);
            
            String aLine;
            int numberOfLines = 0;
            
            while (( aLine = bf.readLine()) !=null) {
                numberOfLines++;
            }
            bf.close();
            
            return numberOfLines;
        }
    }
    
    //Write answer to file
    public class WriteFile {
        
        boolean append_to_file;
        private String path;
    
        public WriteFile(){
            append_to_file = false;
        }
    
        public WriteFile(String file_path) {
            path = file_path;
        }
        
        public WriteFile( String file_path, boolean append_value ) {
            path = file_path;
        }
        
        public void writeToFile (String textLine) throws IOException {
            FileWriter write =  new FileWriter(path, append_to_file);
            PrintWriter print_line = new PrintWriter(write);
            
            print_line.printf("%s", textLine);
            
            print_line.close();
        }
    }
    
}