package sample;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Data.OtherData;
import sample.Data.TodoData;

public class Testmain extends Application{
	double x,y = 0;
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("View/sample.fxml"));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		//calculate the primaryStage position
		root.setOnMousePressed(e->{
			x = e.getSceneX();
			y = e.getSceneY();
		});
		root.setOnMouseDragged(e->{
			primaryStage.setX(e.getSceneX()-x);
			primaryStage.setY(e.getSceneY()-y);
		});
		primaryStage.setScene(new Scene(root,600,400));
		 primaryStage.show();
		
		
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		launch(args);
//
//	}
	@Override
    public void stop() throws Exception {
        try {
            TodoData.getInstance().storeTodoItems();
            OtherData.getInstance().storeOtherItems();

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws Exception {
        try {
            
            TodoData.getInstance().loadTodoItems();
            OtherData.getInstance().loadOtherItems();

        } catch(IOException e) {
            throw e;
        }
    }

}
