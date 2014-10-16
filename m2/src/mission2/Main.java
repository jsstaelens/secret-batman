import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Audrey
 */
public class Main {
    
    public static void main(String[] args) {
    	FileIO fileIn = new FileIO("input");
    	ArrayList<String> ar = fileIn.readByLine();
    	for(int i=0 ; i< ar.size() ; i++){
    		FormalExpressionTreeImplemented arbre = new FormalExpressionTreeImplemented(ar.get(i));
    	}
    }
    	
    
}
