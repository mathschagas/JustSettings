package br.uece.justsettings.testes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

public class TesteAnnotation {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\math_\\Documents\\workspace\\JustBusiness\\AndroidStudio\\JBEmptyProject\\app\\src\\main\\java\\org\\jb\\model");
		try {
			CompilationUnit cUnit = JavaParser.parse(new FileInputStream(file.getAbsolutePath() + "\\Cliente.java"));
			VoidVisitor<?> atributosEMetodosVisitor = new TesteVisitor();
			atributosEMetodosVisitor.visit(cUnit, null);
			System.out.println(cUnit.toString(new PrettyPrinterConfiguration()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
	}

}
