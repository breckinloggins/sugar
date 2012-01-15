package com.breckinloggins.cx;

import java.io.PrintStream;
import java.util.HashMap;

import com.breckinloggins.cx.reader.IReader;

public class Environment {
	
	private HashMap<String, String> _readers;
	private PrintStream _writer;
	
	public Environment(PrintStream writer)	{
		_readers = new HashMap<String, String>();
		_writer = writer;
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
			reader.setWriter(_writer);
			return reader;
			
		} catch (ClassNotFoundException e) {
			// The class name was registered, but doesn't really exist
			_writer.println("The reader alias \"" + alias + "\" does not have a backing class");
			return null;
		} catch (InstantiationException e) {
			// This probably happened because the reader did not specify a default
			// constructor
			_writer.println("There was an error constructing the reader \"" + alias + "\"");
			e.printStackTrace(_writer);
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace(_writer);
			return null;
		}
	}
}
