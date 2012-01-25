package org.sugarlang;

import org.sugarlang.base.IOp;
import org.sugarlang.type.BuiltinTypes;
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
		// I think the best way to do this is to start with single characters only.  There's an order that everything
		// has to be initialized in.  If you don't need certain features, you should be able to "not say", so we'll need
		// a "NULL" symbol.  For future compatibility with algebraic data types, it's pretty clear that this should be '_'
		// by default.
		//
		// If we do it this way, that's fine, but we need a way to document what order the characters need to be typed in.
		//
		// Lastly, when we have a way to chain environments, we need a way to "hide" symbols from the parent environment
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
			
			BuiltinTypes.initialize();
			
			env.setBinding("Type", BuiltinTypes.Type);
			env.setBinding("Char", BuiltinTypes.Char);
			env.setBinding("Macro", BuiltinTypes.Macro);
			env.setBinding("Null", BuiltinTypes.Null);
			env.setBinding("Op", BuiltinTypes.Op);
			env.setBinding("Quote", BuiltinTypes.Quote);
			env.setBinding("Reader", BuiltinTypes.Reader);
			env.setBinding("String", BuiltinTypes.String);
			env.setBinding("Symbol", BuiltinTypes.Symbol);
			env.setBinding("Whitespace", BuiltinTypes.Whitespace);
			
			env.setBinding("bootstrap", new org.sugarlang.reader.Bootstrap());
			env.setBinding("ignore", new org.sugarlang.reader.Ignore());
			env.setBinding("command", new org.sugarlang.reader.Command());
			env.setBinding("symbol", new org.sugarlang.reader.Symbol());
			env.setBinding("discriminator", new org.sugarlang.reader.Discriminator());
			env.setBinding("quoted", new org.sugarlang.reader.Quoted());
			env.setBinding("name", new org.sugarlang.reader.Name());
			env.setBinding("terminator", new org.sugarlang.reader.Terminator());
			env.setBinding("whitespace", new org.sugarlang.reader.Whitespace());
			
			env.setBinding("nop", new org.sugarlang.op.Nop());
			env.setBinding("null", new org.sugarlang.op.Null());
			env.setBinding("mark", new org.sugarlang.op.Mark());
			env.setBinding("stack", new org.sugarlang.op.Stack());
			env.setBinding("env", new org.sugarlang.op.Env());
			env.setBinding("pop", new org.sugarlang.op.Pop());
			env.setBinding("popmark", new org.sugarlang.op.Popmark());
			env.setBinding("quote", new org.sugarlang.op.Quote());
			env.setBinding("unquote", new org.sugarlang.op.Unquote());
			env.setBinding("createmacro", new org.sugarlang.op.CreateMacro());
			env.setBinding("if", new org.sugarlang.op.If());
			env.setBinding("is", new org.sugarlang.op.Is());
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
		// - User defined types based on Product and Sum Types 
		// - Add inheritance concept and add to the is op (edit: nope, is should do structural checking)
		// - I'm pretty sure we can build cons cells from type constructors
		// - Built-in list type
		//		- Create TList (can only contain homogeneous members)
		//		- Lazy
		//		- Can create from Stack: TMark, <el>, <el>, ..., !createlist
		//		- Ops: head, last, tail, init, null, length
		// - List reader (use '()' for syntax for now)
		// - Create an abstraction that IOp and TMacro both inherit from? (ICallable?)
		// - Remainder of Haskell built-in types: Int, Double, Bool, Char
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
		//		- way to hide symbols from the parent environments
		// - Java FFI... should be able to get rid of the "Print" op and probably
				// 	 a few others once we have that
				//		- http://www.javapractices.com/topic/TopicAction.do?Id=113
				//		- http://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string
				//		- Implement JavaType type so we can talk about Java Class Types
				//		- Add type op to push a type on the stack given its name (useful elsewhere)
				//		- Constructing an instance of a JavaType should cause the underlying VM to create the corresponding
				//			class instance (in other words, a JavaType is a UDT, but one the VM knows about)
				//		- Add invoke op (generic FFI)
		// - add evaluate command.  If the thing on the top of the stack is a command, it is 
		//   evaluated, else the stack is undisturbed other than popping off the command
		//   (NOTE: with a simple IsCommand test command, this doesn't have to be a hard-coded command) 
		// - figure out which commands need to be built-in ops.  For example:
		//		* subtract, multiply, if, loop, etc.
		// - find a way to abstract the notion of types so we don't hard code any (including strings and ints) in the 
		//	 interpreter.  Probably want to study up on how F# and Haskell does type definitions.
		// - Construct type system like Haskell (http://www.haskell.org/onlinereport/decls.html)
		// - since we're going for nearly everything to be redefinable, perhaps we should look more into dependent types:
		//	 http://www.cs.st-andrews.ac.uk/~eb/writings/idris-tutorial.pdf
		// - add a coerce command to replace the top value on the stack with the same value under the given new type
		// - move as many readers as possible from hard-coded to user-defined
		// - show unrecognized input in red
		// - "Talk back" to the editor
		//		- Configurable via command line parameter
		//		- Uses System.err as the communications channel
		//		- Writes JSON or something else simple
		//		- Allows for errors, warnings, diagnostic information, editor metadata (like syntax highlighting), 
		//			editor control (e.g. auto parens), autocomplete
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
