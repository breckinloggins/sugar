package org.sugarlang;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VSymbol;

public class Dictionary {
	public Dictionary _parent;
	public HashMap<VSymbol, IValue> _dictionary;
	
	public Dictionary(Dictionary parent)	{
		_parent = parent;
		_dictionary = new HashMap<VSymbol, IValue>();
	}
	
	/**
	 * Binds the value to the given symbol
	 * @param sym The symbol to which to bind
	 * @param val The value to bind
	 * @throws TypeException Thrown if val or sym are null or unsealed
	 */
	public void set(VSymbol sym, IValue val) throws TypeException	{
		if (null == val)	{
			throw new TypeException("Cannot bind a symbol to a null value (did you mean TNull?)");
		}
		
		if (!val.isSealed())	{
			throw new TypeException("Cannot bind a symbol to an unsealed value");
		}
		
		if (null == sym)	{
			throw new TypeException("Cannot bind the value '" + val.toString() + "' to a null symbol");
		}
		
		if (!sym.isSealed())	{
			throw new TypeException("Cannot bind the value '" + val.toString() + "' to the unsealed symbol '" + sym.toString() + "'");
		}
		
		_dictionary.put(sym, val);
	}
		
	/**
	 * Unsets the given symbol in this environment
	 * @param sym The symbol to unset
	 * @throws TypeException Thrown if the symbol is null, unsealed, or isn't bound in this environment
	 */
	public void unset(VSymbol sym) throws TypeException	{
		if (null == sym)	{
			throw new TypeException("Cannot unset a null symbol");
		}
		
		if (!sym.isSealed())	{
			throw new TypeException("Cannot unset the unsealed symbol '" + sym.toString() + "'");
		}
		
		if (!_dictionary.containsKey(sym))	{
			throw new TypeException("The symbol '" + sym.toString() + "' cannot be unset because it is not bound in this environment");
		}
		
		_dictionary.remove(sym);
	}
	
	
	/**
	 * Hides a symbol such that it appears to be unbound in this dictionary even if a parent dictionary has
	 * the symbol bound
	 * @param sym The symbol to hide
	 * @throws TypeException Thrown if the symbol is null or unsealed
	 */
	public void hide(VSymbol sym) throws TypeException	{
		if (null == sym)	{
			throw new TypeException("Cannot hide a null symbol");
		}
		
		if (!sym.isSealed())	{
			throw new TypeException("Cannot hide the unsealed symbol '" + sym.toString() + "'");
		}
		
		_dictionary.put(sym, null);
	}
	
	/**
	 * Looks up the value in this dictionary, and in this dictionary's parent chain
	 * @param sym The symbol to lookup
	 * @return null if not present or hidden, the value if the binding exists
	 * @throws TypeException Thrown if sym is null or unsealed
	 */
	public IValue lookup(VSymbol sym) throws TypeException	{
		if (null == sym)	{
			throw new TypeException("Cannot lookup a null symbol");
		}
		
		if (!sym.isSealed())	{
			throw new TypeException("Cannot lookup the unsealed symbol '" + sym.toString() + "'");
		}
		
		// We do an explicit "contains" check to let null represent a hidden binding
		if (_dictionary.containsKey(sym))	{
			return _dictionary.get(sym);
		}
		
		if (null != _parent)	{
			return _parent.lookup(sym);
		}
		
		return null;
	}
	
	/**
	 * Gets all the bindings in this dictionary, including the parent dictionary
	 * @return
	 */
	public Set<VSymbol> getBindingSymbols()	{
		HashSet<VSymbol> symbols = new HashSet<VSymbol>();
		
		symbols.addAll(_dictionary.keySet());
		if (null != _parent)	{
			symbols.addAll(_parent.getBindingSymbols());
		}
		
		return symbols;
	}
}
