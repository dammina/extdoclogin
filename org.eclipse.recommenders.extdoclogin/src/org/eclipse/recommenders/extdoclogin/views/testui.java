package org.eclipse.recommenders.extdoclogin.views;
import java.io.IOException;

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
