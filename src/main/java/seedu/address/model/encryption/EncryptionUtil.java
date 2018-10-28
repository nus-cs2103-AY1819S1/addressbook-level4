package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.hash.Hashing;
import seedu.address.commons.exceptions.IllegalValueException;

public class EncryptionUtil {

    public final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public final static String DEFAULT_KEY = Hashing.murmur3_128().hashString("", DEFAULT_CHARSET).toString();

    private static String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5PADDING";
    private static String KEY_ALGORITHM = "AES";

    public static String decryptString(String toDecrypt, String password) throws IllegalValueException {
        requireAllNonNull(toDecrypt, password);
        Key key = new SecretKeySpec(password.getBytes(), KEY_ALGORITHM);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(toDecrypt));
            return new String(decryptedBytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException(e.getMessage());
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalValueException(String.format("Error decrypting String: %s", toDecrypt), e);
        }
    }

    public static String encryptString(String toEncrypt, String password) throws IllegalValueException {
        requireAllNonNull(toEncrypt, password);
        Key key = new SecretKeySpec(password.getBytes(), KEY_ALGORITHM);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(toEncrypt.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException(e.getMessage());
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalValueException(String.format("Error encrypting String: %s", toEncrypt), e);
        }
    }

    public static String createEncryptionKey(String password) {
        requireNonNull(password);
        return Hashing.murmur3_128().hashString(password, DEFAULT_CHARSET).toString();
    }
}
