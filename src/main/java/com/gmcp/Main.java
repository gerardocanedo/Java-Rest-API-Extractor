package com.gmcp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Main {

    private static final String PATH= "/path-to-src/";
    public static void main(String[] args) {
        CompilationUnit cu;
        try {
             for ( Path path : Files.walk(Paths.get(PATH)).filter(Files::isRegularFile).collect(Collectors.toList())){
                cu = StaticJavaParser.parse(path.toAbsolutePath());
                Endpoints endpoint = new Endpoints();
    
                VoidVisitor<Endpoints> classNameCollector = new ClassAnnotationCollector();
                classNameCollector.visit(cu,endpoint);
    
                VoidVisitor<Endpoints> methodNameCollector=new MethodAnnotationCollector();
                methodNameCollector.visit(cu,endpoint);
    
                endpoint.print();
             };
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    private static class MethodAnnotationCollector extends VoidVisitorAdapter<Endpoints>{
        private static final String GET = "GET";
        private static final String DELETE = "DELETE";
        private static final String PUT = "PUT";
        private static final String POST = "POST";

        @Override
        public void visit(MethodDeclaration md, Endpoints collector) { 
            super.visit(md, collector);
            Endpoint ep = null;
            if (md.getAnnotationByName(POST).isPresent()){
                ep = new Endpoint(POST);
            } else if (md.getAnnotationByName(PUT).isPresent()){
                ep = new Endpoint(PUT);
            } else if (md.getAnnotationByName(DELETE).isPresent()){
                ep = new Endpoint(DELETE);
            } else if (md.getAnnotationByName(GET).isPresent()){
                ep = new Endpoint(GET);
            }

            if (ep != null){
                if (md.getAnnotationByName("Path").isPresent()){
                    SingleMemberAnnotationExpr annotation = md.getAnnotationByName("Path").get().asSingleMemberAnnotationExpr();
                    if (annotation.getMemberValue() instanceof StringLiteralExpr){
                        StringLiteralExpr literal = annotation.getMemberValue().asStringLiteralExpr();
                        ep.setPath(literal.getValue());
                    }
                    else {
                        System.out.println("NO ES UN LITERAL " + annotation.getName());
                    }
                }
                collector.getEndpoints().add(ep);
            }
        }
    }
    
    private static class ClassAnnotationCollector extends VoidVisitorAdapter<Endpoints>{

        @Override
        public void visit( ClassOrInterfaceDeclaration md, Endpoints collector){
            if (md.getAnnotationByName("Path").isPresent()){
                SingleMemberAnnotationExpr annotation = md.getAnnotationByName("Path").get().asSingleMemberAnnotationExpr();
                if (annotation.getMemberValue() instanceof StringLiteralExpr){
                    StringLiteralExpr literal = annotation.getMemberValue().asStringLiteralExpr();
                    collector.setBasePath(literal.getValue());
                }
                else {
                    System.out.println("CLASE NO ES UN LITERAL " + annotation.getName());
                }
            }
        }
    }

}
