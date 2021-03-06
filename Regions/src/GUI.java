import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GUI {
	public static ProgressBar progressBar;
	protected Shell shell;
    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                new GUI().createUI();
            }
        };

        EventQueue.invokeLater(r);
    }


    private void createUI() {
    	Display display = Display.getDefault();
        Shell shell = new Shell(display);
		shell.setSize(185, 95);
        shell.setText("File Chooser");
		shell.setLayout(new GridLayout(2, false));



		Button saveBtn = new Button(shell, SWT.NONE);
		saveBtn.setText("���������");
		Button openBtn = new Button(shell, SWT.NONE);
		openBtn.setText("���� � �����");
		progressBar = new ProgressBar(shell, SWT.NONE);
		GridData gd_progressBar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_progressBar.widthHint = 159;
		progressBar.setLayoutData(gd_progressBar);
	//	progressBar.setSelection(40);
		progressBar.setMaximum(120);
		
        
        saveBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                JFileChooser saveFile;
				try {
					saveFile = new JFileChooser(new File(GUI.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
	                int returnVal = saveFile.showSaveDialog(null);
	                if(returnVal==JFileChooser.APPROVE_OPTION){
	                File file = saveFile.getSelectedFile();
	                System.out.println("Saving:"+file+".xlsx");
	                job job = new job(file);
	                new LongRunningOperation(display, progressBar).start();
	                job.writeExcel();
	                }
	                else System.out.println("Canceled");
				} catch (URISyntaxException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
        });

        openBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                JFileChooser openFile = new JFileChooser(/*"C:/Personal Soft/eclipse/workspace/Scripts"*/);
                int returnVal = openFile.showOpenDialog(null);
                if(returnVal==JFileChooser.APPROVE_OPTION){
                File file = openFile.getSelectedFile();
                System.out.println("Opening:" + file);
                CSV.changeCodes(file);
                }
                else System.out.println("Canceled");
            }
        });
        shell.open();
        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
    }
}

class LongRunningOperation extends Thread {
	  private Display display;

	  private ProgressBar progressBar;

	  public LongRunningOperation(Display display, ProgressBar progressBar) {
	    this.display = display;
	    this.progressBar = progressBar;
	  }

	  public void run() {
	    for (int i = 0; i < 30; i++) {
	      try {
	        Thread.sleep(1000);
	      } catch (InterruptedException e) {
	      }
	      display.asyncExec(new Runnable() {
	        public void run() {
	          if (progressBar.isDisposed()) return;
	          progressBar.setSelection(progressBar.getSelection() + 1);
	        }
	      });
	    }
	  }
	}