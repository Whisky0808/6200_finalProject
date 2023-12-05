package sample.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Data.TodoData;
import sample.Data.TodoItem;

public class addTaskOnce implements Initializable {
	
	@FXML
	private ImageView Exit;
	
	@FXML
	private BorderPane window;
	
	@FXML
	private TextField Description;
	
	@FXML
	private TextArea DetailsArea;
	
	@FXML 
	void ok(ActionEvent event) {
		Error();
		try {
			String priority = "High";
			String shortDescription = Description.getText();
            String Details = DetailsArea.getText();
            String Category = "Others";
            LocalDate deadValue = LocalDate.now();
      
            
            
            TodoData.getInstance().addTodoItem(new TodoItem(shortDescription, Details, Category, deadValue,priority, false));           
           
            Stage stage = (Stage) window.getScene().getWindow();
            stage.close();
            
            
            
            
		}catch(Exception e) {
			Alert dialog = new Alert(Alert.AlertType.ERROR,"There was an error your submission. Please retry",ButtonType.OK);
            dialog.show();
		}
		
		
	}
	@FXML
	void reset(ActionEvent event) {
		 Description.clear();
	     DetailsArea.clear();
	    
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Exit.setOnMouseClicked(e->{
			Stage stage = (Stage) window.getScene().getWindow();
			stage.close();
			
		});
		
	}
	public void Error() {
		if(
				
				Description.getText().isEmpty() ||
				DetailsArea.getText().isEmpty() 
		
				
				) {
			Alert dialog = new Alert(Alert.AlertType.WARNING,"There were blanks here. Please fill the form",ButtonType.OK);
            dialog.show();
			
		}
	}

}
