package br.uece.justsettings.testes;

import java.util.ArrayList;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import br.uece.justsettings.settings.JBConfig;

public class InjetorConfiguracoes extends VoidVisitorAdapter<ArrayList<JBConfig>> {
	
	@Override
	public void visit(ClassOrInterfaceDeclaration cd, ArrayList<JBConfig> configs) {
		super.visit(cd, configs);
		for (JBConfig config : configs) {
			if (config.getAlvo().equals(cd.getNameAsString() + " (CLASS)")) {
				cd.addAnnotation(config.gerarAnnotation());
			}
		}
		
	}
	
	@Override
	public void visit(MethodDeclaration md, ArrayList<JBConfig> configs) {
		super.visit(md, configs);
		for (JBConfig config : configs) {
			if (config.getAlvo().equals(md.getNameAsString() + " (METHOD)")) {
				md.addAnnotation(config.gerarAnnotation());
				if (config.getNome().equals("JBAction")) {
					System.out.println("Achou um JBAction");
					for (JBConfig configPossivelDeSerParametro : configs) {
						if (configPossivelDeSerParametro.getNome().equals("JBParameter")) {
							System.out.println("Achou um JBParameter");
							System.out.print("JBParameter:\n\t");
							System.out.println("\""+configPossivelDeSerParametro.getAlvo() + " ("+md.getNameAsString()+")\"");
							for (Parameter param : md.getParameters()) {
								System.out.print("Param:\n\t");
								System.out.println(param.getNameAsString());
								String parametroComNomeDoMetodo = param.getNameAsString() + " ("+md.getNameAsString()+")";
								if (parametroComNomeDoMetodo.equals(configPossivelDeSerParametro.getAlvo())) {
									System.out.println("Achou o alvo do JBParameter");
									param.addAnnotation(configPossivelDeSerParametro.gerarAnnotation());
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void visit(FieldDeclaration fd, ArrayList<JBConfig> configs) {
		super.visit(fd, configs);
		for (JBConfig config : configs) {
			if (config.getAlvo().equals(fd.getVariable(0).getNameAsString() + " (FIELD)")) {
				fd.addAnnotation(config.gerarAnnotation());
			}
		}
	}
	
	@Override
	public void visit(EnumDeclaration ed, ArrayList<JBConfig> configs) {
		super.visit(ed, configs);
		for (JBConfig config : configs) {
			if (config.getAlvo().equals(ed.getNameAsString() + " (CLASS)")) {
				ed.addAnnotation(config.gerarAnnotation());
			}
		}
	}

}
