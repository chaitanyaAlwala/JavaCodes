package com.techdata.core;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import javax.jcr.Session;
import org.w3c.dom.Node;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element; 
import java.util.ArrayList;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;    
import javax.servlet.ServletOutputStream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.util.Date;
import java.util.Locale;

import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Reference;
//Sling Imports
import org.apache.sling.api.resource.ResourceResolverFactory ;

 
@SlingServlet(paths = "/bin/tng/updamfile", methods = HttpConstants.METHOD_POST)
public class ExcelToXliff extends SlingAllMethodsServlet {
 private static final Logger logger = LoggerFactory.getLogger(ExcelToXliff.class); 
      
//Inject a Sling ResourceResolverFactory
private XSSFWorkbook workBook;
      
 @Override
 protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
	 logger.info("inside servlet");
             
     try
     {
     final boolean isMultipart = org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent(request);
      
       if (isMultipart) {
         final java.util.Map<String, org.apache.sling.api.request.RequestParameter[]> params = request.getRequestParameterMap();
         for (final java.util.Map.Entry<String, org.apache.sling.api.request.RequestParameter[]> pairs : params.entrySet()) {
            org.apache.sling.api.request.RequestParameter[] pArr = pairs.getValue();
            org.apache.sling.api.request.RequestParameter param = pArr[0];
            InputStream stream = param.getInputStream();
           String targetLocale=request.getParameter("locale");
           logger.info("locale value ",targetLocale);
               //Save the uploaded file
           if (param.isFormField()) {
        	   logger.info("Form field  with value {} detected", targetLocale);
           }else{
        	   logger.info("File field {} with file name {} detected", targetLocale, param.getFileName());
           }
           logger.info("Form field locale  with value {} detected", targetLocale);	
         response= convertExcelToXliff(stream,targetLocale,request,response); 
         break;
         }
       }
     }
     catch (Exception e) {
         e.printStackTrace();
     }
  
 }

@SuppressWarnings("deprecation")
public SlingHttpServletResponse convertExcelToXliff(InputStream is, String languageSelector,SlingHttpServletRequest request, SlingHttpServletResponse response) {
	 logger.info("inside method");
	//POIFSFileSystem fileSystem = null;
	InputStream inputStream = null;
	String libraryFolderPath = "libs";
	String i18nPath = "cq/i18n/";
	try {
		// Initializing the XML document before writing data into the file
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();

		Element rootElement = document.createElement("xliff");
		
		rootElement.setAttribute("version", "1.1");
		document.appendChild(rootElement);

		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		dateFormat.parse("Tue Jul 13 00:00:00 CEST 2011");

		Element fileElement = document.createElement("file");
		fileElement.setAttribute("date", dateFormat.format(new Date()));
		fileElement.setAttribute("tool-id", "com.day.cq.cq-i18n");
		fileElement.setAttribute("datatype", "x-javaresourcebundle");
		fileElement.setAttribute("target-language", languageSelector);
		fileElement.setAttribute("source-language", "en");
		fileElement.setAttribute("original", "/"+libraryFolderPath+"/"+ i18nPath + languageSelector);
		rootElement.appendChild(fileElement);

		Element headElement = document.createElement("header");
		fileElement.appendChild(headElement);

		Element toolElement = document.createElement("tool");
		toolElement.setAttribute("tool-id", "com.day.cq.cq-i18n");
		toolElement.setAttribute("tool-company", "Adobe Systems Incorporated");
		toolElement.setAttribute("tool-version", "6.5.1");
		toolElement.setAttribute("tool-name", "Adobe Granite I18N Module");
		headElement.appendChild(toolElement);
		Element bodyElement = document.createElement("body");
		fileElement.appendChild(bodyElement);
		logger.info(" before read excel");
		workBook = new XSSFWorkbook(is);
		XSSFSheet sheet = workBook.getSheetAt(0);
		Iterator<?> rows = sheet.rowIterator();
		logger.info(" before while");
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		while (rows.hasNext()) {
			XSSFRow row = (XSSFRow) rows.next();
			// get a row, iterate through cells.
			Iterator<?> cells = row.cellIterator();
			ArrayList<String> rowData = new ArrayList<String>();
			while (cells.hasNext()) {
				XSSFCell cell = (XSSFCell) cells.next();
				//rowData.add(cell.getRichStringCellValue().getString());
				if(cell.getCellTypeEnum() == CellType.STRING){
					rowData.add(cell.getRichStringCellValue().getString());
				}else if (cell.getCellTypeEnum() == CellType.NUMERIC){
					rowData.add(cell.getNumericCellValue() + "");
				}else{
					logger.info("Type not supported.");
				}
				} // end while
			data.add(rowData);
		} // end while
		logger.info("copied from excel");
		// Read data from the ArrayList and storing them in the XLIFF File
		int numOfProduct = data.size();
		if(data!=null){
			logger.info("data from excel {} :",numOfProduct);
		}
		for (int i = 1; i < numOfProduct; i++) {
			Element transElement = document.createElement("trans-unit");
			String str = Integer.toString(i - 1);
			transElement.setAttribute("id", str);
			bodyElement.appendChild(transElement);
			int index = 0;
			for (String s : data.get(i)) {
				Element headerElement = null;
				String headerString = data.get(0).get(index);
				if (data.get(0).get(index).equals("Source String")) {
					headerString = "source";
					headerElement = document.createElement(headerString);
					headerElement.setAttribute("xml:lang", "en");
				}

				if (data.get(0).get(index).equals("Translated String")) {
					headerString = "target";
					headerElement = document.createElement(headerString);
					headerElement.setAttribute("xml:lang", languageSelector);
				}
				Node cdata = document.createCDATASection(s);

				// String xmldata = "<![CDATA[ " + s + " ]]>";
				if(null==headerElement) {
					return response;
				}
				transElement.appendChild(headerElement);
				headerElement.appendChild(cdata);
				index++;
				}
				
			}
		logger.info("before xliff");
		TransformerFactory tFactory = TransformerFactory.newInstance();
		tFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		tFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
		Transformer transformer = tFactory.newTransformer();
		// Add no indentation to output
		transformer.setOutputProperty(OutputKeys.INDENT, "no");
		//transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		//transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File("updated_translation_to_upload.xliff"));
		transformer.transform(source, result);
		logger.info("last xliff");
		inputStream = new FileInputStream("updated_translation_to_upload.xliff");
		String XliffFileName="en-trasnlation";
		String filePath=XliffFileName+".xliff";
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-disposition","attachment; filename="+filePath);
	    ServletOutputStream os=response.getOutputStream();
         byte[] buffer = new byte[4096];
         int length;
         while ((length = inputStream.read(buffer)) > 0){
        	 os.write(buffer, 0, length);
         }
         
         inputStream.close();
         os.flush();
         
		logger.info(" executed at last");
		return response;
}catch (IOException e) {
	logger.info(" IOException" + e.getMessage());
	return response;
} catch (ParserConfigurationException e) {
	logger.info("ParserConfigurationException " + e.getMessage());
	return response;
} catch (TransformerConfigurationException e) {
	logger.info("TransformerConfigurationException " + e.getMessage());
	return response;
} catch (TransformerException e) {
	logger.info("TransformerException " + e.getMessage());
	return response;
} catch (ParseException e) {
	logger.info("ParseException " + e.getMessage());
	return response;
} catch (Exception e) {
	logger.info("exception {} done ", e.getMessage());
	return response;
}finally {
	try {
		if (inputStream != null)

			inputStream.close();
	} catch (IOException e) {
		logger.info("exception ", e.getMessage());
		
	}
}


}
 
}