package ch.makery.address;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ch.makery.address.model.ExcelParser;
import ch.makery.address.model.Person;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.RootLayoutController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Person> personDataBase = FXCollections.observableArrayList();
    private ObservableList<String> newTels = FXCollections.observableArrayList();
    ObservableList<Person> per= FXCollections.observableArrayList();


    /**
     * Constructor
     * @throws IOException 
     */
    public MainApp() throws IOException {
        // Add some sample data
    	/*ObservableList<Person> newPersonData;
    	ExcelParser excel = new ExcelParser(new File("C:\\Users\\Дмитрий\\git\\JavaFXCRM\\JavaFXCRM\\src\\база демо 26-28.12.14 - Дима.xlsx"));
    	newPersonData=excel.readNewBase(true);
    	newPersonList(newPersonData);*/
    	ObservableList<Person> newPersonData = FXCollections.observableArrayList();
    	newPersonData.add(new Person("89122849021","124.174.698.234",LocalDateTime.of(2015, 4, 16, 15, 30)));
    	newPersonData.add(new Person("89325197769","24.74.8.24",LocalDateTime.of(2015, 4, 16, 12, 30)));
    	newPersonData.add(new Person("89141235124","124.174.84.254",LocalDateTime.of(2015, 4, 21, 18, 00)));
    	newPersonData.add(new Person("89496239767","1.95.9.94",LocalDateTime.of(2015, 4, 16, 12, 30)));
    	newPersonData.add(new Person("89256268365","12.1.84.24",null));
    	newPersonData.add(new Person("89259240946","24.74.8.24",LocalDateTime.of(2015, 4, 21, 13, 00)));
    	newPersonList(newPersonData);
    }

    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personDataBase;
    }
    
    public void addPersonData(Person person){
    	personDataBase.add(person);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootLayoutController controller = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Copy of TextFields.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
public void newPersonList(ObservableList<Person> pers){
	per=pers;
	newTels.clear();
	for(Person sf:pers)personDataBase.add(sf);
    for (Person s:pers)newTels.add(s.getinitTel());
  
}
public void oldPersonList(ObservableList<Person> pers){
	for(Person sf:pers)personDataBase.add(sf);
}
public ObservableList<String> getNewTelsData() {
    return newTels;
}

public ObservableList<Person> getTelsPers() {
    return per;
}

public void sortTelList(){
    Comparator<Person> comparatorMyObject_byRegion = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
        	if(o1.getdiffTime()!=null&&o2.getdiffTime()!=null)
            return -Integer.parseInt(o1.getdiffTime())+Integer.parseInt(o2.getdiffTime());
        	else return 0;
        }
    };
Collections.sort(per, comparatorMyObject_byRegion);
newTels.clear();
for (Person s:per)newTels.add(s.getinitTel());
}
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}