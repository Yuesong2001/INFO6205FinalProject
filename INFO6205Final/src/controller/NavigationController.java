package controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Stack;

public class NavigationController {
    private Scene scene;
    private Pane currentPane;
    private Stack<Pane> paneStack;

    public NavigationController() {
        this.paneStack = new Stack<>();
    }

    // 把 scene 的设置改为单独的方法
    public void setScene(Scene scene) {
        this.scene = scene;
        this.currentPane = (Pane) scene.getRoot();
    }

    public void pushPane(Pane newPane) {
        if (currentPane != null) {
            paneStack.push(currentPane);
        }
        currentPane = newPane;
        scene.setRoot(newPane);
    }

    public void popPane() {
        if (!paneStack.isEmpty()) {
            currentPane = paneStack.pop();
            scene.setRoot(currentPane);
        } else {
            System.out.println("No more in stack.");
        }
    }
}


