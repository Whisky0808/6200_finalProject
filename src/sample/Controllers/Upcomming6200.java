package sample.Controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
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
    private Color getPriorityColor(TodoItem item){
        Map<String, Color> map = new HashMap<String, Color>() {{
            put("high", Color.RED);
            put("low", Color.GREEN);
            put("medium",Color.YELLOW);
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

                ListCell<TodoItem> cell= new ListCell<TodoItem>(){
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
                                    TitleView.widthProperty().get() - 50, TitleView.widthProperty()));
                            hBox.setSpacing(10);
                            setGraphic(hBox);
                            LinearGradient linearGradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                                    new Stop[]{

                                            new Stop(0.9, Color.TRANSPARENT),
                                            new Stop(1, getPriorityColor(item))
                                    });
                            setBackground(new Background(new BackgroundFill(linearGradient, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    }
                };
                cell.setOnDragDetected(event -> {
                    if (!cell.isEmpty()) {
                        SnapshotParameters snapshotParameters = new SnapshotParameters();
                        WritableImage snapshot = cell.snapshot(snapshotParameters, null);
                        Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent cc = new ClipboardContent();
                        cc.putString(String.valueOf(cell.getIndex()));
                        db.setContent(cc);
                        db.setDragView(snapshot);
                        event.consume();
                    }
                });
                cell.setOnDragOver(event -> {
                    if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });
                cell.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasString()) {
                        int draggedIndex = Integer.parseInt(db.getString());
                        int dropIndex = cell.getIndex();
                        if (draggedIndex != dropIndex) {
                            TodoItem data1=TitleView.getItems().get(draggedIndex);
                            TodoItem data2=TitleView.getItems().get(dropIndex);
                            int index1=TodoData.getInstance().getTodoItems().indexOf(data1);
                            int index2=TodoData.getInstance().getTodoItems().indexOf(data2);
                            TodoData.getInstance().getTodoItems().set(index1,data2);
                            TodoData.getInstance().getTodoItems().set(index2,data1);
                            refresh();
                        }
                    }
                    event.consume();
                });
                return cell;
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