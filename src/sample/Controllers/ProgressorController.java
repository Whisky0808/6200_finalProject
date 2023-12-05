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
import javafx.scene.paint.Color;

public class ProgressorController implements Initializable{
	
	@FXML
	private ProgressBar progressorBar;
	
	@FXML
	private ProgressIndicator progressorIndicator;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private LocalDate currentDate = LocalDate.now();


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		// set 0.5（50%）
		progressorBar.setProgress(1);
        progressorIndicator.setProgress(1);
		
	}
	
	public void updateProgress(LocalDate deadline) {
		long daysBetween = ChronoUnit.DAYS.between(currentDate, deadline);
		
		// initialize the progress
//		double progress; 
		
		double progress = daysBetween >= 0 ? 1.0:0.0;
		
		if(progress==0.0) {
       	 progressorBar.setStyle("-fx-accent: red;"); // Set the progress bar color to red
         progressorIndicator.setStyle("-fx-progress-color: red;"); // Set the progress indicator color to red
         progressorBar.setProgress(1);
         progressorIndicator.setProgress(1);

       }
		if(daysBetween>0) {
			 long totalDays = ChronoUnit.DAYS.between(currentDate, deadline.plusDays(1));
	         long remainingDays = ChronoUnit.DAYS.between(currentDate, deadline);
	         progress =  1.0 - (double) remainingDays / (double) totalDays;
	         //System.out.print("totalDays"+totalDays+"remainingDays"+remainingDays);
	         progressorBar.setProgress(progress);
	         progressorIndicator.setProgress(progress);
		}
		
        
        
		
	}
	
	

}
