package br.com.print.bean;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import br.com.print.util.DominioTipoImpressao;

public class ImpressaoBean implements Serializable {

    /**
     * This field is used to .....
     */
    private static final long serialVersionUID = 1L;

    private DominioTipoImpressao tpImpressao;

    private String nmImpressora;

    private String nmImpressoraHomologada;

    private String dsEmpresa;

    private String dsFilaSenha;

    private String dsSenha;

    private String dsDataSenha;

    private String tpChamada;

    private String dsEspecialidade;

    private String dsTipoRisco;

    private Long nrLinhasAvancoPapel;

    private BufferedImage barCode;
    
    public ImpressaoBean(){    	
    }
    
    public ImpressaoBean(BufferedImage barCode, String dsDataSenha, String dsEmpresa, String dsEspecialidade, String dsFilaSenha, String dsSenha, 
    		String dsTipoRisco, String nmImpressora, String nmImpressoraHomologada, Long nrLinhasAvancoPapel, String tpChamada, DominioTipoImpressao tpImpressao){
    	this.barCode = barCode;
    	this.dsDataSenha = dsDataSenha;
    	this.dsEmpresa = dsEmpresa;
    	this.dsEspecialidade = dsEspecialidade;
    	this.dsFilaSenha = dsFilaSenha;
    	this.dsSenha = dsSenha;
    	this.dsTipoRisco = dsTipoRisco;
    	this.nmImpressora = nmImpressora;
    	this.nmImpressoraHomologada = nmImpressoraHomologada;
    	this.nrLinhasAvancoPapel = nrLinhasAvancoPapel;
    	this.tpChamada = tpChamada;
    	this.tpImpressao = tpImpressao;
    }

    public DominioTipoImpressao getTpImpressao() {
        return tpImpressao;
    }

    public void setTpImpressao(DominioTipoImpressao tpImpressao) {
        this.tpImpressao = tpImpressao;
    }

    public String getNmImpressora() {
        return nmImpressora;
    }

    public void setNmImpressora(String nmImpressora) {
        this.nmImpressora = nmImpressora;
    }

    public String getNmImpressoraHomologada() {
        return nmImpressoraHomologada;
    }

    public void setNmImpressoraHomologada(String nmImpressoraHomologada) {
        this.nmImpressoraHomologada = nmImpressoraHomologada;
    }

    public String getDsEmpresa() {
        return dsEmpresa;
    }

    public void setDsEmpresa(String dsEmpresa) {
        this.dsEmpresa = dsEmpresa;
    }

    public String getDsFilaSenha() {
        return dsFilaSenha;
    }

    public void setDsFilaSenha(String dsFilaSenha) {
        this.dsFilaSenha = dsFilaSenha;
    }

    public String getDsSenha() {
        return dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public String getDsDataSenha() {
        return dsDataSenha;
    }

    public void setDsDataSenha(String dsDataSenha) {
        this.dsDataSenha = dsDataSenha;
    }

    public String getTpChamada() {
        return tpChamada;
    }

    public void setTpChamada(String tpChamada) {
        this.tpChamada = tpChamada;
    }

    public String getDsEspecialidade() {
        return dsEspecialidade;
    }

    public void setDsEspecialidade(String dsEspecialidade) {
        this.dsEspecialidade = dsEspecialidade;
    }

    public String getDsTipoRisco() {
        return dsTipoRisco;
    }

    public void setDsTipoRisco(String dsTipoRisco) {
        this.dsTipoRisco = dsTipoRisco;
    }

    public Long getNrLinhasAvancoPapel() {
        return nrLinhasAvancoPapel;
    }

    public void setNrLinhasAvancoPapel(Long nrLinhasAvancoPapel) {
        this.nrLinhasAvancoPapel = nrLinhasAvancoPapel;
    }

    public BufferedImage getBarCode() {
        return barCode;
    }

    public void setBarCode(BufferedImage bufferedImage) {
        this.barCode = bufferedImage;
    }

}