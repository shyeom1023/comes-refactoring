package kr.co.comes.intranet.infra.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.junit.jupiter.api.Test;

class JasyptConfigTest {
    @Test
    void encrypt() {
        String encrypt = stringEncryptor().encrypt("duatjrgus1");
        System.out.println("result:" + encrypt);
    }

    @Test
    void decrypt() {
        String decrypt = stringEncryptor().decrypt("vq+Ne+CDb1+yoitpkjKLkknLXs6XQ8GbGCxLfvoOuqw=");
        System.out.println("result:" + decrypt);

    }

    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘 설정
        encryptor.setPassword("intranet"); // 비밀 키 설정
        encryptor.setPoolSize(5); // 풀 크기 설정

        // String Output Type 설정 (BASE64, HEXADECIMAL, NO_ENCODING)
        encryptor.setStringOutputType("BASE64");

        // Salt Generator 설정
        RandomSaltGenerator saltGenerator = new RandomSaltGenerator();
        encryptor.setSaltGenerator(saltGenerator);

        // IV Generator 설정
        RandomIvGenerator ivGenerator = new RandomIvGenerator();
        encryptor.setIvGenerator(ivGenerator);

        // Key Obfuscation 설정
        encryptor.setKeyObtentionIterations(1000);

        return encryptor;
    }

}