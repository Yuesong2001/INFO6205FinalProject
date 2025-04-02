package view;

import controller.NavigationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DataStore;
import model.Event;
import model.PriorityLevel;
import model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;
import java.util.UUID;


public class EventFormView extends VBox {

    /**
     * 构造函数 - 使用当前日期创建事件表单
     * @param user 当前登录的用户
     * @param dataStore 数据存储对象
     * @param navController 导航控制器
     */
    public EventFormView(User user, DataStore dataStore, NavigationController navController) {
        this(user, dataStore, navController, LocalDate.now()); // 调用另一个构造函数，使用当前日期
    }

    /**
     * 构造函数 - 使用指定日期创建事件表单
     * @param user 当前登录的用户
     * @param dataStore 数据存储对象
     * @param navController 导航控制器
     * @param initialDate 初始选中的日期
     */
    public EventFormView(User user, DataStore dataStore, NavigationController navController, LocalDate initialDate) {
        // 设置布局属性set layout
        setSpacing(20); // 设置子元素之间的间距
        setAlignment(Pos.CENTER); // 居中对齐
        setPadding(new Insets(30)); // 设置内边距
        setStyle("-fx-background-color: #f5f8ff;"); // 设置背景颜色为淡蓝色

        // 标题部分
        Label titleLabel = new Label("Create New Event");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;"); // 设置标题样式

        // 事件标题字段
        Label eventTitleLabel = new Label("Event Title:");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter event title"); // 设置提示文本
        titleField.setPrefWidth(300); // 设置首选宽度

        // 日期选择器
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(initialDate); // 创建日期选择器，使用传入的初始日期
        
        // 开始时间选择器
        Label startTimeLabel = new Label("Start Time:");
        ComboBox<String> startHourBox = createHourComboBox(); // 创建小时下拉框
        ComboBox<String> startMinuteBox = createMinuteComboBox(); // 创建分钟下拉框
        HBox startTimeBox = new HBox(10); // 水平布局，间距为10
        startTimeBox.getChildren().addAll(startHourBox, new Text(":"), startMinuteBox); // 添加组件并用冒号分隔
        
        // 结束时间选择器
        Label endTimeLabel = new Label("End Time:");
        ComboBox<String> endHourBox = createHourComboBox(); // 创建小时下拉框
        ComboBox<String> endMinuteBox = createMinuteComboBox(); // 创建分钟下拉框
        HBox endTimeBox = new HBox(10); // 水平布局，间距为10
        endTimeBox.getChildren().addAll(endHourBox, new Text(":"), endMinuteBox); // 添加组件并用冒号分隔
        
        // 设置默认时间值 (10:00 - 11:00)
        startHourBox.setValue("10");
        startMinuteBox.setValue("00");
        endHourBox.setValue("11");
        endMinuteBox.setValue("00");

        // 参与者字段
        Label participantsLabel = new Label("Participants:");
        TextField participantsField = new TextField();
        participantsField.setPromptText("Participant1,Participant2"); // 设置提示文本，表明使用逗号分隔

        // 优先级选择
        Label priorityLabel = new Label("Priority:");
        ComboBox<PriorityLevel> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(PriorityLevel.LOW, PriorityLevel.MEDIUM, PriorityLevel.HIGH); // 添加所有优先级选项
        priorityBox.setValue(PriorityLevel.MEDIUM); // 设置默认值为中等优先级

        // 按钮部分
        HBox buttonBox = new HBox(10); // 水平布局，间距为10
        buttonBox.setAlignment(Pos.CENTER); // 居中对齐
        
        Button cancelBtn = new Button("Cancel"); // 取消按钮
        cancelBtn.setPrefWidth(100); // 设置按钮宽度
        cancelBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;"); // 设置红色背景，白色文字
        
        Button saveBtn = new Button("Save"); // 保存按钮
        saveBtn.setPrefWidth(100); // 设置按钮宽度
        saveBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;"); // 设置绿色背景，白色文字
        
        buttonBox.getChildren().addAll(cancelBtn, saveBtn); // 将按钮添加到水平布局中

        // 创建表单布局
        GridPane formGrid = new GridPane(); // 网格布局，用于表单
        formGrid.setHgap(15); // 设置水平间距
        formGrid.setVgap(18); // 设置垂直间距
        formGrid.setAlignment(Pos.CENTER); // 居中对齐
        formGrid.setPadding(new Insets(10)); // 设置内边距
        formGrid.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;"); // 设置白色背景，灰色边框，圆角

        // 设置表单标签样式
        eventTitleLabel.setStyle("-fx-font-weight: bold;"); // 加粗
        dateLabel.setStyle("-fx-font-weight: bold;"); // 加粗
        startTimeLabel.setStyle("-fx-font-weight: bold;"); // 加粗
        endTimeLabel.setStyle("-fx-font-weight: bold;"); // 加粗
        participantsLabel.setStyle("-fx-font-weight: bold;"); // 加粗
        priorityLabel.setStyle("-fx-font-weight: bold;"); // 加粗
        
        // 设置表单字段样式
        titleField.setStyle("-fx-padding: 8; -fx-border-color: #ddd; -fx-border-radius: 3;"); // 内边距，边框颜色，圆角
        participantsField.setStyle("-fx-padding: 8; -fx-border-color: #ddd; -fx-border-radius: 3;"); // 内边距，边框颜色，圆角
        
        // 将表单元素添加到网格中
        int row = 0; // 行索引
        formGrid.add(eventTitleLabel, 0, row); // 添加到第0列，第row行
        formGrid.add(titleField, 1, row); // 添加到第1列，第row行
        
        row++; // 行索引增加
        formGrid.add(dateLabel, 0, row);
        formGrid.add(datePicker, 1, row);
        
        row++;
        formGrid.add(startTimeLabel, 0, row);
        formGrid.add(startTimeBox, 1, row);
        
        row++;
        formGrid.add(endTimeLabel, 0, row);
        formGrid.add(endTimeBox, 1, row);
        
        row++;
        formGrid.add(participantsLabel, 0, row);
        formGrid.add(participantsField, 1, row);
        
        row++;
        formGrid.add(priorityLabel, 0, row);
        formGrid.add(priorityBox, 1, row);

        // 设置按钮动作
        cancelBtn.setOnAction(e -> navController.popPane()); // 点击取消按钮时返回上一个视图

        // 保存按钮的动作处理
        saveBtn.setOnAction(e -> {
            String eventId = UUID.randomUUID().toString(); // 生成唯一的事件ID
            String title = titleField.getText(); // 获取标题文本
            
            // 从datePicker获取日期
            LocalDate date = datePicker.getValue();
            
            // 从时间选择器组合框获取时间
            LocalTime startTime = LocalTime.of(
                Integer.parseInt(startHourBox.getValue()), // 解析小时
                Integer.parseInt(startMinuteBox.getValue()) // 解析分钟
            );
            
            LocalTime endTime = LocalTime.of(
                Integer.parseInt(endHourBox.getValue()), // 解析小时
                Integer.parseInt(endMinuteBox.getValue()) // 解析分钟
            );
            
            // 组合日期和时间
            LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
            
            // 验证结束时间是否晚于开始时间
            if (!endDateTime.isAfter(startDateTime)) {
                // 显示错误提示
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Time");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("The end time of the event must be later than the start time. Please reselect the end time.");
                errorAlert.showAndWait();
                return; // 终止事件创建过程
            }
            
            String participants = participantsField.getText(); // 获取参与者文本
            PriorityLevel pl = priorityBox.getValue(); // 获取选择的优先级

            // 创建事件并添加到数据存储中
            Event newEvent = new Event(eventId, title, startDateTime, endDateTime,
                    List.of(participants.split(",")), pl); // 将参与者字符串按逗号分割成列表
            dataStore.addEvent(user.getUserId(), newEvent);

            // 显示成功消息
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建信息提示对话框
            alert.setTitle("Success");
            alert.setHeaderText(null); // 不显示头部文本
            alert.setContentText("Event successfully created!");
            alert.showAndWait(); // 显示对话框并等待用户响应
            
            // 返回上一个界面
            navController.popPane();
            
            // 打印日志信息
            System.out.println("Save Event => " + newEvent.getTitle() 
                + ", startTime=" + newEvent.getStartTime() 
                + ", userId=" + user.getUserId());
        });

        // 将所有组件添加到主VBox布局中
        getChildren().addAll(
            titleLabel,
            new Separator(), // 分隔线
            formGrid,
            new Separator(), // 分隔线
            buttonBox
        );
    }
    
