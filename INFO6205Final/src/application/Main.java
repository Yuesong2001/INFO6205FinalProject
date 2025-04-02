package application;

import controller.MainController;
import controller.NavigationController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DataStore;
import view.LoginView;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
	    // 1. 先创建数据和控制器
	    DataStore dataStore = new DataStore();
	    NavigationController navController = new NavigationController();
	    MainController mainController = new MainController(dataStore, navController);

	    // 2. 创建初始的 LoginView (它需要 mainController, navController)
	    LoginView loginView = new LoginView(mainController, navController,dataStore);
	    //   假设 LoginView extends VBox（或 Pane 子类）

	    // 3. 用 loginView 做根节点创建 Scene
	    Scene scene = new Scene(loginView, 800, 600);

	    // 4. 把这个 Scene 再传给 navController
	    navController.setScene(scene);

	    // 5. 最后把 scene 设置到 Stage 并 show
	    primaryStage.setTitle("Calendar");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}


    public static void main(String[] args) {
        launch(args);
    }
}
