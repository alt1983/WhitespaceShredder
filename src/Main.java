import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        final String HOSTNAME = "localhost";
        final int PORT = 23334;
        final Thread server = new Thread(null, new Server(HOSTNAME, PORT));
        server.start();
        Thread.sleep(1000);
        final Thread client = new Thread(null, new Client(HOSTNAME, PORT));
        client.start();
    }

}
