package ch.makery.address.util;

import ch.makery.address.view.PersonOverviewController;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;




public class CalendarGrid{
	private GridPane calendaar;
	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
	  DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");
	  DateTimeFormatter formatterHours = DateTimeFormatter.ofPattern("H:mm");
	  String style1="-fx-padding: 0;-fx-background-radius: 0;-fx-background-color:  #FAFAFA;-fx-border-color:  #ADADAD;-fx-border-width: 0 0.3 0 0.3;";
	  String style2="-fx-padding: 0;-fx-background-color:  #EBF3FF;-fx-border-color:  #ADADAD;-fx-border-width: 0 0.3 0 0.3;";

	public CalendarGrid(GridPane calendar){
		calendaar=calendar;
        calendaar.setPadding(new Insets(5));
        calendaar.setHgap(0);
        calendaar.setVgap(0);
	}
public void buildDaysOfWeek(){
	LocalDate now = LocalDate.now();
	LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
    for (int i = 0;i<5;i++){
    	LocalDate tempDay=monday.plus(i, ChronoUnit.DAYS);
    	  String text = tempDay.format(formatter);
    	  System.out.println(text);
    	  Label sr = new Label(text);
    	  GridPane.setHalignment(sr, HPos.CENTER);
    	  calendaar.add(sr, i+1, 1);
    }
}

public void buildTimes(){
	ObservableList<String> times=PersonOverviewController.getTimes();
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
          //  PersonOverviewController.addDnDlisteners(dgfh);
            addClickListeners(dgfh);
   }}
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
           		 addNewButton(i,j, dgfh);
        	 
     	  getPersonByTel(initTelLabel.getText()).setnextCall(dateTime);
           LocalDate tempDatea=dateTime.toLocalDate();
           timePick.getSelectionModel().select(indexByTime(dateTime));
           if (tempDatea!=null)DatePick.setValue(tempDatea);
     	      dgfh.setText(initTelLabel.getText());  
           	 }
           	 else showPersonDetails(ButtText);
            }else if(button==MouseButton.SECONDARY){
           	// if (ButtText!="");

            }else if(button==MouseButton.MIDDLE){
            //as
            }
    

   	    }
   	});
}
public void addNewButton(int i, int j, Button dgfh){
	   
	   Button newButt = new Button();
    System.out.println("Column: " + i + " || Row: " + j);
    newButt.setStyle(style1);
    if ((j & 1) == 1 )newButt.setStyle(style2);
    
  dgfh.setMaxWidth(Double.MAX_VALUE);
  newButt.setMaxWidth(10);
  HBox.setHgrow(dgfh, Priority.ALWAYS);
  HBox.setHgrow(newButt, Priority.ALWAYS);
  newButt.setMaxWidth(Double.MAX_VALUE);
  newButt.setMaxHeight(Double.MAX_VALUE);
  HBox hbox1 = (HBox) dgfh.getParent();
  hbox1.getChildren().addAll(newButt); 
  addClickListeners(newButt);
}
}