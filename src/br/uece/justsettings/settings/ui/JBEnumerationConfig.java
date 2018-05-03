package br.uece.justsettings.settings.ui;

import org.jdom2.Element;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.Name;

import br.uece.justsettings.settings.JBConfig;

public class JBEnumerationConfig extends JBConfig {
	
	public JBEnumerationConfig() {
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		MarkerAnnotationExpr mae = new MarkerAnnotationExpr(this.getNome());
		return mae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.domain.JBEnumeration"), false, false));
	}

	@Override
	public Element gerarXML() {
		Element jbEnumeration = new Element("jb-enumeration");
		return jbEnumeration;
	}

}
