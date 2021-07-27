package com.github.marfikus.helloworld.passwordChecker;

public class PasswordCheckerEmpty extends PasswordChecker {

    @Override
    public boolean isValid(String password) {
        if (!password.isEmpty()) {
            return true;
        } else {
            throw new RuntimeException("Password is empty!");
        }
    }
}
