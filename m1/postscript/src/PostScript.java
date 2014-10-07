import java.util.ArrayList;

/**
 * Classe PostScript effectuant les opérations sur la pile
 * 
 * @author Groupe 5.2
 * 
 */
public class PostScript {

	// Variable mise à jours lors de la detection de problèmes
	public static boolean error = false;

	// Variables d'instances
	private ArrayList<String> stringList;
	private ArrayList<Definition> defList;
	
	private FileIO inputFile;
	private FileIO outputFile;

	private OperationOnStack oos;

	// Constructeur de la classe
	public PostScript(String inputFileTxt, String outputFileTxt) {
		stringList = new ArrayList<String>();
		defList = new ArrayList<Definition>();

		inputFile = new FileIO(inputFileTxt);
		outputFile = new FileIO(outputFileTxt);

		// Mise à jours de l'ArrayList avec les mot contenu dans le file
		// inputFileTxt
		stringList = inputFile.readByWord();

		oos = new OperationOnStack();
	}

	// Méthode principale permettant d'interpreter l'input
	public void start() {

		// Boucle parcourant tous les Strings de l'arraylist
		for (int i = 0; i < stringList.size(); i++) {
			// Si c'est un nombre, alors on l'ajoute à la stack
			if (isDouble(stringList.get(i))) {
				oos.addNumber(stringList.get(i));
			}// Sinon, on interprète le mot et on effectue la bonne opération
			else if (stringList.get(i).equals("add")) {
				oos.add();
			} else if (stringList.get(i).equals("sub")) {
				oos.sub();
			} else if (stringList.get(i).equals("mul")) {
				oos.mul();
			} else if (stringList.get(i).equals("div")) {
				oos.div();
			} else if (stringList.get(i).equals("pop")) {
				oos.pop();
			}

			else if (stringList.get(i).equals("dup")) {
				oos.dup();
			} else if (stringList.get(i).equals("exch")) {
				oos.exch();
			} else if (stringList.get(i).equals("eq")) {
				oos.eq();
			} else if (stringList.get(i).equals("ne")) {
				oos.ne();
			} else if (stringList.get(i).equals("pstack")) {
				oos.pstack(outputFile);
			}// Si le mot commence par un "/" alors nous avons affaire à def
			else if (stringList.get(i).substring(0, 1).equals("/")) {
				// On vérifie que ca ne soit pas la fin de l'ArrayList
				if (stringList.size() > i + 2) {
					// On vérifie que l'opération def est bien présente et que
					// la valeur à définir est un nombre
					if (stringList.get(i + 2).equals("def")
							|| isDouble(stringList.get(i + 1))) {
						// Si le mot à définir n'est pas dans la liste, on
						// l'ajoute
						if (contain(stringList.get(i).substring(1), defList) < 0) {
							Definition newDef = new Definition(stringList
									.get(i).substring(1),
									Double.parseDouble(stringList.get(i + 1)));
							defList.add(newDef);
						}// sinon on le met a jours
						else {
							defList.get(
									contain(stringList.get(i).substring(1),
											defList)).setValue(
									Double.parseDouble(stringList.get(i + 1)));
						}
						i = i + 2;
					}// Sinon on indique une erreur de syntaxe et on continue le
						// programme
					else {
						System.err
								.println("Couldn't find the correct def operation syntax for "
										+ stringList.get(i));
						System.err.println();
						PostScript.error = true;
						i = i + 2;
					}
				}// Sinon on indique une erreur de syntaxe et on arrête le
					// programme
				else {
					System.err
							.println("Couldn't find the correct def operation syntax for "
									+ stringList.get(i));
					System.err.println();
					PostScript.error = true;
					System.exit(-1);
				}
			}// Si on a affaire à un mot déjà défini, on ajoute sa valeur à la
				// stack
			else if (contain(stringList.get(i), defList) >= 0) {
				oos.addNumber(""
						+ defList.get(contain(stringList.get(i), defList))
								.getValue());
			}// Si le mot n'est pas reconnu, on affiche l'erreur et on continue
				// sans rien faire
			else {

				System.err.println("Couldn't regonize the word "
						+ stringList.get(i) + " in the input file");
				System.err.println();
				PostScript.error = true;
			}

		}

		// Si des erreurs sont survenues lors de l'éxécution du programme, un
		// message d'erreur est affiché sur le file outPut
		if (error) {
			outputFile
					.println("Attention, des erreurs sont survenues, vérifier System.err");
		}

	}

	/**
	 * 
	 * @pre : def != null & defList != null
	 * @post : retourne la valeur de la position de def dans defList retourne
	 *       une valeur négative si def ne se trouve pas dans defList
	 * 
	 */
	public static int contain(String def, ArrayList<Definition> defList) {
		for (int i = 0; i < defList.size(); i++) {
			if (defList.get(i).getName().equals(def)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @pre : str !: null
	 * @post : retourne true si str est un nombre, retourne false sinon
	 * 
	 */
	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
