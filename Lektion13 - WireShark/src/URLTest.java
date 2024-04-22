import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;


public class URLTest {
    private static boolean found = false;

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://valutakurser.dk ");
        BufferedReader br = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String line;
        found = false;
        while ((line = br.readLine()) != null && !found) {
            String[] text = line.split("/");
            for (int i = 0; i < text.length - 1; i++) {
                if (text[i].equals("amerikanske-dollar")) {
                    found = true;
                }

            }
        }
        if(found) {
            String s = line.substring(278,284);
            System.out.println("Kursen for USD er: " + s);
        }
    }
}