/**
 * 
 */
package org.sugarlang.type;


/**
 * Represents a quoted item
 * @author bloggins
 */
public class TQuote extends BaseType {
	
	/**
	 * Constructs a quoted object type
	 * @param inner
	 * @throws TypeException 
	 */
	public TQuote() throws TypeException	{
		seal();
	}
		
	@Override
	public String getDescription() {
		return "Quotes an item to prevent it from being immediately evaluated";
	}
	
	@Override
	public String toString()	{
		return "Quote";
	}
}
