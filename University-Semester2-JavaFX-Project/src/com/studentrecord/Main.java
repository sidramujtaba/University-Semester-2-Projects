package com.studentrecord;

import com.studentrecord.manager.StudentManager;
import com.studentrecord.ui.MainWindow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        StudentManager manager = new StudentManager();          // creates the single manager that handles all student data
        MainWindow mainWindow = new MainWindow(manager);        // builds the full window layout and passes the manager to it

        Scene scene = new Scene(mainWindow.getRoot(), 900, 600); // wraps the layout in a scene with a width of 900 and height of 600

        primaryStage.setTitle("Student Record Management System"); // sets the text shown in the title bar of the window
        primaryStage.setScene(scene);                              // attaches the scene to the stage so it can be displayed
        primaryStage.setMinWidth(800);                             // prevents the user from resizing the window too narrow
        primaryStage.setMinHeight(500);                            // prevents the user from resizing the window too short
        primaryStage.show();                                       // makes the window visible on screen
    }


    public static void main(String[] args)
    {
        launch(args); // starts the JavaFX engine and hands control to start()
    }
}