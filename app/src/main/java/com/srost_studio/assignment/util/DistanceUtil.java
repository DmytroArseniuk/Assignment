package com.srost_studio.assignment.util;

public class DistanceUtil {
    public static String getDisplayableDistance(double distance) {
        return String.valueOf(distance / 1000.f) + " km";
    }
}
