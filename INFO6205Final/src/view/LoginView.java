package view;


import controller.MainController;
import controller.NavigationController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.DataStore;

public class LoginView extends VBox {
	
    private MainController mainController;
    private NavigationController navController;
    private DataStore dataStore; // 存起来

    public LoginView(MainController mainController, NavigationController navController, DataStore dataStore) {
    	
        this.mainController = mainController;
        this.navController = navController;
        this.dataStore = dataStore;
    	
        setSpacing(10);
        setAlignment(Pos.CENTER);

        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button loginBtn = new Button("Login");

        loginBtn.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();
            boolean success = mainController.login(username, password);
            if(!success) {
                // 简化处理
                System.out.println("Login Failed!");
            }
        });
        
        Button registerBtn = new Button("Register");
        registerBtn.setOnAction(e -> {
            navController.pushPane(new RegisterView(dataStore, navController));
        });

        getChildren().addAll(userLabel, userField, passLabel, passField, loginBtn,registerBtn);
    }
}
