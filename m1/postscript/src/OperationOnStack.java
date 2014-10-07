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
	 * @post : ajoute str � la stack
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
	 * @post : Enl�ve les deux derniers �l�ments de la stack et y rajoute la
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
					.println("Impossible de faire l'op�ration add sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation add sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont n�cessaires pour l'op�ration add");
			System.err.println();
			PostScript.error = true;
		}
		double number = number1 + number2;
		this.stack.push(Double.toString(number));
	}

	/**
	 * @pre : Minimum deux nombres dans la stack
	 * @post : Enl�ve les deux derniers �l�ments de la stack et y rajoute la
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
					.println("Impossible de faire l'op�ration sub sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation sub sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont n�cessaires pour l'op�ration sub");
			System.err.println();
			PostScript.error = true;
		}
		double number = number1 - number2;
		this.stack.push(Double.toString(number));
	}

	/**
	 * @pre : Minimum deux nombres dans la stack
	 * @post : Enl�ve les deux derniers �l�ments de la stack et y rajoute la
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
					.println("Impossible de faire l'op�ration mul sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation mul sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont n�cessaires pour l'op�ration mul");
			System.err.println();
			PostScript.error = true;
		}
		double number = number1 * number2;
		this.stack.push(Double.toString(number));
	}

	/**
	 * @pre : Minimum deux nombres dans la stack. Le deuxi�me doit �tre
	 *      diff�rent de 0
	 * @post : Enl�ve les deux derniers �l�ments de la stack et y rajoute la
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
					.println("Impossible de faire l'op�ration div sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation div sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont n�cessaires pour l'op�ration div");
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
	 * @pre : Minimum 1 �l�ment dans la stack
	 * @post : Enl�ve 1 �l�ment de la stack
	 */
	public void pop() {
		this.stack.pop();
	}

	/**
	 * @pre : Minimum 1 �l�ment dans la stack
	 * @post : Ajoute une copie du dernier �l�ment de la stack dans celle-ci
	 */
	public void dup() {
		String str = this.stack.pop();
		this.stack.push(str);
		this.stack.push(str);
	}

	/**
	 * @pre : Minimum deux �l�ments dans la stack
	 * @post : Echange la position des deux derniers �l�ments de la stack
	 */
	public void exch() {
		String str1 = this.stack.pop();
		String str2 = this.stack.pop();
		this.stack.push(str1);
		this.stack.push(str2);
	}

	/**
	 * @pre : Minimum deux nombres dans la stack
	 * @post : Enl�ve les deux derniers �l�ments de la stack et y rajoute true
	 *       si les nombres sont �gaux, false sinon
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
					.println("Impossible de faire l'op�ration eq sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation eq sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont n�cessaires pour l'op�ration eq");
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
	 * @post : Enl�ve les deux derniers �l�ments de la stack et y rajoute true
	 *       si les nombres ne sont pas �gaux, false sinon
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
					.println("Impossible de faire l'op�ration ne sur un string");
			System.err.println();
			PostScript.error = true;
		} catch (NullPointerException e) {
			System.err.println("Erreur lors de l'operation ne sur " + pop1
					+ " et " + pop2);
			System.err
					.println("Deux arguments sont n�cessaires pour l'op�ration ne");
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
	 * @post : Imprime la repr�sentation de la stack sur le outputFile
	 */
	public void pstack(FileIO outputFile) {
		outputFile.println(this.stack.toString());
	}

}
