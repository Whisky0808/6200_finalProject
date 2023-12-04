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
        Menu Category=new Menu("Set Category");
        MenuItem option1 = new MenuItem("High");
        MenuItem option2 = new MenuItem("Low");
        MenuItem option3= new MenuItem("Daily");
        MenuItem option4 = new MenuItem("Work");
        MenuItem option5 = new MenuItem("Study");
        MenuItem option6 = new MenuItem("Others");
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
            bindList.getSelectionModel().getSelectedItem().setPriority("High");
            control.refresh();
        });
        option2.setOnAction(e->{
            bindList.getSelectionModel().getSelectedItem().setPriority("Low");
            control.refresh();
        });
        option3.setOnAction(e->{
            bindList.getSelectionModel().getSelectedItem().setCategory("Daily");
            TodoData.getInstance().updateDailyDDl(TodoData.getInstance().getTodoItems());
            control.refresh();
        });
        option4.setOnAction(e->{
            bindList.getSelectionModel().getSelectedItem().setCategory("Work");
            control.refresh();
        });
        option5.setOnAction(e->{
            bindList.getSelectionModel().getSelectedItem().setCategory("Study");
            control.refresh();
        });
        option6.setOnAction(e->{
            bindList.getSelectionModel().getSelectedItem().setCategory("Others");
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
        menu.getItems().addAll(EditDate,Priority,Category, deleteItem, checkMenuItem);
        Priority.getItems().addAll(option1,option2);
        Category.getItems().addAll(option3,option4,option5,option6);
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