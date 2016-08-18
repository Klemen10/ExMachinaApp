package com.reevel.testreevel.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Klemen on 17.8.2016.
 */
public class UtilTest {

    float min = 0;
    float max = 1;
    float a = 0;
    float b = 100;

    @Test
    public void modulate1() throws Exception {

        float value = 0.5f;
        float result = Util.modulate(value, min, max, a, b);
        assertEquals(50, result, 0.01);
    }

    @Test
    public void modulate2() throws Exception {

        float value = 1f;
        float result = Util.modulate(value, min, max, a, b);
        assertEquals(100, result, 0.01);
    }

    @Test
    public void modulate3() throws Exception {
        float min = 0;
        float max = 400;
        float a = 0;
        float b = -100;
        float value = 400;
        float result = Util.modulate(value, min, max, a, b);
        assertEquals(-100, result, 0.01);
    }

    @Test
    public void modulateMax() throws Exception {

        float value = 101f;
        float result = Util.modulate(value, min, max, a, b);
        assertEquals(100, result, 0.01);
    }

    @Test
    public void modulateMin() throws Exception {

        float value = -10f;
        float result = Util.modulate(value, min, max, a, b);
        assertEquals(0, result, 0.01);
    }

}