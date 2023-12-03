package sample.Controllers;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.Data.TodoData;
import sample.Data.TodoItem;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class Today6200 implements Initializable {
    private SortedList<TodoItem> sortedView;
    private Predicate<TodoItem> FilterToday;
    private FilteredList<TodoItem> TodayItems;
    @FXML
    private ListView<TodoItem> TitleView;
    @FXML
    private ListView<TodoItem> PriorityView;
    private TodoItem selectedItem=null;





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
    @Override

    public void initialize(URL location, ResourceBundle resources) {
        FilterToday=(TodoItem)-> TodoItem.getDeadline().equals(LocalDate.now());
        TodayItems=new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), FilterToday);

        TitleView.setItems(TodayItems);
        PriorityView.setItems(TodayItems);


        PriorityView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
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
                            setStyle("-fx-background-color:"+getPriorityColor(item)+";");
                        }
                    }
                };
            }
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
                            // 创建一个水平盒子并添加图像和文本
                            HBox hBox = new HBox(imageView1, label, spacer, imageView2);
                            hBox.prefWidthProperty().bind(Bindings.createDoubleBinding(() ->
                                    TitleView.widthProperty().get() - 20, TitleView.widthProperty()));
                            hBox.setSpacing(10);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
    }

}