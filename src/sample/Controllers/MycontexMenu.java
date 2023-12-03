package sample.Controllers;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

public abstract class MycontexMenu <T>{
    protected ContextMenu menu;
    protected ListView<T> bindList;
    public ContextMenu getMenu(){
        return menu;
    }
    public void show(ContextMenuEvent e){
        menu.show(bindList,e.getScreenX(),e.getScreenY());
    }
    public void hide(){
        menu.hide();
    }
    public abstract void addmenu();
}