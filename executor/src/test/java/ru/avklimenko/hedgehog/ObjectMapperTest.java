package ru.avklimenko.hedgehog;

import org.testng.annotations.Test;
import ru.avklimenko.hedgehog.exceptions.HHCastException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import static org.testng.Assert.*;

public class ObjectMapperTest {

    @Test
    public void testMapTo() {
        Object obj = 5;
        Integer iobj = ObjectMapper.mapTo(obj, Integer.class);
        assertNotNull(iobj);
        assertEquals(iobj, new Integer(5));

        Number nobj = ObjectMapper.mapTo(obj, Number.class);
        assertNotNull(nobj);
        assertEquals(nobj, 5);
    }

    private <T> void test1(Object obj, Class<T> tClass, T exp) {
        T val = ObjectMapper.mapTo(obj, tClass);
        assertNotNull(val);
        assertEquals(val, exp);
    }

    private <T, E extends Exception> void testExc(Object obj, Class<T> tClass, Class<E> eClass) {
        boolean tr = false;
        try {
            ObjectMapper.mapTo(obj, tClass);
        } catch (Exception e) {
            if (e.getClass().isAssignableFrom(eClass)) {
                tr = true;
            }
        }
        assertTrue(tr);
    }

    @Test
    public void testMapToFromByte() {
        byte value = (byte) (Math.random() * (Byte.MAX_VALUE - Byte.MIN_VALUE) + Byte.MIN_VALUE);
        Object obj = value;

        test1(obj, Short.class, (short) value);
        test1(obj, Integer.class, (int) value);
        test1(obj, Long.class, (long) value);
        test1(obj, Float.class, (float) value);
        test1(obj, Double.class, (double) value);
        test1(obj, BigDecimal.class, new BigDecimal(value));
        test1(obj, BigInteger.class, new BigInteger(value + ""));
        test1((byte) 1, Boolean.class, true);
        test1((byte) 0, Boolean.class, false);
        test1(obj, String.class, obj + "");

        testExc((byte) 2, Boolean.class, HHCastException.class);
        testExc((byte) -1, Boolean.class, HHCastException.class);
        testExc(obj, Date.class, HHCastException.class);
    }

    @Test
    public void testMapToFromShort() {
        short value = (short) (Math.random() * (Short.MAX_VALUE - Short.MIN_VALUE) + Short.MIN_VALUE);
        Object obj = value;

        test1((short) 123, Byte.class, (byte) 123);
        test1(obj, Integer.class, (int) value);
        test1(obj, Long.class, (long) value);
        test1(obj, Float.class, (float) value);
        test1(obj, Double.class, (double) value);
        test1(obj, BigDecimal.class, new BigDecimal(value));
        test1(obj, BigInteger.class, new BigInteger(value + ""));
        test1((short) 1, Boolean.class, true);
        test1((short) 0, Boolean.class, false);
        test1(obj, String.class, obj + "");

        testExc((short) 2, Boolean.class, HHCastException.class);
        testExc((short) -1, Boolean.class, HHCastException.class);
        testExc((short) (Byte.MAX_VALUE + 1), Byte.class, HHCastException.class);
        testExc((short) (Byte.MIN_VALUE - 1), Byte.class, HHCastException.class);
    }

    @Test
    public void testMapToFromInteger() {
        int value = (int) (Math.random() * ((long) Integer.MAX_VALUE - Integer.MIN_VALUE) + Integer.MIN_VALUE);
        Object obj = value;

        test1(123, Byte.class, (byte) 123);
        test1(1234, Short.class, (short) 1234);
        test1(obj, Long.class, (long) value);
        test1(obj, Float.class, (float) value);
        test1(obj, Double.class, (double) value);
        test1(obj, BigDecimal.class, new BigDecimal(value));
        test1(obj, BigInteger.class, new BigInteger(value + ""));
        test1(1, Boolean.class, true);
        test1(0, Boolean.class, false);
        test1(obj, String.class, obj + "");

        testExc(2, Boolean.class, HHCastException.class);
        testExc(-1, Boolean.class, HHCastException.class);
        testExc(128, Byte.class, HHCastException.class);
        testExc(-129, Byte.class, HHCastException.class);
        testExc(Short.MAX_VALUE + 1, Short.class, HHCastException.class);
        testExc(Short.MIN_VALUE - 1, Short.class, HHCastException.class);
    }

    @Test
    public void testMapToFromLong() {
        long value = (long) (Math.random() * Long.MAX_VALUE) * (Math.random() < 0.5 ? 1 : -1);
        Object obj = value;

        test1(123L, Byte.class, (byte) 123);
        test1(1234L, Short.class, (short) 1234);
        test1(12345L, Integer.class, 12345);
        test1(obj, Float.class, (float) value);
        test1(obj, Double.class, (double) value);
        test1(obj, BigDecimal.class, new BigDecimal(value));
        test1(obj, BigInteger.class, new BigInteger(value + ""));
        test1(1L, Boolean.class, true);
        test1(0L, Boolean.class, false);
        test1(obj, String.class, value + "");
        test1(obj, Date.class, new Date(value));

        testExc(2L, Boolean.class, HHCastException.class);
        testExc(-1L, Boolean.class, HHCastException.class);
        testExc(128L, Byte.class, HHCastException.class);
        testExc(-129L, Byte.class, HHCastException.class);
        testExc((long) Short.MAX_VALUE + 1, Short.class, HHCastException.class);
        testExc((long) Short.MIN_VALUE - 1, Short.class, HHCastException.class);
        testExc((long) Integer.MAX_VALUE + 1, Integer.class, HHCastException.class);
        testExc((long) Integer.MIN_VALUE - 1, Integer.class, HHCastException.class);
    }

    @Test
    public void testMapToFromFloat() {
        float value = (float) (Math.random() * Float.MAX_VALUE) * (Math.random() < 0.5 ? 1 : -1);
        Object obj = value;

        test1(obj, Double.class, (double) value);
        test1(obj, BigDecimal.class, new BigDecimal(value));
        test1(obj, String.class, value + "");
    }

    @Test
    public void testMapToFromDouble() {
        double value = (Math.random() * Double.MAX_VALUE) * (Math.random() < 0.5 ? 1 : -1);
        Object obj = value;

        test1(12345.678, Float.class, 12345.678F);
        test1(obj, BigDecimal.class, new BigDecimal(value));
        test1(obj, String.class, value + "");

        testExc((double) Float.MAX_VALUE * 1.5, Float.class, HHCastException.class);
        testExc(- (double) Float.MAX_VALUE * 1.5, Float.class, HHCastException.class);
    }

    @Test
    public void testMapToFromByteArray() throws ClassNotFoundException {
        byte[] bytes = {1,2,3,4,5};
        byte[] val = (byte[]) ObjectMapper.mapTo(bytes, Class.forName("[B"));
        assertNotNull(val);
        assertEquals(val, bytes);
    }

    @Test
    public void testMapToDateFromDates() {
        long ts = new Date().getTime();
        Timestamp timestamp = new Timestamp(ts);
        java.sql.Date date = new java.sql.Date(ts);
        java.sql.Time time = new java.sql.Time(ts);

        test1(timestamp, Date.class, timestamp);
        test1(date, Date.class, date);
        test1(time, Date.class, time);
    }
}