package com.smart.scheduler.search;

public class KMPSearcher {

    public static boolean searchPattern(String text, String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return true;
        }
        if (text == null || text.isEmpty()) {
            return false;
        }

        String lowerText = text.toLowerCase();
        String lowerPattern = pattern.toLowerCase();

        int[] lps = computeLPSArray(lowerPattern);
        int i = 0;
        int j = 0;

        while (i < lowerText.length()) {
            if (lowerPattern.charAt(j) == lowerText.charAt(i)) {
                i++;
                j++;
            }
            if (j == lowerPattern.length()) {
                return true;
            } else if (i < lowerText.length() && lowerPattern.charAt(j) != lowerText.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false;
    }

    private static int[] computeLPSArray(String pattern) {
        int length = 0;
        int i = 1;
        int[] lps = new int[pattern.length()];
        lps[0] = 0;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}
