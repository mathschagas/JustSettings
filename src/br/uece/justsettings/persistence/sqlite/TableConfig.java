package br.uece.justsettings.persistence.sqlite;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class TableConfig extends JBConfig {
	
	public TableConfig() {
//		adicionarParametro("catalog", "String", false, "");
//		adicionarParametro("name", "String", false);
//		adicionarParametro("sche,a", "String", false, "");

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
