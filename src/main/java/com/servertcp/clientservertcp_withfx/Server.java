package com.servertcp.clientservertcp_withfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Server extends Application {
    private ServerController controller;
    private Integer ID = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerFX.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        startServer();
        //startServerUDP();
    }

    //TCP connection
    private void startServer() {
        new Thread(() -> {
            try {
                //localhost server
                ServerSocket serverSocket = new ServerSocket(12345);

                //my personal ip for server
                //ServerSocket serverSocket = new ServerSocket(12345,50, InetAddress.getByName("192.168.1.222"));

                while (true) {
                    System.out.println("Waiting for client...");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected.");

                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                    double value1 = dis.readDouble();
                    double value2 = dis.readDouble();
                    double value3 = dis.readDouble();

                    double result = CalculationTask(value1, value2, value3);

                    ID++;
                    Client_Data newData = new Client_Data(ID, value1, value2, value3, result);
                    controller.addRow(newData);
                    Platform.runLater(() -> {
                        String idstr = Integer.toString(ID);
                        controller.changeLabel("Server worked with " + idstr + " users");
                    });
                    dos.writeDouble(result);

                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startServerUDP() {
        new Thread(() -> {
            try {
                DatagramSocket serverSocket = new DatagramSocket(12345, InetAddress.getByName("localhost"));
                //DatagramSocket serverSocket = new DatagramSocket(12345, InetAddress.getByName("192.168.1.222"));

                while (true) {
                    System.out.println("Waiting for client...");

                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                    serverSocket.receive(receivePacket);

                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivePacket.getData());
                    DataInputStream dis = new DataInputStream(byteArrayInputStream);
                    System.out.println("Client connected");
                    System.out.println(receivePacket.getAddress());
                    double value1 = dis.readDouble();
                    double value2 = dis.readDouble();
                    double value3 = dis.readDouble();

                    double result = CalculationTask(value1, value2, value3);

                    ID++;
                    Client_Data newData = new Client_Data(ID, value1, value2, value3, result);
                    controller.addRow(newData);

                    Platform.runLater(() -> {
                        String idstr = Integer.toString(ID);
                        controller.changeLabel("Server worked with " + idstr + " users");
                    });
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    DataOutputStream dos = new DataOutputStream(byteArrayOutputStream);
                    dos.writeDouble(result);

                    DatagramPacket sendPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(),
                            byteArrayOutputStream.size(), receivePacket.getAddress(), receivePacket.getPort());
                    serverSocket.send(sendPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private double CalculationTask(double x, double y, double c) {
        double resault = 0;
        for (int i = 1; i <= 30 ; i++) {
            resault += ((Math.pow(-i,i+1))*((Math.sin(x)*Math.cos(y)+Math.tan(c))/(Factorial(i+3))));
        }
        return resault;
    }

    private int Factorial(int iteration){
        try{
            if(iteration <= 0)
                return 0;
            int res_factorial = 1;
            for(int i = 1; i <= iteration ; i++) {
                res_factorial *= i;
            }
            return res_factorial;
        }
        catch (Exception e){
            System.err.println("An error occurred while calculating factorial: " + e.getMessage());
            return -1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

