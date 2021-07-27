package com.github.marfikus.helloworld.passwordChecker;

public abstract class PasswordChecker implements PasswordCheckerHandler {

    public abstract boolean isValid(String password);
}
