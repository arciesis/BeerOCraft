package xyz.beerocraft.controller;

public class InvalidStateObjectException extends RuntimeException{

    public InvalidStateObjectException(){
        super();
    }

    public InvalidStateObjectException(String message){
        super(message);
    }

    public InvalidStateObjectException(Throwable cause){
        super(cause);
    }

    public InvalidStateObjectException(String message, Throwable cause){
        super(message, cause);
    }
}
