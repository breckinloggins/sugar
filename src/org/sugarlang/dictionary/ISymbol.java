package org.sugarlang.dictionary;

/**
 * 
 * @author bloggins
 * TODO: Do we need this?
 */
public interface ISymbol extends IEntry{
	
	/**
	 * Sets the name of this symbol.  Note that the symbol may be referred to by a different
	 * alias depending on environment
	 * @param name The name of the symbol.
	 */
	void setName(String name);
}
