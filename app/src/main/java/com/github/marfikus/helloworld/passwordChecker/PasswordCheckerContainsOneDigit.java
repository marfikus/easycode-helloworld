package com.github.marfikus.helloworld.password_checker;

public class PasswordCheckerContainsOneDigit extends PasswordChecker {

    @Override
    public boolean isValid(String password) {
        String[] passwordArr = password.split("");
        boolean containDigit = false;

        for (int i = 0; i < passwordArr.length; i++) {
            try {
                Integer.parseInt(passwordArr[i]);
                containDigit = true;
                break;
            } catch (Exception e) { }
        }

        if (containDigit) {
            return true;
        } else {
            throw new RuntimeException("Password not contain digits!");
        }
    }
}
