package sample.Controllers;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Data.TodoData;
import sample.Data.TodoItem;

import java.time.LocalDate;

public class FutureContexMenu extends MycontexMenu<TodoItem>{
    private Upcomming6200 control;
    FutureContexMenu(ListView<TodoItem> l, Upcomming6200 instance){
        super();
        control=instance;
        bindList=l;
        addmenu();
    }
    @Override
    public void addmenu() {
        menu = new ContextMenu();
        Menu Priority=new Menu("Set Priority");
        MenuItem option1 = new MenuItem("high");
        MenuItem option2 = new MenuItem("low");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem EditDate = new MenuItem("EditDeadline");
        EditDate.setOnAction(e->{
            Stage popup = new Stage();
            popup.setTitle("Edit");
            DatePicker datePicker = new DatePicker();
            datePicker.setOnAction(event -> {
                LocalDate selectedDate = datePicker.getValue();
                TodoItem selectedItem = bindList.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    selectedItem.setDeadline(selectedDate);
                    control.refresh();
                }
                popup.close();
            });
            VBox layout = new VBox(20, datePicker);
            layout.setPrefHeight(40);
            layout.setPrefWidth(250);
            Scene scene = new Scene(layout);
            popup.setScene(scene);
            popup.show();
        });
        deleteItem.setOnAction(e->{
            TodoData.getInstance().deleteTodoItem(bindList.getSelectionModel().getSelectedItem());
            control.refresh();
        });
        option1.setOnAction(e->{
            bindList.getSelectionModel().getSelectedItem().setPriority("high");
            control.refresh();
        });
        option2.setOnAction(e->{
            bindList.getSelectionModel().getSelectedItem().setPriority("low");
            control.refresh();
        });
        CheckMenuItem checkMenuItem = new CheckMenuItem("Complete");
        checkMenuItem.setOnAction(e->{
            if (!(bindList.getSelectionModel().getSelectedItem().isSelected())) {
                checkMenuItem.setSelected(true);
                bindList.getSelectionModel().getSelectedItem().setSelected(true);
                control.refresh();
            }
            else{
                checkMenuItem.setSelected(false);
                bindList.getSelectionModel().getSelectedItem().setSelected(false);
                control.refresh();
            }
            control.refresh();
        });
        menu.getItems().addAll(EditDate,Priority, deleteItem, checkMenuItem);
        Priority.getItems().addAll(option1,option2);
    }
    @Override
    public void show(ContextMenuEvent e){
        CheckMenuItem foundItem = null;
        for (MenuItem item : menu.getItems()) {
            if (item instanceof CheckMenuItem && "Complete".equals(item.getText())) {
                foundItem = (CheckMenuItem) item;
                break;
            }
        }
        if(foundItem!=null){
            foundItem.setSelected(bindList.getSelectionModel().getSelectedItem().isSelected());
        }
        menu.show(bindList,e.getScreenX(),e.getScreenY());
    }
}