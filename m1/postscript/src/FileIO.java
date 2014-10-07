import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Classe FileIO permettant de lire et écrire dans les fichiers de plusieurs
 * manières différentes
 * 
 * @author Groupe 5.2
 * 
 */
public class FileIO {

	// Variable d'instance de la classe FileIO
	private String directory;
	private BufferedReader br;
	private FileReader fr;
	private PrintWriter pw;
	private FileWriter fw;
	private boolean firstWrite;

	/*
	 * Constructeur de la classe FileIO
	 */
	public FileIO(String dir) {
		this.directory = dir;
		this.firstWrite = true;

	}

	// reading functions

	/**
	 * @pre
	 * @retourne une ArrayList contenant le fichier d'input mot par mot
	 */
	public ArrayList<String> readByWord() {
		this.openReader();
		ArrayList<String> ar = new ArrayList<String>();
		int i = 0;
		String line = this.readOneLine();

		while (line != null) {
			String[] sLine = line.split(" ");

			for (int j = 0; j < sLine.length; j++) {
				if (!sLine[j].equals("")) {
					ar.add(i, sLine[j]);
					i++;
				}
			}
			line = this.readOneLine();
		}

		this.closeReader();
		return ar;
	}

	/**
	 * @pre /
	 * @post retourne une ArrayList ceontenant le fichier d'input ligne par
	 *       ligne
	 */
	public ArrayList<String> readByLine() {
		this.openReader();
		ArrayList<String> ar = new ArrayList<String>();
		int i = 0;
		String line = this.readOneLine();

		while (line != null) {
			ar.add(i, line);
			i++;
			line = this.readOneLine();
		}

		this.closeReader();
		return ar;

	}

	/**
	 * @pre
	 * @post Retourne la prochaine ligne du fichier input
	 */
	private String readOneLine() {
		String st = null;
		try {
			st = br.readLine();
		} catch (IOException e) {
			System.err.println("Error reading file " + this.directory + " "
					+ e.getMessage());
		}
		return st;
	}

	/**
	 * @pre /
	 * @post Ferme le reader
	 */
	private void closeReader() {
		try {
			this.fr.close();
			this.fr = null;
			this.br.close();
			this.br = null;
		} catch (IOException e) {
			System.err.println("Error closing file " + this.directory + " "
					+ e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * @pre /
	 * @post Ouvre le reader
	 */
	private void openReader() {
		try {
			this.fr = new FileReader(this.directory);
			this.br = new BufferedReader(fr);
		} catch (IOException e) {
			System.err.println("Error opening " + this.directory + " "
					+ e.getMessage());
			System.exit(-1);
		}
	}

	// writing functions
	/**
	 * @pre une ArrayList ar
	 * @post écrit le contenu de l'ArrayList ar dans le fichier output. Ecrit
	 *       chaque element de l'arraylist sur une ligne différente. Overwrite
	 *       si c'est la premiere écriture, sinon écrit a la suite.
	 */
	public void writeArray(ArrayList<String> ar) {
		if (firstWrite) {
			this.openWriter(false);
			this.firstWrite = false;
		} else {
			this.openWriter(true);
		}
		for (int i = 0; i < ar.size(); i++) {
			pw.println(ar.get(i));
		}
		this.closeWriter();
	}

	/**
	 * @pre Sring str
	 * @post écrit str dans le fichier output. Overwrite si c'est la premiere
	 *       écriture, sinon écrit a la suite.
	 */
	public void println(String str) {
		if (firstWrite) {
			this.openWriter(false);
			this.firstWrite = false;
		} else {
			this.openWriter(true);
		}

		this.pw.println(str);
		this.closeWriter();

	}

	/**
	 * @pre boolean op
	 * @post Ouvre le writer en remettant à zero le fichier output si op est
	 *       false sinon l'ouvre en écrivant a la suite.
	 */
	private void openWriter(boolean op) {
		try {
			this.fw = new FileWriter(this.directory, op);
			this.pw = new PrintWriter(this.fw);
		} catch (IOException e) {
			System.err.println("Error opening File " + this.directory + " "
					+ e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * @pre /
	 * @post Ferme le writer
	 */
	private void closeWriter() {
		try {
			this.fw.close();
			this.fw = null;
			this.pw.close();
			this.pw = null;
		} catch (IOException e) {
			System.err.println("Error closing file " + this.directory + " "
					+ e.getMessage());
			System.exit(-1);
		}
	}

}