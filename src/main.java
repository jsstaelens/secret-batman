import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		ArrayList<String> stringList = new ArrayList<String>();
		stringList = reader("source.txt");
		Stack stack = new Stack();
		for(int i = 0;i<stringList.size();i++)
		{
			try
			{
				double number = Double.parseDouble(stringList.get(i));
				stack.push(stringList.get(i));
				
				
			}
			catch(NumberFormatException e)
			{
				if(stringList.get(i).equals("add"))
				{
					String str1 = stack.pop();
					String str2 = stack.pop();
					double number1 = Double.parseDouble(str1);
					double number2 = Double.parseDouble(str2);
					double number = number1 + number2;
					stack.push(Double.toString(number));
					
					
				}
				if(stringList.get(i).equals("sub"))
				{
					String str1 = stack.pop();
					String str2 = stack.pop();
					double number1 = Double.parseDouble(str1);
					double number2 = Double.parseDouble(str2);
					double number = number1 - number2;
					stack.push(Double.toString(number));
					
					
				}
				if(stringList.get(i).equals("mul"))
				{
					String str1 = stack.pop();
					String str2 = stack.pop();
					double number1 = Double.parseDouble(str1);
					double number2 = Double.parseDouble(str2);
					double number = number1 * number2;
					stack.push(Double.toString(number));
					
					
				}
				if(stringList.get(i).equals("div"))
				{
					String str1 = stack.pop();
					String str2 = stack.pop();
					double number1 = Double.parseDouble(str1);
					double number2 = Double.parseDouble(str2);
					if(number2 == 0)
					{
						System.out.println("Probleme : division par 0");
					}
					double number = number1 / number2;
					stack.push(Double.toString(number));
					
					
				}
				if(stringList.get(i).equals("pop"))
				{
					stack.pop();
				}
				if(stringList.get(i).equals("pstack"))
				{
					Stack stack2 = new Stack();
				}
				
			}
		}
		System.out.println(stack.pop());
		
	
		
		
		
		
	}

	public static ArrayList<String> reader(String source) {
		BufferedReader br = null;
		ArrayList<String> stringList = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(source));
		} catch (IOException e) {
			System.out.println("Probleme lors d'ouverture du fichier");
		}
		String str = null;
		try {
			str = br.readLine();
			}
		 catch (IOException e) {
			System.out.println("Probleme lors de la lecture de la première ligne");
		}
		
		while (str != null) {
			try {

				
				String[] tabString = str.split(" ");
				
				for (int i = 0; i < tabString.length; i++) {
					stringList.add(tabString[i]);
				}
				
				str = br.readLine();
			}

			catch (IOException e) {
				System.out.println("Probleme lors de la lecture d'une ligne");
			}
			
		}
		try {
			br.close();
		} catch (IOException e) {
			System.out.println("Probleme lors de la fermeture du fichier");
		}

		return stringList;
	}

}
