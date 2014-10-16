import java.util.ArrayList;
import java.util.Stack;

/**
 * 
 * @author Groupe 5.2
 * 
 */
public class FormalExpressionTreeImplemented implements FormalExpressionTree {

	private LinkedRBinaryTree<String> tree;
	public String element; // valeur de la racine
	String expression;
	public LinkedRBinaryTree treeBuilt;

	public FormalExpressionTreeImplemented(LinkedRBinaryTree<String> tree) {
		this.tree = tree;
	}

	public FormalExpressionTreeImplemented(String expression) {
		Stack<Object> operands = new Stack<>();
		Stack<Object> operateurs = new Stack<>();
		String str[] = createTabExpression(expression);
		String elt;
		for (int i = 0; i < str.length; i++) {

			elt = str[i];
			if (elt.equals("+") || elt.equals("-") || elt.equals("/")
					|| elt.equals("*") || elt.equals("^")) {
				operateurs.push(elt);
			} else if (elt.equals(")")) {
				buildTree(operands, operateurs);
			} else if (!elt.equals("") && !elt.equals("(")) {
				operands.push(elt);
			}
		}
		if (!operands.isEmpty() || !operateurs.isEmpty()) {
			while (!operands.isEmpty() && !operateurs.isEmpty()) {
				buildTree(operands, operateurs);
			}
		}
		System.out.println("operateur : " + operateurs);
		System.out.println("operands : " + operands);
		this.tree = (LinkedRBinaryTree<String>) operands.pop();
		System.out.println(this.toString());
	}

	private String[] createTabExpression(String expression) {
		String str[] = new String[expression.length()];
		char car;
		int i = 0, j = 0;
		while (i < expression.length()) {
			car = expression.charAt(i);
			if (car == 's' || car == 'c' || car == 'l') { // s pour sin, c pour
															// cos et l pour log
				str[j] = "sin";
				i = i + 2;
			} else if (car != ' ') {
				str[j] = String.valueOf(car);
			}
			i++;
			j++;
		}
		String str2[] = resize(str, j);
		return str2;
	}

	private String[] resize(String[] str, int nouvelleTaille) {
		String str2[] = new String[nouvelleTaille];
		for (int i = 0; i < str2.length; i++) {
			str2[i] = str[i];
		}
		return str2;
	}

	private void buildTree(Stack operands, Stack operateurs) {
		Object ob = operateurs.pop();
		LinkedRBinaryTree<String> lb;
		

		if (ob.equals("sin") || ob.equals("cos") || ob.equals("log")) {
			LinkedRBinaryTree lbtLeft = new LinkedRBinaryTree(operands.pop());
			lb = new LinkedRBinaryTree(null, lbtLeft, ob);
		} else {
	    	LinkedRBinaryTree<String> lbRight;
	    	LinkedRBinaryTree<String> lbLeft;
	    	Object op = operands.pop();

			if (op instanceof LinkedRBinaryTree)
				lbRight = (LinkedRBinaryTree<String>) op;
			else
				lbRight = new LinkedRBinaryTree<String>((String) op);

			op = operands.pop();

			if (op instanceof LinkedRBinaryTree)
				lbLeft = (LinkedRBinaryTree<String>) op;
			else
				lbLeft = new LinkedRBinaryTree<String>((String) op);

			lb = new LinkedRBinaryTree<String>(lbRight, lbLeft, (String) ob);

		}
		
		operands.push(lb);
	}

	/*
	 * public FormalExpressionTreeImplemented(String element) { this.element =
	 * element; }
	 */

	public RBinaryTree<String> getRight() {
		return tree.rightTree();
	}

	public RBinaryTree<String> getLeft() {
		return tree.leftTree();
	}

	public String getElement() {
		return element;
	}

	public void setRight(RBinaryTree<String> right) {
		this.tree.setRight(right);
	}

	public void setLeft(RBinaryTree<String> left) {
		this.tree.setLeft(left);
	}

	public void setElement(String element) {
		this.element = element;
	}

	public ArrayList<String> inorder(ArrayList<String> al) {
		return this.tree.inorder(al);
	}

	public ArrayList<String> inorderGet() {
		ArrayList<String> al = new ArrayList<String>();
		return inorder(al);
	}

	private boolean isLeaf() {
		return this.tree.isLeaf();
	}

