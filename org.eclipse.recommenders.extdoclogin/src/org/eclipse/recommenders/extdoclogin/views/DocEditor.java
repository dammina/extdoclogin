package org.eclipse.recommenders.extdoclogin.views;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 *@author Dammina Sahabandu
 */


public class DocEditor {
	public static boolean methods=false;
	public static boolean javadoc=false;
	public static boolean classes=false;
	public static void main(String[] args) throws IOException {
		//		System.out.println(testui.class.getProtectionDomain().getCodeSource().getLocation().getPath()); 


		/*IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        Object firstElement = selection.getFirstElement();
	        if (firstElement instanceof IAdaptable)
	        {
	            IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
	            IPath path = project.getFullPath();
	            System.out.println(path);
	        }
	    }*/
		new DocEditor().editfunc();
		//		new testui().savingtolocalfile();
		//	new testui().createTextEditer();
	}

	public void formlayout() {
		final String file_name;

		if(ExtdocView.abspath.endsWith(".java") || ExtdocView.abspath.endsWith(".xml") || ExtdocView.abspath.endsWith(".txt") ||ExtdocView.abspath.endsWith(".MF")){
			file_name=ExtdocView.abspath.substring(ExtdocView.abspath.lastIndexOf("/")+1, ExtdocView.abspath.lastIndexOf("."));
			//			System.out.println(file_name);
		}else{
			file_name="";
		}


		FormData formData;

		final Shell shell = new Shell(Display.getCurrent());
		shell.setText(file_name+" document");


		//		Image small = new Image(Display.getCurrent(),"icons/extdoc.png");
		//		shell.setImage(small); 

		Device device = Display.getCurrent ();
		Color grey = new Color (device, 46, 52, 54);
		Color font = new Color(device, 136,138,133);


		shell.setBounds(200, 200, 800, 400);
		shell.setLayout(new FormLayout());
		Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText("Clear");
		formData = new FormData();
		formData.right = new FormAttachment(100,-5);
		formData.bottom = new FormAttachment(100,-5);
		cancelButton.setLayoutData(formData);
		Button okButton = new Button(shell, SWT.PUSH);
		okButton.setText("Sava Doc.");

		formData = new FormData();
		formData.right = new FormAttachment(cancelButton,-5);
		formData.bottom = new FormAttachment(100,-5);
		okButton.setLayoutData(formData);
		final Text text = new Text(shell, SWT.MULTI | SWT.BORDER);
		formData = new FormData();
		formData.top = new FormAttachment(0,5);
		formData.bottom = new FormAttachment(cancelButton,-5);
		formData.left = new FormAttachment(0,5);
		formData.right = new FormAttachment(100,-5);
		text.setLayoutData(formData);
		text.setText("Create Your Document Here!");
		text.setForeground(font);
		text.setBackground(grey);

		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//                System.out.println(txt);
				PrintWriter writer;
				try {
					//					final String pathbin=testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
					final String pathbin=ExtdocView.abspath.substring(0, ExtdocView.abspath.lastIndexOf("/"));
					System.out.println(pathbin);
					writer = new PrintWriter(pathbin+"/"+file_name+".txt", "UTF-8");

					final String txt=text.getText();
					writer.println(txt);
					writer.close();
				} catch (FileNotFoundException | UnsupportedEncodingException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}


			}
		});

		/*text.getVerticalBar();*/

		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				CodeMine.dataString="";
				//            	shell.close();
			}
		});


		Button getclassnames = new Button(shell, SWT.PUSH);
		getclassnames.setText("Get Classes");
		formData = new FormData();
		formData.right = new FormAttachment(okButton,-5);
		formData.bottom = new FormAttachment(100,-5);
		getclassnames.setLayoutData(formData);

		getclassnames.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileStructure te = new FileStructure();
				CodeMine code=new CodeMine();
				text.setText("");
				classes=true;
				methods=false;
				javadoc=false;
				FileStructure.contentString="";
				try {
					//					text.setText(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					//					System.out.println(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//            	shell.close();
			}
		});	

		Button getmethodnames = new Button(shell, SWT.PUSH);
		getmethodnames.setText("Get Methods");
		formData = new FormData();
		formData.right = new FormAttachment(getclassnames,-5);
		formData.bottom = new FormAttachment(100,-5);
		getmethodnames.setLayoutData(formData);

		getmethodnames.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				methods=true;
				javadoc=false;
				classes=true;
				FileStructure.contentString="";
				FileStructure te = new FileStructure();
				CodeMine code=new CodeMine();
				text.setText("");

				try {
					//					text.setText(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					//					System.out.println(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//            	shell.close();
			}
		});		

		Button getjavadocs = new Button(shell, SWT.PUSH);
		getjavadocs.setText("Get Javadocs");
		formData = new FormData();
		formData.right = new FormAttachment(getmethodnames,-5);
		formData.bottom = new FormAttachment(100,-5);
		getjavadocs.setLayoutData(formData);

		getjavadocs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileStructure.contentString="";
				javadoc=true;
				classes=false;
				methods=false;
				FileStructure te = new FileStructure();
				CodeMine code=new CodeMine();
				text.setText("");
				try {
					//					text.setText(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					//					System.out.println(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//            	shell.close();
			}
		});		


		shell.open();
		while (!shell.isDisposed()) {
			if (!Display.getCurrent().readAndDispatch()) Display.getCurrent().sleep();
		}

		//		Display.getCurrent().dispose();
	}
	public void editfunc() throws IOException{

		final String pathbin=ExtdocView.abspath.substring(0, ExtdocView.abspath.lastIndexOf("/"));
		final String file_name;

		if(ExtdocView.abspath.endsWith(".java") || ExtdocView.abspath.endsWith(".xml") || ExtdocView.abspath.endsWith(".txt") ||ExtdocView.abspath.endsWith(".MF")){
			file_name=ExtdocView.abspath.substring(ExtdocView.abspath.lastIndexOf("/")+1, ExtdocView.abspath.lastIndexOf("."));
			//			System.out.println(file_name);
		}else{
			file_name="";
		}

		FormData formData;

		Device device = Display.getCurrent ();
		Color grey = new Color (device, 46, 52, 54);
		Color font = new Color(device, 136,138,133);

		final Shell shell = new Shell(Display.getCurrent());
		shell.setText("Extdoc Document Editor");

		String imagepath=DocEditor.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		Image small = new Image(Display.getCurrent(),imagepath+"sample.gif");

		shell.setImage(small); 


		shell.setBounds(200, 200, 800, 400);
		shell.setLayout(new FormLayout());
		Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText("Clear");
		formData = new FormData();
		formData.right = new FormAttachment(100,-5);
		formData.bottom = new FormAttachment(100,-5);
		cancelButton.setLayoutData(formData);
		Button okButton = new Button(shell, SWT.PUSH);
		okButton.setText("Sava Doc.");
		formData = new FormData();
		formData.right = new FormAttachment(cancelButton,-5);
		formData.bottom = new FormAttachment(100,-5);
		okButton.setLayoutData(formData);





		final Text text = new Text(shell, SWT.MULTI | SWT.BORDER);
		formData = new FormData();
		formData.top = new FormAttachment(0,5);
		formData.bottom = new FormAttachment(cancelButton,-5);
		formData.left = new FormAttachment(0,5);
		formData.right = new FormAttachment(100,-5);
		text.setLayoutData(formData);

		//		final String pathbin=testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		//		final String pathbin=testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();

		String existingtext=""; 
		BufferedReader in;
		//		System.out.println(pathbin+"src/Document.txt");

		try {
			in = new BufferedReader(new FileReader(pathbin+"/"+file_name+".txt"));
			try {
				StringBuilder sb = new StringBuilder();
				String line = in.readLine();

				while (line != null) {
					sb.append(line);
					sb.append('\n');
					line = in.readLine();
				}
				String everything = sb.toString();
				text.setText(everything);
			} finally {
				in.close();
			}
			//			in = new BufferedReader(new FileReader(pathbin+"/"+file_name+".txt"));
			//			in = new BufferedReader(new FileReader("src/Document.txt"));
			//			existingtext = in.readLine();
			//			System.out.println("extxt: ");
			//			in.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//		text.setText(existingtext);
		text.setForeground(font);
		text.setBackground(grey);

		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//                System.out.println(txt);
				PrintWriter writer;
				try {


					//        			System.out.println(pathbin);
					writer = new PrintWriter(pathbin+"/"+file_name+".txt", "UTF-8");
					//        			writer = new PrintWriter("src/Document.txt", "UTF-8");
					final String txt=text.getText();
					writer.println(txt);
					writer.close();
				} catch (FileNotFoundException | UnsupportedEncodingException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}


			}
		});

		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				CodeMine.dataString="";
				//            	shell.close();
			}
		});

		Button getclassnames = new Button(shell, SWT.PUSH);
		getclassnames.setText("Get Classes");
		formData = new FormData();
		formData.right = new FormAttachment(okButton,-5);
		formData.bottom = new FormAttachment(100,-5);
		getclassnames.setLayoutData(formData);

		getclassnames.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileStructure te = new FileStructure();
				CodeMine code=new CodeMine();
				methods=false;
				javadoc=false;
				classes=true;
				FileStructure.contentString="";
				text.setText("");
				try {
					//					text.setText(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					//					System.out.println(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//            	shell.close();
			}
		});		


		Button getmethodnames = new Button(shell, SWT.PUSH);
		getmethodnames.setText("Get Methods");
		formData = new FormData();
		formData.right = new FormAttachment(getclassnames,-5);
		formData.bottom = new FormAttachment(100,-5);
		getmethodnames.setLayoutData(formData);

		getmethodnames.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				methods=true;
				javadoc=false;
				classes=true;
				//				classes=false;
				FileStructure.contentString="";
				FileStructure te = new FileStructure();
				CodeMine code=new CodeMine();
				text.setText("");
				try {
					//					text.setText(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					//					System.out.println(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//            	shell.close();
			}
		});		

		Button getjavadocs = new Button(shell, SWT.PUSH);
		getjavadocs.setText("Get Javadocs");
		formData = new FormData();
		formData.right = new FormAttachment(getmethodnames,-5);
		formData.bottom = new FormAttachment(100,-5);
		getjavadocs.setLayoutData(formData);

		getjavadocs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileStructure.contentString="";
				javadoc=true;
				classes=false;
				methods=false;
				FileStructure te = new FileStructure();
				CodeMine code=new CodeMine();
				text.setText("");
				try {
					//					text.setText(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					//					System.out.println(te.man());
					text.setText(code.getclasses(ExtdocView.abspath));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//            	shell.close();
			}
		});		



		shell.open();
		while (!shell.isDisposed()) {
			if (!Display.getCurrent().readAndDispatch()) Display.getCurrent().sleep();
		}

		//		Display.getCurrent().dispose();

	}
	public String readfile(String path) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(path));
		//		in = new BufferedReader(new FileReader("src/Document.txt"));
		String existingtext = in.readLine();
		in.close();
		System.out.println(existingtext);
		return existingtext;
	}


	public boolean checkexistenceoffile(){

		final String pathbin=ExtdocView.abspath.substring(0, ExtdocView.abspath.lastIndexOf("/"));
		final String file_name;

		if(ExtdocView.abspath.endsWith(".java") || ExtdocView.abspath.endsWith(".xml") || ExtdocView.abspath.endsWith(".txt") ||ExtdocView.abspath.endsWith(".MF")){
			file_name=ExtdocView.abspath.substring(ExtdocView.abspath.lastIndexOf("/")+1, ExtdocView.abspath.lastIndexOf("."));
			//			System.out.println(file_name);
		}else{
			file_name="";
		}

		//		final String pathbin=testui.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		File file = new File((pathbin+"/"+file_name+".txt"));
		if(file.exists()){
			return true;
		}else
			return false;
	}


