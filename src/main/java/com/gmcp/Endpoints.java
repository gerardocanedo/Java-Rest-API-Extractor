package com.gmcp;

import java.util.ArrayList;
import java.util.List;

public class Endpoints{
    private String basePath;
    private List<Endpoint> endpoints; 
    
    public Endpoints(){
        basePath = "";
        endpoints = new ArrayList<Endpoint>();
    }

    public void print(){
        endpoints.forEach(n->System.out.println(n.getMethod()+"\t"+basePath+n.getPath()));
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<Endpoint> getEndpoints() {
        return this.endpoints;
    }

    @Override
    public String toString() {
        return "{" +
            " basePath='" + getBasePath() + "'" +
            ", endpoints='" + getEndpoints() + "'" +
            "}";
    }

}
