package com.gmcp;

public class Endpoint{
    private String path;
    private String method;
    
    public Endpoint(){
        path = "";
        method = "";
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "{" +
            " path='" + getPath() + "'" +
            ", method='" + getMethod() + "'" +
            "}";
    }

    public Endpoint(String method, String path) {
        this.path = path;
        this.method = method;
    }

    public Endpoint(String method) {
        this.path = "";
        this.method = method;
    }

}
