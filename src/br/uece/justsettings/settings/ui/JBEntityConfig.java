package br.uece.justsettings.settings.ui;

import org.jdom2.Element;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;

import br.uece.justsettings.settings.JBConfig;
import br.uece.justsettings.settings.ParametroConfig;

public class JBEntityConfig  extends JBConfig {
	
	public JBEntityConfig() {
		adicionarParametro("label", "String", true);
		adicionarParametro("collectionLabel", "String", true);
		adicionarParametro("icon", "String", true, "ic_launcher");
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		NormalAnnotationExpr nae = new NormalAnnotationExpr();
		nae.setName(this.getNome());
		for (ParametroConfig param : this.getParametros()) {
			nae.addPair(param.getNome(), param.getValor().toString());
		}
		return nae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.domain.JBEntity"), false, false));
	}

	@Override
	public Element gerarXML() {
		Element jbEntity = new Element("jb-entity");
		jbEntity.setAttribute("label",getParametros().get(0).getValor().toString());
		jbEntity.setAttribute("collectionLabel",getParametros().get(1).getValor().toString());
		jbEntity.setAttribute("icon",getParametros().get(2).getValor().toString());
		return jbEntity;
	}

}
