package com.breckinloggins.cx.dictionary;

public class Symbol implements ISymbol {
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
