import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server extends Thread {

    private String hostname;
    private int port;

    public Server(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String whitespaceShredder(String string) {

        char[] charArray = string.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != ' ') {
                sb.append(charArray[i]);
            }
        }
        return sb.toString();
    }

    @Override
    public void run() {
        ServerSocketChannel serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(hostname, port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Сервер запущен");
        while (true) {
            try (SocketChannel socketChannel = serverChannel.accept()) {
                System.out.println("К серверу подключился клиент");
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;
                    final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    inputBuffer.clear();
                    String result = whitespaceShredder(msg);
                    socketChannel.write(ByteBuffer.wrap((result).getBytes(StandardCharsets.UTF_8)));
                }
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }
        }

    }

}

