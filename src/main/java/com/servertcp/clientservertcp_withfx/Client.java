package com.servertcp.clientservertcp_withfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class Client extends Application {
    @FXML
    private TextField Xnum;

    @FXML
    private TextField Ynum;

    @FXML
    private TextField Cnum;

    @FXML
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ClientFX.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Client!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    @FXML
    protected void calculate() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 12345);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                double value1 = Double.parseDouble(Xnum.getText());
                double value2 = Double.parseDouble(Ynum.getText());
                double value3 = Double.parseDouble(Cnum.getText());

                dos.writeDouble(value1);
                dos.writeDouble(value2);
                dos.writeDouble(value3);

                double result = dis.readDouble();

                // Update the UI on the JavaFX Application Thread
                Platform.runLater(() -> resultLabel.setText("Result: " + result));

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

