package week11;

/** interface to be implemented to simulate
    card dealing game.
    @author Jemima Lomax-Sawyers
*/

public interface CardPile{
    /** method to load pile with array of cards.
        @param cards array of cards
    */

    public void load(int [] cards);

    /** method to load pile from 1 to n.
        @param n size of pile
    */

    public void load(int n);

    /** method to get state of current pile.
        @return int [] the current pile
    */

    public int [] getPile();

    /** method to transform pile.
        @param rowLength length of rows to deal cards
        @param spec the specification for picking up the cards
    */

    public void transform(int rowLength, String spec);

    /** method to count transformations required to get
        back to original pile.
        @param rowLength length of rows to deal cards
        @param spec the specification for picking up the cards
        @return int the count of transformations
    */

    public int count(int rowLength, String spec);

}