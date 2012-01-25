package org.sugarlang;

import org.sugarlang.base.IOp;
import org.sugarlang.type.BuiltinTypes;
import org.sugarlang.type.TypeException;

public class Interpreter implements Runnable {
	private Environment _rootEnvironment;
	private boolean _shouldStop;
	
	private Listener _listener;
		
	/**
	 * Constructs a new interpreter
	 */
	public Interpreter()	{
		_rootEnvironment = new Environment();
		_shouldStop = false;
		
		// Set up the basic starting environment
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
