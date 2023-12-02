package sample.Data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OtherData {
    private static OtherData instance = new OtherData();
    private static String filename = "TodoListOtherItems.json";

    private ObservableList<OtherItem> OtherItems;
    private DateTimeFormatter formatter;

    public static OtherData getInstance() {
        return instance;
    }

    private OtherData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<OtherItem> getOtherItems() {
        return OtherItems;
    }

    public void addOtherItem(OtherItem item) {
        OtherItems.add(item);
    }

//    public void setOtherItems(List<OtherItem> OtherItems) {
//        this.OtherItems = OtherItems;
//    }

    public void loadOtherItems() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // 为处理Java 8日期和时间类型

        Path path = Paths.get(filename);
        // 检查文件是否存在
        if (!Files.exists(path)) {
            OtherItems = FXCollections.observableArrayList();
            return;
        }

        // 读取JSON文件并转换为OtherItem对象列表
        List<OtherItem> itemList = mapper.readValue(Files.newInputStream(path), new TypeReference<List<OtherItem>>(){});
        OtherItems = FXCollections.observableArrayList(itemList);
    }

    private void OtherItem(OtherItem in_progress) {
        return;
    }

    public void storeOtherItems() throws IOException {

    	ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Path path = Paths.get(filename);
        // 将OtherItems列表写入JSON文件
        mapper.writeValue(Files.newOutputStream(path), OtherItems);


    }

    public void deleteOtherItem(OtherItem item) {
        OtherItems.remove(item);
    }

    public void autoChange() throws IOException{

    }
}
