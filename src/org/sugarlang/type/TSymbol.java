package org.sugarlang.type;



public class TSymbol extends BaseType {
	
	public TSymbol() throws TypeException	{
		seal();
	}
	
	@Override
	public String getDescription() {
		return "Represents objects of Symbol type";
	}

	@Override
	public String toString() {
		return "Symbol";
	}	
}
