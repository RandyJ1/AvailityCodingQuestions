import java.util.*;

/** Coding exercise: You are tasked to write a checker that validates the parentheses of a LISP code.  
    Write a program (in Java or JavaScript) which takes in a string as an input and returns true if all 
    the parentheses in the string are properly closed and nested. 
    
    @Author: Randy Jaouhari
*/
public class ParenthesesChecker { 

    public static void main(String[] args) {
        // Tests
        // System.out.println(solution("(")); // false
        // System.out.println(solution(")")); // false
        // System.out.println(solution("(Hello World!)")); // true
        System.out.println(solution(args[0]));
    }

    public static boolean solution(String s) {
        int openParenthesesCounter = 0;
        // read string one character at a time from left to right.
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                // if character is left paran we increment number of open parantheses.
                openParenthesesCounter++;
            } else if(s.charAt(i) == ')') {
                // if character is right paran we decrement number of open parantheses.
                openParenthesesCounter--;
            } else {
                // any other character and we just want to move to the next one.
                continue;
            }
            // you can give a matching left parantheses for closing one when going from left to right
            // so if we ever get below 0 we know we can't have a valid string.
            if(openParenthesesCounter < 0) {
                return false;
            }
        }
        // the end result we be 0 if all open parantheses are properly closed.
        return openParenthesesCounter == 0;
    }
}