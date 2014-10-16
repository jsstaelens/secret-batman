import java.util.ArrayList;
/**
 *
 * @author Groupe 5.2
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
