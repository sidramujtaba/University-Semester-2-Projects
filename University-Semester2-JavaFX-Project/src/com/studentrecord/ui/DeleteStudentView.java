package com.studentrecord.ui;

// This is the screen where the user selects a student from a table and deletes their record.

import com.studentrecord.manager.StudentManager;
import com.studentrecord.model.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Optional;

public class DeleteStudentView extends VBox
{
    private StudentManager     manager;
    private TableView<Student> table;
    private Button             btnDelete;
    private Label              selectionLabel;


    public DeleteStudentView(StudentManager manager)
    {
        this.manager = manager;
        buildView();
    }


    private void buildView()
    {
        setSpacing(16);
        setPadding(new Insets(30, 40, 30, 40));
        setStyle("-fx-background-color: #f4f6f8;");

        Text heading = new Text("Delete Student Record");
        heading.setFont(Font.font("System", FontWeight.BOLD, 20));
        heading.setStyle("-fx-fill: #2c3e50;");

        Label instruction = new Label("Select a student from the table and click Delete.");
        instruction.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        table = buildTable();
        loadTableData();

        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, selected) ->
                {
                    if (selected != null)
                    {
                        selectionLabel.setText(
                                "Selected: " + selected.getName() + " (ID: " + selected.getStudentID() + ")"
                        );
                        btnDelete.setDisable(false);
                    }
                    else
                    {
                        selectionLabel.setText("");
                        btnDelete.setDisable(true);
                    }
                }
        );

        selectionLabel = new Label("");
        selectionLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");

        HBox buttonRow = buildButtonRow();

        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

        getChildren().addAll(heading, instruction, table, selectionLabel, buttonRow);
    }

    // buildView() selectionLabel is initialized before the table listener to avoid a NullPointerException
    // Selection Listener : updates the label and toggles the Delete button based on whether a row is selected


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

        TableColumn<Student, Integer> colID    = new TableColumn<>("Student ID");
        TableColumn<Student, String>  colName  = new TableColumn<>("Full Name");
        TableColumn<Student, Integer> colAge   = new TableColumn<>("Age");
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
                new Label("No student records available.")
        );

        return tableView;
    }

    // buildTable() Creates a TableView with six columns matching the six student fields. Each column uses
    // PropertyValueFactory to automatically pull the right value from the Student object.


    private void loadTableData()
    {
        ObservableList<Student> data = FXCollections.observableArrayList(
                manager.getAllStudents()
        );

        table.setItems(data);
    }

    // loadTableData() Fetches all students from manager.getAllStudents(), wraps them in an ObservableList

    private HBox buildButtonRow()
    {
        btnDelete = createButton("Delete Student", "#e74c3c", "white");
        btnDelete.setDisable(true);

        btnDelete.setOnAction(e -> handleDelete());

        HBox row = new HBox(12, btnDelete);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    // buildButtonRow() Creates the Delete Student button in red and sets it as disabled by default.


    private void handleDelete()
    {
        Student selected = table.getSelectionModel().getSelectedItem();

        if (selected == null)
        {
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText(null);
        confirmation.setContentText(
                "Are you sure you want to delete the record for:\n\n"
                        + "Name: "  + selected.getName()      + "\n"
                        + "ID:   "  + selected.getStudentID() + "\n\n"
                        + "This action cannot be undone."
        );

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            manager.deleteStudent(selected.getStudentID());

            showAlert(Alert.AlertType.INFORMATION, "Deleted",
                    selected.getName() + "'s record has been removed successfully.");

            loadTableData();
            selectionLabel.setText("");
            btnDelete.setDisable(true);
        }
    }

    // handleDelete() Retrieves the selected student from the table.
    // Shows a CONFIRMATION dialog displaying the student's name and ID. If the user clicks OK, manager.deleteStudent() is called.


    private Button createButton(String text, String bgColor, String textColor)
    {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-text-fill: " + textColor + ";" +
                        "-fx-font-size: 13px;" +
                        "-fx-padding: 9 20 9 20;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;"
        );
        return btn;
    }

    // createButton() This is a helper method that creates a styled button.


    private void showAlert(Alert.AlertType type, String title, String message)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // showAlert() A helper that displays a popup dialog
}