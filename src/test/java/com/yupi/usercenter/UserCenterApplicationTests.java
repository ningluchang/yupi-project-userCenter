package com.yupi.usercenter;

import com.yupi.usercenter.utils.PasswordEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserCenterApplicationTests {


    @Test
    void contextLoads() {
        String plainPassword = "123456";
        // 生成盐值
        String salt = PasswordEncryptor.generateSalt();
        // 对密码进行加密
        String encryptedPassword = PasswordEncryptor.encryptPassword(plainPassword, salt);
        System.out.println(salt);
        System.out.println(encryptedPassword);
    }

}
