package br.uece.justsettings.settings.ui;

import org.jdom2.Element;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class JBTemporalConfig extends JBConfig {
	
	public JBTemporalConfig() {
		adicionarParametro("value", "TemporalType", false ,"TemporalType.DATE");
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
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.visual.JBTemporal"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.visual.enums.TemporalType"), false, false));
	}

	@Override
	public Element gerarXML() {
		Element jbTemporal = new Element("jb-temporal");
		Element value = new Element("value");
		String valueText = getParametros().get(0).getValor().toString();
		value.addContent(valueText.substring(valueText.indexOf(".")+1));
		jbTemporal.addContent(value);
		return jbTemporal;
	}

}
