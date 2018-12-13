package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	 StringTokenizer token = new StringTokenizer(expr, delims);
    	 int counter = token.countTokens();
    	 String temp = expr;
    	 while(counter > 1) {
    		 String current = token.nextToken();
    		 int index = temp.indexOf(current);
    		 char checker2 = temp.charAt(index);
    		 index = index + current.length();
    		 char checker1 = temp.charAt(index);
    		 if(checker1 == '[') {
    			 Array newArray = new Array(current);
    			 int checker3 = arrays.indexOf(newArray);
    			 if(checker3 == -1) {
    				 arrays.add(newArray);
    			 }
    		 }
    		 else if(!Character.isDigit(checker2)) {
    			 Variable newVar = new Variable(current);
    			 int checker3 = vars.indexOf(newVar);
    			 if(checker3 == -1) {
    				 vars.add(newVar);
    			 }
    		 }
    		 temp = temp.substring(index);
    		 counter--;
    	 }
    	 String current = token.nextToken();
    	 int index = temp.indexOf(current);
    	 char checker4 = temp.charAt(index);
    	 if(!Character.isDigit(checker4)) {
    		 Variable newVar = new Variable(current);
    		 int checker3 = vars.indexOf(newVar);
    		 if(checker3 == -1) {
    			 vars.add(newVar);
    		 }
    	 }
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	// following line just a placeholder for compilation
    	Stack<Float> values = new Stack<Float>();
    	Stack<Character> operations = new Stack<Character>();
    	float evaluation = 0;
    	char checker;
    	for(int x = 0; x < expr.length(); x++) {
    		checker = expr.charAt(x);
    		char operation;
    		float value = 0;
    		if(Character.isDigit(checker)) {
    			int start = x;
    			String current = null;
    			while(Character.isDigit(checker)&& x < expr.length()) {
    				checker = expr.charAt(x);
    				if(Character.isDigit(checker)) {
    					x++;
    				}
    			}
    			current = expr.substring(start, x);
    			
    			value = Integer.parseInt(current);
    			values.push(value);
    			
    		}
    		if(Character.isAlphabetic(checker)) {
    			int start = x;
    			checker = expr.charAt(x);
    			while(Character.isAlphabetic(checker) && x < expr.length()) {
    				checker = expr.charAt(x);
    				if(Character.isAlphabetic(checker)) {
    					x++;
    				}
    			}
    			String current = expr.substring(start, x);
    			int length = current.length();
    			int index1 = expr.indexOf(current);
    			int end = index1 + length;
    			char checker3;
    			if(end >= expr.length()) {
    				checker3 = expr.charAt(end-1);
    			}
    			else checker3 = expr.charAt(end);
    			if(checker3 == '[') {
    				int counter = 0;
        			int index;
        			int start1 = end;
        			for(index = start1+1; index < expr.length(); index++) {
        				char checker2 = expr.charAt(index);
        				System.out.println(checker2);
        				if(checker2 == '['  ) {
        					counter++;
        				}
        				if(checker2 == ']' && counter == 0) {
        					break;
        				}
        				if(checker2 == ']' && counter > 0) {
        					counter--;
        				}
        			}
        			String test = expr.substring(start1 +1, index);
        			int arrayvalue = (int)evaluate(test, vars, arrays);
        			for(int b = 0; 0 < arrays.size(); b++) {
        				if(arrays.get(b).name.equals(current)) {
        					int arrayValues[] = arrays.get(b).values;
        					value = arrayValues[arrayvalue];
        					break;
        				}
        			}
        			values.push(value);
        			x = index;
    			}
    			else for(int a = 0; a < vars.size(); a++) {
    				if(vars.get(a).name.equals(current)) {
    					value = vars.get(a).value;
    					values.push(value);
    					break;
    				}
    			}
    		
    		}
    		if(checker == '+' || checker == '-'|| checker == '/' || checker ==  '*') {
    			operation = checker;
    			if(operations.isEmpty()) {
    				operations.push(operation);
    			}
    			else if((operation == '*' || operation == '/') && (operations.peek() != '*' || operations.peek() != '/')) {
    				operations.push(operation);
    			}
    			else if((operation == '+' || operation == '-')) {
    				while(!operations.isEmpty()) {
    					float value1 = values.pop();
    					float value2 = values.pop();
    					char operator = operations.pop();
    					if(operator == '+') {
    						value = value1 + value2;
    					}
    					else if(operator == '-') {
    						value = value2 - value1;
    					}
    					else if(operator == '*') {
    						value = value1 * value2;
    					}
    					else {
    						value = value2 / value1;
    					}
    					values.push(value);
    					}
    					operations.push(operation);
    				}
    			else {
    				float value1 = values.pop();
					float value2 = values.pop();
					char operator = operations.pop();
					if(operator == '+') {
						value = value1 + value2;
					}
					else if(operator == '-') {
						value = value2 - value1;
					}
					else if(operator == '*') {
						value = value1 * value2;
					}
					else {
						value = value2 / value1;
					}
					values.push(value);
					operations.push(operation);
    			}
    			
    		}
    		if(checker == '(') {
    			int counter = 0;
    			int index;
    			int start = x;
    			for(index = x+1; index < expr.length(); index++) {
    				char checker2 = expr.charAt(index);
    				if(checker2 == '('  ) {
    					counter++;
    				}
    				if(checker2 == ')' && counter == 0) {
    					break;
    				}
    				if(checker2 == ')' && counter > 0) {
    					counter--;
    					}
    				
    			}
    			String test = expr.substring(start +1, index);
    			value = (int)evaluate(test, vars, arrays);
    			
    			values.push(value);
    			x = index;
    		}
    		else continue;
    	}
    	while(!operations.isEmpty()) {
    		float value1 = values.pop();
			float value2 = values.pop();
			float value;
			char operator = operations.pop();
			if(operator == '+') {
				value = value1 + value2;
			}
			else if(operator == '-') {
				value = value2 - value1;
			}
			else if(operator == '*') {
				value = value1 * value2;
			}
			else {
				value = value2 / value1;
			}
			
			values.push(value);
    	}
    	
    	
    	evaluation = values.pop();
    	return evaluation;
    }    
}
