package base;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.util.Properties;

public class PasswordDecrypt {

    private static final String mpCryptoPassword = "17Jul98";

    public PasswordDecrypt() {
    }

    public static String decrypt_Password(String encryptedPassword) {
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(mpCryptoPassword);
        return decryptor.decrypt(encryptedPassword);
    }
}