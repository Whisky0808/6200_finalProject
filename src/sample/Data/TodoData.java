package sample.Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoData {
    private static TodoData instance = new TodoData();
    private static String filename = "TodoListItems.json";

    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter formatter;

    public static TodoData getInstance() {
        return instance;
    }

    private TodoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void addTodoItem(TodoItem item) {
        todoItems.add(item);
    }

//    public void setTodoItems(List<TodoItem> todoItems) {
//        this.todoItems = todoItems;
//    }

    public void loadTodoItems() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Path path = Paths.get(filename);
        if (Files.exists(path)) {
            TodoItem[] itemsArray = mapper.readValue(Files.newBufferedReader(path), TodoItem[].class);
            todoItems = FXCollections.observableArrayList(itemsArray);

//            // 更新每个项目的状态
//            for (TodoItem item : todoItems) {
//                updateStatusBasedOnDate(item);
//            }
        } else {
            todoItems = FXCollections.observableArrayList();
        }
    }
    
//    private void updateStatusBasedOnDate(TodoItem item) {
//        LocalDate date = item.getDeadline();
//        if (date.equals(LocalDate.now())) {
//            item.setCategory("In Progress");
//        } else if (date.isBefore(LocalDate.now().plusWeeks(1))) {
//            item.setCategory("Waiting");
//        } else {
//            item.setCategory("Approved");
//        }
//    }


    public DateTimeFormatter getFormatter(){
        return formatter;
    }
    public void storeTodoItems() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Path path = Paths.get(filename);
        mapper.writeValue(Files.newBufferedWriter(path), todoItems);
    }

    public void deleteTodoItem(TodoItem item) {
        todoItems.remove(item);
    }

    public void autoChange() throws IOException{

    }


}
