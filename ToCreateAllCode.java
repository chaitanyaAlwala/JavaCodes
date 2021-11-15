package com.techdata;

import java.util.ArrayList;
import java.util.Scanner;
public class ToCreateAllCode {
	
 public static void main(String[] args) {
	 ToCreateAllCode toCreateAllCode = new ToCreateAllCode();
	ArrayList<String> listOfStrings=new ArrayList<String>();
	ArrayList<String> CamelCaseList=new ArrayList<String>();
	Scanner scan=new Scanner(System.in);
	String modalvariable="modal";
	System.out.println("enter the the variable of modal in html code");
	modalvariable = scan.next();
	
	System.out.println("enter the number of strings");
	int numOfStrings=scan.nextInt();
	System.out.println("Please enter all the strings on by one in new line");
	for(int i=0;i<(numOfStrings+1);i++) {
		listOfStrings.add(scan.nextLine().toString());
	}
	//CamelCaseList=toCreateAllCode.toCamelCase(listOfStrings);
	for(String st: listOfStrings) {
		CamelCaseList.add(toCreateAllCode.toCamelCase(st));
	}
	System.out.println("\n .TS file code, of angular component(only if its SPA)");
	for(String st: CamelCaseList) {
		System.out.println("'"+st+"',");
	}
	toCreateAllCode.javaClassOutPut(listOfStrings,CamelCaseList);
	toCreateAllCode.htmlCodeOutput(listOfStrings,CamelCaseList,modalvariable);
	toCreateAllCode.xmlCodeoutput(listOfStrings,CamelCaseList);
}
 public String toCamelCase(String text) {
	 String[] words = text.split("[\\W_]+");
	 StringBuilder builder = new StringBuilder();
	 for (int i = 0; i < words.length; i++) {
	     String word = words[i];
	     if (i == 0) {
	         word = word.isEmpty() ? word : word.toLowerCase();
	     } else {
	         word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();      
	     }
	     builder.append(word);
	 }
	 return builder.toString();
	}
 public void javaClassOutPut(ArrayList<String> listOfStrings, ArrayList<String> CamelCaseList) {
	ArrayList<String> JavaCodeList=new ArrayList<String>();
	ArrayList<String> javFunctionList=new ArrayList<String>();
	
	 System.out.println("\nThis code has to be placed under java modal---------------------------------------------");
	 int i=listOfStrings.size();
	 for(int j=1;j<i;j++) {
	 	String javaCode="    @I18N(value=\""+listOfStrings.get(j) +"\") \n    private String "+CamelCaseList.get(j)+ ";";
	 	JavaCodeList.add(javaCode);
	 	String word=CamelCaseList.get(j);
	 	word = "get"+Character.toUpperCase(word.charAt(0))+ word.substring(1);
	 	
	 	String javaFunction="    public String "+word+"() {\r\n"
	 			+ "        return "+CamelCaseList.get(j)+";\n    }";
	 	javFunctionList.add(javaFunction);
	 }
	 for(String st :JavaCodeList ) {
		 System.out.println(st+"\n");
	 }
	 for(String st :javFunctionList ) {
		 System.out.println(st+"\n");
	 }
 }
 public void htmlCodeOutput(ArrayList<String> listOfStrings, ArrayList<String> CamelCaseList,String modalvariable) {
	 System.out.println("\nHTML Code of AEM component--------------------------------------------- \n");
	 ArrayList<String> HtmlCodeList=new ArrayList<String>();
	 for(String st: CamelCaseList) {
		 HtmlCodeList.add(st.toLowerCase()+"=\"${"+modalvariable+"."+st+"}\"");	 
	 }
	 for(String ht :HtmlCodeList ) {
		 System.out.println(ht);
	 }
 }
 public void xmlCodeoutput(ArrayList<String> listOfStrings, ArrayList<String> CamelCaseList) {
	 System.out.println("\nXML output should be placed under the cq-dialog->content.xml-------------------------------- \n");
	 ArrayList<String> XMLCodeList=new ArrayList<String>();
	 int i=listOfStrings.size();
	 for(int j=1;j<i;j++) {
		 XMLCodeList.add( "		            <"+CamelCaseList.get(j)+"\r\n"
	 		         + "                        jcr:primaryType=\"nt:unstructured\"\r\n"
	 		         + "                        sling:resourceType=\"cq/gui/components/authoring/dialog/richtext\"\r\n"
	 	           	 + "                        fieldLabel=\""+listOfStrings.get(j)+"\"\r\n"
	 		         + "                        name=\"./"+CamelCaseList.get(j)+"\"\r\n"
	 		         + "                        removeSingleParagraphContainer=\"true\"\r\n"
	 		         + "                        useFixedInlineToolbar=\"{Boolean}true\">\r\n"
	 		         + "                        <rtePlugins\r\n"
	 		         + "                            jcr:primaryType=\"nt:unstructured\"\r\n"
	 		         + "                            sling:resourceSuperType=\"/apps/tng-aem-project/authoring/pluginsRTE/rtePlugins\"/>\r\n"
	 		         + "                        <uiSettings\r\n"
	 		         + "                            jcr:primaryType=\"nt:unstructured\"\r\n"
	 		         + "                            sling:resourceSuperType=\"/apps/tng-aem-project/authoring/pluginsRTE/uiSettings\"/>\r\n"
	 		         + "                    </"+CamelCaseList.get(j)+">");
	 }
	 for(String xt :XMLCodeList ) {
		 System.out.println(xt);
	 }
 }
}

