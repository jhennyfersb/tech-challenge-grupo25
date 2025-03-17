package com.br.arraydesabores.rede.presentation.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public final class NumberUtils {

    private NumberUtils() {
    }

    public static String formatCurrency(BigDecimal value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatter.format(value);
    }

    public static String formatDecimal(Double value) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(value);
    }
}