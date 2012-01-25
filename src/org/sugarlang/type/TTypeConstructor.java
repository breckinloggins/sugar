/**
 * 
 */
package org.sugarlang.type;

import java.util.ArrayList;
import org.sugarlang.base.IType;
import org.sugarlang.value.VSymbol;

/**
 * Represents an immutable algebraic data type, consisting of a union of one or more types.
 * Note that type constructors themselves do not have types and are considered equal
 * if they are structurally identical.  To give a type constructor a name, it must be bound
 * to a symbol in the environment
 * @author bloggins
 */
public class TTypeConstructor extends BaseType {
	
	private ArrayList<VSymbol> _typeVariables = new ArrayList<VSymbol>();
	//private ArrayList<IType> _types = new ArrayList<IType>();
	
	/**
	 * Creates a new Type Constructor
	 */
	public TTypeConstructor()	{
		try {
			seal();
		} catch (TypeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a type variable with the given symbol
	 * @param sym The symbol to add
	 * @throws TypeException Thrown if type has been finalized
	 */
	public void addTypeVariable(VSymbol sym) throws TypeException	{
		throwIfSealed();
		_typeVariables.add(sym);
	}
	
	/**
	 * Adds a type variable with the given symbol
	 * @param s The string value of the symbol to add
	 * @throws TypeException Thrown if type has been finalized
	 */
	public void addTypeVariable(String s) throws TypeException {
		addTypeVariable(new VSymbol(s));
	}
	
	/**
	 * Returns whether the given type matches this type
	 * @param t The type to check
	 * @return true if match, false if not
	 * @throws TypeException Thrown if type has not been finalized
	 */
	public boolean matchType(IType t) throws TypeException	{
		return false;
	}
}
