package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Data.OtherData;
import sample.Data.TodoData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dashboard implements Initializable {

    @FXML
    private ImageView Exit;

    @FXML
    private ImageView Minimize;

    @FXML
    private ImageView Maximize;

    @FXML
    private BorderPane window;

    @FXML
    private StackPane contentArea;

    Stage stage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Exit.setOnMouseClicked(e -> {

            try{

                TodoData.getInstance().storeTodoItems();
                OtherData.getInstance().storeOtherItems();


            }catch (IOException exception){
                System.out.println(exception.getMessage());
            }
            System.exit(0);

        });
        Minimize.setOnMouseClicked(e -> {
            stage = (Stage) window.getScene().getWindow();
            stage.setIconified(true);
        });
        Maximize.setOnMouseClicked(e -> {
            stage = (Stage) window.getScene().getWindow();
            if(stage.isMaximized())
                stage.setMaximized(false);
            else
                stage.setMaximized(true);
        });



        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("View/Upcomming6200.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);

        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Inbox(javafx.event.ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("View/Inbox.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }

    @FXML
    private void Today(javafx.event.ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("View/Today6200.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        try{

            TodoData.getInstance().storeTodoItems();
            OtherData.getInstance().storeOtherItems();

        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    private void Upcomming(javafx.event.ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("View/Upcomming6200.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        try{
            TodoData.getInstance().storeTodoItems();
            OtherData.getInstance().storeOtherItems();
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    private void Previous (javafx.event.ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("View/PreviousTask.fxml"));
        System.out.print("Previous here");
        
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        try{
            TodoData.getInstance().storeTodoItems();
            OtherData.getInstance().storeOtherItems();
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    private void Someday(javafx.event.ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("View/progressorController.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        try{
            TodoData.getInstance().storeTodoItems();
            OtherData.getInstance().storeOtherItems();
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }

    }

    @FXML
    private void Trash(javafx.event.ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("View/Trash.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        try{

            TodoData.getInstance().storeTodoItems();
            OtherData.getInstance().storeOtherItems();

        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }
    
    @FXML
    void Addonce (ActionEvent event) throws Exception {
    	addOncePage();
    }
    
    

    @FXML
    void Addnew(ActionEvent event) throws Exception {
        Addnewpage();
    }
    double x,y = 0;
    public void addOncePage() throws Exception{
    
    	Parent root = FXMLLoader.load(getClass().getResource("View/newQuickTask.fxml"));
    	Stage stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(e -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });

        //move around here
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
        });
        stage.setScene(new Scene(root));
        stage.show();
    	
    }
    public void Addnewpage()throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/newTask.fxml"));
        Stage stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        //move around here
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.setScene(new Scene(root));
        stage.show();
    }



}
