package org.sugarlang;

import org.sugarlang.dictionary.ICommand;

public class Interpreter implements Runnable {
	private Environment _rootEnvironment;
	
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
		env.setReader("command", new org.sugarlang.reader.Command());
		env.setReader("symbol", new org.sugarlang.reader.Symbol());
		env.setReader("discriminator", new org.sugarlang.reader.Discriminator());
		env.setReader("error", new org.sugarlang.reader.Error());
		env.setReader("name", new org.sugarlang.reader.Name());
		env.setReader("reader", new org.sugarlang.reader.Reader());
		env.setReader("terminator", new org.sugarlang.reader.Terminator());
		env.setReader("whitespace", new org.sugarlang.reader.Whitespace());
		
		env.setCommand("nop", new org.sugarlang.command.Nop());
		env.setCommand("stack", new org.sugarlang.command.Stack());
		env.setCommand("env", new org.sugarlang.command.Env());
		env.setCommand("pop", new org.sugarlang.command.Pop());
		env.setCommand("isCommand", new org.sugarlang.command.IsCommand());
		env.setCommand("if", new org.sugarlang.command.If());
		env.setCommand("set", new org.sugarlang.command.Set());
		env.setCommand("unset", new org.sugarlang.command.Unset());
		env.setCommand("get", new org.sugarlang.command.Get());
		env.setCommand("add", new org.sugarlang.command.Add());
		env.setCommand("getchar", new org.sugarlang.command.Getchar());
		env.setCommand("read", new org.sugarlang.command.Read());
		env.setCommand("print", new org.sugarlang.command.Print());
		env.setCommand("execute", new org.sugarlang.command.Execute());
		env.setCommand("reader", new org.sugarlang.command.Reader());
		env.setCommand("error", new org.sugarlang.command.Error());
		
		// TODO:
		// - discriminator should check symbol binding for characters to switch on instead of having them 
		// hard-coded
		// - add bootstrap reader to set up initial discriminator symbols (at least reader and command symbols)
		// - add integer reader
		// - add stack marker so that we can see where we start to defined commands
		// - change if command to use stack markers instead of counts
		// - add user-defined commands
		// - add #new reader reader
		// - add #new command reader
		// - replace current _readers and _commands with the single dictionary
		// - replace isCommand with simple is test command.  IsCommand doesn't have to be hard-coded
		// - add evaluate command.  If the thing on the top of the stack is a command, it is 
		//   evaluated, else the stack is undisturbed other than popping off the command
		//   (NOTE: with a simple IsCommand test command, this doesn't have to be a hard-coded command) 
		// - figure out which commands need to be built-in.  For example:
		//		* subtract, multiply, if, loop, etc.
		// - what are evaluators and how do they work?  Do we need them?
		// - add accept and expect readers that take as an argument a reader to accept or expect
		// - replace error, name, and whitespace with dynamically defined readers 
		// - find a way to abstract the notion of types so we don't hard code any (including strings and ints) in the 
		//	 interpreter.  Probably want to study up on how F# and Haskell does type definitions.
		// - show unrecognized input in red
	}
	
	@Override
	public void run() {
		try {
			do {
			
				// Temporary code for testing
				Environment e = _rootEnvironment;
				
				e.pushReader("discriminator");
				e.pushCommand("read");
			
				while(!_rootEnvironment.isStackEmpty()) {
					ICommand cmd = _rootEnvironment.evaluateStack();
					if (null == cmd)	{
						break;
					}
				
					if (cmd instanceof org.sugarlang.command.Error)	{
						System.err.println("SUGAR STACK:");
						_rootEnvironment.printStack(System.err);	
						throw new Exception("A Sugar Error was thrown");
					}
				}
			} while (true);
		} catch (Exception e)	{
			System.err.println("The interpreter has terminated");
			e.printStackTrace(System.err);
		}
		
	}
}