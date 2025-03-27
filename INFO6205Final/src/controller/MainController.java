package controller;

import model.DataStore;
import model.User;
import view.CalendarView;
import view.LoginView;

public class MainController {

    private DataStore dataStore;
    private NavigationController navigationController;

    public MainController(DataStore dataStore, NavigationController navigationController) {
        this.dataStore = dataStore;
        this.navigationController = navigationController;
    }

    // 登录逻辑
    public boolean login(String username, String password) {
        User user = dataStore.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // 登录成功 -> 切换到日历视图
            navigationController.pushPane(new CalendarView(user, dataStore, navigationController));
            return true;
        }
        return false;
    }

    // 登出逻辑
    public void logout() {
        // 回到登录页面
        navigationController.popPane();
    }
}
