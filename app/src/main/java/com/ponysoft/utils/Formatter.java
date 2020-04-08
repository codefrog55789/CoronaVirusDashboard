package com.ponysoft.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Formatter {

    public static String numberFormat(int n) {

        DecimalFormat fmt = new DecimalFormat(",###,###");
        return fmt.format(new BigDecimal(n));
    }
}
