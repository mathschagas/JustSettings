package br.uece.justsettings.settings.ui;

import org.jdom2.Element;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class JBParameterConfig extends JBConfig {
	
	public JBParameterConfig() {
		adicionarParametro("name", "String", false);
		adicionarParametro("order", "Integer", false);
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		NormalAnnotationExpr nae = new NormalAnnotationExpr();
		nae.setName(getNome());
		// Name
		nae.addPair(getParametros().get(0).getNome(), "\""+getParametros().get(0).getValor().toString()+"\"");
		// Order
		nae.addPair(getParametros().get(1).getNome(), getParametros().get(1).getValor().toString());

		return nae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.domain.JBParameter"), false, false));
	}

	@Override
	public Element gerarXML() {
		Element jbParameter = new Element("jb-parameter");
		Element name = new Element("name");
		name.addContent(getParametros().get(0).getValor().toString());
		Element order = new Element("order");
		order.addContent(getParametros().get(1).getValor().toString());
		jbParameter.addContent(name);
		jbParameter.addContent(order);
		return jbParameter;
	}

}
