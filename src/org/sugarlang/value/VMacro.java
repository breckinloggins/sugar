/**
 * 
 */
package org.sugarlang.value;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import org.sugarlang.base.IValue;
import org.sugarlang.type.BuiltinTypes;
import org.sugarlang.type.TypeException;

/**
 * Contains a stack of zero or more quoted elements
 * @author bloggins
 */
public class VMacro extends BaseValue {
	
	private Stack<IValue> _stack;
	
	/**
	 * Constructs a VMacro with a given Stack
	 * @param stack The Stack to use
	 * @throws TypeException Thrown if null is passed
	 */
	public VMacro(Stack<IValue> stack) throws TypeException	{
		setType(BuiltinTypes.Macro);
		
		if (null == stack)	{
			throw new TypeException("Cannot create a null VMacro");
		}

		_stack = stack;
		seal();
	}
	
	/**
	 * Gets the macro's stack in list form, with the top of the stack at the end
	 * @return The stack list
	 */
	public List<IValue> getStackList()	{
		return Collections.unmodifiableList(_stack);
	}

	/**
	 * Creates a new VMacro copy
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		try {
			return new VMacro(_stack);
		} catch (TypeException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Checks whether the given VMacro is equal to this VMacro
	 */
	@Override
	public boolean equals(Object obj) {
		return null != obj && 
				obj instanceof VMacro &&
				getStackList().equals(((VMacro)obj).getStackList());
	}

	/**
	 * Compures the hash code
	 */
	@Override
	public int hashCode() {
		return "VMacro".hashCode() + _stack.hashCode();
	}

	/**
	 * Gets the string value of this VString
	 */
	@Override
	public String toString() {
		return "Macro (" + _stack.size() + " objects)";
	}

}
