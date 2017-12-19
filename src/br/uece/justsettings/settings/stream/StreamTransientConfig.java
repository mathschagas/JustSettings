package br.uece.justsettings.settings.stream;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.Name;

import br.uece.justsettings.settings.JBConfig;

public class StreamTransientConfig extends JBConfig {
	
	public StreamTransientConfig() {
		
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		MarkerAnnotationExpr mae = new MarkerAnnotationExpr();
		mae.setName("StreamTransient");
		return mae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.stream.annotation.StreamTransient"), false, false));
	}

}
