package org.sugarlang.type;

import org.sugarlang.dictionary.BaseType;


public class TSymbol extends BaseType {
	private String _val;
	
	/**
	 * Creates a symbol with the given value
	 * @param name
	 */
	public TSymbol(String val)	{
		_val = val;
	}
	
	@Override
	public String getDescription() {
		return "Represents a collection of non-whitespace characters";
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = (!(null == obj) && 
				(obj instanceof TSymbol) && 
				((TSymbol)obj).toString().equals(_val));
		
		return result;	
	}
	
	@Override
	public int hashCode() {
		return "TSymbol".hashCode() + _val.hashCode();
	}
	
	@Override
	public String toString() {
		return _val;
	}	
}
