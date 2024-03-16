package com.servertcp.clientservertcp_withfx;

public class LinearCongruentialGenerator {
    private long seed;
    private long a;
    private long c;
    private long m;

    public LinearCongruentialGenerator(long seed, long a, long c, long m) {
        this.seed = seed;
        this.a = a;
        this.c = c;
        this.m = m;
    }

    public long generate() {
        seed = (a * seed + c) % m;
        return seed;
    }
}
