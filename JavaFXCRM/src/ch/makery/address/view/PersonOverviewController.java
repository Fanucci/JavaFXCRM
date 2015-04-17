package ch.makery.address.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Line;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import ch.makery.address.util.TimeUtil;
import ch.makery.address.util.Utils;

public class PersonOverviewController {
  Person superPerson;

    @FXML
    private ListView<String> telephones;


    @FXML
    private Button SaveButton;
    @FXML
	protected GridPane calendaar;

   @FXML
   private MenuButton initTelLabel;
    @FXML
    private Label theIPLabel;
    @FXML
    private Label regionLabel;
    @FXML
    private Label whereFromLabel;
    @FXML
    private Label promoCodeLabel;
    @FXML
    private Label queryTimeLabel;
    @FXML
    private Label queryDateLabel;
    @FXML
    private Label diffTimeLabel;
    @FXML
    private TextField firstNameLabel;
    @FXML
    private TextArea commentsField;
    @FXML
    private TextField SearchField;
    @FXML
	public DatePicker DatePick;
    @FXML
	public ChoiceBox<String> timePick;
    
    @FXML
 	public CheckBox howMany;
    private MainApp mainApp;
	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
	  DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
	  DateTimeFormatter formatterHours = DateTimeFormatter.ofPattern("H:mm");
    // Reference to the main application.
	  
	  
	  LocalDate currentWeekMonday;
String style1="-fx-padding: 0;-fx-background-radius: 0;-fx-background-color:  #FAFAFA;-fx-border-color:  #ADADAD;-fx-border-width: 0 0.3 0 0.3;";
String style2="-fx-padding: 0;-fx-background-color:  #EBF3FF;-fx-border-color:  #ADADAD;-fx-border-width: 0 0.3 0 0.3;";

private static ObservableList<String> times=FXCollections.observableArrayList();
private static ObservableList<String> dayNames=FXCollections.observableArrayList();
private static ObservableList<Person> telsToCall=FXCollections.observableArrayList();
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    	  currentWeekMonday = LocalDate.now().with(DayOfWeek.MONDAY);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
       	int hours = 9;
    	String correctHour = null;
        for (int j = 2;j<23;j++){
        	if ((j & 1) != 1 )correctHour = hours+":00";
        	else {
        		correctHour=hours+":30";
        		hours++;
        	}
    	if (correctHour!=null){

    		times.add(correctHour);
    	}
        }
        calendaar.setPadding(new Insets(5));
        calendaar.setHgap(0);
        calendaar.setVgap(0);
        dayNames.add("Понедельник");
        dayNames.add("Вторник");
        dayNames.add("Среда");
        dayNames.add("Четверг");
        dayNames.add("Пятница");
    	
        // Initialize the person table with the two columns.
      
        showPersonDetails("");
        initTelLabel.getItems().add(new SeparatorMenuItem());

        //(".box:disable{: -visibility:0}")
timePick.setItems(times);
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

