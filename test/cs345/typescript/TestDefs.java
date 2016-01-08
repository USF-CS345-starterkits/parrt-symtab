package cs345.typescript;

import org.antlr.symtab.Scope;
import org.antlr.symtab.Utils;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class TestDefs extends TestTypeScript {
	public TestDefs(String filename, String input) {
		super(filename, input);
		resultFileSuffix = ".defs";
	}

	@Test
	public void testDefinitions() throws IOException {
		checkScopes(input);
	}

	public void checkScopes(String input) throws IOException {
		URL testFolder = TestTypeScript.class.getClassLoader().getResource(SAMPLES_DIR);
		String outputFilename = basename(filename)+resultFileSuffix;

		String expecting = readFile(new File(testFolder.getPath(),outputFilename).getPath());

		TypeScriptLexer lexer = new TypeScriptLexer(new ANTLRInputStream(input));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		TypeScriptParser parser = new TypeScriptParser(tokens);
		TypeScriptParser.ProgramContext tree = parser.program();
		DefScopesAndSymbols defPhase = new DefScopesAndSymbols();
		ParseTreeWalker.DEFAULT.walk(defPhase, tree);
		Scope globalScope = defPhase.getGlobalScope();
		String result = Utils.toString(globalScope);
		assertEquals(expecting, result);
	}
}
