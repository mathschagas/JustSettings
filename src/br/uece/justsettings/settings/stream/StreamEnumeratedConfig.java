package br.uece.justsettings.settings.stream;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;

import br.uece.justsettings.settings.JBConfig;

public class StreamEnumeratedConfig extends JBConfig {
	
	public StreamEnumeratedConfig() {
		adicionarParametro("value", "EnumType", false, "EnumType.STRING");
	}

	@Override
	public AnnotationExpr gerarAnnotation() {
		NormalAnnotationExpr nae = new NormalAnnotationExpr();
		nae.setName("StreamEnumerated");
		nae.addPair("value", getParametros().get(0).getValor().toString());
		return nae;
	}

	@Override
	public void gerarImports(CompilationUnit cUnit) {
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.stream.annotation.StreamEnumerated"), false, false));
		cUnit.addImport(new ImportDeclaration(new Name("org.jb.stream.annotation.enums.EnumType"), false, false));
	}

}
