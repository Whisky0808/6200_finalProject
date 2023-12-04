package sample;

import javafx.application.Platform;
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
import sample.Data.TodoData;

import java.awt.*;
import java.awt.event.ActionListener;
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
    private ImageView ToTray;

    @FXML
    private BorderPane window;

    @FXML
    private StackPane contentArea;

    Stage stage = null;

    private void systemTray(Stage s){
        try {
            Toolkit.getDefaultToolkit();
            URL imageUrl = Main.class.getResource("/sample/img/icon.png");
            Image image = Toolkit.getDefaultToolkit().getImage(imageUrl);
            PopupMenu trayMenu = new PopupMenu();
            MenuItem show = new MenuItem("show");
            MenuItem exit = new MenuItem("exit");
            SystemTray tray = SystemTray.getSystemTray();
            trayMenu.add(show);
            trayMenu.add(exit);
            TrayIcon trayIcon = new TrayIcon(image,"TaskManager",trayMenu);
            trayIcon.setImageAutoSize(true);
            show.addActionListener(actionListener -> {
                Platform.runLater(s::show);
                tray.remove(trayIcon);
            });
            exit.addActionListener(actionListener -> {
                System.out.println("click exit");
                System.exit(0);
            });
            tray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Exit.setOnMouseClicked(e -> {
            try{

                TodoData.getInstance().storeTodoItems();


            }catch (IOException exception){
                System.out.println(exception.getMessage());
            }
            System.exit(0);
        });
        Minimize.setOnMouseClicked(e -> {
            stage = (Stage) window.getScene().getWindow();
            stage.setIconified(true);
        });
        ToTray.setOnMouseClicked(e -> {
            Platform.runLater(() -> {
                if (SystemTray.isSupported()) {
                    stage = (Stage) window.getScene().getWindow();
                    systemTray(stage);
                    Platform.setImplicitExit(false);
                    stage.hide();
                } else {
                    System.exit(0);
                }
            });
        });



        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("View/Today6200.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);

        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Today(javafx.event.ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("View/Today6200.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        try{

            TodoData.getInstance().storeTodoItems();

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
