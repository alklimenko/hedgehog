package ru.avklimenko.hedgehog;

import ru.avklimenko.hedgehog.exceptions.HHCastException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class ObjectMapper {
    private static final BigDecimal INT_MAX_VALUE = new BigDecimal(Integer.MAX_VALUE);
    private static final BigDecimal INT_MIN_VALUE = new BigDecimal(Integer.MIN_VALUE);
    private static final BigDecimal LONG_MAX_VALUE = new BigDecimal(Long.MAX_VALUE);
    private static final BigDecimal LONG_MIN_VALUE = new BigDecimal(Long.MIN_VALUE);
    private static final BigDecimal FLOAT_MAX_VALUE = new BigDecimal(Float.MAX_VALUE);
    private static final BigDecimal FLOAT_MIN_VALUE = new BigDecimal(Float.MIN_VALUE);
    private static final BigDecimal DOUBLE_MAX_VALUE = new BigDecimal(Double.MAX_VALUE);
    private static final BigDecimal DOUBLE_MIN_VALUE = new BigDecimal(Double.MIN_VALUE);

    private ObjectMapper() {
        //
    }

    interface Transformable {
        Object transform(Object obj);
    }

    private static class KeyClass2 {
        Class from;
        Class to;

        KeyClass2(Class from, Class to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            return from.hashCode() + to.hashCode() * 3 + 11;
        }

        @Override
        public String toString() {
            return from.getName() + "->" + to.getName();
        }

        @Override
        public boolean equals(Object key) {
            if (key == this) {
                return true;
            }
            if (!key.getClass().equals(KeyClass2.class)) {
                return false;
            }
            return hashCode() == key.hashCode() && (from.equals(((KeyClass2) key).from) && to.equals(((KeyClass2) key).to));
        }
    }

    private static Map<KeyClass2, Transformable> transform = new HashMap<>();

    private static void addTransform(Class fromC, Class toC, Transformable lambda) {
        transform.put(new KeyClass2(fromC, toC), lambda);
    }

    static {
        // BYTE
        addTransform(Byte.class, Short.class, x -> ((Byte) x).shortValue());
        addTransform(Byte.class, Integer.class, x -> ((Byte) x).intValue());
        addTransform(Byte.class, Long.class, x -> ((Byte) x).longValue());
        addTransform(Byte.class, Float.class, x -> ((Byte) x).floatValue());
        addTransform(Byte.class, Double.class, x -> ((Byte) x).doubleValue());
        addTransform(Byte.class, BigDecimal.class, x -> new BigDecimal((Byte) x));
        addTransform(Byte.class, BigInteger.class, x -> new BigInteger(x.toString()));
        addTransform(Byte.class, Boolean.class, x -> {
            if ((Byte) x < 0 || (Byte) x > 1) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Byte to the value of the class Boolean! The value goes beyond the bounds!");
            }
            return (Byte) x == 1;
        });

        // SHORT
        addTransform(Short.class, Byte.class, x -> {
            if ((Short) x > Byte.MAX_VALUE || (Short) x < Byte.MIN_VALUE) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Short to the value of the class Byte! The value goes beyond the bounds!");
            }
            return ((Short) x).byteValue();
        });
        addTransform(Short.class, Integer.class, x -> ((Short) x).intValue());
        addTransform(Short.class, Long.class, x -> ((Short) x).longValue());
        addTransform(Short.class, Float.class, x -> ((Short) x).floatValue());
        addTransform(Short.class, Double.class, x -> ((Short) x).doubleValue());
        addTransform(Short.class, BigDecimal.class, x -> new BigDecimal((Short) x));
        addTransform(Short.class, BigInteger.class, x -> new BigInteger(x.toString()));
        addTransform(Short.class, Boolean.class, x -> {
            if ((Short) x < 0 || (Short) x > 1) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Short to the value of the class Boolean! The value goes beyond the bounds!");
            }
            return (Short) x == 1;
        });

        // INTEGER
        addTransform(Integer.class, Byte.class, x -> {
            if ((Integer) x > Byte.MAX_VALUE || (Integer) x < Byte.MIN_VALUE) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Integer to the value of the class Byte! The value goes beyond the bounds!");
            }
            return ((Integer) x).byteValue();
        });
        addTransform(Integer.class, Short.class, x -> {
            if ((Integer) x > Short.MAX_VALUE || (Integer) x < Short.MIN_VALUE) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Integer to the value of the class Short! The value goes beyond the bounds!");
            }
            return ((Integer) x).shortValue();
        });
        addTransform(Integer.class, Long.class, x -> ((Integer) x).longValue());
        addTransform(Integer.class, Float.class, x -> ((Integer) x).floatValue());
        addTransform(Integer.class, Double.class, x -> ((Integer) x).doubleValue());
        addTransform(Integer.class, BigDecimal.class, x -> new BigDecimal((Integer) x));
        addTransform(Integer.class, BigInteger.class, x -> new BigInteger(x.toString()));
        addTransform(Integer.class, Boolean.class, x -> {
            if ((Integer) x < 0 || (Integer) x > 1) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Integer to the value of the class Boolean! The value goes beyond the bounds!");
            }
            return (Integer) x == 1;
        });

        // LONG
        addTransform(Long.class, Byte.class, x -> {
            if ((Long) x > Byte.MAX_VALUE || (Long) x < Byte.MIN_VALUE) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Long to the value of the class Byte! The value goes beyond the bounds!");
            }
            return ((Long) x).byteValue();
        });
        addTransform(Long.class, Short.class, x -> {
            if ((Long) x > Short.MAX_VALUE || (Long) x < Short.MIN_VALUE) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Long to the value of the class Short! The value goes beyond the bounds!");
            }
            return ((Long) x).shortValue();
        });
        addTransform(Long.class, Integer.class, x -> {
            if ((Long) x > Integer.MAX_VALUE || (Long) x < Integer.MIN_VALUE) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Long to the value of the class Integer! The value goes beyond the bounds!");
            }
            return ((Long) x).intValue();
        });
        addTransform(Long.class, Float.class, x -> ((Long) x).floatValue());
        addTransform(Long.class, Double.class, x -> ((Long) x).doubleValue());
        addTransform(Long.class, BigDecimal.class, x -> new BigDecimal((Long) x));
        addTransform(Long.class, BigInteger.class, x -> new BigInteger(x.toString()));
        addTransform(Long.class, Date.class, x -> new Date((Long) x));
        addTransform(Long.class, Boolean.class, x -> {
            if ((Long) x < 0 || (Long) x > 1) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Long to the value of the class Boolean! The value goes beyond the bounds!");
            }
            return (Long) x == 1;
        });

        // BOOLEAN
        addTransform(Boolean.class, Byte.class, x -> (Boolean) x ? (byte) 0 : (byte) 1);
        addTransform(Boolean.class, Short.class, x -> (Boolean) x ? (short) 0 : (short) 1);
        addTransform(Boolean.class, Integer.class, x -> (Boolean) x ? 0 : 1);
        addTransform(Boolean.class, Long.class, x -> (Boolean) x ? 0L : 1L);

        // FLOAT
        addTransform(Float.class, Double.class, x -> ((Float) x).doubleValue());
        addTransform(Float.class, BigDecimal.class, x -> new BigDecimal((Float) x));

        // DOUBLE
        addTransform(Double.class, Float.class, x -> {
            if (((Double) x).compareTo((double) Float.MAX_VALUE) > 0 || ((Double) x).compareTo(-(double) Float.MAX_VALUE) < 0) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class Double to the value of the class Float! The value goes beyond the bounds!");
            }
            return ((Double) x).floatValue();
        });
        addTransform(Double.class, BigDecimal.class, x -> new BigDecimal((Double) x));

        // BIGDECIMAL
        addTransform(BigDecimal.class, Integer.class, x -> {
            if (((BigDecimal) x).compareTo(INT_MAX_VALUE) > 0 || ((BigDecimal) x).compareTo(INT_MIN_VALUE) < 0) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class BigDecimal to the value of the class Integer! The value goes beyond the bounds!");
            }
            if (((BigDecimal) x).abs().compareTo((BigDecimal) x) != 0) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class BigDecimal to the value of the class Integer! The value is not an integer!");
            }
            return ((BigDecimal) x).intValue();
        });
        addTransform(BigDecimal.class, Long.class, x -> {
            if (((BigDecimal) x).compareTo(LONG_MAX_VALUE) > 0 || ((BigDecimal) x).compareTo(LONG_MIN_VALUE) < 0) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class BigDecimal to the value of the class Long! The value goes beyond the bounds!");
            }
            if (((BigDecimal) x).abs().compareTo((BigDecimal) x) != 0) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class BigDecimal to the value of the class Long! The value is not an integer!");
            }
            return ((BigDecimal) x).longValue();
        });
        addTransform(BigDecimal.class, Float.class, x -> {
            if (((BigDecimal) x).compareTo(FLOAT_MAX_VALUE) > 0 || ((BigDecimal) x).compareTo(FLOAT_MIN_VALUE) < 0) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class BigDecimal to the value of the class Float! The value goes beyond the bounds!");
            }
            return ((BigDecimal) x).floatValue();
        });
        addTransform(BigDecimal.class, Double.class, x -> {
            if (((BigDecimal) x).compareTo(DOUBLE_MAX_VALUE) > 0 || ((BigDecimal) x).compareTo(DOUBLE_MIN_VALUE) < 0) {
                throw new HHCastException("It is impossible to convert the value " + x.toString()
                        + " of the class BigDecimal to the value of the class Double! The value goes beyond the bounds!");
            }
            return ((BigDecimal) x).doubleValue();
        });
    }

    @SuppressWarnings("unchecked")
    public static <T> T mapTo(Object obj, Class<T> tClass) {
        if (obj == null) {
            return null;
        }

        // преобразование к базовому классу
        if (tClass.isAssignableFrom(obj.getClass())) {
            return (T) obj;
        }
        // преобразование к строке
        if (tClass.equals(String.class)) {
            return (T) obj.toString();
        }

        KeyClass2 key = new KeyClass2(obj.getClass(), tClass);
        if (transform.containsKey(key)) {
            return (T) transform.get(key).transform(obj);
        }

        throw new HHCastException("Cannot convert value of the class \"" + obj.getClass().getName() + "\" to the value of the class \"" + tClass.getName() + "\"!");
    }
}
