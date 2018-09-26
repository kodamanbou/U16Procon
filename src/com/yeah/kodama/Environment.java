package com.yeah.kodama;

public final class Environment {

    private static Environment theInstance;
    private Environment() {}    //シングルトンパターン

    public static Environment getInstance() {
        if (theInstance == null) {
            theInstance = new Environment();
        }
        return theInstance;
    }

}
