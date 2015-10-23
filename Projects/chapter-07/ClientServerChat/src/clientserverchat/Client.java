package clientserverchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Client, connects to server and displays messages in textBox
public class Client{
    //Client variables
    private BufferedReader clientIn;
    public static PrintWriter clientOut;
    JFrame frame = new JFrame("Client");
    JFrame drawFrame = new JFrame("Draw");
    private String text = "";
    DrawStuff ds;
    public static String name;
    public static ArrayList<Square> squares = new ArrayList<Square>();
    public static boolean settingSquares = false;
    public static boolean drawingSquares = false;
    String autoIpAdress = "";

    public Client(){
        // Layout GUI
        ds = new DrawStuff();
        drawFrame.setContentPane(ds);
        drawFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawFrame.setLocation(10, 10);
        drawFrame.setVisible(true);
        drawFrame.pack();
    }

    public void runClient() {
        ClientThread ct = new ClientThread();
        ExecutorService ex = Executors.newCachedThreadPool();
        ex.execute(ct);
        ex.execute(ds);

        ex.shutdown();
    }

    //Runnable client class to constantly get data from server
    private class ClientThread implements Runnable{

        public ClientThread(){}

        @Override
        public void run() {
            // Make connection and initialize streams
            String serverAddress = getServerAddress();
            Socket socket = null;
            try {
                socket = new Socket(serverAddress, ClientServerChat.PORT);
            } catch (IOException e) {System.out.println(e);}
            try {
                //recieves messages from server
                clientIn = new BufferedReader(
                    new InputStreamReader(
                        socket.getInputStream()
                    )
                );
            } catch (IOException e) {System.out.println(e);}
            try {
                //sends messages to server
                clientOut = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {System.out.println(e);}

            // Process all messages from server, according to the protocol.
            while (true) {
                String allLines = "";
                try {
                    allLines = clientIn.readLine();
                } catch (IOException e) {System.out.println(e);}
//                System.out.println(allLines);
                if(allLines == null) continue;
                allLines = ClientServerChat.decodeLine(allLines);
//                System.out.println(allLines);
                if (allLines.startsWith("SUBMITNAME")) {
                    name = getScreenName();
                    clientOut.println(name);
                } else if (allLines.startsWith("NAMEACCEPTED")) {
                    ds.enterField.setEditable(true);
                } else if (allLines.startsWith("MESSAGE")) {
                    ds.messageDisplayArea.setText(allLines.substring(8));
                    text = allLines.substring(8);
                    System.out.println(text);
                }
                else if (allLines.startsWith("SQUARE")){
                    if(!drawingSquares){
                        settingSquares = true;
                        squares.clear();
                        String actualText = allLines.substring(7);   
                        String[] squareInfos = actualText.split("\n");
                        for(int i = 0; i < squareInfos.length; i++){
    //                        System.out.println("**"+squareInfos[i]+"**");
                            String[] parameters = squareInfos[i].split(",");
                            squares.add(new Square(parameters[0], Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2])));
                        }
                        settingSquares = false;
                        drawingSquares = true;
                    }
                }
            }
        }

    }

    //prompt for ip adress and user name
    private String getServerAddress() {
        if(autoIpAdress.equals(""))
        return JOptionPane.showInputDialog(
            null,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
        else
            return autoIpAdress;
    }

    private String getScreenName() {
        return JOptionPane.showInputDialog(
            null,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }
}