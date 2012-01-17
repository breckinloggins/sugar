package com.breckinloggins.cx;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import com.breckinloggins.cx.dictionary.IReader;

public class Interpreter {
	private Environment _rootEnvironment;
	private IReader _reader;
	private PrintStream _writer;

	// TODO: Let's not try to interpret C-like code right away.  Let's do:
	// 1. LISP
	// 2. BASIC
	// 3. Pascal
	// 4. C-ish (like Go, with easy parse semantics)
	// 5. C/C#/Java
	
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
	 * @param writer The print writer to use for output
	 */
	public Interpreter(PrintStream writer)	{
		_writer = writer;
		_rootEnvironment = new Environment(_writer);
		
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
		env.setReader("command", new com.breckinloggins.cx.reader.Command());
		env.setReader("symbol", new com.breckinloggins.cx.reader.Symbol());
		env.setReader("discriminator", new com.breckinloggins.cx.reader.Discriminator());
		env.setReader("error", new com.breckinloggins.cx.reader.Error());
		env.setReader("name", new com.breckinloggins.cx.reader.Name());
		env.setReader("reader", new com.breckinloggins.cx.reader.Reader());
		env.setReader("terminator", new com.breckinloggins.cx.reader.Terminator());
		env.setReader("whitespace", new com.breckinloggins.cx.reader.Whitespace());
		
		env.setCommand("nop", new com.breckinloggins.cx.command.Nop());
		env.setCommand("env", new com.breckinloggins.cx.command.Env());
		env.setCommand("pop", new com.breckinloggins.cx.command.Pop());
		env.setCommand("print", new com.breckinloggins.cx.command.Print());
		env.setCommand("execute", new com.breckinloggins.cx.command.Execute());
		env.setCommand("reader", new com.breckinloggins.cx.command.Reader());
		env.setCommand("error", new com.breckinloggins.cx.command.Error());
		
		// TODO:
		// - reading from input should be a request FROM the interpreter rather than an input INTO the interpreter
		// - show unrecognized input in red
		// - readers should push their next reader on the stack rather than return it
		// - add read command
		// - add the concept of a "value" to all entries
		// - add get, unget, mark, reset reader commands
		// - figure out which commands need to be built-in.  For example:
		//		* add, subtract, multiply, if, loop, etc.
		// - refactor setWriter() functionality
		// - add an if command
		// - what are evaluators and how do they work?  Do we need them?
		// - add set and unset commands, which create and destroy bindings in the current environment
		// - add accept and expect readers that take as an argument a reader to accept or expect
		// - add a dictionary to the environment that stores a map between a symbol and an IEntry
		// - make readers read character by character
		// - add #new reader reader
		// - add #new command reader
		// - replace pair, beingPair, endPair, error, name, and whitespace with dynamically defined readers 
		// - find a way to abstract the notion of types so we don't hard code any (including strings and ints) in the 
		//	 interpreter
	}
	
	/**
	 * Reads input from a string reader
	 * @param sr The string reader from which to read
	 */
	public void TEMP_read(StringReader sr)	{
		if (null == _reader)	{
			_reader = _rootEnvironment.getReader("discriminator");
		}
		
		try {
			do {
				_reader = _reader.read(sr, _rootEnvironment);
			} while (_reader != null && _reader.getClass().getName() != "Terminator");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(_writer);
		}
	}
}
