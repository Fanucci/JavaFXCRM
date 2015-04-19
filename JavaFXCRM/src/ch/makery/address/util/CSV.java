package ch.makery.address.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;
 
public class CSV {
     static String CodePath = "C:\\Users\\ִלטענטי\\git\\JavaFXCRM\\JavaFXCRM\\src\\Kody.csv";
     FileReader FR;
     static CSVReader reader;
	public static void changeCodes(File file2){
    	CodePath = file2.getPath();
	}
	public CSV() throws FileNotFoundException{
		FR= new FileReader(CodePath);
		
	}
    	public String checkNumber(String num) throws NumberFormatException, IOException{
    		reader = new CSVReader(FR,';');
            
            String dig3 = num.substring(1,4);
            int dig7 = Integer.parseInt(num.substring(4,11));
            String [] nextLine;
            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
          //      for(@SuppressWarnings("unused") String token : nextLine)
          //      {
                	
                	 if (dig3.equals(nextLine[0])){
                		 
                		if(dig7>Integer.parseInt(nextLine[1])) {
                	
                		if( dig7<Integer.parseInt(nextLine[2])){
                			return nextLine[5];
                			
                		}}
                	 }
                }
         //   }


		return "no";
    }}
