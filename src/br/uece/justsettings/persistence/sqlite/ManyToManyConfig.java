package br.uece.justsettings.persistence.sqlite;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class ManyToManyConfig extends JBConfig {
	public ManyToManyConfig() {
		// TODO Auto-generated constructor stub
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
