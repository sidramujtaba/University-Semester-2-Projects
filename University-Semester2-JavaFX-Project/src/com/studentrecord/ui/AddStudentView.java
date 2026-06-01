package com.studentrecord.ui;

// AddStudentView is the UI class responsible for displaying the form that allows users to
// add a new student record to the system.

import com.studentrecord.manager.StudentManager;
import com.studentrecord.model.Student;
import com.studentrecord.util.Validator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AddStudentView extends VBox
{
    private StudentManager manager;
    private MainWindow mainWindow;

    private TextField fieldID;
    private TextField fieldName;
    private TextField fieldAge;
    private TextField fieldEmail;
    private TextField fieldPhone;
    private TextField fieldGrade;


    public AddStudentView(StudentManager manager, MainWindow mainWindow)
    {
        this.manager = manager;
        this.mainWindow = mainWindow;

        buildView();
    }

    // AddStudentView handles one specific responsibility — collecting new student data from the user and passing
    // it to StudentManager for saving.


    private void buildView()
    {
        setSpacing(20);
        setPadding(new Insets(30, 40, 30, 40));
        setStyle("-fx-background-color: #f4f6f8;");

        Text heading = new Text("Add New Student");
        heading.setFont(Font.font("System", FontWeight.BOLD, 20));
        heading.setStyle("-fx-fill: #2c3e50;");

        GridPane form = buildForm();

        HBox buttonRow = buildButtonRow();

        getChildren().addAll(heading, form, buttonRow);
    }

    // buildView Sets the background color, spacing, and padding of the screen
    // It also calls buildForm() and buildButtonRow() to build the two main sections

    private GridPane buildForm()
    {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(14);
        grid.setPadding(new Insets(20, 0, 10, 0));

        fieldID    = new TextField();
        fieldName  = new TextField();
        fieldAge   = new TextField();
        fieldEmail = new TextField();
        fieldPhone = new TextField();
        fieldGrade = new TextField();

        fieldID.setPromptText("e.g. 1001");
        fieldName.setPromptText("e.g. Ali Hassan");
        fieldAge.setPromptText("e.g. 20");
        fieldEmail.setPromptText("e.g. ali@email.com");
        fieldPhone.setPromptText("e.g. 0312-1234567");
        fieldGrade.setPromptText("e.g. A");

        String fieldStyle =
                "-fx-background-color: white;" +
                        "-fx-border-color: #ccd1d9;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 7 10 7 10;" +
                        "-fx-font-size: 13px;" +
                        "-fx-pref-width: 260px;";

        fieldID.setStyle(fieldStyle);
        fieldName.setStyle(fieldStyle);
        fieldAge.setStyle(fieldStyle);
        fieldEmail.setStyle(fieldStyle);
        fieldPhone.setStyle(fieldStyle);
        fieldGrade.setStyle(fieldStyle);

        grid.add(styledLabel("Student ID"),  0, 0);
        grid.add(fieldID,                    1, 0);
        grid.add(styledLabel("Full Name"),   0, 1);
        grid.add(fieldName,                  1, 1);
        grid.add(styledLabel("Age"),         0, 2);
        grid.add(fieldAge,                   1, 2);
        grid.add(styledLabel("Email"),       0, 3);
        grid.add(fieldEmail,                 1, 3);
        grid.add(styledLabel("Phone"),       0, 4);
        grid.add(fieldPhone,                 1, 4);
        grid.add(styledLabel("Grade"),       0, 5);
        grid.add(fieldGrade,                 1, 5);

        return grid;
    }

    // buildForm Creates a GridPane, rows and columns
    // also ensures visual consistency


    private HBox buildButtonRow()
    {
        Button btnSave  = createButton("Save Student", "#1abc9c", "white");
        Button btnClear = createButton("Clear", "#95a5a6", "white");

        btnSave.setOnAction(e -> handleSave());
        btnClear.setOnAction(e -> clearFields());

        HBox row = new HBox(12, btnSave, btnClear);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    // buildButtonRow() Creates the Save Student and Clear buttons, connects them to handleSave() and clearFields()


    private void handleSave()
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

        if (manager.idAlreadyExists(studentID))
        {
            showAlert(Alert.AlertType.WARNING, "Duplicate ID",
                    "A student with ID " + studentID + " already exists.");
            return;
        }

        int age = Integer.parseInt(ageText.trim());

        Student newStudent = new Student(studentID, name.trim(), age,
                email.trim(), phone.trim(), grade.trim());
        manager.addStudent(newStudent);

        showAlert(Alert.AlertType.INFORMATION, "Success",
                "Student record saved successfully.");

        clearFields();
        mainWindow.showView(new ViewStudentView(manager));
    }

    // handleSave() Reads all six field values and passes them to Validator.validateStudentForm()
    // checks id the entered ID already exists using manager.idAlreadyExists()
    // if both checks pass, a new student object is passed to manager.addStudent()


    private void clearFields()
    {
        fieldID.clear();
        fieldName.clear();
        fieldAge.clear();
        fieldEmail.clear();
        fieldPhone.clear();
        fieldGrade.clear();
        fieldID.requestFocus();
    }

    // clearFields() Clears all the six text fields


    private Label styledLabel(String text)
    {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 13px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");
        return label;
    }

    // styledLabel() keeps label styling consistent across the form


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

    // createButton() creates the green save and gray clear buttons


    private void showAlert(Alert.AlertType type, String title, String message)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // showAlert() warning for invalid input, warning for duplicate ID, and confirmation of a successful save.
}