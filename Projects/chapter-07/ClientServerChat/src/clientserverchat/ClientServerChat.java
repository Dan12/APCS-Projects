package clientserverchat;

import javax.swing.JOptionPane;

/**
 * A multithreaded chat room server.  When a client connects the
 * server requests a screen name by sending the client the
 * text "SUBMITNAME", and keeps requesting a name until
 * a unique one is received.  After a client submits a unique
 * name, the server acknowledges with "NAMEACCEPTED".  Then
 * all messages from that client will be broadcast to all other
 * clients that have submitted a unique screen name.  The
 * broadcast messages are prefixed with "MESSAGE ".
 * Also, Experiment with multithreading by running client and updating
 * JPanel simultaneously
 */

public class ClientServerChat {

    /**
     * The port that the server listens on.
     */
    public static final int PORT = 9001;
    
    boolean isServer;
    
    
    //JFrame for displaying messages
    public ClientServerChat(){}
    
    public static void main(String[] args) throws Exception {
        ClientServerChat client = new ClientServerChat();
        client.Go();
    }
    
    private void Go(){
        int result = JOptionPane.showOptionDialog(null, "Is this the Server", "Server?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        //No-1,Yes-0
        System.out.println(result);
        if(result == 0){
            Server server = new Server();
            server.runServer();
        }
        else{
            Client client = new Client();
            client.runClient();
        }
    }
    
    public static String decodeLine(String inLines){
        String temp = "";
        String[] lineSplit = inLines.split("~n`");
        for(int i = 0; i < lineSplit.length; i++)
            temp+=lineSplit[i]+"\n";
        return temp;
    }
}