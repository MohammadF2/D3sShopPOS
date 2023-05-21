package com.example.d3sshoppos;

import com.example.d3sshoppos.helpers.DataSaver;
import com.example.d3sshoppos.helpers.Logger;

import java.awt.Desktop;

import com.example.d3sshoppos.helpers.TableGenerator;
import com.example.d3sshoppos.model.Regal;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;

public class ProductManagement implements Initializable {

    public final static String FILE_XLSX_PATH = "C:\\Kareem\\data.xlsx";
    public final static String FILE_PATH = "C:\\Kareem\\";

    @FXML
    private Button deleteData;

    @FXML
    private Button downloadData;

    @FXML
    private Button uploadData;

    @FXML
    void deleteDataAction(ActionEvent event) {
        backup(FILE_XLSX_PATH, "C:\\Kareem\\backups", Logger.getTimeAndDate());
        deleteData();
    }

    @FXML
    void downloadDataAction(ActionEvent event) {
        save();
    }

    @FXML
    void uploadDataAction(ActionEvent event) {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();

        // Set the extension filter to only allow XLSX files
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Show the file chooser dialog and wait for user selection
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
            backup(selectedFile.getAbsolutePath() , FILE_PATH, fileNameWithoutExtension);
            convert(FILE_XLSX_PATH);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("حدث خطأ");
            a.setContentText("لم تختار اي مكان لحفظ الملف.");
            a.setHeaderText("خطأ");
            a.show();
        }

        for (Regal r: DataSaver.regals) {
            System.out.println(r.getNumber() + ": " + r.getRegalName());
            for (Product p: r.getProducts()) {
                System.out.println(p.getProductName() + " " + p.getWeight());
            }
        }

    }

    @FXML
    void editButtonAction(ActionEvent event) {


        File file = new File(FILE_XLSX_PATH);

        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("حدث خطأ");
            a.setContentText("الملف غير موجود");
            a.setHeaderText("خطأ");
            a.show();
        }
    }

    @FXML
    void saveEditButtonAction(ActionEvent event) {
        convert(FILE_XLSX_PATH);
    }

    public void convert(String xlsxFilePath) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(xlsxFilePath))) {

            regalComboBox.getItems().clear();
            DataSaver.regals.clear();
            regalComboBox.getSelectionModel().clearSelection();
            regalComboBox.setPromptText("Choose regal");

            String regalName = "";
            int regalNumber = 1;
            String productName = "";
            for (Sheet sheet : workbook) {
                regalName = sheet.getSheetName();
                Regal r = new Regal(regalNumber, regalName);
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        if(cell.getColumnIndex() == 0){
                            productName = cell.getStringCellValue();
                        }
                        if(row.getPhysicalNumberOfCells() < 2) {
                            r.addToList(productName, "0");
                        }
                        if(cell.getColumnIndex() == 1){

                            if(cell.getCellType().name().equals("STRING")) {
                                r.addToList(productName, cell.getStringCellValue() + "");
                            } else {
                                r.addToList(productName, cell.getNumericCellValue() + "");
                            }

                        }
                    }
                }
                DataSaver.regals.add(r);
                regalNumber++;
            }
            System.out.println("Conversion completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        addToComboBox();
    }

    private void deleteData() {
        Workbook workbook = new XSSFWorkbook();
        // Create a new Sheet
        workbook.createSheet("Products");
        // Save the Workbook to a file
        try (FileOutputStream outputStream = new FileOutputStream(FILE_XLSX_PATH)) {
            workbook.write(outputStream);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("حذف البيانات الملف");
            a.setContentText("تم حذف جميع البيانات");
            a.setHeaderText("تأكيد");
            a.show();
            System.out.println("Empty XLSX file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Close the Workbook
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save() {
        Stage dirictoryStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("اختار مجلد الحفظ");

        // Show the dialog and wait for user selection
        File selectedDirectory = directoryChooser.showDialog(dirictoryStage);

        if (selectedDirectory != null) {
            // Construct the source file path
            String sourceFilePath = "C:/Kareem/data.xlsx";
            File sourceFile = new File(sourceFilePath);

            // Construct the destination directory path
            String destinationDirectoryPath = selectedDirectory.getAbsolutePath();

            try {
                // Copy the source file to the destination directory
                Path destinationPath = Path.of(destinationDirectoryPath, sourceFile.getName());
                Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                Logger.logConfirmation("File copied successfully to: " + destinationPath, "Copying");
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("حفظ الملف");
                a.setContentText("تم نسخ الملف الى: : " + destinationPath);
                a.setHeaderText("تأكيد");
                a.show();
                System.out.println("File copied successfully to: " + destinationPath);
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("حدث خطأ");
                a.setContentText(e.getMessage());
                a.setHeaderText("خطأ");
                a.show();
                e.printStackTrace();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("حدث خطأ");
            a.setContentText("لم تختار اي مكان لحفظ الملف.");
            a.setHeaderText("خطأ");
            a.show();
            System.out.println("No directory selected.");
        }
    }

    private void backup(String sourceFilePath,String destinationDirectoryPath, String newName) {
        File sourceFile = new File(sourceFilePath);
        try {
            // Get the original file name and extension
            String originalFileName = sourceFile.getName();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // Create the new file name with the specified new name and original extension
            String newFileName = newName + extension;

            // Create the destination file path with the new file name
            Path destinationPath = Paths.get(destinationDirectoryPath, newFileName);

            // Copy the source file to the destination directory with the new file name
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            Logger.logConfirmation("File backup successfully to: " + destinationPath, "backing up");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // TODO: REGAL TABLE







    @FXML
    private TableColumn<Product, String> productNameC;

    @FXML
    private TableColumn<Product, String> productWeightC;

    @FXML
    private ComboBox<Regal> regalComboBox;

    @FXML
    private TableView<Product> regalTable;


    private void addToComboBox() {
        for (Regal r: DataSaver.regals) {
            regalComboBox.getItems().add(r);
        }

    }

    @FXML
    void showRegalData(ActionEvent event) {

        try {
            ObservableList<Product> productList = FXCollections.observableArrayList(regalComboBox.getSelectionModel().getSelectedItem().getProducts());

            for (Product p: productList) {
                System.out.println(p.getProductName());
            }

            regalTable.setPlaceholder(new Label("لا يوجد بيانات داخل الملف"));
            // Define table columns

            productNameC.setCellValueFactory(new PropertyValueFactory<>("productName"));

            productWeightC.setCellValueFactory(new PropertyValueFactory<>("weight"));

            regalTable.setItems(productList);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @FXML
    void PDFButtonActon(ActionEvent event) {

        if(regalComboBox.getSelectionModel().isEmpty())
            return;

        String[] names = new String[regalComboBox.getSelectionModel().getSelectedItem().getProducts().size()];

        for (int i = 0; i < regalComboBox.getSelectionModel().getSelectedItem().getProducts().size(); i++) {
            names[i] = regalComboBox.getSelectionModel().getSelectedItem().getProducts().get(i).getProductName();
        }

        TableGenerator.generatePDFFile(names);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        convert(FILE_XLSX_PATH);
    }
}