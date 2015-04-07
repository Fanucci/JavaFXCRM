package ch.makery.address.view;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

public class PersonOverviewController {
  Person superPerson;
  /*  @FXML
    private TableView<Person> telephones;
    @FXML
    private TableColumn<Person, String> telephonesCol;*/
    @FXML
    private ListView<Person> telephones;
  /*  @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> telephonesCol;
    @FXML
    private TableView<ObservableList<String>> tableTime;
    @FXML
    private TableColumn<Person, String> timee;*/

    @FXML
    private Button DragMeButton;
    @FXML
    private GridPane calendaar;

   @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private MainApp mainApp;
	private int i;
	private int j;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    	
        // Initialize the person table with the two columns.

     /*   telephones.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        telephonesCol.setCellFactory(TextFieldTableCell.forTableColumn());*/
    /*	MainApp.getPersonData().get(1);
        telephones.setItems(personData.firstNameProperty());*/
        // Clear person details.
        showPersonDetails(null);

addDnDlisteners(DragMeButton);
initGrid();
        // Listen for selection changes and show the person details when changed.

        telephones.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table

        telephones.setItems(mainApp.getPersonData());
        telephones.setCellFactory(new Callback<ListView<Person>, ListCell<Person>>(){
            @Override
            public ListCell<Person> call(ListView<Person> p) {
                
                ListCell<Person> cell = new ListCell<Person>(){

                    @Override
                    protected void updateItem(Person t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getFirstName());
                        }
                    }

                };
                
                return cell;
            }
        });

    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
        	superPerson=person;
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = telephones.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	telephones.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            
            alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = telephones.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            
            alert.showAndWait();
        }
    }
   
    
    private static void log(Object o) {
        System.out.println(""+o);
   }
    public void addDnDlisteners(Node where){
        // dnd stuff begin
    	where.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override public void handle(final MouseEvent me) {
                 log("setOnDragDetected("+me+")");
                 final Dragboard db = where.startDragAndDrop(TransferMode.COPY);
                 final ClipboardContent content = new ClipboardContent();
                 content.putString(where.toString());
                 log(content);
                 db.setContent(content);
                 me.consume();
            }
       });
    	/*where.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override public void handle(final DragEvent de) {
            	log("setOnDragEntered("+de+")");
            }
       });*/
       
    	where.setOnDragOver(new EventHandler<DragEvent>() {
            @Override public void handle(final DragEvent de) {
                 de.acceptTransferModes(TransferMode.COPY);
             	
                 de.consume();
            }
       });
    	where.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override public void handle(final DragEvent de) {
                Object source = de.getSource();
  	          Button clickedBtn = (Button) source; // that's the button that was clicked
	          int x = GridPane.getColumnIndex(clickedBtn);
	          int y = GridPane.getRowIndex(clickedBtn);
  	        
	          System.out.println(x+"||"+y);
	          
  	      Dragboard db = de.getDragboard();
  	    log(db.getString());
        //	Person selectedPerson = telephones.getSelectionModel().getSelectedItem();
        	String telPerson = superPerson.getFirstName();
  	        clickedBtn.setText(telPerson); // prints the id of the button
            	log("setOnDragDropped("+de+")");
            }
       });
    }
    
    public void initGrid(){
    	int hours = 9;
    	String correctHour = null;
        GridPane gridpane = calendaar;
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(0);
        gridpane.setVgap(0);
       
        
       /* for (int j = 0; j < 21; j++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.SOMETIMES);
            gridpane.getColumnConstraints().add(cc);
        }

        for (int j = 0; j < 6; j++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.SOMETIMES);
            gridpane.getRowConstraints().add(rc);
        }*/
        
        
        
        for (j=1;j<22;j++){
        	if ((j & 1) == 1 )correctHour = hours+":00";
        	else {
        		correctHour=hours+":30";
        		hours++;
        	}
        	 Label sr = new Label(correctHour);
        	 gridpane.add(sr, 0, j);
        	 
        	 
             for (i=1;i<6;i++){
            	 
                 Button dgfh = new Button(); //can add text
                 dgfh.setStyle("-fx-background-radius: 0;-fx-background-color:  #FAFAFA;-fx-border-color:  #ADADAD;-fx-border-width: 0 0.3 0 0.3;");
                 if ((j & 1) == 1 )dgfh.setStyle("-fx-background-color:  #EBF3FF;-fx-border-color:  #ADADAD;-fx-border-width: 0 0.3 0 0.3;");
               //  dgfh.setPrefWidth(150);
                 dgfh.setMaxWidth(Double.MAX_VALUE);
                 dgfh.setMaxHeight(Double.MAX_VALUE);
                 gridpane.add(dgfh, i, j);

                 addDnDlisteners(dgfh);
                 dgfh.setOnAction(new EventHandler<ActionEvent>() {
     
                	    @Override public void handle(ActionEvent e) {
                	    Button newButt = new Button();
                	      HBox hbox1 = new HBox();
                	      
                	      Object source = e.getSource();

                	          Button clickedBtn = (Button) source; // that's the button that was clicked
                	          int i = GridPane.getColumnIndex(dgfh);
                	          int j = GridPane.getRowIndex(dgfh);
                	          System.out.println("Column: " + i + " || Row: " + j);
                	          newButt.setStyle("-fx-background-radius: 0;-fx-background-color:  #FAFAFA;-fx-border-color:  #ADADAD;-fx-border-width: 0 0.3 0 0.3;");
                              if ((j & 1) == 1 )newButt.setStyle("-fx-background-color:  #EBF3FF;-fx-border-color:  #ADADAD;-fx-border-width: 0 0.3 0 0.3;");
                	          clickedBtn.setText("Added"); // prints the id of the button
                	      
                	      gridpane.add(hbox1, i, j);
                	      dgfh.setMaxWidth(Double.MAX_VALUE);
                
                	      newButt.setMaxWidth(10);
                	      hbox1.getChildren().addAll(dgfh,newButt);    
                	      HBox.setHgrow(dgfh, Priority.ALWAYS);
                	      HBox.setHgrow(newButt, Priority.ALWAYS);
 
                	    }
                	});
                 }
        }
            
      
    }
}