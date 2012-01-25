/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IOp;
import org.sugarlang.base.IType;
import org.sugarlang.type.TOp;
import org.sugarlang.type.TypeException;

/**
 * @author bloggins
 *
 */
public abstract class BaseOp implements IOp {

	private boolean _isSealed = false;
	private IType _type;
	
	public BaseOp() throws TypeException	{
		setType(new TOp());
		
		seal();
	}
	
	@Override
	public IType getType() {
		return _type;
	}
	
	protected void setType(IType type) throws TypeException	{
		throwIfSealed();
		_type = type;
	}

	@Override
	public void seal() throws TypeException {
		throwIfSealed();
		_isSealed = true;
	}

	@Override
	public boolean isSealed() {
		return _isSealed;
	}

	/*
	 * @see com.breckinloggins.cx.IEntry#getDescription()
	 */
	@Override
	public String getDescription() {
		return "(opcode)";
	}

	/*
	 * @see com.breckinloggins.cx.command.IOp#execute(com.breckinloggins.cx.Environment)
	 */
	@Override
	final public void execute(Environment env) {
		try	{
			executeInternal(env);
		} catch (Exception e)	{
			e.printStackTrace(System.err);
			env.pushError(e.getMessage());
		}
	}
	
	/**
	 * Override this method to perform the actual execution of the op.  Exceptions will be 
	 * caught and pushed as an error on the stack
	 * @param env The environment in which to execute
	 * @throws Exception Any type of exception from the execution
	 */
	protected abstract void executeInternal(Environment env) throws Exception;
	
	/**
	 * Throws a TypeException if the object is sealed
	 * @throws TypeException Thrown if object is sealed
	 */
	protected void throwIfSealed() throws TypeException	{
		if (_isSealed)	{
			throw new TypeException("Invalid operation; object is sealed");
		}
	}
}
