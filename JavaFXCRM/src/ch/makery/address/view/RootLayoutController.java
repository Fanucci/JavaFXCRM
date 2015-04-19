package ch.makery.address.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ch.makery.address.MainApp;
import ch.makery.address.model.ExcelParser;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import ch.makery.address.util.TimeUtil;

/**
 * Dialog to edit details of a person.
 * 
 * @author Marco Jakob
 */
public class RootLayoutController {

    @FXML
    private TextField initTelField;
    @FXML
    private TextField theIPField;
    @FXML
    private TextField regionField;
    @FXML
    private TextField promoCodeField;
    @FXML
    private TextField queryTimeField;
    @FXML
    private TextField queryDateField;

    private boolean okClicked = false;
	final FileChooser fileChooser = new FileChooser();
	private Stage dialogStage;
	ObservableList<Person> newPersonData;
	private MainApp mainApp;
	File rememberNew;
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /**
     * Called when the user clicks ok.
     * @throws IOException 
     */
    @FXML
    private void openNewBase() throws IOException {
        fileChooser.setTitle("Открыть новую базу");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XLSX", "*.XLSX"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))); 
                File file = fileChooser.showOpenDialog(dialogStage);
                rememberNew=file;
                if (file != null) {
                	ExcelParser excel = new ExcelParser(file);
                	newPersonData=excel.readNewBase(true);
                System.out.println(file);
                System.out.println(newPersonData);
                mainApp.newPersonList(newPersonData);
                }
                }
    @FXML
    private void openOldBase() throws IOException {
        fileChooser.setTitle("Открыть старую базу");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XLSX", "*.XLSX"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))); 
                File file = fileChooser.showOpenDialog(dialogStage);
                if (file != null) {
                	ExcelParser excel = new ExcelParser(file);
                	newPersonData=excel.readNewBase(false);
                System.out.println(file);
              //  System.out.println(newPersonData);
                mainApp.oldPersonList(newPersonData);
                }
    }
    
    @FXML
    private void saveChanges() throws IOException{
    	ExcelParser excel = new ExcelParser(rememberNew);
    	excel.writeNewBase(mainApp.getTelsPers(),true);
    }
    
	public void setMainApp(MainApp mainApp) {
		// TODO Auto-generated method stub
	    this.mainApp = mainApp;
	}
            
    public ObservableList<Person> getPersonData() {
        return newPersonData;
    }

}