/**
 * 
 */
package org.sugarlang.type;

/**
 * Represents a sequence of one or more whitespace characters
 * @author bloggins
 * 
 * TODO: This type can most certainly be removed and replaced with
 * an enumerated type as soon as we have user-defined types
 */
public class TWhitespace extends BaseType {
	public TWhitespace() throws TypeException	{
		seal();
	}
	
	@Override
	public String getDescription() {
		return "Represents a sequence of one or more whitespace characters";
	}

	@Override
	public String toString() {
		return "Whitespace";
	}	

}
