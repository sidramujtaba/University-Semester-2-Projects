package com.studentrecord.ui;

import com.studentrecord.manager.StudentManager;
import com.studentrecord.model.Student;
import com.studentrecord.util.Validator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UpdateStudentView extends VBox
{
    private StudentManager manager;
    private TableView<Student> table;

    private TextField fieldID;
    private TextField fieldName;
    private TextField fieldAge;
    private TextField fieldEmail;
    private TextField fieldPhone;
    private TextField fieldGrade;

    private Button btnUpdate;
    private Button btnClear;


    public UpdateStudentView(StudentManager manager)
    {
        this.manager = manager;
        buildView();
    }


    private void buildView()
    {
        setSpacing(16);
        setPadding(new Insets(30, 40, 30, 40));
        setStyle("-fx-background-color: #f4f6f8;");

        Text heading = new Text("Update Student Record");
        heading.setFont(Font.font("System", FontWeight.BOLD, 20));
        heading.setStyle("-fx-fill: #2c3e50;");

        Label instruction = new Label("Select a student from the table below to load their details.");
        instruction.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        // buttons must be created before the table listener is registered
        // because populateForm() references them
        GridPane form = buildForm();
        HBox buttonRow = buildButtonRow();

        table = buildTable();
        loadTableData();

        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, selected) ->
                {
                    if (selected != null)
                    {
                        populateForm(selected);
                    }
                    else
                    {
                        clearForm();
                        btnUpdate.setDisable(true);
                        btnClear.setDisable(true);
                    }
                }
        );

        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

        getChildren().addAll(heading, instruction, table, form, buttonRow);
    }

    // Sets up the screen layout.


    private TableView<Student> buildTable()
    {
        TableView<Student> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setMaxHeight(200);
        tableView.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #dde1e7;"   +
                        "-fx-border-radius: 6;"        +
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
                new Label("No student records available.")
        );

        return tableView;
    }

    // Creates a TableView with six columns and a max height iof 200 so only it takes the top portion of the screen.


    private void loadTableData()
    {
        ObservableList<Student> data = FXCollections.observableArrayList(
                manager.getAllStudents()
        );

        table.setItems(data);
    }

    // Fetches all students from manager.getAllStudents() and loads them into the table
    // Called once on startup and again after a update


    private GridPane buildForm()
    {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(12);
        grid.setPadding(new Insets(16, 0, 10, 0));

        fieldID    = new TextField();
        fieldName  = new TextField();
        fieldAge   = new TextField();
        fieldEmail = new TextField();
        fieldPhone = new TextField();
        fieldGrade = new TextField();

        fieldID.setEditable(false);
        fieldID.setStyle(
                "-fx-background-color: #ecf0f1;" +
                        "-fx-border-color: #ccd1d9;"     +
                        "-fx-border-radius: 5;"          +
                        "-fx-background-radius: 5;"      +
                        "-fx-padding: 7 10 7 10;"        +
                        "-fx-font-size: 13px;"           +
                        "-fx-pref-width: 260px;"
        );

        String fieldStyle =
                "-fx-background-color: white;" +
                        "-fx-border-color: #ccd1d9;"   +
                        "-fx-border-radius: 5;"        +
                        "-fx-background-radius: 5;"    +
                        "-fx-padding: 7 10 7 10;"      +
                        "-fx-font-size: 13px;"         +
                        "-fx-pref-width: 260px;";

        fieldName.setStyle(fieldStyle);
        fieldAge.setStyle(fieldStyle);
        fieldEmail.setStyle(fieldStyle);
        fieldPhone.setStyle(fieldStyle);
        fieldGrade.setStyle(fieldStyle);

        grid.add(styledLabel("Student ID"), 0, 0);
        grid.add(fieldID,                   1, 0);
        grid.add(styledLabel("Full Name"),  0, 1);
        grid.add(fieldName,                 1, 1);
        grid.add(styledLabel("Age"),        0, 2);
        grid.add(fieldAge,                  1, 2);
        grid.add(styledLabel("Email"),      0, 3);
        grid.add(fieldEmail,                1, 3);
        grid.add(styledLabel("Phone"),      0, 4);
        grid.add(fieldPhone,                1, 4);
        grid.add(styledLabel("Grade"),      0, 5);
        grid.add(fieldGrade,                1, 5);

        return grid;
    }

    // Creates a GridPane with six labeled fields. The Student ID field is set to setEditable(false)


    private HBox buildButtonRow()
    {
        btnUpdate = createButton("Save Changes", "#1abc9c", "white");
        btnClear  = createButton("Clear",        "#95a5a6", "white");

        btnUpdate.setDisable(true);
        btnClear.setDisable(true);

        btnUpdate.setOnAction(e -> handleUpdate());
        btnClear.setOnAction(e ->
        {
            clearForm();
            table.getSelectionModel().clearSelection();
        });

        HBox row = new HBox(12, btnUpdate, btnClear);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    // Creates the Save Changes and Clear buttons, both disabled by default. Save Changes calls handleUpdate()


    private void populateForm(Student student)
    {
        fieldID.setText(String.valueOf(student.getStudentID()));
        fieldName.setText(student.getName());
        fieldAge.setText(String.valueOf(student.getAge()));
        fieldEmail.setText(student.getEmail());
        fieldPhone.setText(student.getPhone());
        fieldGrade.setText(student.getGrade());

        btnUpdate.setDisable(false);
        btnClear.setDisable(false);
    }

    // Called when a row is selected. Fills all six fields with the selected student's data and enables both buttons
    // so the user can start editing.


    private void handleUpdate()
    {
        String idText = fieldID.getText();
        String name = fieldName.getText();
        String ageText = fieldAge.getText();
        String email = fieldEmail.getText();
        String phone = fieldPhone.getText();
        String grade = fieldGrade.getText();

        String error = Validator.validateStudentForm(idText, name, ageText, email, phone, grade);

        if (error != null)
        {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", error);
            return;
        }

        int studentID = Integer.parseInt(idText.trim());
        int age = Integer.parseInt(ageText.trim());

        Student updated = new Student(studentID, name.trim(), age,
                email.trim(), phone.trim(), grade.trim());
        manager.updateStudent(updated);

        showAlert(Alert.AlertType.INFORMATION, "Success",
                "Student record updated successfully.");

        clearForm();
        loadTableData();
        table.getSelectionModel().clearSelection();

        btnUpdate.setDisable(true);
        btnClear.setDisable(true);
    }

    // Reads all 6 values ------> Performs validation checks
    // If validation succeeds -------> a new Student Obj is created


    private void clearForm()
    {
        fieldID.clear();
        fieldName.clear();
        fieldAge.clear();
        fieldEmail.clear();
        fieldPhone.clear();
        fieldGrade.clear();
    }

    // Clears all six text fields.


    private Label styledLabel(String text)
    {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 13px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");
        return label;
    }


    private Button createButton(String text, String bgColor, String textColor)
    {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-background-color: " + bgColor   + ";" +
                        "-fx-text-fill: "        + textColor + ";" +
                        "-fx-font-size: 13px;"                     +
                        "-fx-padding: 9 20 9 20;"                  +
                        "-fx-background-radius: 6;"                +
                        "-fx-cursor: hand;"
        );
        return btn;
    }

    // styledLabel() and createButton()
    // Helper methods that keep styling consistent


    private void showAlert(Alert.AlertType type, String title, String message)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}