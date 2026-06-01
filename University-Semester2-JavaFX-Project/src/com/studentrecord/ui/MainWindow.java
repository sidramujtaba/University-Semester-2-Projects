package com.studentrecord.ui;

import com.studentrecord.manager.StudentManager;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainWindow
{
    private BorderPane root;
    private StudentManager manager;

    private Button btnAdd;
    private Button btnView;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnSearch;


    public MainWindow(StudentManager manager)
    {
        this.manager = manager;
        this.root = new BorderPane();

        buildSidebar();
        showView(new ViewStudentView(manager));
    }


    private void buildSidebar()
    {
        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(20, 15, 20, 15));
        sidebar.setStyle("-fx-background-color: #2c3e50; -fx-min-width: 180px;");

        Text appTitle = new Text("Student\nRecords");
        appTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        appTitle.setStyle("-fx-fill: #ecf0f1;");

        btnAdd = createNavButton("Add Student");
        btnView = createNavButton("View All");
        btnUpdate = createNavButton("Update");
        btnDelete = createNavButton("Delete");
        btnSearch = createNavButton("Search");

        btnAdd.setOnAction(e -> showView(new AddStudentView(manager, this)));
        btnView.setOnAction(e -> showView(new ViewStudentView(manager)));
        btnUpdate.setOnAction(e -> showView(new UpdateStudentView(manager)));
        btnDelete.setOnAction(e -> showView(new DeleteStudentView(manager)));
        btnSearch.setOnAction(e -> showView(new SearchStudentView(manager)));

        sidebar.getChildren().addAll(
                appTitle,
                createSpacer(),
                btnAdd,
                btnView,
                btnUpdate,
                btnDelete,
                btnSearch
        );

        root.setLeft(sidebar);
    }

    // Creates a dark-colored VBox for the sidebar. Creates all five navigation buttons and wires each one to showView()
    // Adds the title, a spacer and all the five buttons to the sidebar


    private Button createNavButton(String label)
    {
        Button btn = new Button(label);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle(
                "-fx-background-color: #34495e;" +
                        "-fx-text-fill: #ecf0f1;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 10 15 10 15;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 6;"
        );

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #1abc9c;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 10 15 10 15;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 6;"
        ));

        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: #34495e;" +
                        "-fx-text-fill: #ecf0f1;" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 10 15 10 15;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 6;"
        ));

        return btn;
    }

    // A helper that creates a styled navigation button, it also adds a hover effect.

    private VBox createSpacer()
    {
        VBox spacer = new VBox();
        VBox.setMargin(spacer, new Insets(10, 0, 10, 0));
        return spacer;
    }

    // Creates a small empty space between the app title and the first button.


    public void showView(VBox view)
    {
        root.setCenter(view);
    }

    // It calls root.setCenter(view), which replaces whatever is currently showing in the center with the new view.


    public BorderPane getRoot()
    {
        return root;
    }

    // Returns the BorderPane so that Main.java can wrap it inside a Scene and display it on the Stage.
    // It is a JavaFX layout that divides the main window into 5 regions. Left for sidebar and center region.
}