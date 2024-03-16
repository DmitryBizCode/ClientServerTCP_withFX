package com.servertcp.clientservertcp_withfx;

public class EikhnauerLenaGenerator {
    private final int a;
    private final int c;
    private final int m;
    private int x;

    public EikhnauerLenaGenerator(int a, int c, int m) {
        this.a = a;
        this.c = c;
        this.m = m;
        this.x = ModInv(c, m); // Початкове значення x - зворотне до c по модулю m
    }

    public int nextInt() {
        if (x == 0) {
            return c;
        } else {
            int y = (a * ModInv(x, m) + c) % m; // Розрахунок наступного числа з використанням зворотного елемента x
            x = y;
            return y;
        }
    }

    public static int ModInv(long a, int n) {
        long b0 = n, t = 0, q = 0, x0 = 0, x1 = 1;

        if (n == 1) return 1;

        while (a > 1) {
            q = a / n;
            t = n;
            n = (int)(a % n);
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0) x1 += b0;

        return (int)x1;
    }
}
