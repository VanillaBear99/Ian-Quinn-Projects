package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) {
		BigInteger parsedList = new BigInteger();
		DigitNode head = null;
		DigitNode newNode;
		parsedList.numDigits = integer.length();
		int counter = 0;
		char a = integer.charAt(counter);
		if(integer.charAt(0) == '-') {
			parsedList.negative = true;
			integer = integer.substring(1);
		}
		if(integer.charAt(0) == '+') {
			parsedList.negative = false;
			integer = integer.substring(1);
		}
		while(integer.charAt(0) == '0' && integer.length() > 1) {
			integer = integer.substring(1);
		}
		
		while(integer.length()>0) {
			int data = 0;
			a=integer.charAt(0);
			if(!Character.isDigit(a)) {
				throw new IllegalArgumentException();
			}
			if(a == '0') {
				data = 0;
			}
			if(a == '1') {
				data = 1;
			}
			if(a == '2') {
				data = 2;
			}
			if(a == '3') {
				data = 3;
			}
			if(a == '4') {
				data = 4;
			}
			if(a == '5') {
				data = 5;
			}
			if(a == '6') {
				data = 6;
			}
			if(a == '7') {
				data = 7;
			}
			if(a == '8') {
				data = 8;
			}
			if(a == '9') {
				data = 9;
			}
			counter++;
			newNode = new DigitNode(data, head);
			head = newNode;
			integer = integer.substring(1);
			
		}
		parsedList.numDigits = counter;
		parsedList.front = head;
		return parsedList;
		
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */

	public static boolean confirmed(BigInteger first, BigInteger second) {
		/**
		BigInteger temp1 = first;
		BigInteger temp2 = second;
		*/
		
		boolean confirmed = false;
		DigitNode comp1 = first.front;
		
		DigitNode comp2 = second.front;
		int size1 = first.numDigits;
		int size2 = second.numDigits;
		while(size1 > 0) {
			for(int counter1 = size1; counter1 > 1; counter1--) {
				comp1 = comp1.next;
			}
			for(int counter2 = size2; counter2> 1; counter2--) {
				comp2 = comp2.next;
			}
			if(comp1.digit != comp2.digit) {
				if(comp1.digit >= comp2.digit) {
					confirmed = true;
					break;
				}
				else{
					confirmed = false;
					break;
				}
			}
			size1--;
			size2--;
		
			comp1 = first.front;
			comp2 = second.front;
		}
		
		
		return confirmed;
	}
	public static BigInteger add(BigInteger first, BigInteger second) {
		BigInteger finalSum = new BigInteger();
		int size1 = first.numDigits;
		int size2 = second.numDigits;
		boolean confirmed = true;
		if(size1 > size2) {
			confirmed = true;
		}
		else if(size2 > size1) {
			confirmed = false;
		}
		else confirmed = confirmed(first, second);
		if(first.negative == true && second.negative == true) {
			finalSum.negative = true;
		}
		if(first.negative == false && second.negative == true) {
			finalSum.negative = false;
		}
		if(first.negative == true && second.negative == false) {
			finalSum.negative = true;
		}
		if(first.negative == true && second.negative == false) {
			if(confirmed == true) {
				finalSum.negative = true;
			}
			else finalSum.negative = false;
		}
		if(first.negative == false & second.negative == true) {
			if(confirmed == true) {
				finalSum.negative = false;
			}
			else finalSum.negative = true;
		}
		BigInteger sum = new BigInteger();
		DigitNode firstNode = first.front;
		DigitNode secondNode = second.front;
		DigitNode sumNode;
		DigitNode nextNode = sum.front;
		BigInteger temp1 = first;
		BigInteger temp2 = second;
		int firstDigit = firstNode.digit;
		int secondDigit = secondNode.digit;
		int sumDigit;
		int carryover = 0; 
		if(confirmed == false) {
			first = temp2;
			second = temp1;
		}
		firstNode = first.front;
		secondNode = second.front;
		firstDigit = firstNode.digit;
		secondDigit = secondNode.digit;
		
		while(firstNode != null || secondNode != null) {
			// The actual arithmetic
			sumDigit = 0;
			
				if(first.negative == true && second.negative == true) {
					sumDigit = carryover + firstDigit + secondDigit;
				}
				else if((first.negative == false && second.negative == true)){
					if(confirmed == true) {
						firstDigit = firstNode.digit;
						sumDigit = firstDigit - secondDigit - carryover;
					}
					else {
						sumDigit =  firstDigit - secondDigit - carryover;
					}
				}
				else if(first.negative == true && second.negative == false) {
					if(confirmed == true) {
						sumDigit = firstDigit - secondDigit - carryover;
					}
					else{
						sumDigit = firstDigit - secondDigit - carryover;
					}
				}
				
				else {
					sumDigit = firstDigit + secondDigit + carryover;
				}
				carryover = 0;
			
				if(sumDigit >= 10) {
					sumDigit = sumDigit - 10;
					carryover = 1;
				}
				if(sumDigit < 0) {
					sumDigit = sumDigit + 10;
					carryover = 1;
				}
				sumNode = new DigitNode(sumDigit, null);

				if(sum.front == null) {
					sum.front = sumNode;
					sumNode.next = nextNode;
				}
				else {
					nextNode.next = sumNode;
				}
				nextNode = sumNode;
				
				if(firstNode != null) {
					firstNode = firstNode.next;
					if(firstNode == null) {
					firstDigit= 0;
					}
					else firstDigit = firstNode.digit;
				}
				if(firstNode == null) {
					firstDigit = 0;
				}
				if(secondNode != null) {
					secondNode = secondNode.next;
				if(secondNode == null) {
					secondDigit = 0;
				}
				else secondDigit = secondNode.digit;
			}
			if(secondNode == null) {
				secondDigit = 0;
			}
		}
		if(carryover > 0) {
			nextNode.next = new DigitNode(carryover, null);
		}
		DigitNode test3 = sum.front;
		int size3 = 0;
		while(test3 != null) {
			test3 = test3.next;
			size3++;
		}
		
		test3 = sum.front;
		sum.numDigits = size3;
		while(size3 > 1) {
			int counter = size3;
			test3 = sum.front;
			while(counter >= 2 && test3.next!= null) {
				test3 = test3.next;
				counter--;
			}
			if(test3.digit == 0) {
				size3--;
			}
			else break;
		}
		test3 = sum.front;
		int counter2 = size3;
		int finalSize = size3;
		int finalCounter = size3;
		DigitNode finalFront = null;
		while(finalCounter >= 1) {
			counter2 = size3;
			while(counter2 > 1) {
				test3 = test3.next;
				counter2--;
			}
			DigitNode finalNext = new DigitNode(test3.digit, finalFront);
			finalFront = finalNext;
			test3 = sum.front;
			finalCounter--;
			size3--;
		}
		finalSum.front = finalFront;
		finalSum.numDigits = finalSize;
		
		return finalSum;
		
	}
		
	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		
		
		BigInteger finalProduct = new BigInteger();
		BigInteger runningTally = new BigInteger();
		DigitNode firstNode = first.front;
		DigitNode secondNode = second.front;
		BigInteger temp = new BigInteger();
		DigitNode resultNode = new DigitNode(0, null);
		temp.front = resultNode;
		int value1;
		int value2;
		int multiple;
		int zeroes;
		int zero1 = 0;
		int zero2 = 0;
		
		while(firstNode != null) {
			value1 = firstNode.digit;
			while(secondNode != null) {
				BigInteger input = new BigInteger();
				DigitNode head = new DigitNode(0, null);
				value2 = secondNode.digit;
				zeroes = zero1 + zero2;
				multiple = value1 * value2;
				if(multiple >= 10) {
					head.digit = multiple / 10;
					DigitNode newNode = new DigitNode(multiple % 10, head);
					head = newNode;
				}
				
				else {
					head.digit = multiple;
				}
				for(int counter = 0; counter < zeroes; counter++) {
					DigitNode newNode = new DigitNode(0, null);
					newNode.next = head;
					head = newNode;
				}
				input.front = head;
				input.negative = false;
				temp.negative = false;
				runningTally = add(temp, input);
				temp = runningTally;
				secondNode = secondNode.next;
				zero2++;
				
		
			}
			zero2 = 0;
			secondNode = second.front;
			zero1++;
			finalProduct = runningTally;
			firstNode = firstNode.next;
			if((first.negative == true || second.negative == true) && !(first.negative == true && second.negative == true)){
				finalProduct.negative = true;
			}
		}
		// following line is placeholder - compiler needs a return
		// modify it according to need
		return finalProduct; 
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	
}
