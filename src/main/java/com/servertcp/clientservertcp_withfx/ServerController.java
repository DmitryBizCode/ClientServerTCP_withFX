package com.servertcp.clientservertcp_withfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {

    @FXML
    private TableColumn<Client_Data, Double> CS;

    @FXML
    private TableColumn<Client_Data, Integer> ID;

    @FXML
    private TableColumn<Client_Data, Double> Res;

    @FXML
    private TableColumn<Client_Data, Double> XS;

    @FXML
    private TableColumn<Client_Data, Double> YS;

    @FXML
    private TableView<Client_Data> tableOfServerWork;

    @FXML
    private Label Iterati;

    ObservableList<Client_Data> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ID.setCellValueFactory(new PropertyValueFactory<Client_Data,Integer>("ID"));
        XS.setCellValueFactory(new PropertyValueFactory<Client_Data,Double>("Xs"));
        YS.setCellValueFactory(new PropertyValueFactory<Client_Data,Double>("Ys"));
        CS.setCellValueFactory(new PropertyValueFactory<Client_Data,Double>("Cs"));
        Res.setCellValueFactory(new PropertyValueFactory<Client_Data,Double>("Res"));
        tableOfServerWork.setItems(list);
    }
    public void addRow(Client_Data newData) {
        list.add(newData);
    }
    public void changeLabel(String newIteration) {
        Platform.runLater(() -> Iterati.setText(newIteration));
    }
}
