package faktury.invcreator;

import faktury.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import faktury.Main;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    List<String> fieldTexts = new ArrayList<>();
    int rowCounter;
    int labelRow;
    int prodCounter;
    private HashMap sumLabelMap = new HashMap<String, Label>();


    public void returnHome() {
        Main.set_pane(0);
    }


    @FXML
    private TextField invTitle, sName, sMail, sAddress, sNip, sTel, bMail, bAddress, bNip, bTel, pName, pPrice, pQnt, pDesc;
    @FXML
    private ChoiceBox bName;


    public void invGenerate() {
        ArrayList<String> productText = new ArrayList<>();

        productText.add(pName.getText());
        productText.add(pPrice.getText());
        productText.add(pQnt.getText());
        productText.add(pDesc.getText());

        for (int i = 0; i < productList.size(); i++) {
            productText.add(productList.get(i).getText());
        }

        fieldTexts.add(invTitle.getText());

        fieldTexts.add(sName.getText());
        fieldTexts.add(sMail.getText());
        fieldTexts.add(sAddress.getText());
        fieldTexts.add(sNip.getText());
        fieldTexts.add(sTel.getText());

        fieldTexts.add(bName.getSelectionModel().getSelectedItem().toString());
        fieldTexts.add(bMail.getText());
        fieldTexts.add(bAddress.getText());
        fieldTexts.add(bNip.getText());
        fieldTexts.add(bTel.getText());

        List<Label> priceListLabels = new ArrayList<Label>(sumLabelMap.values());
        ArrayList<String> priceList = new ArrayList<>();

        for (int i = 0; i < priceListLabels.size(); i++){
            priceList.add(priceListLabels.get(i).getText());
        }

        System.out.println(priceList);

        pdfGenerator.generatePdf(fieldTexts, productText, prodCounter, priceList);

        fieldTexts.clear();
    }

    @FXML
    private GridPane productTable;
    @FXML
    private Button addingButton;

    public void sumComp(int row) {
        double price;
        double quantity;

        // DO SHASHOWANIA

        HBox prodPriceContainer = (HBox) (productTable.getChildren().get(7 + 6 * row + 1));
        TextField prodPriceField = (TextField) (prodPriceContainer.getChildren().get(0));
        HBox prodQntContainer = (HBox) (productTable.getChildren().get(7 + 6 * row + 2));
        TextField prodQntField = (TextField) (prodQntContainer.getChildren().get(0));

        if (prodPriceField.getText().trim().isEmpty()) {
            price = 0;
        } else {
            price = Double.parseDouble(prodPriceField.getText());
        }

        if (prodQntField.getText().trim().isEmpty()) {
            quantity = 0;
        } else {
            quantity = Double.parseDouble(prodQntField.getText());
        }

        Label label = getLabelByName("sumLabel" + row);
        label.setText(String.format("%.2f", price * quantity) + " zł");
    }

    ArrayList<TextField> productList = new ArrayList<>();

    public void addProduct() {
        TextField prodName, prodPrice, prodQnt, prodDesc;
        prodName = new TextField();
        prodPrice = new TextField();
        prodQnt = new TextField();
        prodDesc = new TextField();
        labelRow = (rowCounter - 1) / 2;

        prodName.setPromptText("Nazwa produktu");
        prodPrice.setPromptText("0.00");
        prodPrice.setOnKeyTyped(e -> sumComp(labelRow));
        prodQnt.setPromptText("0");
        prodQnt.setOnKeyTyped(e -> sumComp(labelRow));
        prodDesc.setPromptText("Opis produktu");
        prodDesc.setPrefHeight(100);

        productList.add(prodName);
        productList.add(prodPrice);
        productList.add(prodQnt);
        productList.add(prodDesc);


        productTable.add(productsTextFieldContainer(prodName, 0), 1, rowCounter);
        productTable.add(productsTextFieldContainer(prodPrice, 30), 2, rowCounter);
        productTable.add(productsTextFieldContainer(prodQnt, 60), 3, rowCounter);
        productTable.add(productsTextFieldContainer(prodDesc, 0), 1, rowCounter + 1);

        Label label = new Label("0.00 zł");
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setId("sumLabel" + labelRow);
        sumLabelMap.put("sumLabel" + labelRow, label);
        productTable.add(label, 4, rowCounter);

        CheckBox checkBox = new CheckBox();
        checkBox.setAlignment(Pos.CENTER);
        checkBox.setMaxWidth(Double.MAX_VALUE);
        productTable.add(checkBox, 5, rowCounter);

        rowCounter += 2;

        Button button = addingButton;
        productTable.getChildren().remove(addingButton);
        productTable.add(button, 0, rowCounter + 2);

        prodCounter += 1;
    }

    public HBox productsTextFieldContainer(TextField textField, double leftPadding) {
        HBox container = new HBox();
        textField.prefWidthProperty().bind(container.widthProperty());
        container.getChildren().add(textField);
        Insets padding = new Insets(0, 0, 0, leftPadding);
        container.setPadding(padding);

        return container;
    }

    @FXML
    private VBox invContainer;
    @FXML
    private TextField prodPriceField0, prodQntField0;

    ArrayList<String> defList = new ArrayList<>();
    ArrayList<String> buyerList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prodCounter = 0;

        Label label = new Label("0.00 zł"); // testy labeli, do zfunkcjowania
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setId("sumLabel0");
        sumLabelMap.put("sumLabel0", label);
        productTable.add(label, 4, 1);

        pPrice.setOnKeyTyped(e -> sumComp(0));
        pQnt.setOnKeyTyped(e -> sumComp(0));
        //product list row counter
        rowCounter = 3;

        //main container shadow
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.color(0, 0, 0, 0.16));
        invContainer.setEffect(dropShadow);


        try {
            defList = Database.getDefaultSeller();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (defList.size() > 0) {
            sName.setText(defList.get(0));
            sMail.setText(defList.get(1));
            sAddress.setText(defList.get(2));
            sNip.setText(defList.get(3));
            sTel.setText(defList.get(4));
        }

        subjectRefresh();

        bName.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            ArrayList<String> buyerInfo = new ArrayList<>();

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                try {
                    buyerInfo = Database.getSubjectByName(bName.getItems().get((Integer) number2) + "");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                bMail.setText(buyerInfo.get(1));
                bAddress.setText(buyerInfo.get(2));
                bNip.setText(buyerInfo.get(3));
                bTel.setText(buyerInfo.get(4));
            }
        });


    }


    public Label getLabelByName(String name) {
        if (sumLabelMap.containsKey(name)) {
            return (Label) sumLabelMap.get(name);
        } else return null;
    }

    public void defaultSeller() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../selleredit/sample.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Zmień dane");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBuyer() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../buyeradd/sample.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Dodaj nabywcę");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subjectRefresh() {
        try {
            buyerList = Database.getSubjectNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (int i = 1 + bName.getItems().size(); i < buyerList.size(); i++) {
            bName.getItems().add(buyerList.get(i));
        }
    }

    public void deleteBuyer() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../buyerdelete/sample.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Dodaj nabywcę");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
