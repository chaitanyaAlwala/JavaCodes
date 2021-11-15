package com.techdata;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class RemoveExtraSpaces {
			public void readExcelIntoMap() throws InvalidFormatException, IOException {
				TreeMap<String, String> dictionaryMap=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				
				TreeSet<String> newSetofStrings=new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
				TreeSet<String> existingStrings=new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
				ArrayList<String> newUniqueList= new ArrayList<String>();
				//ArrayList<String> uniqueEnSetValues= new ArrayList<String>();
				File file = new File("C:\\Users\\chaitanyaa\\Desktop\\finalUploads.xlsx");
				// file 2
				//File file2 = new File("C:\\Users\\chaitanyaa\\Desktop\\AllNewStrings.xlsx");
			    XSSFWorkbook workbook = new XSSFWorkbook(file);
		        XSSFSheet sheet = workbook.getSheetAt(0);
		        Iterator<Row> rowIterator = sheet.iterator();
		        System.out.println("New One");
		        while (rowIterator.hasNext()) 
		        {
		            Row row = rowIterator.next();
		            Iterator<Cell> cellIterator = row.cellIterator();
		            	Cell cell = cellIterator.next();
		            	if (cell.getCellType().equals(CellType.STRING)) {
		            		newSetofStrings.add(cell.getStringCellValue().trim());
		            		existingStrings.add(cellIterator.next().getStringCellValue().trim());
		            		//dictionaryMap.put(cell.getStringCellValue().trim(),cellIterator.next().getStringCellValue().trim());
		            	}else if(cell.getCellType().equals(CellType.NUMERIC)) {
		            		newSetofStrings.add(Double.toString(cell.getNumericCellValue()));
		            		existingStrings.add(Double.toString(cellIterator.next().getNumericCellValue()).trim());
		            		
		            		//dictionaryMap.put(Double.toString(cell.getNumericCellValue()).trim(),Double.toString(cellIterator.next().getNumericCellValue()).trim());
		            	}else {
		            		System.out.println("invalid Type");
		            	}

		        }
		        //second file
		        System.out.println(newUniqueList.size()+" =newUniqueList");
		        for(String uniqueEnValue : newSetofStrings) {
		        	System.out.println(uniqueEnValue);
		        }
		        System.out.println("----------------------------------------------------------------------------------------");
		        for(String existingString : existingStrings) {
		        	System.out.println(existingString);
		        }
		        
			}
			public static void main(String[] args) {
				FindNewStrings rdj= new FindNewStrings();
				try {
					rdj.readExcelIntoMap();
				} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
}
