package Operations;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;


/**
 * Created by Henrik on 2016-04-25.
 */
public class EncryptPassword {
        StandardPBEStringEncryptor encryptor;
    public EncryptPassword(){
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("hejhej");
    }



    public String encryptPassword(String password){
        String encryptedPassword = encryptor.encrypt(password);
        return encryptedPassword;
    }

    public String decryptPassword(String encryptedPassword){
        String password = encryptor.decrypt(encryptedPassword);
        return password;
    }
}
