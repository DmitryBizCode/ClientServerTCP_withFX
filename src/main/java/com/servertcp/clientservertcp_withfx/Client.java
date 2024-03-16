package com.servertcp.clientservertcp_withfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client extends Application {
    @FXML
    private TextField Xnum;

    @FXML
    private TextField Ynum;

    @FXML
    private TextField Cnum;

    @FXML
    private Label resultLabel;
    @FXML
    private CheckBox TCPUDP;
    @FXML
    private CheckBox Linear;
    @FXML
    private CheckBox EikhnauerLena;
    @FXML
    private void TCPUDPConnectionCalculate(){
        if(TCPUDP.isSelected())
            calculateTCP();
        else
            calculateUDP();
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ClientFX.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Client!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    protected void calculateTCP() {
        new Thread(() -> {
            try {
                if(Xnum.getText().isEmpty() && Ynum.getText().isEmpty() && Cnum.getText().isEmpty()){
                    if(Linear.isSelected()) {
                        // Параметри генератора
                        long seed = 1; // початкове значення (сід)
                        long a = 1664525; // множник
                        long c = 1013904223; // приріст
                        long m = 4294967296L; // модуль (2^32)
                        LinearCongruentialGenerator linearG = new LinearCongruentialGenerator(seed, a, c, m);
                        Xnum.setText(String.valueOf(linearG.generate()));
                        Ynum.setText(String.valueOf(linearG.generate()));
                        Cnum.setText(String.valueOf(linearG.generate()));
                    }
                    else if(EikhnauerLena.isSelected()) {
                        EikhnauerLenaGenerator generator = new EikhnauerLenaGenerator(11, 7, 29);
                        Xnum.setText(String.valueOf(generator.nextInt()));
                        Ynum.setText(String.valueOf(generator.nextInt()));
                        Cnum.setText(String.valueOf(generator.nextInt()));
                    }
                }
                //not empty
                String xInput = Xnum.getText();
                String yInput = Ynum.getText();
                String cInput = Cnum.getText();

                if (Xnum.getText().isEmpty() || Ynum.getText().isEmpty() || Cnum.getText().isEmpty()) {
                    Platform.runLater(() -> resultLabel.setText("Please enter valid numbers"));
                    Platform.runLater(() -> {
                        resultLabel.setText("Please enter valid numbers");
                        Xnum.clear();
                        Ynum.clear();
                        Cnum.clear();
                    });
                    return;
                }
                try {
                    //is number
                    double value1 = Double.parseDouble(xInput);
                    double value2 = Double.parseDouble(yInput);
                    double value3 = Double.parseDouble(cInput);

                    //localhost server
                    Socket socket = new Socket("localhost", 12345);
                    //personal ip for server
                    //Socket socket = new Socket("192.168.1.222", 12345);

                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeDouble(value1);
                    dos.writeDouble(value2);
                    dos.writeDouble(value3);

                    double result = dis.readDouble();

                    Platform.runLater(() -> resultLabel.setText("Result: " + result));
                    socket.close();
                } catch (NumberFormatException e) {
                    Platform.runLater(() -> resultLabel.setText("Please enter valid numbers"));
                    Platform.runLater(() -> {
                        resultLabel.setText("Please enter valid numbers");
                        Xnum.clear();
                        Ynum.clear();
                        Cnum.clear();
                    });
                }


            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> {

                    resultLabel.setText("Please enter valid numbers");
                    Xnum.clear();
                    Ynum.clear();
                    Cnum.clear();
                });
            }
        }).start();
    }
    private void calculateUDP() {
        new Thread(() -> {
            try {
                String xInput = Xnum.getText();
                String yInput = Ynum.getText();
                String cInput = Cnum.getText();

                if (xInput.isEmpty() || yInput.isEmpty() || cInput.isEmpty()) {
                    Platform.runLater(() -> resultLabel.setText("Please enter valid numbers"));
                    return;
                }

                try {
                    double value1 = Double.parseDouble(xInput);
                    double value2 = Double.parseDouble(yInput);
                    double value3 = Double.parseDouble(cInput);

                    InetAddress serverAddress = InetAddress.getByName("localhost");
                    int serverPort = 12345;

                    DatagramSocket socket = new DatagramSocket();

                    // Send data to the server
                    ByteBuffer buffer = ByteBuffer.allocate(24);
                    buffer.putDouble(value1);
                    buffer.putDouble(value2);
                    buffer.putDouble(value3);

                    byte[] sendData = buffer.array();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                    socket.send(sendPacket);

                    // Receive result from the server
                    byte[] receiveData = new byte[8];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);

                    double result = ByteBuffer.wrap(receiveData).getDouble();

                    Platform.runLater(() -> resultLabel.setText("Result: " + result));

                    socket.close();
                } catch (NumberFormatException e) {
                    Platform.runLater(() -> resultLabel.setText("Please enter valid numbers"));
                }
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> resultLabel.setText("Error occurred"));
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

