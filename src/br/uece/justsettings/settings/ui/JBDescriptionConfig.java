package br.uece.justsettings.settings.ui;

import org.jdom2.Element;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class JBDescriptionConfig extends JBConfig {
	
	public JBDescriptionConfig() {
		adicionarParametro("value", "DescriptionType", false, "DescriptionType.PRIMARY");
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		SingleMemberAnnotationExpr smae = new SingleMemberAnnotationExpr();
		smae.setName(this.getNome());
		smae.setMemberValue(new NameExpr(getParametros().get(0).getValor().toString()));
		return smae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.visual.JBDescription"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.visual.enums.DescriptionType"), false, false));
	}

	@Override
	public Element gerarXML() {
		Element jbDescription = new Element("jb-description");
		Element value = new Element("value");
		String valueString = getParametros().get(0).getValor().toString();
		value.addContent(valueString.substring(valueString.indexOf(".")+1));
		jbDescription.addContent(value);
		return jbDescription;
	}
	
}
