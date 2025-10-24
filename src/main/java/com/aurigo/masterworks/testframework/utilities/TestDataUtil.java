package com.aurigo.masterworks.testframework.utilities;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataUtil {

    public static ThreadLocalRandom random;

    public static int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
