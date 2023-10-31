package BurntingClub.Burnting.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomCode {
    int randomStrLen = 0;
    @BeforeEach
    void initRandomStrLen() {
        // 랜덤 문자열 길이 설정
        randomStrLen = 8;
    }
    int calculateStrLen(String str) {
        // 문자열의 길이 계산 메서드
        return str.length();
    }
    void generateRandomStrAndAssert() {
        boolean useLetters = true;
        boolean useNumbers = true;
        String randomStr = RandomStringUtils.random(randomStrLen, useLetters, useNumbers);

        System.out.println("[generateRandomStrAndAssert] randomStr : " + randomStr);

        int calculatedStrLen = calculateStrLen(randomStr);

        // assertEquals로 예상한 값과 일치하는지 검증
        assertEquals(randomStrLen, calculatedStrLen);
    }
}
