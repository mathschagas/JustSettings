package br.uece.justsettings.settings.stream;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class StreamTemporalConfig extends JBConfig {
	
	public StreamTemporalConfig() {
		adicionarParametro("pattern", "String", false);
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		// TODO Auto-generated method stub
		
	}

}
