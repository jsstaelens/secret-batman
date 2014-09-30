/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// package algo.mission1.postscript.source;


import java.util.ArrayList;

/**
 *
 * @author Audrey
 */
public class PostScript {

    private Stack stack;
    private ArrayList<String> stringList;
    private FileIO fileIn;
    private FileIO fileOut;
    
    public PostScript() {
        stack = new Stack();
    }
    
    private Stack getStack(){
        return stack;
    }
    
    public void runPostScript(String dirIn , String dirOut){
    	 this.fileIn = new FileIO(dirIn);
    	 this.fileOut = new FileIO(dirOut);
    	 
    	 this.stringList = fileIn.readByWord(); 
    			 
    	 double nb1, nb2;
         for (int i = 0; i < stringList.size(); i++) {
             try {
                 double number = Double.parseDouble(stringList.get(i));
                 stack.push(stringList.get(i));
             } catch (NumberFormatException e) {
                 if (stringList.get(i).equals("add")) {
                     nb1 = Double.parseDouble(stack.pop());
                     nb2 = Double.parseDouble(stack.pop());
                     this.add(nb1, nb2);
                 }
                 if (stringList.get(i).equals("sub")) {
                     nb1 = Double.parseDouble(stack.pop());
                     nb2 = Double.parseDouble(stack.pop());
                     this.sub(nb1,nb2);
                 }
                 if (stringList.get(i).equals("mul")) {
                     nb1 = Double.parseDouble(stack.pop());
                     nb2 = Double.parseDouble(stack.pop());
                     this.mul(nb1,nb2);
                 }
                 if (stringList.get(i).equals("div")) {
                     nb1 = Double.parseDouble(stack.pop());
                     nb2 = Double.parseDouble(stack.pop());
                     this.div(nb1, nb2);
                 }
                 if (stringList.get(i).equals("pop")) {
                     stack.pop();
                 }
                 if (stringList.get(i).equals("pstack")) {
                     fileOut.printStack(stack);
                 }
                 if (stringList.get(i).equals("dup")) {
                     this.dup(Double.parseDouble(stack.pop()));
                 }
                 if (stringList.get(i).equals("exch")) {
                     String str1 = stack.pop();
                     String str2 = stack.pop();
                     stack.push(str1);
                     stack.push(str2);
                 }
                 if (stringList.get(i).equals("eq")) {
                     nb1 = Double.parseDouble(stack.pop());
                     nb2 = Double.parseDouble(stack.pop());
                     this.exch(nb1, nb2);
                 }
                 if (stringList.get(i).equals("ne")) {
                     nb1 = Double.parseDouble(stack.pop());
                     nb2 = Double.parseDouble(stack.pop());
                     this.ne(nb1, nb2);
                 }
             }
         }
    }

    private void add(double number1, double number2) {
        double result = number1 + number2;
        stack.push(Double.toString(result));
    }

    private void sub(double number1, double number2) {
        double result = number1 - number2;
        stack.push(Double.toString(result));
    }

    private void mul(double number1, double number2) {
        double result = number1 * number2;
        stack.push(Double.toString(result));
    }

    private void div(double number1, double number2) {
        if (number2 == 0) {
            System.out.println("Erreur : division par 0");
        } else {
            double result = number1 / number2;
            stack.push(Double.toString(result));
        }
    }



    private void dup(double number) {
        stack.push(Double.toString(number));
        stack.push(Double.toString(number));
    }

    private void exch(double number1, double number2) {
        stack.push(Double.toString(number1));
        stack.push(Double.toString(number2));
    }

    private void eq(double number1, double number2) {
        if (number1 == number2) {
            stack.push("true");
        } else {
            stack.push("false");
        }
    }
    
    private void ne(double number1, double number2) {
        if (number1 == number2) {
            stack.push("false");
        } else {
            stack.push("true");
        }
    }

}
