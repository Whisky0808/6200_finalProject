package sample.Controllers;
import javafx.scene.control.*;
import sample.Data.TodoData;
import sample.Data.TodoItem;
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
        deleteItem.setOnAction(e->{
            TodoData.getInstance().deleteTodoItem(bindList.getSelectionModel().getSelectedItem());
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
        menu.getItems().addAll(Priority, deleteItem, checkMenuItem);
        Priority.getItems().addAll(option1,option2);
    }
}