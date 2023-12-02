package sample.Controllers;



import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Data.OtherData;
import sample.Data.OtherItem;
import sample.Data.TodoData;
import sample.Data.TodoItem;

public class newTaskAdd implements Initializable {
	
	@FXML
	private ImageView Exit;
	
	@FXML
	private BorderPane window;
	
	@FXML
	private TextField Description;
	
	@FXML
	private TextArea DetailsArea;
	
	@FXML
	private DatePicker Deadline;
	
	@FXML
	private ComboBox<String> Categories;
	
	@FXML
	private JFXCheckBox hightPriority;
	
	@FXML
	private JFXCheckBox lowPriority;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		Exit.setOnMouseClicked(e->{
			Stage stage = (Stage) window.getScene().getWindow();
			stage.close();
			
		});
		Deadline.valueProperty().addListener(
				(observable, oldValue, newValue) ->{
					if (newValue.equals(LocalDate.now())) {
					    Categories.getItems().setAll("In Progress", "Someday", "Important");
					} else if (newValue.isBefore(LocalDate.now().plusDays(1))) {
					    Categories.getItems().setAll("Approved", "Someday", "Important");
					} else if (newValue.isBefore(LocalDate.now().plusDays(8))) {
					    Categories.getItems().setAll("Waiting", "Someday", "Important");
					} else {
					    Categories.getItems().setAll("");
					}
					
				});		
	}
	
	@FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) window.getScene().getWindow();
        stage.close();
    }
	
	@FXML 
	void ok(ActionEvent event) {
		Error();
		try {
			String priority = null;
			String shortDescription = Description.getText();
            String Details = DetailsArea.getText();
            String Category = Categories.getValue().toString();
            LocalDate deadValue = Deadline.getValue();
            boolean Hpriority = hightPriority.isSelected();
            boolean Lpriority = lowPriority.isSelected();
            if(Hpriority) {
            	 priority = "height";
     
            }else if(Lpriority) {
            	 priority = "low";
            }
            if(Category.equals("Waiting") || Category.equals("In Progress")) {
            	TodoData.getInstance().addTodoItem(new TodoItem(shortDescription, Details, Category, deadValue,priority));           
            }else if(Category.equals("Important") || Category.equals("Someday")) {
            	OtherData.getInstance().addOtherItem(new OtherItem(shortDescription, Details, Category, deadValue,priority));
            }
            
            clearText();
            
            
		}catch(Exception e) {
			Alert dialog = new Alert(Alert.AlertType.ERROR,"There was an error your submission. Please retry",ButtonType.OK);
            dialog.show();
		}
		
		
	}
	


	public void clearText() {
		 Description.clear();
	     DetailsArea.clear();
	     Deadline.getEditor().clear();
	     Categories.getItems().clear();
	     lowPriority.setSelected(false);
	     hightPriority.setSelected(false);
	     
	     
	    
	     
	}
	public void Error() {
		if(
				Deadline.getValue() == null ||
				Categories.getValue() == null ||
				Description.getText().isEmpty() ||
				DetailsArea.getText().isEmpty() ||
				!hightPriority.isSelected() && !lowPriority.isSelected()
				
				) {
			Alert dialog = new Alert(Alert.AlertType.WARNING,"There were blanks here. Please fill the form",ButtonType.OK);
            dialog.show();
			
		}
	}
	

}
