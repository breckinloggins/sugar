package com.breckinloggins.cx;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.reader.BeginPair;
import com.breckinloggins.cx.reader.Discriminator;
import com.breckinloggins.cx.reader.EndPair;
import com.breckinloggins.cx.reader.Error;
import com.breckinloggins.cx.reader.IReader;
import com.breckinloggins.cx.reader.Name;
import com.breckinloggins.cx.reader.Pair;
import com.breckinloggins.cx.reader.Reader;
import com.breckinloggins.cx.reader.Terminator;
import com.breckinloggins.cx.reader.Whitespace;

public class Interpreter {
	private Environment _rootEnvironment;
	private IReader _reader;

	// TODO: Need an interpreter listener interface that will notify when we have:
	// 1. Read a char
	// 2. Changed readers
	// 3. Evaluated something
	//
	// This will be the start of syntax highlighting in the input pane.  For example, it should
	// be listening for a "reader selector" or "name" and can highlight appropriately.
	//
	// For syntax highlighting, we'll also need to carry "SourceLocations" with us as we read so 
	// we can figure out what to go back and highlight.
	
	/**
	 * Constructs a new interpreter
	 */
	public Interpreter()	{
		_rootEnvironment = new Environment();
		
		// Set up the basic starting environment
		// TODO: Need to find a better way to do this
		//
		// As stated below, reader should start from nothing.
		// First character is the reader metachar (usually '#' but can be anything).  After that, 
		// there it would be nice if we could configure most or all of these readers from source itself.
		// In other words, what is the minimum set of "hard coded" readers.  I'm thinking the reader system
		// will end up being a stack-based system.
		//
		// Should even define syntax highlighting from the reader, so that maybe we start with:
		// ReaderType (name) gets a certain behavior and has certain "metadata", and we can start with
		// fontWeight, backgroundColor, foregroundColor.  This will have to be made more generic in the future, of 
		// course, but it will do for now.  So we declare (for example) ReaderType = name (foregroundColor = blue) and
		// then every time we read a NAME the text area will be able to see the source location and turn it blue.
		//
		// But, again, we ultimately want to hard-code as little of this as possible.
		Environment env = _rootEnvironment;
		env.setReaderAlias("pair", Pair.class.getName());
		env.setReaderAlias("beginPair", BeginPair.class.getName());
		env.setReaderAlias("endPair", EndPair.class.getName());
		env.setReaderAlias("discriminator", Discriminator.class.getName());
		env.setReaderAlias("error", Error.class.getName());
		env.setReaderAlias("name", Name.class.getName());
		env.setReaderAlias("reader", Reader.class.getName());
		env.setReaderAlias("terminator", Terminator.class.getName());
		env.setReaderAlias("whitespace", Whitespace.class.getName());
	}
	
	/**
	 * Reads input from a string reader
	 * @param sr The string reader from which to read
	 */
	public void TEMP_read(StringReader sr)	{
		if (null == _reader)	{
			_reader = new Discriminator();
		}
		
		try {
			do {
				_reader = _reader.read(sr, _rootEnvironment);
			} while (_reader != null && _reader.getClass().getName() != "Terminator");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the given character and interprets it
	 * @param c The character to read
	 */
	public void readChar(char c)	{
		if (null == _reader)	{
			// TODO: This is not right.  In a null (fresh state), the interpreter should expect that
			// its first character is the one that will be use to distinguish a reader literal.  In other words, 
			// the environment should be set up "from nothing" each time.  We can then have a standard preamble that
			// can be run by clicking a button or something, and in the future that preamble set will be run based
			// on language selection and/or file extension.
			_reader = new Discriminator();
		}
		
		// TODO: Readers need to read a character at a time, returning the same reader if it 
		// needs to accept more input.
		//_reader = _reader.read(, env);
	}
}
