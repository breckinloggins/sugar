/**
 * 
 */
package com.breckinloggins.cx;

/**
 * Represents an entry in the system's dictionary
 * @author bloggins
 */
public interface IEntry {
	
	/**
	 * Gets the canonical name of this entry.  It is usually referred to be something else in the current environment
	 * @return The canonical name
	 */
	String getName();
	
	/**
	 * Gets the human-readable description of this entry.
	 * @return The description
	 */
	String getDescription();
}
