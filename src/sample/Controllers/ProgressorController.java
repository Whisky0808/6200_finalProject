package sample.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.Controllers.CustomProgressIndicator;
import sample.Data.TodoItem;

public class ProgressorController implements Initializable{
	private TodoItem item;
	@FXML
	private Pane ParentPane;
	@FXML
	private ProgressBar progressorBar;
	StackPane tick;
	@FXML
	private ProgressIndicator progressorIndicator;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private LocalDate currentDate = LocalDate.now();
	private CustomProgressIndicator customProgressIndicator;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// TODO Auto-generated method stub
		 customProgressIndicator = new CustomProgressIndicator();

		int index = ParentPane.getChildren().indexOf(progressorIndicator);
		ParentPane.getChildren().set(index, customProgressIndicator);

		progressorBar.setProgress(1);
        customProgressIndicator.setProgress(1,null);
		customProgressIndicator.setLayoutX(139.0);
		customProgressIndicator.setLayoutY(21.0);
		customProgressIndicator.setPrefHeight(60.0);
		customProgressIndicator.setPrefWidth(118.0);
		customProgressIndicator.getStylesheets().add(getClass().getResource("/sample/Style/style.css").toExternalForm());
	}
	public void setitem(TodoItem i){
		this.item=i;
	}
	public void updateProgress(LocalDate deadline) {

		long daysBetween = ChronoUnit.DAYS.between(currentDate, deadline);
		
		// initialize the progress
//		double progress; 
		
		double progress = daysBetween >= 0 ? 1.0:0.0;
		if (this.item!=null){
			if(tick==null) {
				tick = (StackPane) ParentPane.lookup("#myTick");
			}
			if(this.item.getCompleted()){
				tick.getStyleClass().remove("not-complete");
				tick.getStyleClass().remove("complete");
				tick.getStyleClass().add("complete");

			}
			else{
				tick.getStyleClass().remove("not-complete");
				tick.getStyleClass().remove("complete");
				tick.getStyleClass().add("not-complete");
			}

		}
		if(progress==0.0) {
       	 progressorBar.setStyle("-fx-accent: red;"); // Set the progress bar color to red
         customProgressIndicator.setStyle("-fx-progress-color: red;"); // Set the progress indicator color to red
         progressorBar.setProgress(1);
         customProgressIndicator.setProgress(1,this.item);

       }
		if(daysBetween>0) {
			 long totalDays = ChronoUnit.DAYS.between(item.getCreateDate(), deadline);
	         long remainingDays = ChronoUnit.DAYS.between(currentDate, deadline);
	         progress =  1.0 - (double) remainingDays / (double) totalDays;
	         //System.out.print("totalDays"+totalDays+"remainingDays"+remainingDays);
	         progressorBar.setProgress(progress);
	         customProgressIndicator.setProgress(progress,this.item);
		}
		
        
        
		
	}
	
	

}
