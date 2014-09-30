
public class Stack {
	public class Node{
		public String data;
		public Node next;
		public Node(String data)
		{
			this.data = data;
		}
	}
	private Node head;
	public Stack() {
		this.head = null;
	}
	public void push(String data)
	{
		Node node = new Node(data);
		if(this.head == null)
		{
			node.next = null;
			this.head = node;
			
		}
		else
		{
			node.next = this.head;
			this.head = node;
		}
	}
	public boolean isEmpty()
	{
		if(this.head == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String pop(){
		if(this.head == null)
		{
			System.out.println("Couldn't pop because the stack is Empty");
			return null;
		}
		else if(this.head.next == null)
		{
			Node node = this.head;
			this.head = null;
			return node.data;
		}
		else
		{
			Node node = this.head;
			this.head = this.head.next;
			return node.data;
		}
	}

}
