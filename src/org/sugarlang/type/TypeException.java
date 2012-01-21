/**
 * 
 */
package org.sugarlang.type;

/**
 * Represents an exception that has occured in the Sugar type system
 * @author bloggins
 */
public class TypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2633904604826036828L;

	public TypeException(String message)	{
		super(message);
	}
}
