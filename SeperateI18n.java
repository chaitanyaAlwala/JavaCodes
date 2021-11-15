package com.techdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.util.HashSet;
import java.util.Scanner;

public class SeperateI18n {
	public static HashSet<String> i18Strings;
	void listFilesForFolder(final File folder) {
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()&&fileEntry.getName()!="content-packages" ) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	if(fileEntry.getName().endsWith(".java")||fileEntry.getName().endsWith(".js")||fileEntry.getName().endsWith(".ts")||fileEntry.getName().endsWith(".html")||fileEntry.getName().endsWith(".jsp")) {
	        		
	        		
	        		addi18nStrings(fileEntry);}
	        }
	    }
	   
	   
	}
	public void addi18nStrings(File fileEntry) {
		try {
		      Scanner myReader = new Scanner(fileEntry);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        if(data.contains("translate")) {
		        	//data.contains("i18n")||
		        	SeperateI18n.i18Strings.add(data);
		        }
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	public void writeToFile(HashSet<String> i18Strings) {
		try {
			FileWriter myWriter = new FileWriter("D://fileTest//folder1//hellofrom104AngularAEM.txt");
			for(String s : i18Strings) {
				String i18nValue=s.trim();
				if(i18nValue.startsWith("@I18N") ){
					String value=i18nValue;
							//.substring(12,(i18nValue.length()-1));
					myWriter.write(value.trim()+"\n");
				}else {
				
				myWriter.write(s.trim()	+"\n");
				}
			}  
		      myWriter.close();
		     
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		System.out.println("completed");
	}
	public static void main(String[] args) {
		i18Strings=new HashSet<String>();
		SeperateI18n SeperateI18n=new SeperateI18n();
		final File homefolder = new File("D://Repos//CodeBaseJune//StreamOne//website//tng-next-generation//aem-angular//src");
		SeperateI18n.listFilesForFolder(homefolder);
		SeperateI18n.writeToFile(i18Strings);
		 
	}
	

	
}
