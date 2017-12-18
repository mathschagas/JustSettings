package br.uece.justsettings.settings.ui;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.Name;

import br.uece.justsettings.settings.JBConfig;

public class JBLargeConfig extends JBConfig {
	
	public JBLargeConfig() {
		
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		MarkerAnnotationExpr mae = new MarkerAnnotationExpr(getNome());
		return mae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.visual.JBLarge"), false, false));
	}

}
