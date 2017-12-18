package br.uece.justsettings.settings.stream;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class StreamEnumeratedConfig extends JBConfig {
	
	public StreamEnumeratedConfig() {
		adicionarParametro("value", "EnumType", false, "EnumType.STRING");
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
