package org.sugarlang.type;



/**
 * Represents the null type
 * @author bloggins
 *
 */
public class TNull extends BaseType {

	public TNull() throws TypeException	{
		seal();
	}
	
	@Override
	public String getDescription() {
		return "The Null Type";
	}

	@Override
	public String toString() {
		return "Null";
	}

}
