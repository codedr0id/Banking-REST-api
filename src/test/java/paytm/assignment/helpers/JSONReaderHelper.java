package paytm.assignment.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JSONReaderHelper {
    public static String read(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
        StringBuilder content = new StringBuilder();

        while (sc.hasNextLine())
            content.append(sc.nextLine());
        sc.close();

        return content.toString();
    }
}
