package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import ch.makery.address.util.TimeUtil;

/**
 * Dialog to edit details of a person.
 * 
 * @author Marco Jakob
 */
public class PersonEditDialogController {

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


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        initTelField.setText(person.getinitTel());
        theIPField.setText(person.gettheIP());
        regionField.setText(person.getregion());
        promoCodeField.setText(Integer.toString(person.getpromoCode()));
        queryTimeField.setText(TimeUtil.format(person.getqueryTime()));
        queryDateField.setText(DateUtil.format(person.getqueryDate()));
        queryDateField.setPromptText("dd.mm.yyyy");
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
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setinitTel(initTelField.getText());
            person.settheIP(theIPField.getText());
            person.setregion(regionField.getText());
            person.setpromoCode(Integer.parseInt(promoCodeField.getText()));
            person.setqueryTime(TimeUtil.parse(queryTimeField.getText()));
            person.setqueryDate(DateUtil.parse(queryDateField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (initTelField.getText() == null || initTelField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (theIPField.getText() == null || theIPField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (regionField.getText() == null || regionField.getText().length() == 0) {
            errorMessage += "No valid region!\n"; 
        }

        if (promoCodeField.getText() == null || promoCodeField.getText().length() == 0) {
            errorMessage += "No valid promo code!\n"; 
        } else {
            // try to parse the promo code into an int.
            try {
                Integer.parseInt(promoCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid promo code (must be an integer)!\n"; 
            }
        }

        if (queryTimeField.getText() == null || queryTimeField.getText().length() == 0) {
            errorMessage += "No valid queryTime!\n"; 
        }

        if (queryDateField.getText() == null || queryDateField.getText().length() == 0) {
            errorMessage += "No valid queryDate!\n";
        } else {
            if (!DateUtil.validDate(queryDateField.getText())) {
                errorMessage += "No valid queryDate. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}