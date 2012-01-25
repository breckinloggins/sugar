/**
 * 
 */
package org.sugarlang.type;

/**
 * A character value (or EOF)
 * @author bloggins
 */
public class TChar extends BaseType {

	/**
	 * Constructs a character object type
	 * @throws TypeException 
	 */
	public TChar() throws TypeException	{
		seal();
	}
		
	@Override
	public String getDescription() {
		return "A character value (or EOF)";
	}
	
	@Override
	public String toString()	{
		return "Char";
	}
}
