Sugar is a stack-based language with an emphasis on reading and evaluating source text.  In other words, the purpose is to 
"transform" it into any language you choose.  The language itself should remind you somewhat of FORTH.

Sugar comes with a Java-based "IDE" that shows how the language itself can choose the syntax highlighting, completion assist, and 
other features as it is being written.

Fundamentally, Sugar is a functional language.  That is, some operations have side effects (like IO) and can mutate the state of the underlying stack machine, but type values themselves cannot be mutated.  Sugar does have the notion of environment bindings, 
and these bindings can be changed (in other words, the scope is itself mutable), but the underlying values themselves can't be changed.  Users can, of course, implement mutable types on top of the language if they so choose.
