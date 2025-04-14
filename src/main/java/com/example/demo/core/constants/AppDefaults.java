package com.example.demo.core.constants;

public class AppDefaults {

  public static final String passwordRegex =
      "^(?=.*[0-9])(?=.*[A-Za-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
  public static final String emailRegex =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  public static final String urlRegex = "^(https?|ftp):\\/\\/[^ /$.?#].[^ ]*$";
}
