package br.uece.justsettings.settings.stream;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class StreamTemporalConfig extends JBConfig {
	
	public StreamTemporalConfig() {
		adicionarParametro("pattern", "String", true, "");
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		NormalAnnotationExpr nae = new NormalAnnotationExpr();
		nae.setName("StreamTemporal");
		nae.addPair("pattern", "\""+getParametros().get(0).getValor().toString()+"\"");
		return nae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.stream.annotation.StreamTemporal"), false, false));
	}

}
