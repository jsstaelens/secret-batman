/**
 * Classe Stack représentant une Pile
 * 
 * @author Groupe 5.2
 * 
 */
public class Stack {

	/*
	 * Classe node(nested class) permettant de faire des liens entre les
	 * différents éléments de la Pile
	 */

	public static class Node {

		// Variable d'instance de la nested classe Node
		public String data;
		public Node next;

		/*
		 * Constructeur de la classe Node
		 */
		public Node(String data) {
			this.data = data;
		}
	}

	// Variable d'instance de la classe Stack
	private Node head;

	/*
	 * Constructeur de stack
	 */
	public Stack() {
		this.head = null;
	}

	/**
	 * @pre /
	 * @post rajoute data au sommet de la pile
	 */
	public void push(String data) {
		Node node = new Node(data);
		if (this.head == null) {
			node.next = null;
			this.head = node;

		} else {
			node.next = this.head;
			this.head = node;
		}
	}

	/**
	 * @pre /
	 * @post retourne true si la pile est vide sinon false
	 */
	public boolean isEmpty() {
		if (this.head == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @pre /
	 * @post Enlever l'element du dessus de la pile de la pile et le retourne
	 */
	public String pop() {
		if (this.head == null) {
			System.err
					.println("Impossible de pop un element de la pile car elle est vide");
			return null;
		} else if (this.head.next == null) {
			Node node = this.head;
			this.head = null;
			return node.data;
		} else {
			Node node = this.head;
			this.head = this.head.next;
			return node.data;
		}
	}

	/**
	 * @pre /
	 * @post retourne un string contenant le contenu de la pile
	 */
	public String toString() {
		Stack stack2 = new Stack();
		String str = "";
		String strC = "";
		while (!this.isEmpty()) {
			stack2.push(this.pop());
		}
		while (!stack2.isEmpty()) {
			strC = stack2.pop();
			str = str + strC + " ";
			this.push(strC);
		}
		return str;

	}

}
