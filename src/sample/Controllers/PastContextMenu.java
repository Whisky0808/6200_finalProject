package sample.Controllers;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;

import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import sample.Data.TodoData;
import sample.Data.TodoItem;

public class PastContextMenu extends MycontexMenu<TodoItem> {
	private PastController control;
	
	
	PastContextMenu(ListView<TodoItem> l, PastController instance){
        super();
        control=instance;
        bindList=l;
        addmenu();
    }
	
	@Override
	public void addmenu() {
		// TODO Auto-generated method stub
		 menu = new ContextMenu();
	       
	        MenuItem deleteItem = new MenuItem("Delete");
	        deleteItem.setOnAction(e->{
	            TodoData.getInstance().deleteTodoItem(bindList.getSelectionModel().getSelectedItem());
	            control.refresh();
	        });
	        
	        CheckMenuItem checkMenuItem = new CheckMenuItem("Complete");
	        checkMenuItem.setOnAction(e->{
	            if (!(bindList.getSelectionModel().getSelectedItem().getCompleted())) {
	                checkMenuItem.setSelected(true);
	                bindList.getSelectionModel().getSelectedItem().setCompleted(true);
	                control.refresh();
	            }
	            else{
	                checkMenuItem.setSelected(false);
	                bindList.getSelectionModel().getSelectedItem().setCompleted(false);
	                control.refresh();
	            }
	            control.refresh();
	        });
	        menu.getItems().addAll(deleteItem, checkMenuItem);
	   
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
	            foundItem.setSelected(bindList.getSelectionModel().getSelectedItem().getCompleted());
	        }
	        menu.show(bindList,e.getScreenX(),e.getScreenY());
	    }
	

}