	@Override
	public String toString() {
		return getString(this.tree);
	}

	private String getString(RBinaryTree<String> t) {

		LinkedRBinaryTree<String> tree = (LinkedRBinaryTree<String>) t;

		if (tree.rightTree() == null && tree.leftTree() == null)
			return tree.element;
		else if (tree.rightTree() == null)
			return "(" + tree.element.concat(getString(tree.leftTree())) + ")";
		else if (tree.leftTree() == null)
			return "(" + getString(tree.rightTree()).concat(tree.element) + ")";
		else
			return "("
					+ getString(tree.rightTree()).concat(
							tree.element.concat(getString(tree.leftTree())))
					+ ")";

	}

	private void setTree(LinkedRBinaryTree<String> tree) {
		this.tree = tree;
	}

	/*
	 * public FormalExpressionTreeImplemented build(String expression) { //TODO
	 * return null; }
	 */
	/**
	 * @pre : l'arbre surlequel on applique la fonction derive() a été
	 *      correctement construit
	 * @post : Retourne une expression sous forme d'arbre représentant la dérivé
	 *       de l'arbre surlequel on a appliqué la fonction
	 */
	@Override
	public FormalExpressionTreeImplemented derive() {

		LinkedRBinaryTree<String> tree = this.derivePrivate();
		return new FormalExpressionTreeImplemented(tree);

	}

	private LinkedRBinaryTree<String> derivePrivate() {
		// Analyse de la valeur de la racine et lance l'operation adéquate
		if (this.tree.element == null) {
			return null;
		} else if (this.tree.element.equals("+")) {
			return this.operationPlus();

		} else if (this.tree.element.equals("-")) {
			return this.operationMinus();

		} else if (this.tree.element.equals("*")) {
			return this.operationMultiply();

		} else if (this.tree.element.equals("/")) {
			return this.operationDivide();

		} else if (this.tree.element.equals("^")) {
			return this.operationExp();

		} else if (this.tree.element.equals("x")) {
			return this.operationX();

		} else {
			return this.operationInt();
		}
	}

	/**
	 * 
	 * @return Retourne la dérivé de x, c'est-à-dire 1, sous forme de
	 *         FormalExpressionTreeImplemented
	 */
	private LinkedRBinaryTree<String> operationX() {
		LinkedRBinaryTree<String> tree = new LinkedRBinaryTree<String>(
				this.tree.leftTree(), this.tree.rightTree(), "1");
		return tree;
	}

	/**
	 * 
	 * @return Retourne la dérivé d'une constante a, c'est-à-dire 0, sous forme
	 *         de FormalExpressionTreeImplemented
	 */
	private LinkedRBinaryTree<String> operationInt() {
		LinkedRBinaryTree<String> tree = new LinkedRBinaryTree<String>(
				this.tree.leftTree(), this.tree.rightTree(), "0");
		return tree;
	}

