
import java.io.*;
import java.net.*;

public class ChatClient {

    private static final String SERVER_ADDRESS = "localhost"; // Server address
    private static final int SERVER_PORT = 12345; // Port number

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)); BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Start a thread to listen for incoming messages from the server
            new Thread(new ServerListener(serverInput)).start();

            // Continuously read user input and send messages to the server
            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message); // Send the message to the server
            }

        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    // Listener for messages from the server
    private static class ServerListener implements Runnable {

        private BufferedReader serverInput;

        public ServerListener(BufferedReader serverInput) {
            this.serverInput = serverInput;
        }

        public void run() {
            try {
                String message;
                while ((message = serverInput.readLine()) != null) {
                    System.out.println(message); // Display the message from the server
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
            }
        }
    }
}
