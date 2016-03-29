package cs345.typescript;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class TestTypes extends TestTypeScript {
	public TestTypes(String filename, String input) {
		super(filename, input);
		resultFileSuffix = ".types";
	}

	@Test
	public void testTypeComputations() throws IOException {
		checkTypes(input);
	}

	public void checkTypes(String input) throws IOException {
		URL testFolder = TestTypeScript.class.getClassLoader().getResource(SAMPLES_DIR);
		String outputFilename = basename(filename)+resultFileSuffix;
		String expecting = readFile(new File(testFolder.getPath(),outputFilename).getPath());

		TypeScriptLexer lexer = new TypeScriptLexer(new ANTLRInputStream(input));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		TypeScriptParser parser = new TypeScriptParser(tokens);
		TypeScriptParser.ProgramContext tree = parser.program();

		// DEFS
		DefScopesAndSymbols defPhase = new DefScopesAndSymbols();
		ParseTreeWalker.DEFAULT.walk(defPhase, tree);

		// TYPES
		ComputeTypes typesPhase = new ComputeTypes();
		typesPhase.visit(tree);

		assertEquals(expecting, typesPhase.getRefOutput());
	}
}
