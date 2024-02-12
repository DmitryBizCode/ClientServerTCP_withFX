package com.servertcp.clientservertcp_withfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        resultLabel = new Label("Result will be displayed here");

        VBox root = new VBox(resultLabel);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("TCP Server FX");
        primaryStage.setScene(scene);
        primaryStage.show();

        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(12345);

                while (true) {
                    System.out.println("Waiting for client...");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected.");

                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                    double value1 = dis.readDouble();
                    double value2 = dis.readDouble();
                    double value3 = dis.readDouble();

                    double result = performCalculation(value1, value2, value3);

                    // Відображення результату на UI потоці
                    Platform.runLater(() -> resultLabel.setText("Result: " + result));

                    dos.writeDouble(result);

                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private double performCalculation(double value1, double value2, double value3) {
        return value1 + value2 * value3;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

