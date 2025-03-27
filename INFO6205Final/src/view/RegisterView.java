package view;

import java.util.UUID;

import controller.NavigationController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.DataStore;
import model.User;

public class RegisterView extends VBox {
    
    public RegisterView(DataStore dataStore, NavigationController navController) {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("Register a New Account");
        
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        
        Label messageLabel = new Label(); // 用于显示“注册成功 / 失败”消息
        
        Button registerBtn = new Button("Register");
        registerBtn.setOnAction(e -> {
            String username = userField.getText().trim();
            String password = passField.getText().trim();
            
            // 简化的判空检查
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Username/Password cannot be empty");
                return;
            }
            
            // 检查是否已存在
            if (dataStore.findUserByUsername(username) != null) {
                messageLabel.setText("Username already taken, please choose another");
                return;
            }
            
            // 否则创建User，并放入userMap
            String newUserId = UUID.randomUUID().toString();
            User newUser = new User(newUserId, username, password);
            dataStore.addUser(newUser); 
            
            messageLabel.setText("Registration successful!");
            
            // 注册完成后，你可以直接回到登录页面
            // navController.popPane(); // 如果上一个页面是登录
            // 或者 navController.pushPane(new LoginView(...));
        });
        
        Button backBtn = new Button("Back to Login");
        backBtn.setOnAction(e -> {
            navController.popPane(); 
            // 或者 pushPane(new LoginView(dataStore, navController)) 
            // 看你具体怎么设计流程
        });
        
        getChildren().addAll(
            titleLabel,
            userLabel, userField,
            passLabel, passField,
            registerBtn,
            backBtn,
            messageLabel
        );
    }
}
