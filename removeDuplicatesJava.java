package com.techdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class removeDuplicatesJava {

	
	public void readExcelIntoMap() throws InvalidFormatException, IOException {
		TreeMap<String, String> dictionaryMap=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		TreeMap<String, String> AfterMappingdictionary=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		ArrayList<String> uniqueEnSet= new ArrayList<String>();
		ArrayList<String> uniqueEnSetValues= new ArrayList<String>();
		File file = new File("C:\\Users\\chaitanyaa\\Desktop\\ToRemDup.xlsx");
	    XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) 
        {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            	Cell cell = cellIterator.next();
            	if (cell.getCellType().equals(CellType.STRING)) {
            		dictionaryMap.put(cell.getStringCellValue(),cellIterator.next().getStringCellValue());
            	}else if(cell.getCellType().equals(CellType.NUMERIC)) {
            		dictionaryMap.put(Double.toString(cell.getNumericCellValue()),Double.toString(cellIterator.next().getNumericCellValue()));
            	}else {
            		System.out.println("invalid Type");
            	}
            	if(cellIterator.hasNext()) {
            		cell=cellIterator.next();
            		if (cell.getCellType()==CellType.STRING) {
            			uniqueEnSet.add(cell.getStringCellValue());
                	}else if(cell.getCellType()==CellType.NUMERIC) {
                		uniqueEnSet.add(Double.toString(cell.getNumericCellValue()));
                	}else {
                		System.out.println("invalid Type");
                	}
            	}
        }
        System.out.println("uniqueEnSet size : "+uniqueEnSet.size());
        System.out.println(dictionaryMap.size()+" =dictionaryMapsize");
        for(String uniqueEnValue : uniqueEnSet) {
        	AfterMappingdictionary.put(uniqueEnValue, dictionaryMap.get(uniqueEnValue));
        	uniqueEnSetValues.add(dictionaryMap.get(uniqueEnValue));
        }
        System.out.println("final size : "+AfterMappingdictionary.size());
        System.out.println("uniqueEnSetValues size : "+uniqueEnSetValues.size());

        for(String uniqueEnValue : uniqueEnSetValues) {
        	System.out.println(uniqueEnValue);
        }
	}
	public static void main(String[] args) {
		removeDuplicatesJava rdj= new removeDuplicatesJava();
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
