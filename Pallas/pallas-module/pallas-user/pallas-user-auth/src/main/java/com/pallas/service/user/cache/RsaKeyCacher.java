package com.pallas.service.user.cache;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.cache.cacher.AbstractHashCacher;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jax
 * @time: 2020/8/31 9:36
 * @desc:
 */
@Component
public class RsaKeyCacher extends AbstractHashCacher<String> {

    private static final String KEY = "pls:rsa-key";
    private static final String PRIVATE_KEY = "private";
    private static final String PUBLIC_KEY = "public";

    @Override
    public String getKey() {
        return KEY;
    }

    public String decrypt(String str) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
            byte[] input = Base64.getDecoder().decode(str.getBytes("UTF-8"));
            return new String(cipher.doFinal(input));
        } catch (Exception e) {
            throw new PlsException(ResultType.ENCRYPTION_ERR);
        }
    }

    public PrivateKey getPrivateKey() {
        String privateKeyStr = getData(PRIVATE_KEY);
        byte[] privateKey = Base64.getDecoder().decode(privateKeyStr);
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKey));
        } catch (Exception ex) {
            throw new PlsException(ResultType.ENCRYPTION_ERR, "系统密钥异常");
        }
    }

    public String getPublicKeyStr() {
        return getData(PUBLIC_KEY);
    }

    public PublicKey getPublicKey() {
        String publicKeyStr = getData(PUBLIC_KEY);
        byte[] publicKey = Base64.getDecoder().decode(publicKeyStr);
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey));
        } catch (Exception ex) {
            throw new PlsException(ResultType.ENCRYPTION_ERR, "系统密钥异常");
        }
    }

    @Override
    public Map<String, String> loadData() {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        Map<String, String> keyMap = new HashMap<>(2);
        keyMap.put(PRIVATE_KEY, Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        keyMap.put(PUBLIC_KEY, Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        return keyMap;
    }

    @Override
    public String getData(String key) {
        if (this.ifExist(key)) {
            return this.getCache(key);
        }
        Map<String, String> data = this.loadData();
        cacheData(data);
        return data.get(key);
    }
}
