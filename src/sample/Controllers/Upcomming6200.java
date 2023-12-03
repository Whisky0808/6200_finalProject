package sample.Controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import sample.Data.TodoData;
import sample.Data.TodoItem;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

public class Upcomming6200 implements Initializable {
    private int showMode;
    private FutureContexMenu menu;
    private SortedList<TodoItem> sortedView;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Predicate<TodoItem> FilterFuture;
    private FilteredList<TodoItem> FutureItems;
    @FXML
    private ListView<TodoItem> TitleView;
    @FXML
    private ListView<TodoItem> DeadlineView;
    @FXML
    private TextArea DescriptionView;
    @FXML Label CreateTimeLabel;
    @FXML Label CategoryLabel;
    private final ObjectProperty<TodoItem> selectedItem=new SimpleObjectProperty<>(null);

    @FXML public void showMenu(ContextMenuEvent event){
        if(TitleView.getSelectionModel().getSelectedItem()!=null) {
            menu.show(event);
        }
    }

    @FXML
    public void TitleViewClick(MouseEvent e){
        setSelected(TitleView.getSelectionModel().getSelectedItem());
    }
    private void setSelected(TodoItem item){
        selectedItem.set(item);
    }
    private String getcheckUrl(TodoItem item){
        return "";
    }
    private String getCategoryUrl(TodoItem item){
        return "";
    }
    private String getPriorityColor(TodoItem item){
        Map<String, String> map = new HashMap<String, String>() {{
            put("high", "red");
            put("low", "green");
            put("medium","yellow");
        }};
        return map.get(item.getPriority());
    }

    private void DescriptionInitialize(){
        DescriptionView.setEditable(false);
        DescriptionView.setWrapText(true);
        DescriptionView.textProperty().bind(
                Bindings.createStringBinding(() -> {
                    TodoItem todo = selectedItem.get();
                    return todo != null ?  todo.getDetails(): "";
                }, selectedItem)
        );
    }
    private void TitleViewInitialize(){
        TitleView.setItems(FutureItems);
        TitleView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                setSelected(null);
            }
        });
        TitleView.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            menu.hide();
            TitleView.getSelectionModel().clearSelection();
            selectedItem.set(null);
        });
        TitleView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {

                return new ListCell<TodoItem>(){
                    private final ImageView imageView1 = new ImageView();
                    private final ImageView imageView2 = new ImageView();
                    private final Label label = new Label();
                    @Override
                    protected void updateItem(TodoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        }  else {
                            imageView1.setImage(new Image("/sample/img/close.png", 20, 20, true, true));
                            label.setText(item.getShortDescription());
                            imageView2.setImage(new Image("/sample/img/close.png", 20, 20, true, true));
                            Region spacer = new Region();
                            HBox.setHgrow(spacer, Priority.ALWAYS);
                            HBox hBox = new HBox(imageView1, label, spacer, imageView2);
                            hBox.prefWidthProperty().bind(Bindings.createDoubleBinding(() ->
                                    TitleView.widthProperty().get() - 20, TitleView.widthProperty()));
                            hBox.setSpacing(10);
                            setGraphic(hBox);
                            setStyle("-fx-background-color:"+getPriorityColor(item)+";");
                        }
                    }
                };
            }
        });
    }
    private void DeadlineViewInitialize(){
        DeadlineView.setItems(FutureItems);
        DeadlineView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {
                return new ListCell<TodoItem>(){
                    @Override
                    protected void updateItem(TodoItem item,boolean empty){
                        super.updateItem(item,empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        }  else {
                            setText(item.getDeadline().format(formatter));

                        }
                    }
                };
            }
        });
    }
    private void LabelInitialize(){
        CreateTimeLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            TodoItem todo = selectedItem.get();
            return todo != null ?  todo.getCreateDate().format(formatter): "";
        }, selectedItem));
        CategoryLabel.textProperty().bind(
                Bindings.createStringBinding(() -> {
                    TodoItem todo = selectedItem.get();
                    return todo != null ?  todo.getCategory(): "";
                }, selectedItem)
        );
    }
    private void LoadTodayItems(){
        FilterFuture=(TodoItem)-> TodoItem.getDeadline().isAfter(LocalDate.now());
        FutureItems=new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), FilterFuture);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showMode=0;
        menu=new FutureContexMenu(TitleView,this);
        LoadTodayItems();
        DescriptionInitialize();
        TitleViewInitialize();
        DeadlineViewInitialize();
        LabelInitialize();
    }
    public void refresh(){
        LoadTodayItems();
        TitleView.setItems(FXCollections.observableArrayList());
        DeadlineView.setItems(FXCollections.observableArrayList());
        if(showMode==0) {
            TitleView.setItems(FutureItems);
            DeadlineView.setItems(FutureItems);
        }

    }
}