
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Groupe 5.2
 *
 */
public class FormalExpressionTreeImplemented implements FormalExpressionTree {

    public FormalExpressionTreeImplemented right;
    public FormalExpressionTreeImplemented left;
    public String element;
    public String expression;
    public LinkedRBinaryTree treeBuilt;

    public FormalExpressionTreeImplemented() {
        this.right = null;
        this.left = null;
        this.element = null;
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

        treeBuilt = new LinkedRBinaryTree(operands.pop());
        System.out.println(treeBuilt.toString());
    }

    private String[] createTabExpression(String expression) {
        String str[] = new String[expression.length()];
        char car;
        int i = 0, j = 0;
        while (i < expression.length()) {
            car = expression.charAt(i);
            if (car == 's' || car == 'c' || car == 'l') {  //s pour sin, c pour cos et l pour log
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
        Object op = operateurs.pop();
        LinkedRBinaryTree lbt;
        if (op.equals("sin") || op.equals("cos") || op.equals("log")) {
            LinkedRBinaryTree lbtLeft = new LinkedRBinaryTree(operands.pop());
            lbt = new LinkedRBinaryTree(null, lbtLeft, op);
        } else {
            LinkedRBinaryTree lbtRight = new LinkedRBinaryTree(operands.pop());
            LinkedRBinaryTree lbtLeft = new LinkedRBinaryTree(operands.pop());
            lbt = new LinkedRBinaryTree(lbtRight, lbtLeft, op);
        }
        operands.push(lbt);
    }

    public FormalExpressionTreeImplemented(FormalExpressionTreeImplemented left, FormalExpressionTreeImplemented right, String element) {
        this.right = right;
        this.left = left;
        this.element = element;
    }

    public FormalExpressionTreeImplemented getRight() {
        return right;
    }

    public FormalExpressionTreeImplemented getLeft() {
        return left;
    }

    public String getElement() {
        return element;
    }

    public void setRight(FormalExpressionTreeImplemented right) {
        this.right = right;
    }

    public void setLeft(FormalExpressionTreeImplemented left) {
        this.left = left;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public ArrayList<String> inorder(ArrayList<String> al) {
        if (isLeaf()) {
            al.add(element);
        } else {
            this.left.inorder(al);
            al.add(element);
            this.right.inorder(al);
        }
        return al;

    }

    public ArrayList<String> inorderGet() {
        ArrayList<String> al = new ArrayList<String>();
        return inorder(al);
    }

    private boolean isLeaf() {
        return this.right == null & this.left == null;
    }

    @Override
    public String toString() {
        ArrayList<String> al = this.inorderGet();
        String string = null;
        for (String str : al) {
            string = string + str;
        }
        return string;
    }

    /*public FormalExpressionTreeImplemented build(String expression) {
     //TODO
     return null;
     }*/
    /**
     * @pre : l'arbre surlequel on applique la fonction derive() a été
     * correctement construit
     * @post : Retourne une expression sous forme d'arbre représentant la dérivé
     * de l'arbre surlequel on a appliqué la fonction
     */
    @Override
    public FormalExpressionTreeImplemented derive() {
        //Analyse de la valeur de la racine et lance l'operation adéquate
        if (this.element == null) {
            return null;
        } else if (this.element.equals("+")) {
            return this.operationPlus();

        } else if (this.element.equals("-")) {
            return this.operationMinus();

        } else if (this.element.equals("*")) {
            return this.operationMultiply();

        } else if (this.element.equals("/")) {
            return this.operationDivide();

        } else if (this.element.equals("^")) {
            return this.operationExp();

        } else if (this.element.equals("x")) {
            return this.operationX();

        } else {
            return this.operationInt();
        }
    }

    /**
     *
     * @return Retourne la dérivé de x, c'est-à-dire 1, sous forme de
     * FormalExpressionTreeImplemented
     */
    public FormalExpressionTreeImplemented operationX() {
        FormalExpressionTreeImplemented tree = new FormalExpressionTreeImplemented(this.left, this.right, "1");
        return tree;
    }

    /**
     *
     * @return Retourne la dérivé d'une constante a, c'est-à-dire 0, sous forme
     * de FormalExpressionTreeImplemented
     */
    public FormalExpressionTreeImplemented operationInt() {
        FormalExpressionTreeImplemented tree = new FormalExpressionTreeImplemented(this.left, this.right, "0");
        return tree;
    }

    /**
     * Operation de dérivé sur une expression de type f+g => (f+g)'
     *
     * @return retourne la dérivé sous forme f' + g'
     */
    public FormalExpressionTreeImplemented operationPlus() {
        FormalExpressionTreeImplemented tree = new FormalExpressionTreeImplemented(this.left.derive(), this.right.derive(), "+");
        return tree;
    }

    /**
     * Operation de dérivé sur une expression de type f-g => (f-g)'
     *
     * @return retourne la dérivé sous forme f' - g'
     */
    public FormalExpressionTreeImplemented operationMinus() {
        FormalExpressionTreeImplemented tree = new FormalExpressionTreeImplemented(this.left.derive(), this.right.derive(), "-");
        return tree;
    }

    /**
     * Operation de dérivé sur une expression de type f*g => (f*g)'
     *
     * @return retourne la dérivé sous forme g*f' + f*g'
     */
    public FormalExpressionTreeImplemented operationMultiply() {
        FormalExpressionTreeImplemented treeLeft = new FormalExpressionTreeImplemented(this.left.derive(), this.right, "*");
        FormalExpressionTreeImplemented treeRight = new FormalExpressionTreeImplemented(this.left, this.right.derive(), "*");
        FormalExpressionTreeImplemented tree = new FormalExpressionTreeImplemented(treeLeft, treeRight, "+");
        return tree;
    }

    /**
     * Operation de dérivé sur une expression de type f/g => (f/g)'
     *
     * @return retourne la dérivé sous forme (g*f' - f*g')/(g^2)
     */
    public FormalExpressionTreeImplemented operationDivide() {
        FormalExpressionTreeImplemented treeLeft1 = new FormalExpressionTreeImplemented(this.left.derive(), this.right, "*");
        FormalExpressionTreeImplemented treeRight1 = new FormalExpressionTreeImplemented(this.left, this.right.derive(), "*");
        FormalExpressionTreeImplemented treeLeft = new FormalExpressionTreeImplemented(treeLeft1, treeRight1, "-");
        FormalExpressionTreeImplemented tree2 = new FormalExpressionTreeImplemented("2");
        FormalExpressionTreeImplemented treeRight = new FormalExpressionTreeImplemented(this.right, tree2, "^");
        FormalExpressionTreeImplemented tree = new FormalExpressionTreeImplemented(treeLeft, treeRight, "/");
        return tree;
    }

    /**
     * Operation de dérivé sur une expression de type sin(f) => sin'(f)
     *
     * @return retourne la dérivé sous forme f'*cos(f)
     */
    public FormalExpressionTreeImplemented operationSinus() {
        FormalExpressionTreeImplemented treeCos = new FormalExpressionTreeImplemented(this.left, null, "cos");
        FormalExpressionTreeImplemented tree = new FormalExpressionTreeImplemented(this.left.derive(), treeCos, "*");
        return tree;
    }

    /**
     * Operation de dérivé sur une expression de type cos(f) => cos'(f)
     *
     * @return retourne la dérivé sous forme (0-f')*sin(f)
     */
    public FormalExpressionTreeImplemented operationCosinus() {
        FormalExpressionTreeImplemented treeSin = new FormalExpressionTreeImplemented(this.left, null, "sin");
        FormalExpressionTreeImplemented tree0 = new FormalExpressionTreeImplemented("0");
        FormalExpressionTreeImplemented treeLeft = new FormalExpressionTreeImplemented(tree0, this.left.derive(), "-");
        FormalExpressionTreeImplemented tree = new FormalExpressionTreeImplemented(treeLeft, treeSin, "*");
        return tree;
    }

    /**
     * Operation de dérive sur une expression de type f^a => (f^a)'
     *
     * @return retourne la dérivé sous forme a*(f^(a-1))*f'
     */
    public FormalExpressionTreeImplemented operationExp() {
        FormalExpressionTreeImplemented tree1 = new FormalExpressionTreeImplemented("1");
        FormalExpressionTreeImplemented treeE = new FormalExpressionTreeImplemented(this.right, tree1, "-");
        FormalExpressionTreeImplemented treeExp = new FormalExpressionTreeImplemented(this.left, treeE, "^");
        FormalExpressionTreeImplemented treeLeft = new FormalExpressionTreeImplemented(treeExp, this.left.derive(), "*");
        FormalExpressionTreeImplemented tree = new FormalExpressionTreeImplemented(this.right, treeLeft, "*");
        return tree;

    }
}
