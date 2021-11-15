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



public class FindNewStrings {
	
		
		public void readExcelIntoMap() throws InvalidFormatException, IOException {
			TreeSet<String> newSetofStrings=new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
			TreeSet<String> existingStrings=new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
			ArrayList<String> newUniqueList= new ArrayList<String>();
			//ArrayList<String> uniqueEnSetValues= new ArrayList<String>();
			File file = new File("C:\\Users\\chaitanyaa\\Desktop\\AllFromES_es.xlsx");
			// file 2
			File file2 = new File("C:\\Users\\chaitanyaa\\Desktop\\AllNewStrings.xlsx");
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
	            		existingStrings.add(cell.getStringCellValue());
	            	}else if(cell.getCellType().equals(CellType.NUMERIC)) {
	            		existingStrings.add(Double.toString(cell.getNumericCellValue()));
	            	}else {
	            		System.out.println("invalid Type");
	            	}

	        }
	        //second file
	        XSSFWorkbook workbook2 = new XSSFWorkbook(file2);
	        XSSFSheet sheet2 = workbook2.getSheetAt(0);
	        Iterator<Row> rowIterator2 = sheet2.iterator();
	        System.out.println("New two");
	        while (rowIterator2.hasNext()) 
	        {
	            Row row2 = rowIterator2.next();
	            Iterator<Cell> cellIterator = row2.cellIterator();
	            	Cell cell2 = cellIterator.next();
	            	if (cell2.getCellType().equals(CellType.STRING)) {
	            		if((existingStrings.add(cell2.getStringCellValue()))) {
	            			newUniqueList.add(cell2.getStringCellValue());
	            		}
	            	}
	            	

	        }
	        
	        //second file
	        System.out.println(newUniqueList.size()+" =newUniqueList");
	        for(String uniqueEnValue : newUniqueList) {
	        	System.out.println(uniqueEnValue);
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

