package kr.co.comes.intranet.infra.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Value("${jasypt.encryptor.password}")
    private String jasyptPassword;


    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘 설정
        encryptor.setPassword(jasyptPassword); // 비밀 키 설정
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
