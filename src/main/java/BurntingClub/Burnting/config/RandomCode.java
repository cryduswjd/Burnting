package BurntingClub.Burnting.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomCode {
    private int randomStrLen = 8;
    public void setRandomStrLen(int randomStrLen) {
        this.randomStrLen = randomStrLen;
    }
    public String generateRandomStrAndAssert() {
        boolean useLetters = true;
        boolean useNumbers = true;
        String randomStr = RandomStringUtils.random(randomStrLen, useLetters, useNumbers);
        System.out.println("[generateRandomStrAndAssert] randomStr : " + randomStr);
        int calculatedStrLen = calculateStrLen(randomStr);
        assertEquals(randomStrLen, calculatedStrLen);
        return randomStr;
    }
    private int calculateStrLen(String str) {
        return str.length();
    }
}