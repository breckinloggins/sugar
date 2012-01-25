package org.sugarlang.type;

public class TType extends BaseType {
	
	public TType() throws TypeException	{
		seal();
	}
	
	@Override
	public String getDescription() {
		return "Represents a type";
	}

	@Override
	public String toString() {
		return "Type";
	}
}
