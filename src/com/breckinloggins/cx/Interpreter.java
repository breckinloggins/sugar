package com.breckinloggins.cx;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import com.breckinloggins.cx.command.Execute;
import com.breckinloggins.cx.command.Nop;
import com.breckinloggins.cx.command.Print;
import com.breckinloggins.cx.reader.BeginPair;
import com.breckinloggins.cx.reader.Command;
import com.breckinloggins.cx.reader.Discriminator;
import com.breckinloggins.cx.reader.EndPair;
import com.breckinloggins.cx.reader.Error;
import com.breckinloggins.cx.reader.IReader;
import com.breckinloggins.cx.reader.List;
import com.breckinloggins.cx.reader.Name;
import com.breckinloggins.cx.reader.Pair;
import com.breckinloggins.cx.reader.Reader;
import com.breckinloggins.cx.reader.Terminator;
import com.breckinloggins.cx.reader.Whitespace;

public class Interpreter {
	private Environment _rootEnvironment;
	private IReader _reader;
	private PrintStream _writer;

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
		env.setReaderAlias("command", Command.class.getName());
		env.setReaderAlias("pair", Pair.class.getName());
		env.setReaderAlias("beginPair", BeginPair.class.getName());
		env.setReaderAlias("endPair", EndPair.class.getName());
		env.setReaderAlias("discriminator", Discriminator.class.getName());
		env.setReaderAlias("error", Error.class.getName());
		env.setReaderAlias("list", List.class.getName());	// TODO: Change to an environment query command
		env.setReaderAlias("name", Name.class.getName());
		env.setReaderAlias("reader", Reader.class.getName());
		env.setReaderAlias("terminator", Terminator.class.getName());
		env.setReaderAlias("whitespace", Whitespace.class.getName());
		
		env.setCommandAlias("nop", Nop.class.getName());
		env.setCommandAlias("print", Print.class.getName());
		env.setCommandAlias("execute", Execute.class.getName());
		
		// TODO:
		// - add a symbol reader that accepts anything that isn't whitespace
		// - figure out which commands need to be built-in.  For example:
		//		* add, subtract, multiply, if, loop, etc.
		// - figure out if the stack needs arg types or if we can keep them all as strings.  I'd prefer to 
		// - have types encoded in commands.  For example, add_int vs add_float vs add_string, etc.
		// - add an if command
		// - make readers and commands stateless (we use the environment for state)
		// - what are evaluators and how do they work?  Do we need them?
		// - add error command
		// - add get, unget, mark, reset reader commands
		// - add set and unset commands, which create and destroy bindings in the current environment
		// - add accept and expect readers that take as an argument a reader to accept or expect
		// - make readers read character by character
		// - add #new reader reader
		// - add #new command reader
		// - add a super-interface for commands and readers (an IThingy... need to come up with a name)
		//   this way we can join readers, commands, and other object types into a generic system that we can 
		//   create by name with #new
		// - replace pair, beingPair, endPair, error, name, and whitespace with dynamically defined readers 
	}
	
	/**
	 * Reads input from a string reader
	 * @param sr The string reader from which to read
	 */
	public void TEMP_read(StringReader sr)	{
		if (null == _reader)	{
			_reader = _rootEnvironment.createReader("discriminator");
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
