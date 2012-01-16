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
		env.setReader("list", new com.breckinloggins.cx.reader.List());	// TODO: Change to an environment query command
		env.setReader("name", new com.breckinloggins.cx.reader.Name());
		env.setReader("reader", new com.breckinloggins.cx.reader.Reader());
		env.setReader("terminator", new com.breckinloggins.cx.reader.Terminator());
		env.setReader("whitespace", new com.breckinloggins.cx.reader.Whitespace());
		
		env.setCommand("nop", new com.breckinloggins.cx.command.Nop());
		env.setCommand("print", new com.breckinloggins.cx.command.Print());
		env.setCommand("execute", new com.breckinloggins.cx.command.Execute());
		env.setCommand("error", new com.breckinloggins.cx.command.Error());
		
		// TODO:
		// - add a dictionary to the environment that stores a map between a symbol and an IEntry
		// - figure out which commands need to be built-in.  For example:
		//		* add, subtract, multiply, if, loop, etc.
		// - add reader command (pops first arg off stack and uses it as name of reader)
		// - refactor setWriter() functionality
		// - figure out if the stack needs arg types or if we can keep them all as strings.  I'd prefer to 
		// - have types encoded in commands.  For example, add_int vs add_float vs add_string, etc.
		// - add an if command
		// - what are evaluators and how do they work?  Do we need them?
		// - add get, unget, mark, reset reader commands
		// - add set and unset commands, which create and destroy bindings in the current environment
		// - add accept and expect readers that take as an argument a reader to accept or expect
		// - make readers read character by character
		// - add #new reader reader
		// - add #new command reader
		// - replace pair, beingPair, endPair, error, name, and whitespace with dynamically defined readers 
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
			//_reader = new Discriminator();
		}
		
		// TODO: Readers need to read a character at a time, returning the same reader if it 
		// needs to accept more input.
		//_reader = _reader.read(, env);
	}
}
