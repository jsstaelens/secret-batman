/**
 * Classe OperationOnStack. Mini-Interpreteur PostScript
 * 
 * @author Groupe 5.2
 * 
 */
public class OperationOnStack {

	private Stack stack;

	/*
	 * Constructeur de la classe OperationOnStack
	 */
	public OperationOnStack() {
		this.stack = new Stack();
	}

	/**
	 * @pre : str est un nombre != null
	 * @post : ajoute str à la stack
	 */
	public void addNumber(String str) {
		double number = 0;
		try {
			number = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			System.err.println("Impossible de parser un mot en double");
		}
		this.stack.push(Double.toString(number));
	}

	/**
	 * @pre : Minimum deux nombres dans la stack
	 * @post : Enlève les deux derniers éléments de la stack et y rajoute la
	 *       somme de ces deux nombres
	 */
	public void add() {
		double number1 = 0;
		double number2 = 0;
		String pop1 = this.stack.pop();
		String pop2 = this.stack.pop();
		try {
			number1 = Double.parseDouble(pop1);
			number2 = Double.parseDouble(pop2);
		} catch (NumberFormatException e) {
			System.err.println("Erreur lors de l'operation add sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Impossible de faire l'opération add sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation add sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont nécessaires pour l'opération add");
			System.err.println();
			PostScript.error = true;
		}
		double number = number1 + number2;
		this.stack.push(Double.toString(number));
	}

	/**
	 * @pre : Minimum deux nombres dans la stack
	 * @post : Enlève les deux derniers éléments de la stack et y rajoute la
	 *       soustraction de ces deux nombres
	 */
	public void sub() {
		double number1 = 0;
		double number2 = 0;
		String pop1 = this.stack.pop();
		String pop2 = this.stack.pop();
		try {
			number1 = Double.parseDouble(pop1);
			number2 = Double.parseDouble(pop2);
		} catch (NumberFormatException e) {
			System.err.println("Erreur lors de l'operation sub sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Impossible de faire l'opération sub sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation sub sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont nécessaires pour l'opération sub");
			System.err.println();
			PostScript.error = true;
		}
		double number = number1 - number2;
		this.stack.push(Double.toString(number));
	}

	/**
	 * @pre : Minimum deux nombres dans la stack
	 * @post : Enlève les deux derniers éléments de la stack et y rajoute la
	 *       multiplication de ces deux nombres
	 */
	public void mul() {
		double number1 = 0;
		double number2 = 0;
		String pop1 = this.stack.pop();
		String pop2 = this.stack.pop();
		try {
			number1 = Double.parseDouble(pop1);
			number2 = Double.parseDouble(pop2);
		} catch (NumberFormatException e) {
			System.err.println("Erreur lors de l'operation mul sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Impossible de faire l'opération mul sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation mul sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont nécessaires pour l'opération mul");
			System.err.println();
			PostScript.error = true;
		}
		double number = number1 * number2;
		this.stack.push(Double.toString(number));
	}

	/**
	 * @pre : Minimum deux nombres dans la stack. Le deuxième doit être
	 *      différent de 0
	 * @post : Enlève les deux derniers éléments de la stack et y rajoute la
	 *       division de ces deux nombres
	 */
	public void div() {
		double number1 = 0;
		double number2 = 0;
		String pop1 = this.stack.pop();
		String pop2 = this.stack.pop();
		try {
			number1 = Double.parseDouble(pop1);
			number2 = Double.parseDouble(pop2);
		} catch (NumberFormatException e) {
			System.err.println("Erreur lors de l'operation div sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Impossible de faire l'opération div sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation div sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont nécessaires pour l'opération div");
			System.err.println();
			PostScript.error = true;
		}
		if (number2 == 0) {
			System.err.println("Erreur lors de l'operation div sur " + pop1
					+ " et " + pop2);
			System.err.println("Division par 0");
			System.err.println();
			PostScript.error = true;
			this.stack.push(0.0 + "");
		} else {
			double number = number1 / number2;
			this.stack.push(Double.toString(number));
		}
	}

	/**
	 * @pre : Minimum 1 élément dans la stack
	 * @post : Enlève 1 élément de la stack
	 */
	public void pop() {
		this.stack.pop();
	}

	/**
	 * @pre : Minimum 1 élément dans la stack
	 * @post : Ajoute une copie du dernier élément de la stack dans celle-ci
	 */
	public void dup() {
		String str = this.stack.pop();
		this.stack.push(str);
		this.stack.push(str);
	}

	/**
	 * @pre : Minimum deux éléments dans la stack
	 * @post : Echange la position des deux derniers éléments de la stack
	 */
	public void exch() {
		String str1 = this.stack.pop();
		String str2 = this.stack.pop();
		this.stack.push(str1);
		this.stack.push(str2);
	}

	/**
	 * @pre : Minimum deux nombres dans la stack
	 * @post : Enlève les deux derniers éléments de la stack et y rajoute true
	 *       si les nombres sont égaux, false sinon
	 */
	public void eq() {
		double number1 = 0;
		double number2 = 0;
		String pop1 = this.stack.pop();
		String pop2 = this.stack.pop();
		try {
			number1 = Double.parseDouble(pop1);
			number2 = Double.parseDouble(pop2);
		} catch (NumberFormatException e) {
			System.err.println("Erreur lors de l'operation eq sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Impossible de faire l'opération eq sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation eq sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont nécessaires pour l'opération eq");
			System.err.println();
			PostScript.error = true;
		}
		if (number1 == number2) {
			this.stack.push("true");
		} else {
			this.stack.push("false");
		}

	}

	/**
	 * @pre : Minimum deux nombres dans la stack
	 * @post : Enlève les deux derniers éléments de la stack et y rajoute true
	 *       si les nombres ne sont pas égaux, false sinon
	 */
	public void ne() {
		double number1 = 0;
		double number2 = 0;
		String pop1 = this.stack.pop();
		String pop2 = this.stack.pop();
		try {
			number1 = Double.parseDouble(pop1);
			number2 = Double.parseDouble(pop2);
		} catch (NumberFormatException e) {
			System.err.println("Erreur lors de l'operation ne sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Impossible de faire l'opération ne sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation ne sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont nécessaires pour l'opération ne");
			System.err.println();
			PostScript.error = true;
		}
		if (number1 == number2) {
			this.stack.push("false");
		} else {
			this.stack.push("true");
		}

	}

	/**
	 * @pre : -
	 * @post : Imprime la représentation de la stack sur le outputFile
	 */
	public void pstack(FileIO outputFile) {
		outputFile.println(this.stack.toString());
	}

}
