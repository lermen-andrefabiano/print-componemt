package br.com.print.util;

public enum DominioImpressorasHomologadas {

    BEMATECH("BEMATECH", "Bematech MP-4000 TH"), 
    
    DIEBOLD("DIEBOLD", "Diebold Procomp IM4X3T_A");

    private String codigo;

    private String descricao;

    DominioImpressorasHomologadas(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

}