package com.example.demo.core.constants;

public class AppDefaults {

    public final static String passwordRegex = "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    public final static String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public final static String urlRegex = "^(https?|ftp):\\/\\/[^\s/$.?#].[^\s]*$";

}
