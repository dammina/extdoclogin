package org.eclipse.recommenders.extdoclogin.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Contains some methods to list files and folders from a directory
 *
 * @author Dammina Sahabandu
 */
public class FileStructure {
	public static String contentString="";
	/**
	 * List all the files and folders from a directory
	 * @param directoryName to be listed
	 */
	public void listFilesAndFolders(String directoryName){

		File directory = new File(directoryName);

		//get all the files from a directory
		File[] fList = directory.listFiles();

		for (File file : fList){
			System.out.println(file.getName());
		}
	}

	/**
	 * List all the files under a directory
	 * @param directoryName to be listed
	 */
	public void listFiles(String directoryName){

		File directory = new File(directoryName);

		//get all the files from a directory
		File[] fList = directory.listFiles();

		for (File file : fList){
			if (file.isFile()){
				System.out.println(file.getName());
			}
		}
	}

	/**
	 * List all the folder under a directory
	 * @param directoryName to be listed
	 */
	public void listFolders(String directoryName){

		File directory = new File(directoryName);

		//get all the files from a directory
		File[] fList = directory.listFiles();

		for (File file : fList){
			if (file.isDirectory()){
				System.out.println(file.getName());
			}
		}
	}
	/**
	 * List all files from a directory and its subdirectories
	 * @param directoryName to be listed
	 * @throws IOException 
	 */
	public void listFilesAndFilesSubDirectories(String directoryName) throws IOException{

		File directory = new File(directoryName);

		//get all the files from a directory
		File[] fList = directory.listFiles();

		for (File file : fList){
			if (file.isFile()){
				//                System.out.println(file.getAbsolutePath());
				getclasses(file.getAbsolutePath(),file);
			} else if (file.isDirectory()){
				listFilesAndFilesSubDirectories(file.getAbsolutePath());
			}
		}
	}

	public void getclasses(String path, File file) throws IOException{
		BufferedReader br;

		br = new BufferedReader(new FileReader(file));
		if(path.endsWith(".java")){
			//    		System.out.println(path);
			boolean jdoc=false;
			String line;
			while ((line = br.readLine()) != null) {
				if(DocEditor.javadoc){
					if(line.contains("/**")){
						FileStructure.contentString+="\n\n\n";
						jdoc=true;
					}
					/*else if(jdoc && line.con("*")){
						testextdoc.contentString+=line+"\n";
					}*/else if(jdoc && line.contains("*/")){
						jdoc=false;
						FileStructure.contentString+=line+"\n";
					}
					if(jdoc)
						FileStructure.contentString+=line+"\n";
				}
				if((line.startsWith("class") || line.startsWith("public class")) && DocEditor.classes){
					//    				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString()+"src/Document.txt", true)));


					if(!DocEditor.methods){
						//    					out.println(line.substring(0, line.length()-1)+"\n \n \n \n");
						FileStructure.contentString+=line.substring(0, line.length()-1)+"\n \n \n\t\t\t\t\t***\t\t\t\t\t\n\n \n";
					}
					else{
						//    					out.println(line.substring(0, line.length()-1)+"\n");
						FileStructure.contentString+="\n\t\t\t\t\t***\t\t\t\t\t\n"+line.substring(0, line.length()-1)+"\n";
					}
					//    				out.close();
				}else if(line.contains("public") && line.contains("(") && line.contains(")") && DocEditor.methods){
					//    				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString()+"src/Document.txt", true)));
					//    				out.println("\t"+line.substring(0, line.length()-1)+"\n");
					FileStructure.contentString+="\n\t"+line.substring(0, line.length()-1)+"\n";
					//					out.close();
				}
			}
			br.close();
		}
	}
	//    public static void main (String[] args) throws IOException{
	public String man() throws IOException{

		FileStructure listFilesUtil = new FileStructure();

		//        final String directoryLinuxMac ="/Users/loiane/test";

		//Windows directory example
/*		final String directoryWindows ="/home/dammina/driveapp_v1/org.eclipse.recommenders.extdoclogin/src";

		listFilesUtil.listFilesAndFilesSubDirectories(directoryWindows);*/
		return contentString;
	}
}

/*public class testextdoc{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String path=testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		System.out.println(path);
		File folder = new File(path.substring(0, path.length()-4)+"src");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
	}

}*/
