package cs345.typescript;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class TestRefs extends TestTypeScript {
	public TestRefs(String filename, String input) {
		super(filename, input);
		resultFileSuffix = ".refs";
	}

	@Test
	public void testReferences() throws IOException {
		checkRefs(input);
	}

	public void checkRefs(String input) throws IOException {
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

		// REFS
		RefSymbols refPhase = new RefSymbols();
		ParseTreeWalker.DEFAULT.walk(refPhase, tree);

		assertEquals(expecting, refPhase.getRefOutput());
	}
}
