package sample.Controllers;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
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
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.Data.TodoData;
import sample.Data.TodoItem;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
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






    private String getCategoryUrl(TodoItem item){
        return "";
    }
    @Override

    public void initialize(URL location, ResourceBundle resources) {
        FilterToday=(TodoItem)-> TodoItem.getDeadline().equals(LocalDate.now());
        TodayItems=new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), FilterToday);

        TitleView.setItems(TodayItems);
        PriorityView.setItems(TodayItems);

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
                            imageView1.setImage(new Image("https://media-public.canva.com/k0FkQ/MAFeXuk0FkQ/1/tl.png", 32, 32, true, true));
                            label.setText(item.getShortDescription());
                            imageView2.setImage(new Image("https://media-public.canva.com/k0FkQ/MAFeXuk0FkQ/1/tl.png", 32, 32, true, true));

                            // 创建一个水平盒子并添加图像和文本
                            HBox hBox = new HBox(imageView1, label, imageView2);
                            hBox.setSpacing(10);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
    }

}