	/**
	 * Operation de dérivé sur une expression de type f+g => (f+g)'
	 * 
	 * @return retourne la dérivé sous forme f' + g'
	 */
	private LinkedRBinaryTree<String> operationPlus() {
		FormalExpressionTreeImplemented tr = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.rightTree());
		FormalExpressionTreeImplemented tl = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.leftTree());

		LinkedRBinaryTree<String> tree = new LinkedRBinaryTree<String>(
				tr.derivePrivate(), tl.derivePrivate(), "+");
		return tree;
	}

	/**
	 * Operation de dérivé sur une expression de type f-g => (f-g)'
	 * 
	 * @return retourne la dérivé sous forme f' - g'
	 */
	private LinkedRBinaryTree<String> operationMinus() {
		FormalExpressionTreeImplemented tr = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.rightTree());
		FormalExpressionTreeImplemented tl = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.leftTree());

		LinkedRBinaryTree<String> tree = new LinkedRBinaryTree<String>(
				tl.derivePrivate(), tr.derivePrivate(), "-");
		return tree;
	}

	/**
	 * Operation de dérivé sur une expression de type f*g => (f*g)'
	 * 
	 * @return retourne la dérivé sous forme g*f' + f*g'
	 */
	private LinkedRBinaryTree<String> operationMultiply() {

		FormalExpressionTreeImplemented tr = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.rightTree());
		FormalExpressionTreeImplemented tl = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.leftTree());

		LinkedRBinaryTree<String> treeLeft = new LinkedRBinaryTree<String>(
				tl.derivePrivate(), this.tree.rightTree(), "*");
		LinkedRBinaryTree<String> treeRight = new LinkedRBinaryTree<String>(
				this.tree.leftTree(), tr.derivePrivate(), "*");
		LinkedRBinaryTree<String> tree = new LinkedRBinaryTree<String>(
				treeLeft, treeRight, "+");
		return tree;
	}

	/**
	 * Operation de dérivé sur une expression de type f/g => (f/g)'
	 * 
	 * @return retourne la dérivé sous forme (g*f' - f*g')/(g^2)
	 */
	private LinkedRBinaryTree<String> operationDivide() {

		FormalExpressionTreeImplemented tr = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.rightTree());
		FormalExpressionTreeImplemented tl = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.leftTree());

		LinkedRBinaryTree<String> treeLeft1 = new LinkedRBinaryTree<String>(
				tl.derivePrivate(), this.tree.rightTree(), "*");
		LinkedRBinaryTree<String> treeRight1 = new LinkedRBinaryTree<String>(
				this.tree.leftTree(), tr.derivePrivate(), "*");
		LinkedRBinaryTree<String> treeLeft = new LinkedRBinaryTree<String>(
				treeLeft1, treeRight1, "-");
		LinkedRBinaryTree<String> tree2 = new LinkedRBinaryTree<String>("2");
		LinkedRBinaryTree<String> treeRight = new LinkedRBinaryTree<String>(
				this.tree.rightTree(), tree2, "^");
		LinkedRBinaryTree<String> tree = new LinkedRBinaryTree<String>(
				treeLeft, treeRight, "/");
		return tree;
	}

	/**
	 * Operation de dérivé sur une expression de type sin(f) => sin'(f)
	 * 
	 * @return retourne la dérivé sous forme f'*cos(f)
	 */
	private LinkedRBinaryTree<String> operationSinus() {
		FormalExpressionTreeImplemented tl = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.leftTree());

		LinkedRBinaryTree<String> treeCos = new LinkedRBinaryTree<String>(
				this.tree.leftTree(), null, "cos");
		LinkedRBinaryTree<String> tree = new LinkedRBinaryTree<String>(
				tl.derivePrivate(), treeCos, "*");
		return tree;
	}

	/**
	 * Operation de dérivé sur une expression de type cos(f) => cos'(f)
	 * 
	 * @return retourne la dérivé sous forme (0-f')*sin(f)
	 */
	private LinkedRBinaryTree<String> operationCosinus() {
		FormalExpressionTreeImplemented tl = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.leftTree());

		LinkedRBinaryTree<String> treeSin = new LinkedRBinaryTree<String>(
				this.tree.leftTree(), null, "sin");
		LinkedRBinaryTree<String> tree0 = new LinkedRBinaryTree<String>("0");
		LinkedRBinaryTree<String> treeLeft = new LinkedRBinaryTree<String>(
				tree0, tl.derivePrivate(), "-");
		LinkedRBinaryTree<String> tree = new LinkedRBinaryTree<String>(
				treeLeft, treeSin, "*");
		return tree;
	}

	/**
	 * Operation de dérive sur une expression de type f^a => (f^a)'
	 * 
	 * @return retourne la dérivé sous forme a*(f^(a-1))*f'
	 */
	private LinkedRBinaryTree<String> operationExp() {
		FormalExpressionTreeImplemented tl = new FormalExpressionTreeImplemented(
				(LinkedRBinaryTree<String>) this.tree.leftTree());

		LinkedRBinaryTree<String> tree1 = new LinkedRBinaryTree<String>("1");
		LinkedRBinaryTree<String> treeE = new LinkedRBinaryTree<String>(
				this.tree.rightTree(), tree1, "-");
		LinkedRBinaryTree<String> treeExp = new LinkedRBinaryTree<String>(
				this.tree.leftTree(), treeE, "^");
		LinkedRBinaryTree<String> treeLeft = new LinkedRBinaryTree<String>(
				treeExp, tl.derivePrivate(), "*");
		LinkedRBinaryTree<String> tree = new LinkedRBinaryTree<String>(
				this.tree.rightTree(), treeLeft, "*");
		return tree;

	}
}
