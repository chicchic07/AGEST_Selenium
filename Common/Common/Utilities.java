package Common;

import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

public class Utilities {
    // Get project root path
    public static String getProjectPath() {
        return Paths.get("").toAbsolutePath().toString();
    }
    
    // Generate random string
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }
    
    // Generate random email
    public static String generateRandomEmail() {
        return "user" + UUID.randomUUID().toString().substring(0, 5) + "@mail.com";
    }

    // Generate random number
    public static int generateRandomNumber(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
}