package org.eclipse.recommenders.extdoclogin.views;


import java.io.IOException;

import org.eclipse.recommenders.extdoclogin.Activator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
//import org.eclipse.mylyn.commons.repositories.core.auth.*;


/**
 * This class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 * @author Dammina Sahabandu
 * <p>
 */

public class ExtdocView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.recommenders.extdoclogin.views.ExtdocView";
	public static DocEditor tui=new DocEditor();
	private TableViewer viewer;
	public static String abspath;

	//for test purposes dammina
	public Action getLogin_button() {
		return login_button;
	}

	public static String getAbspath() {
		return abspath;
	}

	public static void setAbspath(String abspath) {
		ExtdocView.abspath = abspath;
	}

	public void setLogin_button(Action login_button) {
		ExtdocView.login_button = login_button;
	}
	//
	public static Action login_button;


	//	private Action action2;
	private Action doubleClickAction;

	//	private Button loginbutton;
	private final ImageDescriptor loginimage = Activator.getImageDescriptor("icons/user_login.gif");
	//	private final ImageDescriptor loginimage2 = Activator.getImageDescriptor("icons/logincheck.gif");
	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */


	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return new String[] { "Create", "Edit" };
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			/*return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);*/
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public ExtdocView() {
	}

	/**
	 * For testing purposes only.
	 * @return the table viewer in the Extdoc view
	 */
	public TableViewer getExtdocViewer() {
		return viewer;
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "org.eclipse.recommenders.extdoclogin.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ExtdocView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(login_button);
		manager.add(new Separator());
		//		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(login_button);
		//		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(login_button);
		//		manager.add(action2);
	}
	public String getAbsPath(){
		ISelectionService service = getSite().getWorkbenchWindow().getSelectionService();
		IStructuredSelection structured = (IStructuredSelection) service.getSelection("org.eclipse.jdt.ui.PackageExplorer");
		try{
			IFile file = (IFile) ((IStructuredSelection) structured).getFirstElement();
			IPath path=file.getFullPath();
			//			System.out.println(path.toPortableString());
			//			showMessage(path.toPortableString());
			return path.toPortableString();
		}catch(ClassCastException e){
			try{
				ICompilationUnit file=(ICompilationUnit)((IStructuredSelection)structured).getFirstElement();
				IPath path=file.getPath();
				//				System.out.println(path.toPortableString());
				//				showMessage(path.toPortableString());
				return path.toPortableString();
			}catch(ClassCastException ex){
				System.out.println("folder selected");
				//folder structure
				return "folder selected";
			}
		}
	}
	private void makeActions() {
		login_button = new Action() {
			public void run() {

				/*ISelectionService service = getSite().getWorkbenchWindow().getSelectionService();
				IStructuredSelection structured = (IStructuredSelection) service.getSelection("org.eclipse.jdt.ui.PackageExplorer");
				try{
					IFile file = (IFile) ((IStructuredSelection) structured).getFirstElement();
					IPath path=file.getRawLocation();
					System.out.println(path.toPortableString());
					showMessage(path.toPortableString());
				}catch(ClassCastException e){
					try{
						ICompilationUnit file=(ICompilationUnit)((IStructuredSelection)structured).getFirstElement();
						IFile ifile = (IFile)file.getUnderlyingResource();
						IPath path=ifile.getRawLocation();
						System.out.println(path.toPortableString());
						showMessage(path.toPortableString());
					}catch(ClassCastException ex){
						System.out.println("folder selected");
						//folder structure
					} catch (JavaModelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}*/
				String url = "https://accounts.google.com/ServiceLogin"; 

				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//				showMessage("login button still get implemented!");


			}
		};
		login_button.setText("Login");
		login_button.setToolTipText("Login to the system");
		login_button.setImageDescriptor(loginimage);
		//PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK)

		/*action2 = new Action() {
			public void run() {

				showMessage("Action 2 executed");
			}
		};*/
		//		action2.setText("Action 2");
		//		action2.setToolTipText("Action 2 tooltip");
		//		action2.setImageDescriptor(loginimage2);
		//		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				if(obj.toString().equals("Edit")){
					//
					/**
					 * This method finds the absolute path to the selected file in the package explorer.
					 */
					ISelectionService service = getSite().getWorkbenchWindow().getSelectionService();
					IStructuredSelection structured = (IStructuredSelection) service.getSelection("org.eclipse.jdt.ui.PackageExplorer");
					try{
						IFile file = (IFile) ((IStructuredSelection) structured).getFirstElement();
						IPath path=file.getRawLocation();
						abspath=path.toPortableString();
					}catch(ClassCastException e){
						try{
							ICompilationUnit file=(ICompilationUnit)((IStructuredSelection)structured).getFirstElement();
							IFile ifile = (IFile)file.getUnderlyingResource();
							IPath path=ifile.getRawLocation();
							abspath=path.toPortableString();
						}catch(ClassCastException ex){
							System.out.println("folder selected");
							abspath="folder structure";
							//folder structure
						} catch (JavaModelException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					//
					//					showMessage(abspath);
					try {
						if(!tui.checkexistenceoffile()){
							showMessage("There is no document for this file!");
						}
						else{
							tui.editfunc();
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
				else if(obj.toString().equals("Create")){
					//

					ISelectionService service = getSite().getWorkbenchWindow().getSelectionService();
					IStructuredSelection structured = (IStructuredSelection) service.getSelection("org.eclipse.jdt.ui.PackageExplorer");
					try{
						IFile file = (IFile) ((IStructuredSelection) structured).getFirstElement();
						IPath path=file.getFullPath();
						abspath=path.toPortableString();
					}catch(ClassCastException e){
						try{
							ICompilationUnit file=(ICompilationUnit)((IStructuredSelection)structured).getFirstElement();
							IFile ifile = (IFile)file.getUnderlyingResource();

							//							System.out.println(file.getPackageDeclarations());
							IPath path=ifile.getRawLocation();
							abspath=path.toPortableString();
						}catch(ClassCastException ex){
							System.out.print("folder selected: ");
							abspath="folder structure";
							//folder structure
						} catch (JavaModelException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					//
					//					abspath=getAbspath();
					//					showMessage("Please log into the system before Creating the document");
					//					showMessage(abspath);
					if(tui.checkexistenceoffile()){
						showMessage("There already exists a document file for this project!");
					}
					else{
						tui.formlayout();
					}

				}
				else if(obj.toString().equals("Rate"))
					showMessage("Please log into the system before Rating the document");
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
				viewer.getControl().getShell(),
				"Extdoc",
				message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
