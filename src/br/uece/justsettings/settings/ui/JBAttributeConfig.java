package br.uece.justsettings.settings.ui;


import org.jdom2.Element;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class JBAttributeConfig extends JBConfig {
	
	public JBAttributeConfig() {
		adicionarParametro("name", "String", false);
		adicionarParametro("order", "Integer", false);
		adicionarParametro("id", "Boolean", false, "FALSE");
		adicionarParametro("views", "KindView", false, "{}");
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		NormalAnnotationExpr nae = new NormalAnnotationExpr();
		nae.setName(this.getNome());
		// Name
		nae.addPair(getParametros().get(0).getNome(), "\""+getParametros().get(0).getValor().toString()+"\"");
		// Order
		nae.addPair(getParametros().get(1).getNome(), getParametros().get(1).getValor().toString());
		// id
		if (getParametros().get(2).getValor().toString().equals("TRUE")) {
			nae.addPair(getParametros().get(2).getNome(), "true");
		}
		// views
		String views = getParametros().get(3).getValor().toString().replace("[", "{").replace("]", "}");
		nae.addPair(getParametros().get(3).getNome(), views);
		return nae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.domain.JBAttribute"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.domain.enums.KindView"), false, false));
	}

	@Override
	public Element gerarXML() {
		Element jbAttribute = new Element("jb-attribute");
		Element name = new Element("name");
		name.addContent(getParametros().get(0).getValor().toString());
		jbAttribute.addContent(name);
		Element order = new Element("order");
		order.addContent(getParametros().get(1).getValor().toString());
		jbAttribute.addContent(order);
		Element id = new Element("id");
		if (getParametros().get(2).getValor().toString().equals("TRUE")) {
			id.addContent("true");
		} else {
			id.addContent("false");
		}
		jbAttribute.addContent(id);
		Element views = new Element("views");
		String viewsText = getParametros().get(3).getValor().toString().replace("[", "").replace("]", "").replace(" ", "");
		String[] viewsSplit = viewsText.split(",");
		for (int i = 0; i < viewsSplit.length; i++) {
			Element kindView = new Element("kind-view");
			kindView.addContent(viewsSplit[i].substring(viewsSplit[i].indexOf(".")+1));
			views.addContent(kindView);
		}
		jbAttribute.addContent(views);
		
		return jbAttribute;
	}
}
