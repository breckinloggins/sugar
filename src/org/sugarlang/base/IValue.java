package org.sugarlang.base;

/**
 * Represents an object with immutable type and value
 * @author bloggins
 */
public interface IValue extends ISealable {
	
	/**
	 * Gets the type of this value
	 * @return The type
	 */
	IType getType();
	
	/**
	 * Gets the human-readable description of this value
	 * @return The description string
	 */
	String getDescription();
}
