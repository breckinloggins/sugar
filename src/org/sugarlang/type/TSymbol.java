package org.sugarlang.type;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseType;


public class TSymbol extends BaseType {
	private String _val;
	
	@Override
	public String getName() {
		return _val;
	}

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
				((TSymbol)obj).getName().equals(getName()));
		
		return result;	
	}
	
	@Override
	public int hashCode() {
		return "TSymbol".hashCode() + getName().hashCode();
	}
	
	@Override
	public String toString() {
		return _val;
	}

	@Override
	public void Evaluate(Environment env) {
		// Nope
	}
	
}
