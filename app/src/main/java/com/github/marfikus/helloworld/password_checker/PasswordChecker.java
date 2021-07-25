package com.github.marfikus.helloworld.password_checker;

public abstract class PasswordChecker implements PasswordCheckerHandler {

    public abstract boolean isValid(String password);
}
