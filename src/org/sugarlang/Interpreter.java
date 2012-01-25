package org.sugarlang;

import org.sugarlang.base.IOp;
import org.sugarlang.type.TypeException;

public class Interpreter implements Runnable {
	private Environment _rootEnvironment;
	private boolean _shouldStop;
	
	private Listener _listener;
	
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
		_shouldStop = false;
		
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
	
		try {
			env.setBinding("bootstrap", new org.sugarlang.reader.Bootstrap());
			env.setBinding("command", new org.sugarlang.reader.Command());
			env.setBinding("symbol", new org.sugarlang.reader.Symbol());
			env.setBinding("discriminator", new org.sugarlang.reader.Discriminator());
			env.setBinding("quoted", new org.sugarlang.reader.Quoted());
			env.setBinding("name", new org.sugarlang.reader.Name());
			env.setBinding("terminator", new org.sugarlang.reader.Terminator());
			env.setBinding("whitespace", new org.sugarlang.reader.Whitespace());
			
			env.setBinding("nop", new org.sugarlang.op.Nop());
			env.setBinding("mark", new org.sugarlang.op.Mark());
			env.setBinding("stack", new org.sugarlang.op.Stack());
			env.setBinding("env", new org.sugarlang.op.Env());
			env.setBinding("pop", new org.sugarlang.op.Pop());
			env.setBinding("popmark", new org.sugarlang.op.Popmark());
			env.setBinding("quote", new org.sugarlang.op.Quote());
			env.setBinding("unquote", new org.sugarlang.op.Unquote());
			env.setBinding("createmacro", new org.sugarlang.op.CreateMacro());
			env.setBinding("if", new org.sugarlang.op.If());
			env.setBinding("set", new org.sugarlang.op.Set());
			env.setBinding("unset", new org.sugarlang.op.Unset());
			env.setBinding("get", new org.sugarlang.op.Get());
			env.setBinding("add", new org.sugarlang.op.Add());
			env.setBinding("getchar", new org.sugarlang.op.Getchar());
			env.setBinding("read", new org.sugarlang.op.Read());
			env.setBinding("print", new org.sugarlang.op.Print());
			env.setBinding("execute", new org.sugarlang.op.Execute());
			env.setBinding("reader", new org.sugarlang.op.Reader());
			env.setBinding("error", new org.sugarlang.op.Error());
		} catch (TypeException e) {
			e.printStackTrace(System.err);
			return;
		}
		
		// TODO:
		// - Built-in types should be singleton or there should be a static "built-in types" class
		// - Abstract TTypeConstructor's finalizable semantics to an interface and ensure that all
		// objects on the stack and binding environment are of type IFinalizable.  The interpreter should
		// check whether an object is finalized before being allowed to be put on the stack or set in the
		// environment.  This will ensure that we can use a convenient API on Java classes but that they
		// will be immutable after being finalized.
		// - Built-in list type
		//		- Create TList (can only contain homogeneous members)
		//		- Lazy
		//		- Can create from Stack: TMark, <el>, <el>, ..., !createlist
		//		- Ops: head, last, tail, init, null, length
		// - List reader (use '()' for syntax for now)
		// - Create an abstraction that IOp and TMacro both inherit from? (ICallable?)
		// - Remainder of Haskell built-in types: Int, Double, Bool, Char
		// - Add "is" type test op
		// - Create type system (http://cs.wallawalla.edu/research/KU/PR/Haskell.html)
		//		- User-defined types as an instance of TUserType
		//		- Simple Type algebra for how the type is defined
		//		- Op to construct types from arguments
		//		- Op to coerce one type to another
		//		- Op to extract values
		//		- Do we want to do named type (record-like) components or pure structural types?
		//		  I'm leaning toward structural types.  So we'll probably need to define lists and 
		//		  tuples as built-in types.  Haskell-like
		// - replace !createlist, !createmacro, etc. with more generic syntax
		// - start working towards defining a basic LISP syntax
		//		- hard-coded "list" reader
		// - add accept and expect readers that take as an argument a type to accept or expect
		// - add integer reader
		// - add ops to push and pop chained environments
		// - add evaluate command.  If the thing on the top of the stack is a command, it is 
		//   evaluated, else the stack is undisturbed other than popping off the command
		//   (NOTE: with a simple IsCommand test command, this doesn't have to be a hard-coded command) 
		// - figure out which commands need to be built-in ops.  For example:
		//		* subtract, multiply, if, loop, etc.
		// - what are evaluators and how do they work?  Do we need them?
		// - replace error, name, and whitespace with dynamically defined readers 
		// - find a way to abstract the notion of types so we don't hard code any (including strings and ints) in the 
		//	 interpreter.  Probably want to study up on how F# and Haskell does type definitions.
		// - since we're going for nearly everything to be redefinable, perhaps we should look more into dependent types:
		//	 http://www.cs.st-andrews.ac.uk/~eb/writings/idris-tutorial.pdf
		// - add a coerce command to replace the top value on the stack with the same value under the given new type
		// - move as many readers as possible from hard-coded to user-defined
		// - show unrecognized input in red
	}
	
	/**
	 * @return the listener
	 */
	public Listener getListener() {
		return _listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(Listener listener) {
		this._listener = listener;
	}

	/**
	 * Signals to the thread that it should stop running
	 */
	public void stop()	{
		_shouldStop = true;
	}
	
	@Override
	public void run() {
		boolean error = false;
		_shouldStop = false;
		
		System.err.println("Sugar interpreter started");
		
		if (null != _listener)	{
			_listener.interpreterStarted(this);
		}
		
		try {
			Environment e = _rootEnvironment;
			e.pushReader("bootstrap");
			e.pushOp("read");
			e.evaluateStack();
			
			do {
				
				e.pushReader("discriminator");
				e.pushOp("read");
			
				while(!_rootEnvironment.isStackEmpty()) {
					if (_shouldStop)	{
						throw new Exception("The interpreter has been stopped");
					}
					
					IOp op = _rootEnvironment.evaluateStack();
					if (null == op)	{
						break;
					}
				
					// TODO: Error should be a TYPE, not an op.  There should be a "throw"
					// op that does this itself
					if (op instanceof org.sugarlang.op.Error)	{
						System.err.println("SUGAR STACK:");
						_rootEnvironment.printStack(System.err);	
						error = true;
						throw new Exception("A Sugar Error was thrown");
					}
				}
			} while (true);
		} catch (Exception e)	{
			System.err.println("The interpreter has terminated");
			
			if (error)	{
				e.printStackTrace(System.err);
			}
		}
		
	}
	
	/**
	 * The listener for this interpreter
	 * @author bloggins
	 */
	public interface Listener	{
		/**
		 * Called when the interpreter has started
		 * @param e The interpreter
		 */
		void interpreterStarted(Interpreter e);
	}
}
