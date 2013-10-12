package org.eclipse.recommenders.extdoclogin.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

/**
 * Contains some methods which are defined to data mine from the selected file
 *
 * @author Dammina Sahabandu
 */

public class CodeMine {
	public static String dataString;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		try {
			new CodeMine().getclasses("/home/dammina/driveapp_v1/org.eclipse.recommenders.extdoclogin/src/org/eclipse/recommenders/extdoclogin/views/ExtdocView.java");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
	public String getclasses(String path) throws IOException{
		BufferedReader br;

		br = new BufferedReader(new FileReader(path));
		if(path.endsWith(".java")){
			//    		System.out.println(path);
			boolean jdoc=false;
			String line;

			/*while ((line = br.readLine()) != null) {
				CodeMine.dataString+=line+"\n";
			}*/


			while ((line = br.readLine()) != null) {
				if(DocEditor.javadoc){
					if(line.contains("/**")){
						CodeMine.dataString+="\n\n\n";
						jdoc=true;
					}
					/*else if(jdoc && line.con("*")){
						testextdoc.contentString+=line+"\n";
					}*/else if(jdoc && line.contains("*/")){
						jdoc=false;
						CodeMine.dataString+=line+"\n";
					}
					if(jdoc)
						CodeMine.dataString+=line+"\n";
				}
				if((line.startsWith("class") || line.startsWith("public class")) && DocEditor.classes){
					//    				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString()+"src/Document.txt", true)));


					if(!DocEditor.methods){
						//    					out.println(line.substring(0, line.length()-1)+"\n \n \n \n");
						CodeMine.dataString+=line.substring(0, line.length()-1)+"\n \n \n\t\t\t\t\t***\t\t\t\t\t\n\n \n";
					}
					else{
						//    					out.println(line.substring(0, line.length()-1)+"\n");
						CodeMine.dataString+="\n\t\t\t\t\t***\t\t\t\t\t\n"+line.substring(0, line.length()-1)+"\n";
					}
					//    				out.close();
				}else if(line.contains("public") && line.contains("(") && line.contains(")") && DocEditor.methods){
					//    				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString()+"src/Document.txt", true)));
					//    				out.println("\t"+line.substring(0, line.length()-1)+"\n");
					CodeMine.dataString+="\n\t"+line.substring(0, line.length()-1)+"\n";
					//					out.close();
				}
			}
			br.close();

		}
		//		System.out.println(CodeMine.dataString);
		return dataString;
	}


}
