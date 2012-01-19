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

	
}
