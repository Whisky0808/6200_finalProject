package sample.Controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Callback;
import sample.Data.TodoData;
import sample.Data.TodoItem;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

public class Today6200 implements Initializable {
    String css = this.getClass().getResource("/sample/Style/ShowTask.css").toExternalForm();
    private int showMode;
    private TodayContexMenu menu;
    private SortedList<TodoItem> sortedView;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Predicate<TodoItem> FilterToday;
    int startindex;
    private FilteredList<TodoItem> TodayItems;
    private FilteredList<TodoItem> ShowItems;

    @FXML
    private ListView<TodoItem> TitleView;
    @FXML
    private ListView<TodoItem> PriorityView;
    @FXML
    private TextArea DescriptionView;
    @FXML Label CreateTimeLabel;
    @FXML Label CategoryLabel;
    @FXML Polygon PrevPage;
    @FXML Polygon NextPage;
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

    @FXML
    public void ToNextPage(MouseEvent e){
        int tar=startindex+17;
        if(tar>=TodayItems.size()){
            return;
        }
        else{
            startindex=tar;
        }
        refresh();
    }
    @FXML void ToPrevPage(MouseEvent e){
        startindex= Math.max(startindex - 17, 0);
        refresh();
    }
    private void setSelected(TodoItem item){
        selectedItem.set(item);
    }

    private String getcheckUrl(TodoItem item){

        if (item.isSelected()){
            return "/sample/img/check.png";
        }
        else{
            return "/sample/img/close.png";
        }
    }
    private String getCategoryUrl(TodoItem item){
        switch (item.getCategory()) {
            case "Daily" :
                return "/sample/img/Daily.png";
            case "Work" :
                return "/sample/img/Work.png";

            case "Study" :
                return "/sample/img/Study.png";

            case "Others" :
                return "/sample/img/Other.png";
        }
        return "/sample/img/notfind.png";
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
        TitleView.getStylesheets().add(css);
        TitleView.addEventFilter(ScrollEvent.SCROLL, Event::consume);
        TitleView.setItems(ShowItems);
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
                            setBackground(null);
                        }  else {
                            imageView1.setImage(new Image(getCategoryUrl(item), 22, 22, true, true));
                            label.setText(item.getShortDescription());
                            imageView2.setImage(new Image(getcheckUrl(item), 22, 22, true, true));
                            Region spacer = new Region();
                            HBox.setHgrow(spacer, Priority.ALWAYS);
                            HBox hBox = new HBox(imageView1, label, spacer, imageView2);
                            hBox.prefWidthProperty().bind(Bindings.createDoubleBinding(() ->
                                    TitleView.widthProperty().get() - 20, TitleView.widthProperty()));
                            hBox.setSpacing(10);
                            setGraphic(hBox);
                            selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                                if (isNowSelected) {
                                    setBackground(new Background(new BackgroundFill(Color.web("#0097b2"), CornerRadii.EMPTY, Insets.EMPTY)));
                                } else {
                                    setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                                }
                            });
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
                cell.setPrefHeight(30);
                return cell;
            }
        });
    }
    private void PriorityViewInitialize(){
        PriorityView.getStylesheets().add(css);
        PriorityView.addEventFilter(ScrollEvent.SCROLL, Event::consume);
        PriorityView.setItems(ShowItems);
        PriorityView.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);
        PriorityView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {
                return new ListCell<TodoItem>(){
                    @Override
                    protected void updateItem(TodoItem item,boolean empty){
                        super.updateItem(item,empty);
                        setPrefHeight(30);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                            setBackground(null);
                        }  else {
                            LinearGradient linearGradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                                    new Stop[]{

                                            new Stop(0.8, Color.TRANSPARENT),
                                            new Stop(1, getPriorityColor(item))
                                    });

                            setText(item.getPriority());
                            setBackground(new Background(new BackgroundFill(linearGradient, CornerRadii.EMPTY, Insets.EMPTY)));
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
        FilterToday=(TodoItem)-> TodoItem.getDeadline().equals(LocalDate.now());
        TodayItems=new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), FilterToday);
    }
    private void LoadShowItems(){
        ShowItems = new FilteredList<TodoItem>(TodayItems, s -> {
            int index = TodayItems.indexOf(s);
            return index >= startindex && index < Math.min(startindex + 17, TodayItems.size());
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startindex=0;
        showMode=0;
        LoadTodayItems();
        LoadShowItems();
        menu=new TodayContexMenu(TitleView,this);
        DescriptionInitialize();
        TitleViewInitialize();
        PriorityViewInitialize();
        LabelInitialize();
    }
    public void refresh(){
        LoadTodayItems();
        LoadShowItems();
        TitleView.setItems(FXCollections.observableArrayList());
        PriorityView.setItems(FXCollections.observableArrayList());
        if(showMode==0) {
            TitleView.setItems(ShowItems);
            PriorityView.setItems(ShowItems);
        }

    }
}