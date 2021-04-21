package week11;

/** class extending the runtime exception class.
    provides exceptions for the CP class when inputs are invalid.
    @author Jemima Lomax-Sawyers
*/

public class CardPileException extends RuntimeException{

    /** data field for serialisation.
     */

    private static final long serialVersionUID = 18052020L;

    /** constructor for exception class.
        @param message the error message to print
    */

    public CardPileException(String message){
        super(message);
    }
}
