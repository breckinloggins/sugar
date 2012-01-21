/**
 * 
 */
package org.sugarlang.type;

import java.util.ArrayList;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseType;
import org.sugarlang.dictionary.IType;

/**
 * Represents an immutable algebraic data type, consisting of a union of one or more types.
 * Note that type constructors themselves do not have types and are considered equal
 * if they are structurally identical.  To give a type constructor a name, it must be bound
 * to a symbol in the environment
 * @author bloggins
 */
public class TTypeConstructor extends BaseType {
	
	private boolean _isFinalized = false;
	
	private ArrayList<TSymbol> _typeVariables = new ArrayList<TSymbol>();
	//private ArrayList<IType> _types = new ArrayList<IType>();
	
	
	public TTypeConstructor()	{
	}
	
	/**
	 * Gets whether the type has been finalized
	 * @return
	 */
	public boolean isFinalized()	{
		return _isFinalized;
	}
	
	/**
	 * Marks the Type Constructor as final, that is, changes to the type constructor are no longer allowed
	 * @throws TypeException thrown if the type has already been finalized
	 */
	public void finalize() throws TypeException {
		throwIfFinalized();
		_isFinalized = true;
	}
	
	/**
	 * Adds a type variable with the given symbol
	 * @param sym The symbol to add
	 * @throws TypeException Thrown if type has been finalized
	 */
	public void addTypeVariable(TSymbol sym) throws TypeException	{
		throwIfFinalized();
		_typeVariables.add(sym);
	}
	
	/**
	 * Adds a type variable with the given symbol
	 * @param s The string value of the symbol to add
	 * @throws TypeException Thrown if type has been finalized
	 */
	public void addTypeVariable(String s) throws TypeException {
		addTypeVariable(new TSymbol(s));
	}
	
	/**
	 * Returns whether the given type matches this type
	 * @param t The type to check
	 * @return true if match, false if not
	 * @throws TypeException Thrown if type has not been finalized
	 */
	public boolean matchType(IType t) throws TypeException	{
		throwIfNotFinalized();
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.sugarlang.dictionary.IType#Evaluate(org.sugarlang.Environment)
	 */
	@Override
	public void Evaluate(Environment env) {
		// nope
	}

	/**
	 * Throws a TypeException if the object is not finalized
	 * @throws TypeException
	 */
	private void throwIfNotFinalized() throws TypeException	{
		if (!isFinalized())	{
			throw new TypeException("Invalid operation, type is not finalized");
		}
	}
	
	/**
	 * Throws a TypeException if the object is finalized
	 * @throws TypeException
	 */
	private void throwIfFinalized() throws TypeException {
		if (isFinalized())	{
			throw new TypeException("Invalid operation, type is finalized");
		}
	}
}
