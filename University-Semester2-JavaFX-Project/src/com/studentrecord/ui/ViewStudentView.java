package com.studentrecord.ui;

import com.studentrecord.manager.StudentManager;
import com.studentrecord.model.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ViewStudentView extends VBox
{
    private StudentManager manager;
    private TableView<Student> table;


    public ViewStudentView(StudentManager manager)
    {
        this.manager = manager;
        buildView();
    }


    private void buildView()
    {
        setSpacing(16);
        setPadding(new Insets(30, 40, 30, 40));
        setStyle("-fx-background-color: #f4f6f8;");

        Text heading = new Text("All Student Records");
        heading.setFont(Font.font("System", FontWeight.BOLD, 20));
        heading.setStyle("-fx-fill: #2c3e50;");

        table = buildTable();
        loadTableData();

        Label countLabel = new Label("Total records: " + manager.getAllStudents().size());
        countLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

        getChildren().addAll(heading, table, countLabel);
    }

    // Sets up the screen — heading, table, and a count label at the bottom.


    private TableView<Student> buildTable()
    {
        TableView<Student> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #dde1e7;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;"
        );

        TableColumn<Student, Integer> colID = new TableColumn<>("Student ID");
        TableColumn<Student, String>  colName = new TableColumn<>("Full Name");
        TableColumn<Student, Integer> colAge = new TableColumn<>("Age");
        TableColumn<Student, String>  colEmail = new TableColumn<>("Email");
        TableColumn<Student, String>  colPhone = new TableColumn<>("Phone");
        TableColumn<Student, String>  colGrade = new TableColumn<>("Grade");

        colID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        colID.setStyle("-fx-alignment: CENTER;");
        colAge.setStyle("-fx-alignment: CENTER;");
        colGrade.setStyle("-fx-alignment: CENTER;");

        tableView.getColumns().addAll(colID, colName, colAge, colEmail, colPhone, colGrade);

        tableView.setPlaceholder(
                new Label("No student records found. Add a student to get started.")
        );

        return tableView;
    }

    // Creates a TableView with six columns — Student ID, Full Name, Age, Email, Phone, and Grade.


    private void loadTableData()
    {
        ObservableList<Student> data = FXCollections.observableArrayList(
                manager.getAllStudents()
        );

        table.setItems(data);
    }

    // Fetches all students from manager.getAllStudents(), wraps them in an ObservableList
    // Observable List is a special JavaFX list that the TableView can read and display directly.


    public TableView<Student> getTable()
    {
        return table;
    }

    // getTable()
    // A public method that returns the table object.
}