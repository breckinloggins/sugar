package com.breckinloggins.cx.dictionary;

/**
 * Represents a string entry in the dictionary
 * @author bloggins
 *
 */
public class TString implements IEntry {

	private String _value = "";
	
	public TString(String value)	{
		_value = value;
	}
	
	@Override
	public String getName() {
		return _value;
	}

	@Override
	public String getDescription() {
		return "(string)";
	}

}
