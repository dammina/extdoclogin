package org.eclipse.recommenders.extdoclogin.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

<<<<<<< HEAD
/**
 * Contains some methods which are defined to data mine from the selected file
 *
 * @author Dammina Sahabandu
 */

=======
>>>>>>> 0551bec1a5ec598c2b0cf9f0409ccc78686b8568
public class CodeMine {
	public static String dataString;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
<<<<<<< HEAD
/*		try {
=======
		try {
>>>>>>> 0551bec1a5ec598c2b0cf9f0409ccc78686b8568
			new CodeMine().getclasses("/home/dammina/driveapp_v1/org.eclipse.recommenders.extdoclogin/src/org/eclipse/recommenders/extdoclogin/views/ExtdocView.java");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
<<<<<<< HEAD
		}*/
=======
		}
>>>>>>> 0551bec1a5ec598c2b0cf9f0409ccc78686b8568

	}
	public String getclasses(String path) throws IOException{
		BufferedReader br;

		br = new BufferedReader(new FileReader(path));
		if(path.endsWith(".java")){
			//    		System.out.println(path);
			boolean jdoc=false;
			String line;
<<<<<<< HEAD

			/*while ((line = br.readLine()) != null) {
				CodeMine.dataString+=line+"\n";
			}*/


			while ((line = br.readLine()) != null) {
				if(DocEditor.javadoc){
					if(line.contains("/**")){
						CodeMine.dataString+="\n\n\n";
=======
			
			/*while ((line = br.readLine()) != null) {
				CodeMine.dataString+=line+"\n";
			}*/
			
			
			while ((line = br.readLine()) != null) {
				if(testui.javadoc){
					if(line.contains("/**")){
						CodeMine.dataString+="\n\n\n\n\n\n";
>>>>>>> 0551bec1a5ec598c2b0cf9f0409ccc78686b8568
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
<<<<<<< HEAD
				if((line.startsWith("class") || line.startsWith("public class")) && DocEditor.classes){
					//    				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString()+"src/Document.txt", true)));


					if(!DocEditor.methods){
=======
				if((line.startsWith("class") || line.startsWith("public class")) && testui.classes){
					//    				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString()+"src/Document.txt", true)));


					if(!testui.methods){
>>>>>>> 0551bec1a5ec598c2b0cf9f0409ccc78686b8568
						//    					out.println(line.substring(0, line.length()-1)+"\n \n \n \n");
						CodeMine.dataString+=line.substring(0, line.length()-1)+"\n \n \n\t\t\t\t\t***\t\t\t\t\t\n\n \n";
					}
					else{
						//    					out.println(line.substring(0, line.length()-1)+"\n");
						CodeMine.dataString+="\n\t\t\t\t\t***\t\t\t\t\t\n"+line.substring(0, line.length()-1)+"\n";
					}
					//    				out.close();
<<<<<<< HEAD
				}else if(line.contains("public") && line.contains("(") && line.contains(")") && DocEditor.methods){
=======
				}else if(line.contains("public") && line.contains("(") && line.contains(")") && testui.methods){
>>>>>>> 0551bec1a5ec598c2b0cf9f0409ccc78686b8568
					//    				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString()+"src/Document.txt", true)));
					//    				out.println("\t"+line.substring(0, line.length()-1)+"\n");
					CodeMine.dataString+="\n\t"+line.substring(0, line.length()-1)+"\n";
					//					out.close();
				}
			}
			br.close();
<<<<<<< HEAD

		}
		//		System.out.println(CodeMine.dataString);
=======
			
		}
//		System.out.println(CodeMine.dataString);
>>>>>>> 0551bec1a5ec598c2b0cf9f0409ccc78686b8568
		return dataString;
	}


}
