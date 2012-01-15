package com.breckinloggins.cx;

import java.util.HashMap;

import com.breckinloggins.cx.reader.IReader;

public class Environment {
	
	private HashMap<String, String> _readers;
	
	public Environment()	{
		_readers = new HashMap<String, String>();
	}
	
	/**
	 * Returns the Java class name of the IReader by the given alias
	 * @param alias A registered alias for a reader
	 * @return The class name of the reader, or NULL
	 */
	public String getReaderClassName(String alias)	{
		return _readers.get(alias);
	}
	
	public void setReaderAlias(String alias, String className)	{
		_readers.put(alias, className);
	}
	
	public IReader createReader(String alias)	{
		String className = this.getReaderClassName(alias);
		if (className == null || className.isEmpty())	{
			return null;
		}
		
		try {
			Class<?> theClass = Class.forName(className);
			IReader reader = (IReader)theClass.newInstance();
			return reader;
			
		} catch (ClassNotFoundException e) {
			// The class name was registered, but doesn't really exist
			return null;
		} catch (InstantiationException e) {
			// This probably happened because the reader did not specify a default
			// constructor
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
