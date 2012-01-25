package org.sugarlang.base;

import org.sugarlang.type.TypeException;

/**
 * Represents an object that is immutable once seal() is called.  Any attempt to modify
 * the value of the object should throw a TypeException
 * @author bloggins
 *
 * NOTE: The purpose of implementing this interface is to specify a way for objects to be cleanly
 * "setup" with an API that extends beyond the constructor, while still allowing the object to be
 * "read only" after a certain point.
 */
public interface ISealable {
	
	/**
	 * Marks this object as sealed.  No further modifications are allowed to this object
	 * @throws TypeException Thrown if the object has already been sealed
	 */
	void seal() throws TypeException;
	
	/**
	 * Gets whether this object has been sealed
	 * @return
	 */
	boolean isSealed();
}
