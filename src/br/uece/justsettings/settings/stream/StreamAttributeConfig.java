package br.uece.justsettings.settings.stream;

import org.jdom2.Element;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class StreamAttributeConfig extends JBConfig {

	public StreamAttributeConfig() {
		adicionarParametro("name", "String", true, "");
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		NormalAnnotationExpr nae = new NormalAnnotationExpr();
		nae.setName("StreamAnnotation");
		nae.addPair("name", "\""+getParametros().get(0).getValor().toString()+"\"");
		return nae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.stream.annotation.StreamAttribute"), false, false));
	}

	@Override
	public Element gerarXML() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
