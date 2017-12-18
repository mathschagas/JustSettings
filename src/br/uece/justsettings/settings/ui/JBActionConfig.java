package br.uece.justsettings.settings.ui;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import br.uece.justsettings.settings.JBConfig;

public class JBActionConfig extends JBConfig {
	
	public JBActionConfig() {
		adicionarParametro("name", "String", false);
		adicionarParametro("order", "Integer", false);
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		NormalAnnotationExpr nae = new NormalAnnotationExpr();
		nae.setName(this.getNome());
		// Name
		nae.addPair(getParametros().get(0).getNome(), "\""+getParametros().get(0).getValor().toString()+"\"");
		// Order
		nae.addPair(getParametros().get(1).getNome(), getParametros().get(1).getValor().toString());
		return nae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.annotation.domain.JBAction"), false, false));
	}


}