        telephones.setItems(mainApp.getNewTelsData());
        initGrid();
    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    public void showPersonDetails(String persona) {
        if (persona !="") {
            // Fill the labels with info from the person object.
        	Person person=getPersonByTel(persona);
            initTelLabel.setText(person.getinitTel());


            for(int i=3;i<initTelLabel.getItems().size();i++){
            	initTelLabel.getItems().remove(i);
            	}
      
           ObservableList<Person> listt= getSimilarIp(person);
           int i=listt.size();
           if(i>0)howMany.setSelected(true);
           else howMany.setSelected(false);
           howMany.setText(String.valueOf(i));
            for (Person p:listt){
            	MenuItem newItem=new MenuItem(p.getinitTel());
            	initTelLabel.getItems().addAll(newItem);
            	//-->
            	newItem.setOnAction(new EventHandler<ActionEvent>() {
                       @Override public void handle(ActionEvent e) {
                    	   showPersonDetails(p.getinitTel());
                       }
                   });
            	//-->
            
            	
            }
            theIPLabel.setText(person.gettheIP());
            regionLabel.setText(person.getregion());
            whereFromLabel.setText(person.getwhereFrom());
            promoCodeLabel.setText(person.getpromoCode());
            diffTimeLabel.setText(person.getdiffTime());
            queryTimeLabel.setText(TimeUtil.format(person.getqueryTime()));
            queryDateLabel.setText(DateUtil.format(person.getqueryDate()));
       //     if(person.getcomments()!=null)commentsField.setText(person.getcomments());
        //    else commentsField.setText("");
            LocalDateTime tempDatec= person.getnextCall();
    
            if (tempDatec!=null)DatePick.setValue(tempDatec.toLocalDate());
            else DatePick.setValue(null);
           
            if (tempDatec!=null)timePick.getSelectionModel().select(indexByTime(tempDatec));
            else timePick.getSelectionModel().select(null);
            commentsField.setText(person.getcomments());
        } else {
            // Person is null, remove all the text.
            initTelLabel.setText("");
            theIPLabel.setText("");
            regionLabel.setText("");
            whereFromLabel.setText("");
            diffTimeLabel.setText("");
            promoCodeLabel.setText("");
            queryTimeLabel.setText("");
            queryDateLabel.setText("");
        
        }
    }
    public ObservableList<Person> getSimilarIp(Person b){
    	String bsip=b.gettheIP();
    	ObservableList<Person> listp= FXCollections.observableArrayList();
    	for(Person a:mainApp.getPersonData()){
    		String aIP=a.gettheIP();
    		if (a!=b&&aIP!=null&&aIP.equals(bsip))listp.add(a);
    	}
		return listp;
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
        String selectedPerson = telephones.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(getPersonByTel(selectedPerson));
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
    public Person getPersonByTel(String tel){
    for(Person a:mainApp.getPersonData()){
    	if(a.getinitTel()==tel) return a;
    	}
    return null;
    }
    public int indexByTime(LocalDateTime t){
        String text = t.format(formatterHours);
        int hi=0;
        for (String h:times){
        	if (text.equals(h))break;
        	hi++;
        }
    	return hi;
    }
    public static ObservableList<String> getTimes(){
    	return times;
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
        	String telPerson = initTelLabel.getText();
  	        clickedBtn.setText(telPerson); // prints the id of the button
            	log("setOnDragDropped("+de+")");
            }
       });
    }
    
    public void initGrid(){
        buildDaysOfWeek();
        buildTimes();
        buildCellGrid();
        buildCallDates();    
        addTimeLine();
    }
   
   
   public void addClickListeners(Button dgfh){
	   dgfh.setOnMouseClicked(new EventHandler<MouseEvent>() {

           @Override
           public void handle(MouseEvent event) {

        	   int i = GridPane.getColumnIndex(dgfh.getParent());
        	   int j = GridPane.getRowIndex(dgfh.getParent());
   	  

   	          String str = null;
   	            for(Node node : calendaar.getChildren()) {
   	                if(GridPane.getRowIndex(node) != null&& GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == i) {
   	                	str=((Label) node).getText();
   	                }
   	               if(GridPane.getRowIndex(node) != null&& GridPane.getRowIndex(node) == j && GridPane.getColumnIndex(node) == 0) {
   	            	   str= str+" "+((Label) node).getText();
   	            	  break;
   	            }}
   	          LocalDateTime dateTime = LocalDateTime.parse(str, formatter1);
   	          
   	          
   	         System.out.println(dateTime);
          	 String ButtText= dgfh.getText();
               MouseButton button = event.getButton();
               
               if(button==MouseButton.PRIMARY){
              	 if(ButtText==""&&initTelLabel.getText()!=""){
        	  getPersonByTel(initTelLabel.getText()).setnextCall(dateTime);
              LocalDate tempDatea=dateTime.toLocalDate();
              timePick.getSelectionModel().select(indexByTime(dateTime));
              if (tempDatea!=null)DatePick.setValue(tempDatea);
        	      dgfh.setText(initTelLabel.getText());  
           	   calendaar.getChildren().clear();
           	   initGrid();
              	 }
              	 else showPersonDetails(ButtText);
               }else if(button==MouseButton.SECONDARY){
            	   if(ButtText!=""){
            		   getPersonByTel(ButtText).setnextCall(null);
            		   if (ButtText==initTelLabel.getText()){
            			   DatePick.setValue(null);
            			   timePick.getSelectionModel().select(null);
            		   }
            		  
            		   calendaar.getChildren().clear();
                   	   initGrid();
            	   }

               }else if(button==MouseButton.MIDDLE){
               //as
               }
       

      	    }
      	});
   }
   public void buildDaysOfWeek(){
	    for (int i = 0;i<5;i++){
	    	
	    	LocalDate tempDay=currentWeekMonday.plus(i, ChronoUnit.DAYS);
	    	  String text = tempDay.format(formatter);
	    	  Label sr = new Label(text);
	    	  GridPane.setHalignment(sr, HPos.CENTER);
	    	  calendaar.add(sr, i+1, 1);
	    	  Label sr1 = new Label(dayNames.get(i));
	    	  GridPane.setHalignment(sr1, HPos.CENTER);
	    	  calendaar.add(sr1, i+1, 0);
	    }
	}
   public void buildTimes(){
	    for (int j = 2;j<23;j++){
	      	 Label sr = new Label(times.get(j-2));
	      	 calendaar.add(sr, 0, j);
	    }
	}

	public void buildCellGrid(){

	    for (int j = 2;j<23;j++){

	        for (int i = 1;i<6;i++){
	       	 
	            Button dgfh = new Button(); //can add text
	            dgfh.setStyle(style1);
	            if ((j & 1) == 1 )dgfh.setStyle(style2);
	            dgfh.setMaxWidth(Double.MAX_VALUE);
	            dgfh.setMaxHeight(Double.MAX_VALUE);
	            
	            HBox hbox1 = new HBox();
	            HBox.setHgrow(dgfh, Priority.ALWAYS);
	            hbox1.getChildren().add(dgfh); 
	            calendaar.add(hbox1, i, j);
	            addDnDlisteners(dgfh);
	            addClickListeners(dgfh);
	   }}
	}
	
	public void buildCallDates(){
	    newListWeek();
		for (Person p:telsToCall){
			
			LocalDate temp = p.getnextCall().toLocalDate();
			LocalTime temp1 = p.getnextCall().toLocalTime();
			int i = (int) currentWeekMonday.until(temp, ChronoUnit.DAYS);
			int j =  (int) (LocalTime.of(9,0).until(temp1, ChronoUnit.HOURS)*2);
			if (temp1.getMinute()==30)j++;
			HBox hbox1 = (HBox) getNodeByRowColumnIndex(i+1,j+2);
            ObservableList<Node> childrens=hbox1.getChildren();
            Button dgfh = (Button) childrens.get(childrens.size()-1); 
			dgfh.setText(p.getinitTel());

            dgfh.setMaxWidth(Double.MAX_VALUE);
            dgfh.setMaxHeight(Double.MAX_VALUE);
     	   Button newButt = new Button();
           newButt.setStyle(style1);
           if ((j & 1) == 1 )newButt.setStyle(style2);
           newButt.setMaxWidth(40);
           newButt.setMinWidth(15);
           newButt.setMaxHeight(Double.MAX_VALUE);
           HBox.setHgrow(dgfh, Priority.ALWAYS);
           HBox.setHgrow(newButt, Priority.ALWAYS);
           hbox1.getChildren().add(newButt);
            addClickListeners(newButt);
		}
		
	}
	public void addTimeLine(){
		int i = (int) currentWeekMonday.until(LocalDate.now(), ChronoUnit.DAYS);
		if (i<6&&i>-1){
		if(LocalTime.now().isAfter(LocalTime.of(9,0))&&LocalTime.now().isBefore(LocalTime.of(19,0))){
	
		Line line = new Line();
		line.setEndX(261);
		line.setStrokeWidth(2);
		GridPane.setValignment(line, VPos.TOP);
		
		int j =  (int) (LocalTime.of(9,0).until(LocalTime.now(), ChronoUnit.HOURS)*2);
		if (LocalTime.now().getMinute()>=30)j++;
	//	GridPane.setRowSpan(line,GridPane.REMAINING);
	//	double j =  (double) ChronoUnit.MINUTES.between(LocalTime.of(9,0), LocalTime.now());
	//	double j =  (double) (LocalTime.now().until(LocalTime.of(18,0), ChronoUnit.MINUTES));
		double k=LocalTime.now().getMinute();
		if (LocalTime.now().getMinute()>=30)k=k-30;
	//	k =  Utils.scale(k, 0, LocalTime.now().getMinute(), 0, 32);
		System.out.println(1+k*0.85);
		line.setTranslateY(k*0.85);
		calendaar.add(line, i+1, j+2);
		}
		}
	}
	@FXML
	private void getBack(){
		currentWeekMonday= currentWeekMonday.minus(1, ChronoUnit.WEEKS);
		 calendaar.getChildren().clear();
		initGrid();
		//newListWeek();
	}
	@FXML
	private void getForw(){
		currentWeekMonday= currentWeekMonday.plus(1, ChronoUnit.WEEKS);
		 calendaar.getChildren().clear();
		initGrid();
		//newListWeek();
	}
	
	@FXML
	private void getToday(){
		currentWeekMonday= LocalDate.now().with(DayOfWeek.MONDAY);
		 calendaar.getChildren().clear();
		initGrid();
	}
	@FXML
	private void addNewDateButt(){
		System.out.println(initTelLabel.getText()+"   "+DatePick.getValue()+"   "+ timePick.getSelectionModel().getSelectedItem());
		if(initTelLabel.getText()!=""&&DatePick.getValue()!=null&&timePick.getSelectionModel().getSelectedItem()!=null){
			LocalTime time1 = LocalTime.parse(timePick.getSelectionModel().getSelectedItem(),formatterHours);
			getPersonByTel(initTelLabel.getText())
			.setnextCall(LocalDateTime.of(DatePick.getValue(), time1));
		}
		calendaar.getChildren().clear();
		initGrid();
	}
	@FXML
	private void fireSearch(){
		String searcht = SearchField.getText();
		for(Person p:mainApp.getPersonData()){
			if(p.getinitTel().equals(searcht)){
				showPersonDetails(p.getinitTel());
				break;
			}
		}
		SearchField.setText("");
	}
	
	@FXML
	private void copyToCBoard(){
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(initTelLabel.getText());
   //     content.putHtml("<b>Some</b> text");
        clipboard.setContent(content);

	}
	@FXML
	private void SaveAllButt(){
		Person saved=getPersonByTel(initTelLabel.getText());
		System.out.println(commentsField.getText());
		saved.setcomments(commentsField.getText());
		firstNameLabel.getText();
	}
	public void newListWeek(){
		telsToCall.clear();
		LocalDateTime tempDat;
		LocalDate tempDate = null;
		LocalDate WeekFriday=currentWeekMonday.with(DayOfWeek.FRIDAY);

		for(Person p:mainApp.getPersonData()){
			tempDat=p.getnextCall();
			if (tempDat!=null){
				tempDate= tempDat.toLocalDate();

			if(tempDate.isAfter(currentWeekMonday)&&tempDate.isBefore(WeekFriday)||tempDate.isEqual(currentWeekMonday)||tempDate.isEqual(WeekFriday))telsToCall.add(p);
			}
			
		}
	}
	 public Node getNodeByRowColumnIndex(final int column,final int row) {
	        Node result = null;
	        ObservableList<Node> childrens = calendaar.getChildren();
	        for(Node node : childrens) {
	            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
	                result = node;
	                break;
	            }
	        }
	        return result;
	    }
/*	 scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		        System.out.println("Width: " + newSceneWidth);
		    }
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		        System.out.println("Height: " + newSceneHeight);
		    }
		});*/
}