import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class FileIO {
	
	private String directory;
	private BufferedReader br ;
	private FileReader fr;
	private PrintWriter pw;
	private FileWriter fw;
	

	public FileIO ( String dir ){
		this.directory = dir;

	}
	
// reading functions
	
	public ArrayList<String> readByWord(){
		this.openReader();
		ArrayList<String> ar = new ArrayList<String>();
		int i = 0;
		String line = this.readOneLine();
		
		while(line != null){
			String[] sLine = line.split(" ");
			
			for(int j = 0 ; j<sLine.length ; j++){
				if( !sLine[j].equals("")){
					ar.add(i , sLine[j]);
					i++;
				}
			}
			line = this.readOneLine();
		}
		
		this.closeReader();
		return ar;
	}
	
	public ArrayList<String> readByLine(){
		this.openReader();
		ArrayList<String> ar = new ArrayList<String>();
		int i = 0;
		String line = this.readOneLine();
		
		while(line != null){
			ar.add(i, line);
			i++;
			line = this.readOneLine();
		}
		
		this.closeReader();
		return ar;
		
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
	
	
	private void closeReader(){
		try{
			this.fr.close();
			this.fr = null;
			this.br.close();
			this.br = null;
		}
		catch(IOException e){
			System.err.println("Error closing file "+this.directory+" "+e.getMessage());
			System.exit(-1);
		}
	}

	
	private void openReader(){
		try{
			this.fr = new FileReader(this.directory);
			this.br = new BufferedReader(fr);
		}
		catch(IOException e ){
			System.err.println("Error opening "+this.directory+" "+e.getMessage());
			System.exit(-1);
		}
	}

	
	//writing functions
	
	public void writeArray(ArrayList<String> ar){
		this.openWriter();
		for(int i = 0 ; i<ar.size() ; i++){
			pw.println(ar.get(i));
		}
		this.closeWriter();
	}
	
	private void openWriter(){
		try{
			this.fw = new FileWriter(this.directory);
			this.pw = new PrintWriter(this.fw);
		}
		catch(IOException e){
			System.err.println("Error opening File "+this.directory+" "+e.getMessage());
			System.exit(-1);
		}
	}
	
	
	private void closeWriter(){
		try{
			this.fw.close();
			this.fw = null;
			this.pw.close();
			this.pw = null;
		}
		catch(IOException e){
			System.err.println("Error closing file "+this.directory+" "+e.getMessage());
			System.exit(-1);
		}
	}
	

	
}
