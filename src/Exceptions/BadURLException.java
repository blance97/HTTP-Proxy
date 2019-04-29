package Exceptions;

public class BadURLException extends Exception {

    public BadURLException(Exception e){
        System.out.println("URL could not be accessed" + e.toString());
    }
}
