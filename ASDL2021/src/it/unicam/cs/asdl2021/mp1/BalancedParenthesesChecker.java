package it.unicam.cs.asdl2021.mp1;

import java.util.Collection;

/**
 * An object of this class is an actor that uses an ASDL2021Deque<Character> as
 * a Stack in order to check that a sequence containing the following
 * characters: '(', ')', '[', ']', '{', '}' in any order is a string of balanced
 * parentheses or not. The input is given as a String in which white spaces,
 * tabs and newlines are ignored.
 * 
 * Some examples:
 * 
 * - " (( [( {\t (\t) [ ] } ) \n ] ) ) " is a string o balanced parentheses - " " is a
 * string of balanced parentheses - "(([)])" is NOT a string of balanced
 * parentheses - "( { } " is NOT a string of balanced parentheses - "}(([]))" is
 * NOT a string of balanced parentheses - "( ( \n [(P)] \t ))" is NOT a string
 * of balanced parentheses
 * 
 * @author Template: Luca Tesei, Implementation: Niccolò Cuartas -
 * niccolo.cuartas@studenti.unicam.it
 *
 */
public class BalancedParenthesesChecker {

    // The stack is to be used to check the balanced parentheses
    private ASDL2021Deque<Character> stack;

    /**
     * Create a new checker.
     */
    public BalancedParenthesesChecker() {
        this.stack = new ASDL2021Deque<Character>();
    }

    /**
     * Check if a given string contains a balanced parentheses sequence of
     * characters '(', ')', '[', ']', '{', '}' by ignoring white spaces ' ',
     * tabs '\t' and newlines '\n'.
     * 
     * @param s
     *              the string to check
     * @return true if s contains a balanced parentheses sequence, false
     *         otherwise
     * @throws IllegalArgumentException
     *                                      if s contains at least a character
     *                                      different form:'(', ')', '[', ']',
     *                                      '{', '}', white space ' ', tab '\t'
     *                                      and newline '\n'
     */
    public boolean check(String s) {
        ASDL2021Deque<Character> toIgnore = new ASDL2021Deque<>();
        if(!stack.isEmpty())
            stack.clear();

        char[] toAdd=s.toCharArray();
        for(char c:toAdd)
        {
            if(c!='(' && c!= ')' && c!= '[' && c!= ']' && c!= '{' && c!= '}' && c!=' ' && c!='\n' && c!='\t')//lancia eccezione se non sono caratteri previsti
                throw new IllegalArgumentException();
            if(c=='(' || c== '[' || c== '{')//se sono parentesi di apertura, pusho dall'inizio
            {
                stack.push(c);
                //continue;
            }

            else
            {
                toIgnore.push(c);//ignoro i caratteri accettati ma non considerati, filtro la stringa
            }

            if(stack.isEmpty() && toIgnore.isEmpty())
                return false;
            //if(stack.getFirst()==')'||stack.getFirst()==']'||stack.getFirst()=='}')//una stringa bilanciata non può iniziare così
                //return false;

            char check;

            switch (c)
            {
                case ')':
                    check=stack.pop();
                    if(check=='{' || check=='[')
                        return false;
                    break;

                case '}':
                    check = stack.pop();
                    if (check == '(' || check == '[')
                        return false;
                    break;

                case ']':
                    check = stack.pop();
                    if (check == '(' || check == '{')
                        return false;
                    break;
            }

        }
        return stack.isEmpty();
    }

}
