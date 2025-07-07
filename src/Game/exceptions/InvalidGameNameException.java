package Game.exceptions;

public class InvalidGameNameException extends Exception{
    public InvalidGameNameException(){
        super();
    }

    public InvalidGameNameException(String str){
        super(str);
    }
}
