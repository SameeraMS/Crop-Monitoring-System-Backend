package com.example.CropMonitoringSystem.util;

import java.util.Base64;

public class AppUtil {
    public static String convertImageToBase64(byte[] image) {return Base64.getEncoder().encodeToString(image);}

    public static byte[] convertBase64ToImage(String image) {return Base64.getDecoder().decode(image);}

}
