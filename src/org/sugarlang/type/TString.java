/**
 * 
 */
package org.sugarlang.type;

/**
 * Represents all string objects
 * @author bloggins
 * TODO: Replace this with [Char]
 */
public class TString extends BaseType {

	public TString() throws TypeException	{
		seal();
	}
	
	@Override
	public String getDescription() {
		return "Represents objects of type String";
	}

	@Override
	public String toString() {
		return "String";
	}

}
