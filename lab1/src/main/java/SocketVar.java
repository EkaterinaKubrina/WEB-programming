import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class SocketVar {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input URL: ");
        String str = in.nextLine();
        java.net.URL url = new URL(str);


        Socket socket = new Socket(url.getHost(), 80);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        writer.write("GET / HTTP/1.1\r\n"
                + "Host: " + url.getHost() + "\r\n"
                + "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 YaBrowser/21.8.3.614 Yowser/2.5 Safari/537.36\r\n"
                + "Connection: Close\r\n"
                + "\r\n");
        writer.flush();


        try (FileWriter writer_file = new FileWriter("site.txt", false)) {
            String line;
            boolean flag = true;

            while ((line = reader.readLine()) != null) {
                if (flag) {
                    if (line.equals("")) {
                        flag = false;
                    } else {
                        System.out.println(line);
                    }
                } else {
                    writer_file.write(line + "\n");
                }
            }
            writer_file.flush();

        }
    }

}

