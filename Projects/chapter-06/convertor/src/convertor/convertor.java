package convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class convertor {
	public static void main(String[] args) throws IOException{
		BufferedReader text = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat("/src/convertor/in.rad")));
		BufferedReader textLine = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat("/src/convertor/in.rad")));
		File outFile = new File("src/convertor/out.txt");
		outFile.createNewFile();
		FileWriter print = new FileWriter(outFile);
		int numLines = 0;
		String aLine;
		while ((aLine = textLine.readLine()) !=null){
			numLines++;
		}
		String input[] = new String[numLines];
		String output = "";
		for(int i = 0; i<numLines; i++){
			input[i] = text.readLine();
		}
		for(int i = 0; i<numLines; i++){
			if(input[i].length()>2){
				if(input[i].substring(0, 3).equals("<p>")){
					output = output+"p\n";
					String color = "";
					while(true){
						i++;
						if(input[i].length()>3){
							if(input[i].substring(0, 4).equals("</p>")){
								output = output+color+"end\n";
								break;
							}
						}
						if(input[i].length()>1){
							//System.out.println(input[i].substring(0, 1));
							if(input[i].substring(0, 1).equals("p")){
								int index = input[i].indexOf(")");
								output = output+input[i].substring(2, index)+"\n";
							}
							if(input[i].substring(0, 1).equals("c")){
								int index = input[i].indexOf(")");
								color = input[i].substring(2, index)+"\n";
							}
							if(input[i].substring(0, 1).equals("g")){
								int index = input[i].indexOf(")");
								color = "220,220,255"+"\n";
							}
						}
					}
				}
				if(input[i].substring(0, 1).equals("w")){
					int index = input[i].indexOf(")");
					String cut = input[i].substring(2, index);
					String extract[] = cut.split(",");
					output = output+"w\n"+extract[0]+","+extract[1]+","+extract[2]+",1,.4\nend\n";
				}
			}
		}
		print.write(output);
		print.flush();
		print.close();
	}
}
