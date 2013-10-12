package org.eclipse.recommenders.extdoclogin.test;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.quickaccess.ActionProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.eclipse.recommenders.extdoclogin.views.ExtdocView;
/**
* The class <code>FavoritesViewTest</code> contains tests
* for the class {@link
* org.eclipse.recommenders.extdoclogin.views.ExtdocView}.
* @pattern JUnit Test Case
*/


public class ExtdocViewTest {
	private static final String VIEW_ID =
	"org.eclipse.recommenders.extdoclogin.views.ExtdocView";
	/**
	* The object that is being tested.
	*
	* @see com.qualityeclipse.favorites.views.FavoritesView
	*/
	private ExtdocView testView;
	private ExtdocView tempview=new ExtdocView();
	/**
	* Perform pre-test initialization.
	*/
	@Before
	public void setUp() throws Exception {
		
	// Initialize the test fixture for each test
	// that is run.
	waitForJobs();
	testView = (ExtdocView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(VIEW_ID);
	
	
	// Delay for 3 seconds so that
	// the Extdoc view can be seen.
	waitForJobs();
	delay(1000);
	// Add additional setup code here.
	}
	/**
	* Run the view test.
	*/
	@Test
	public void test() {
		TableViewer viewer = testView.getExtdocViewer();
		Object[] expectedContent =
		new Object[] { "Create", "Edit", "Rate" };
		Object[] expectedLabels =
		new String[] { "Create", "Edit", "Rate" };
		Object expectedloginlabel=new String();//
		Object expectedtooltiptext=new String();
		expectedloginlabel="Login";//
		expectedtooltiptext="Login to the system";
		// Assert valid content.
		IStructuredContentProvider contentProvider =
		(IStructuredContentProvider)
		viewer.getContentProvider();
		assertArrayEquals(expectedContent,
		contentProvider.getElements(viewer.getInput()));
		/*System.out.println("dammina :");
		System.out.println("test :"+"###"+tempview.getAction1().getText());
		System.out.println("Over!");*/
		assertEquals(expectedloginlabel, new ExtdocView().getLogin_button().getText());//test login label
		assertEquals(expectedtooltiptext, new ExtdocView().getLogin_button().getToolTipText());//test login button tool tip text
		
		// Assert valid labels.
		ITableLabelProvider labelProvider =
		(ITableLabelProvider) viewer.getLabelProvider();
		for (int i = 0; i < expectedLabels.length; i++)
			assertEquals(expectedLabels[i], labelProvider.getColumnText(expectedContent[i], 1));
		
	}
	
	/**
	* Process UI input but do not return for the
	* specified time interval.
	*
	* @param waitTimeMillis the number of milliseconds
	*/
	private void delay(long waitTimeMillis) {
		Display display = Display.getCurrent();
		// If this is the UI thread,
		// then process input.
		if (display != null) {
			long endTimeMillis =
			System.currentTimeMillis() + waitTimeMillis;
			while (System.currentTimeMillis() < endTimeMillis)
			{
			if (!display.readAndDispatch())
			display.sleep();
			}
			display.update();
			}
			// Otherwise, perform a simple sleep.
			else {
			try {
			Thread.sleep(waitTimeMillis);
			}
			catch (InterruptedException e) {
			// Ignored.
			}
			}
			}
	/**
	* Wait until all background tasks are complete.
	*/
	public void waitForJobs() {
		while (!Job.getJobManager().isIdle())
		delay(1000);
	}
	

}
