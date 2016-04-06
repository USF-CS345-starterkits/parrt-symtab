package cs345.typescript;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@RunWith(Parameterized.class)
public abstract class TestTypeScript {
	public static final String SAMPLES_DIR = "samples";

	protected String resultFileSuffix;

	protected String filename;
	protected String input;

	public TestTypeScript(String filename, String input) {
		this.filename = filename;
		this.input = input;
	}

	@Parameterized.Parameters(name="{0}:{1}")
	public static Collection<Object[]> findInputFiles() throws IOException {
		return findTestCasesInFolder(SAMPLES_DIR);
	}

	protected static Collection<Object[]> findTestCasesInFolder(String folder) throws IOException {
		URL testFolder = TestTypeScript.class.getClassLoader().getResource(folder);
		if ( testFolder==null ) {
			System.err.println("Can't find sample .ts file directory.");
			return Collections.emptyList();
		}
		Collection<Object[]> result = new ArrayList<>();
		// only feed test methods with .ts source files.
		File dir = new File(testFolder.getPath());
		File[] files = dir.listFiles();
		if ( files!=null ) {
			for (File f : files) {
				if ( f.getName().endsWith(".ts") ) {
					Object[] args = new Object[2];
					args[0] = f.getName();
					args[1] = readFile(f.getPath());
					result.add(args);
				}
			}
		}
		return result;
	}

	public static String readFile(String path) throws IOException {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded);
		}
		catch (NoSuchFileException nsfe) {
			return "";
		}
	}

	/** e.g., replaceFileSuffix("foo.ts", ".output") */
	public static String replaceFileSuffix(String s, String suffix) {
		if ( s==null || suffix==null ) return s;
		int dot = s.lastIndexOf('.');
		return s.substring(0,dot)+suffix;
	}

	public static String basename(String s) {
		int dot = s.lastIndexOf('.');
		return s.substring(0,dot);
	}
}
