import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class URLConnectionVar {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input URL: ");
        String str = in.nextLine();
        java.net.URL url = new URL(str);

        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 YaBrowser/21.8.3.614 Yowser/2.5 Safari/537.36");
        connection.connect();
        try (InputStream is = connection.getInputStream()) {
            Map<String, List<String>> header = connection.getHeaderFields();

            for (String key : header.keySet()) {
                System.out.print(key + " : ");
                for (String s : header.get(key)) {
                        System.out.print(s + "\n");
                    }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            try (FileWriter writer_file = new FileWriter("site.txt", false)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer_file.write(line + "\n");
                }
                writer_file.flush();

            }
        }

    }

}
