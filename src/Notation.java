import java.util.regex.Pattern;

public class Notation {

    private static final String Operator = "-+*/";
    private static final String operands = "0123456789";

    static NotationQueue<Character> output;
    static NotationStack<Character> Stack;
    
 public static Double evaluatePostfixExpression(String postfix) throws InvalidNotationFormatException {
     
	    NotationStack<Double> stack = new NotationStack<Double>();
        
        if (postfix == null) {
            throw new InvalidNotationFormatException("Invalid postfix expression!");
        }

        try {
            char[] chars = postfix.toCharArray();

            for (char c : chars) {
                if (isOperand(c)) {
                    stack.push(new Double(c - '0')); 
                } else if (isOperator(c)) {
                    Double op1 = 0.0;
                    Double op2 = 0.0;
                    if (stack.isEmpty()) {
                        throw new InvalidNotationFormatException("Invalid format");
                    }
                    op1 = stack.pop();
                    if (stack.isEmpty()) {
                        throw new InvalidNotationFormatException("Invalid format");
                    }
                    op2 = stack.pop();
                    Double result;
                    switch (c) {
                        case '*':
                            result = op1 * op2;
                            stack.push(result);
                            break;
                        case '/':
                            result = op2 / op1;
                            stack.push(result);
                            break;
                        case '+':
                            result = op1 + op2;
                            stack.push(result);
                            break;
                        case '-':
                            result = op2 - op1;
                            stack.push(result);
                            break;
                    }
                }
            }
            return stack.pop();
        } catch (Exception e) {
            throw new InvalidNotationFormatException("Invalid postfix expression!");
        }
        
    }
    
    public static String convertInfixToPostfix(String input) throws InvalidNotationFormatException {
        if (input == null ) {
            throw new InvalidNotationFormatException("Invalid input");
        }
        
        try {
            output = new MyQueue<Character>(input.length());
            Stack = new MyStack<Character>(input.length());
            for (int j = 0; j < input.length(); j++) {
                char cha = input.charAt(j);
                switch (cha) {
                    case '+':case '-':
                        gotOper(cha, 1);
                        break; 
                    case '*': case '/':
                        gotOper(cha, 2); 
                        break; 
                    case '(': 
                        Stack.push('('); 
                        break;
                    case ')': 
                        gotParen(cha); 
                        break;
                    default: 
                        output.enqueue(cha);
                        break;
                }
            }
            while (!Stack.isEmpty()) {
                output.enqueue(Stack.pop());

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return output.toString(); 
    }

    public static String convertPostfixToInfix(String expression) throws InvalidNotationFormatException {
        String infix = "";
        String s = "";
        
        while (expression.indexOf(' ') >= 0) {
            expression = expression.replaceAll(" ", "");
        }
        for (int i = 0; i < expression.length(); i++) {
            s = s + expression.charAt(i) + " ";
        }

        expression = s.trim();

        NotationStack<String> postfixStack = new NotationStack<String>(expression.length());
        NotationStack<String> infixStack = new NotationStack<String>(expression.length());
        
        

        String opd = "";        
        String op2 = infixStack.pop();
        String op1 = infixStack.pop();
        String nChar = postfixStack.pop();
        try {
            for (int i = 0; i < expression.length(); i++) {
                infixStack.push(Character.toString(expression.charAt(i)));
            }

            while (!infixStack.isEmpty()) {
                postfixStack.push(infixStack.pop());
            }
            while (!postfixStack.isEmpty()) {

                if (nChar.equalsIgnoreCase("+") || nChar.equalsIgnoreCase("-")
                        || nChar.equalsIgnoreCase("*") || nChar.equalsIgnoreCase("/")) {
                    if (!opd.isEmpty()) {
                        infixStack.push(opd);
                        opd = "";
                    }
                    try {
                        if (!postfixStack.isEmpty()) {
                            String exp = "(" + op1 + nChar + op2 + ")";
                            infixStack.push(exp);
                        } else {
                            String exp = op1 + nChar + op2;
                            infixStack.push(exp);
                        }
                    } catch (StackUnderflowException e) {
                        throw new InvalidNotationFormatException("\nUnable to process user entered expression\n");

                    }

                } else if (isNumber(nChar) || nChar.equals(".")) {
                    opd += nChar;
                } else if (nChar.equalsIgnoreCase(" ")) {
                    if (!opd.isEmpty()) {
                        infixStack.push(opd);
                        opd = "";
                    }

                } else if (Pattern.matches("[A-z]", nChar.toString())) {

                    opd += nChar;

                } else {

                    s = ("Invalid: \"" + nChar + "\"");
                    throw new InvalidNotationFormatException(s);

                }
            }

            infix = infixStack.top();
            if (infixStack.top().equals("")) {

                throw new InvalidNotationFormatException("Unable to convert user entered Postfix expression: \n  \""
                        + expression + "\"");
            } else {
                
                while (expression.endsWith(" ")) {
                    expression = expression.trim();
                }
                while (infix.endsWith(" ")) {
                    infix = infix.substring(0, infix.length() - 2);
                }

            }
        } catch (Exception e) {
            throw new InvalidNotationFormatException(s);
        }
        return "(" + infix + ")";

    }


    private static boolean isOperator(char value) {
        return Operator.indexOf(value) >= 0;
    }

    private static boolean isOperand(char value) {
        return operands.indexOf(value) >= 0;
    }

    private static boolean isNumber(String st) {
        try {
            Integer.parseInt(st);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
 

    private static void gotOper(char opThis, int p1) throws StackUnderflowException, QueueOverflowException, StackOverflowException {
        while (!Stack.isEmpty()) {
            char opT = (String Stack.pop() + "").charAt(0);
            if (opT == '(') {
                Stack.push(opT);
                break;
            }
            else {
                int p2;
                if (opT == '+' || opT == '-') {
                    p2 = 1;
                } else {
                    p2 = 2;
                }
                if (p2 < p1) 
                { 
                    Stack.push(opT); 
                    break;
                } else 
                {
                    output.enqueue(opT); 
                }
            }
        }
        Stack.push(opThis);
    }

    private static void gotParen(char CH) throws StackUnderflowException, QueueOverflowException {
        while (!Stack.isEmpty()) {
            char CH = (String Stack.pop() + "").charAt(0);
            if (CH == '(') {
                break;
            } else {
                output.enqueue(CH);
            }
        }
    }

}