    /**
     * 创建小时选择下拉框
     * @return 包含0-23小时选项的ComboBox
     */
    private ComboBox<String> createHourComboBox() {
        ComboBox<String> hourBox = new ComboBox<>();
        for (int i = 0; i < 24; i++) {
            hourBox.getItems().add(String.format("%02d", i)); // 添加格式化的小时字符串（两位数，不足补0）
        }
        return hourBox;
    }
    
    /**
     * 创建分钟选择下拉框
     * @return 包含0-59分钟选项的ComboBox
     */
    private ComboBox<String> createMinuteComboBox() {
        ComboBox<String> minuteBox = new ComboBox<>();
        // 首先添加5分钟间隔的选项（0, 5, 10, 15...）
        for (int i = 0; i < 60; i += 5) {
            minuteBox.getItems().add(String.format("%02d", i)); // 添加格式化的分钟字符串（两位数，不足补0）
        }
        // 添加自定义选项，用于选择精确的分钟
        if (!minuteBox.getItems().contains("01")) { // 检查是否已经包含"01"
            for (int i = 1; i < 60; i++) {
                if (i % 5 != 0) { // 只添加非5的倍数的分钟值
                    minuteBox.getItems().add(String.format("%02d", i));
                }
            }
        }
        return minuteBox;
    }
}