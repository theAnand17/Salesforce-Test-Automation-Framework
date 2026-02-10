package base;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PasswordDecrypt {

    private static final String SECRETS_FILE = "secrets.properties";
    private static final String SECRETS_KEY = "jasypt.encryption.key";

    public PasswordDecrypt() {
    }

    /**
     * Retrieves the Jasypt encryption key from (checked in order):
     *   1. secrets.properties file (jasypt.encryption.key)
     *   2. System property  -Djasypt.key=...
     *   3. Environment variable  JASYPT_KEY
     */
    private static String getEncryptionKey() {
        // 1. Try secrets.properties file
        File secretsFile = new File(System.getProperty("user.dir") + File.separator + SECRETS_FILE);
        if (secretsFile.exists()) {
            try (FileReader reader = new FileReader(secretsFile)) {
                Properties secrets = new Properties();
                secrets.load(reader);
                String key = secrets.getProperty(SECRETS_KEY);
                if (key != null && !key.isBlank()) {
                    return key;
                }
            } catch (IOException e) {
                RunLog.warn("Could not read " + SECRETS_FILE + ": " + e.getMessage());
            }
        }

        // 2. Try system property (-Djasypt.key=...)
        String key = System.getProperty("jasypt.key");
        if (key != null && !key.isBlank()) {
            return key;
        }

        // 3. Try environment variable
        key = System.getenv("JASYPT_KEY");
        if (key != null && !key.isBlank()) {
            return key;
        }

        throw new RuntimeException(
                "Jasypt encryption key not found. Provide it via one of:\n"
                + "  1. Create a 'secrets.properties' file with: jasypt.encryption.key=<your-key>\n"
                + "  2. System property: -Djasypt.key=<your-key>\n"
                + "  3. Environment variable: JASYPT_KEY=<your-key>"
        );
    }

    public static String decrypt_Password(String encryptedPassword) {
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(getEncryptionKey());
        return decryptor.decrypt(encryptedPassword);
    }
}