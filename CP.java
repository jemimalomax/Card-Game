package week11;

import java.util.Arrays;
import java.util.Scanner;

/**
 * implementation of CardPile class for the simulation of a card dealing game.
 */

public class CP implements CardPile {

	/**
	 * data field representing the pile of cards which is altered throughout the
	 * various methods.
	 */

	private int[] pile;

	/**
	 * method taking an input array and loading to be the card pile.
	 * 
	 * @param cards the array to be loaded into a pile.
	 */

	public void load(int[] cards) {
		this.pile = cards;
	}

	/**
	 * method loading a default pile from 1 to n.
	 * 
	 * @param n the size of the pile to be loaded
	 */

	public void load(int n) {
		if (n < 0) {
			throw new CardPileException("input is not a positive integer");
		} else {
			this.pile = new int[n];
			for (int i = 0; i < n; i++) {
				pile[i] = i + 1;
			}
		}

	}

	/**
	 * method returning the current state of the pile.
	 * 
	 * @return copyPile a copy of the current state of the pile
	 */

	public int[] getPile() {
		int[] copyPile = Arrays.copyOf(pile, pile.length);
		return copyPile;
	}

	/**
	 * method to create placement of the current card pile.
	 * 
	 * @param rowLength the length of rows in which to lay the pile
	 * @param spec      the specification for picking up the cards
	 * @return place the 2D placement of the current pile
	 */

	public int[][] createPlacement(int rowLength, String spec) {
		int[][] place;
		if (pile.length % rowLength != 0) {
			throw new CardPileException("row length must be a multiple of " + pile.length);
		} else if (!checkSpec(spec)) {
			throw new CardPileException("specification is not valid");
		} else {
			place = new int[pile.length / rowLength][rowLength];
			int i = 0;
			for (int row = 0; row < place.length; row++) {
				for (int col = 0; col < place[row].length; col++) {
					place[row][col] = pile[i];
					i++;
				}
			}
		}
		return place;
	}

	/**
	 * method to reverse the pile.
	 * 
	 * @param arr the pile to be reversed
	 * @return arr the array that represents the reversed pile
	 */

	public int[] reverse(int[] arr) {
		for (int i = 0; i < arr.length / 2; i++) {
			int temp = arr[i];
			arr[i] = arr[arr.length - i - 1];
			arr[arr.length - i - 1] = temp;
		}
		return arr;
	}

	/** a method utilising recursion to transform
	    the pile based on a given row length and specification.
	        @param rowLength the length of the rows in which to lay the pile
	        @param spec the specification of how to pick up the cards
	    */
	 
	 public void transform(int rowLength, String spec){
	        int [][] place;
	        int i;
	        if(pile.length%rowLength != 0){
	            throw new CardPileException
	            ("row length must be a multiple of " + pile.length);
	        }else if(!checkSpec(spec)){
	            throw new CardPileException("specification is not valid");
	        }else{
	            place = createPlacement(rowLength, spec);
	            i = 0;
	            switch(spec){
	                case "LT":
	                    pile = getPile()
	                    break;
	                case "RB":
	                    pile = reverse(getPile());
	                    break;
	                case "LB":
	                    for(int row = place.length - 1; row >= 0; row--){
	                        for(int col = 0; col < place[row].length; col++){
	                            pile[i] = place[row][col];
	                            i++;
	                        }
	                    }
	                    break;
	                case "RT":
	                    transform(rowLength, "LB");
	                    pile = reverse(getPile());
	                    break;
	                case "TL":
	                    for(int col = 0; col < place[0].length; col++){
	                        for(int row = 0; row < place.length; row++){
	                            pile[i] = place[row][col];
	                            i++;
	                        }
	                    }
	                    break;
	                case "BR":
	                    transform(rowLength, "TL");
	                    pile = reverse(getPile());
	                    break;
	                    
	                case "BL":
	                    for(int col = 0; col < place[0].length; col++){
	                        for(int row = place.length - 1; row >= 0; row--){
	                            pile[i] = place[row][col];
	                            i++;
	                        }
	                    }
	                    break;
	                case "TR":
	                    transform(rowLength, "BL");
	                    pile = reverse(getPile());
	                    break;
	            }
	        }
	    }

	/**
	 * method counting the number of transformations a pile must go through with a
	 * given specification in order to get back to the original pile.
	 * 
	 * @param rowLength the length of rows in which to lay the cards
	 * @param spec      the specification for how the cards are to be picked up
	 * @return count the number of transformations to get back to the original pile
	 */

	public int count(int rowLength, String spec) {
		int count = 0;
		if (pile.length % rowLength != 0) {
			throw new CardPileException("row length must be a multiple of " + pile.length);
		} else if (!checkSpec(spec)) {
			throw new CardPileException("specification is not valid");
		} else {
			int[] originalPile = getPile();
			do {
				transform(rowLength, spec);
				count++;
			} while (!Arrays.equals(pile, originalPile));
		}
		return count;
	}

	/**
	 * method dealing with how to handle input from files and the various
	 * 'instructions' they can contain.
	 */

	public void fileInput() {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			Scanner sc = new Scanner(scan.nextLine());
			while (sc.hasNext()) {
				switch (sc.next()) {
				case "c":
					int count = count(sc.nextInt(), sc.next());
					System.out.println(count);
					break;
				case "l":
					load(sc.nextInt());
					break;
				case "L":
					String s = "";
					while (sc.hasNext()) {
						s = s + sc.next() + " ";
					}
					String[] strInt = s.split(" ");
					int[] loadLine = new int[strInt.length];
					for (int i = 0; i < loadLine.length; i++) {
						loadLine[i] = Integer.parseInt(strInt[i]);
					}
					load(loadLine);
					break;
				case "P":
					int r = sc.nextInt();
					int[][] p = createPlacement(r, "LT");
					for (int[] row : p) {
						for (int j = 0; j < row.length; j++) {
							System.out.print(row[j] + " ");
						}
						System.out.println();
					}
					break;
				case "p":
					for (int card : pile) {
						System.out.print(card + " ");
					}
					System.out.println();
					break;
				case "t":
					transform(sc.nextInt(), sc.next());
					break;
				default:
					System.out.println("invalid input from file");
				}
			}
		}
	}

	/**
	 * method to test the validity of a spec inputted.
	 * 
	 * @param s the input specification to be tested
	 * @return false if the spec is invalid
	 */

	public boolean checkSpec(String s) {
		String[] specs = { "TL", "LT", "BL", "LB", "TR", "RT", "BR", "RB" };
		for (String spec : specs) {
			if (spec.equals(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * main method running the simulation. Handles input from the command line or
	 * stdin
	 * 
	 * @param args the command line arguments
	 */

	public static void main(String[] args) {
		CP cp = new CP();
		String[] specs = { "TL", "BL", "TR", "BR", "LT", "LB", "RT", "RB" };
		switch (args.length) {
		case 0:
			cp.fileInput();
			break;
		case 1:
			System.out.println("invalid number of arguments");
			break;
		case 2:
			cp.load(Integer.parseInt(args[0]));
			for (String spec : specs) {
				int count = cp.count(Integer.parseInt(args[1]), spec);
				System.out.println(spec + " " + count);
			}
			break;
		default:
			cp.load(Integer.parseInt(args[0]));
			for (int card : cp.pile) {
				System.out.print(card + " ");
			}
			System.out.println();
			for (int i = 2; i < args.length; i++) {
				cp.transform(Integer.parseInt(args[1]), args[i]);
				for (int j = 0; j < cp.pile.length; j++) {
					System.out.print(cp.pile[j] + " ");
				}
				System.out.println();

			}
		}
	}
}
