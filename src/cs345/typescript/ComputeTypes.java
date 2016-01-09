package cs345.typescript;

import org.antlr.symtab.ClassSymbol;
import org.antlr.symtab.FieldSymbol;
import org.antlr.symtab.FunctionSymbol;
import org.antlr.symtab.Scope;
import org.antlr.symtab.Symbol;
import org.antlr.symtab.Type;
import org.antlr.symtab.VariableSymbol;

import java.util.List;

public class ComputeTypes extends TypeScriptBaseVisitor<Type> {
	protected StringBuilder buf = new StringBuilder();
	protected Scope currentScope;

	// TODO

	// S U P P O R T
	public String getRefOutput() {
		return buf.toString();
	}
}
