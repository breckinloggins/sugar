/**
 * 
 */
package org.sugarlang.type;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseType;

/**
 * Type representing a stack of quoted instructions, the unquoted form of which can be 
 * placed on the interpreter's stack and evaluated
 * @author bloggins
 * 
 * TODO: Make immutable
 */
public class TMacro extends BaseType {

	private Stack<Object> _stack;
	
	public TMacro(Stack<Object> stack)	{
		_stack = stack;
		
	}
	
	/**
	 * Gets the macro's stack in list form, with the top of the stack at the end
	 * @return The stack list
	 */
	public List<Object> getStackList()	{
		return Collections.unmodifiableList(_stack);
	}
	
	@Override
	public String getName() {
		return "TMacro (" + _stack.size() + " objects)";
	}

	@Override
	public String getDescription() {
		return "Type representing a stack of quoted instructions, the unquoted form of which can be placed on the interpreter's stack and evaluated";
	}

	@Override
	public String toString() {
		return getName();
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.dictionary.IType#Evaluate(org.sugarlang.Environment)
	 */
	@Override
	public void Evaluate(Environment env) {
		// remove
	}

}
