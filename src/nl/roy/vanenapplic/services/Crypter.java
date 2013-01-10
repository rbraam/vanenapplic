/*
 * B3P Commons GIS is a library with commonly used classes for OGC
 * reading and writing. Included are wms, wfs, gml, csv and other
 * general helper classes and extensions.
 *
 * Copyright 2005 - 2008 B3Partners BV
 * 
 * This file is part of B3P Commons GIS.
 * 
 * B3P Commons GIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * B3P Commons GIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with B3P Commons GIS.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.roy.vanenapplic.services;

import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Chris
 */
public class Crypter {

    private static final Log log = LogFactory.getLog(Crypter.class);
    /**
     * Key waarmee de url wordt encrypt/decrypt.
     */
    protected final static String CHARSET = "US-ASCII";
    protected final static String encryptionAlgorithm = "DES";
    protected final static String encryptionMode = "ECB";
    protected final static String encryptionPadding = "PKCS5Padding";
    protected static SecretKey secretKey;
    protected static String cipherParameters;

    static {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptionAlgorithm);
            DESKeySpec desKeySpec = new DESKeySpec("proxy00000070707707".getBytes(CHARSET));
            secretKey = keyFactory.generateSecret(desKeySpec);
        } catch (Exception e) {
            log.error("error: ", e);
        }
        cipherParameters = encryptionAlgorithm + "/" + encryptionMode + "/" + encryptionPadding;
    }

    /**
     * Encrypt a string.
     *
     * @param clearText
     * @return clearText, encrypted
     */
    public static String encryptText(String clearText) throws Exception {
        if (clearText == null) {
            log.error("text to encrypt may not be null!");
            throw new Exception("text to encrypt may not be null!");
        }
        Base64 encoder = new Base64();
        Cipher c1 = Cipher.getInstance(cipherParameters);
        c1.init(Cipher.ENCRYPT_MODE, secretKey);
        byte clearTextBytes[];
        clearTextBytes = clearText.getBytes();
        byte encryptedText[] = c1.doFinal(clearTextBytes);
        String encryptedEncodedText = new String(encoder.encode(encryptedText), CHARSET);
        /* Verwijder eventuele \r\n karakters die door Commons-Codec 1.4
         * zijn toegevoegd. Deze zijn niet toegestaan in een cookie.
         */
        encryptedEncodedText = encryptedEncodedText.replaceAll("[\r\n]", "");

        return URLEncoder.encode(encryptedEncodedText, "utf-8");
    }

    /**
     * Decrypt a string.
     *
     * @param encryptedText
     * @return encryptedText, decrypted
     */
    public static String decryptText(String encryptedText) throws Exception {
        if (encryptedText == null) {
            return null;
        }
        String et = URLDecoder.decode(encryptedText, "utf-8");
        Base64 decoder = new Base64();
        byte decodedEncryptedText[] = decoder.decode(et.getBytes(CHARSET));
        Cipher c1 = Cipher.getInstance(cipherParameters);
        c1.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedText = c1.doFinal(decodedEncryptedText);
        String decryptedTextString = new String(decryptedText);
        return decryptedTextString;
    }
}
