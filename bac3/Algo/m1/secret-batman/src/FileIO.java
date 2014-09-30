import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class FileIO {
	
	private String directory;
	private BufferedReader br ;
	
/*	Quelques testes possible
 * 		public static void main (String[] args){
		FileIO file = new FileIO("input");
		ArrayList<String> arLine = file.readByLine();
		
		for (int i = 0 ; i<arLine.size() ; i++){
			System.out.println(i+" "+arLine.get(i));
		}
		
		file.close();
		file = new FileIO("input");
		ArrayList<String> arWord = file.readByWord();
		
		for (int i = 0 ; i<arWord.size() ; i++){
			System.out.println(i+" "+arWord.get(i));
		}
		
		file.close();
		file = new FileIO("dj");
	}
*/
	
	public FileIO ( String dir ){
		this.directory = dir;
		try{
			br = new BufferedReader (new FileReader( this.directory));
		}
		catch(IOException e){
			System.err.println("Error opening file "+this.directory+" "+e.getMessage());
			System.exit(-1);
		}
	}
	
	private String readOneLine(){
		String st = null;
		try{
			st = br.readLine();
		}
		catch( IOException e){
			System.err.println("Error reading file "+this.directory+" "+e.getMessage());
		}
		return st;
	}
	
	public ArrayList<String> readByLine(){
		ArrayList<String> ar = new ArrayList<String>();
		int i = 0;
		String line = this.readOneLine();
		
		while(line != null){
			ar.add(i, line);
			i++;
			line = this.readOneLine();
		}
		
		return ar;
		
	}
	
	public void close(){
		try{
			this.br.close();
		}
		catch(IOException e){
			System.err.println("Error closing file "+this.directory+" "+e.getMessage());
		}
	}

	public ArrayList<String> readByWord(){
		ArrayList<String> ar = new ArrayList<String>();
		int i = 0;
		String line = this.readOneLine();
		
		while(line != null){
			String[] sLine = line.split(" ");
			
			for(int j = 0 ; j<sLine.length ; j++){
				ar.add(i , sLine[j]);
				i++;
			}

			line = this.readOneLine();
		}
		
		return ar;
	}
}
