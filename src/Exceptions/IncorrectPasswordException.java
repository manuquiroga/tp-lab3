package Exceptions;

public class IncorrectPasswordException extends Exception{


    public IncorrectPasswordException(String s) {
        super(s);
    }

    public void closeApp(){
        System.exit(0);
    }
}
