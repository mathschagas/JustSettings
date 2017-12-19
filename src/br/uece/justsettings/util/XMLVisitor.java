package br.uece.justsettings.util;

import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Element;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class XMLVisitor extends VoidVisitorAdapter<Element> {

	@Override
	public void visit(ClassOrInterfaceDeclaration cid, Element element) {
		Element entity = new Element("entity");
		// TODO: Tem que pegar o nome completo...
		entity.setAttribute("class", cid.getNameAsString());
		element.addContent(entity);
		super.visit(cid, element);
	}
	
	@Override
	public void visit(EnumDeclaration ed, Element element) {
		Element enumeration = new Element("enumeration");
		// TODO: Tem que pegar o nome completo...
		enumeration.setAttribute("enum", ed.getNameAsString());
		element.addContent(enumeration);
		super.visit(ed, element);
	}

	@Override
	public void visit(FieldDeclaration fd, Element element) {
		Element attribute = new Element("attribute");
		Attribute name = new Attribute("name",fd.getVariable(0).getNameAsString());
		attribute.setAttribute(name);
		if(fd.getVariable(0).getType().toString().contains("<")) {
			String nomeTipo = fd.getVariable(0).getType().toString();
			Attribute type = new Attribute("type", nomeTipo.substring(0, nomeTipo.indexOf("<")));
			Attribute enclosingType = new Attribute("enclosing-type", nomeTipo.substring(nomeTipo.indexOf("<")+1, nomeTipo.indexOf(">")));
			attribute.setAttribute(type);
			attribute.setAttribute(enclosingType);
		} else {
			String nomeTipo = fd.getVariable(0).getType().toString();
			Attribute type = new Attribute("type", nomeTipo);
			Attribute enclosingType = new Attribute("enclosing-type", nomeTipo);
			attribute.setAttribute(type);
			attribute.setAttribute(enclosingType);
		}
		
		List<Element> classesXML = element.getChildren();
		Element ultimoFilho = classesXML.get(classesXML.size()-1);
		ultimoFilho.addContent(attribute);
		super.visit(fd, element);
	} 
	
	@Override
	public void visit(MethodDeclaration md, Element element) {
		Element method = new Element("method");
		Attribute name = new Attribute("name",md.getNameAsString());
		method.setAttribute(name);
		if(md.getType().toString().contains("<")) {
			String nomeTipo = md.getType().toString();
			Attribute type = new Attribute("type", nomeTipo.substring(0, nomeTipo.indexOf("<")));
			Attribute enclosingType = new Attribute("enclosing-type", nomeTipo.substring(nomeTipo.indexOf("<")+1, nomeTipo.indexOf(">")));
			method.setAttribute(type);
			method.setAttribute(enclosingType);
		} else {
			String nomeTipo = md.getType().toString();
			Attribute type = new Attribute("type", nomeTipo);
			Attribute enclosingType = new Attribute("enclosing-type", nomeTipo);
			method.setAttribute(type);
			method.setAttribute(enclosingType);
		}
		
		List<Element> classesXML = element.getChildren();
		Element ultimoFilho = classesXML.get(classesXML.size()-1);
		ultimoFilho.addContent(method);
		super.visit(md, element);
	}
	
}
