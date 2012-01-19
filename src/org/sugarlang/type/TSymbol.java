package org.sugarlang.type;

import org.sugarlang.dictionary.ISymbol;

public class TSymbol implements ISymbol {
	private String _name;
	
	@Override
	public String getName() {
		return _name;
	}
	@Override

	public void setName(String name) {
		_name = name;
	}

	@Override
	public String getDescription() {
		// TODO: Probably can come up with a better description
		return "(symbol)";
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = (!(null == obj) && 
				(obj instanceof TSymbol) && 
				((TSymbol)obj).getName().equals(getName()));
		
		return result;
		
	}
	
	@Override
	public int hashCode() {
		return "TSymbol".hashCode() + getName().hashCode();
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