/*	public void savingtolocalfile(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("src/Document.txt", "UTF-8");
			writer.println("The first line");
			writer.println("The second line");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/






	public void createTextEditer(){
		//	Display display = new Display();
		Shell shell = new Shell(Display.getCurrent());
		shell.setText("Document");
		shell.setBounds(200, 200, 800, 400);
		//	shell.setBounds(100, 100, 200, 100);
		shell.setLayout(new FillLayout());
		final Text text = new Text(shell, SWT.MULTI);

		Button button = new Button(shell, SWT.PUSH);
		button.setBounds(25, 25, 100, 25); 
		button.setText("Save Doc.");



		button.setSize(20, 10);
		text.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {

				/*event.doit = event.text.length() == 0
	|| Character.isDigit(event.text.charAt(0));*/
			}
		});
		shell.open();
		text.setText("Create Your Document Here!");
		while (!shell.isDisposed()) {
			if (!Display.getCurrent().readAndDispatch()) Display.getCurrent().sleep();
		}
		Display.getCurrent().dispose();
	}
}





/*import java.io.IOException;

import org.eclipse.mylyn.internal.gerrit.ui.*;
import org.eclipse.mylyn.internal.tasks.ui.TaskRepositoryLocationUi;
import org.eclipse.mylyn.tasks.core.TaskRepository;

public class testui {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		GerritRepositoryLocationUi gui=new GerritRepositoryLocationUi(super(taskRepository));

		String url = "https://accounts.google.com/ServiceLogin"; 

		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
 */
