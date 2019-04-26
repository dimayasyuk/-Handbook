package encrypt;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncryptor {
    public static String encrypt(String pass) {
        return DigestUtils.md5Hex(pass);
    }
}
