package ru.avklimenko.hedgehog.cache;

public class SQLCacheOptions {
    private int rawCaheSize;
    private int paramCaheSize;
    private int totalCacheSize;
    private int secondsInCache;

    public int getRawCaheSize() {
        return rawCaheSize;
    }

    public void setRawCaheSize(int rawCaheSize) {
        this.rawCaheSize = rawCaheSize;
    }

    public int getParamCaheSize() {
        return paramCaheSize;
    }

    public void setParamCaheSize(int paramCaheSize) {
        this.paramCaheSize = paramCaheSize;
    }

    public int getTotalCacheSize() {
        return totalCacheSize;
    }

    public void setTotalCacheSize(int totalCacheSize) {
        this.totalCacheSize = totalCacheSize;
    }

    public int getSecondsInCache() {
        return secondsInCache;
    }

    public void setSecondsInCache(int secondsInCache) {
        this.secondsInCache = secondsInCache;
    }
}
