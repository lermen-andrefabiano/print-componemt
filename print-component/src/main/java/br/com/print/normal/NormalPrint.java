package br.com.print.normal;

import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;

import br.com.print.applet.AbstractPrint;
import br.com.print.bean.ImpressaoBean;
import br.com.print.util.Message;
import br.com.print.util.Utilidades;

public class NormalPrint extends AbstractPrint{
    
    private String stringToPrint = "";
    
    private PrintService printService = null;
    
    private String mensagemErro = "";
    
    PrintRequestAttributeSet printRequestAttributeSet;    

    @Override
    public boolean configure(String printerName){
        try{
            mensagemErro = "PrintPS: Erro ao setar propriedades para o processo de impressão.";
            printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
            printRequestAttributeSet.add(new JobName("Impressao", null));            

            mensagemErro = "PrintPS: Erro ao buscar impressoras disponiveis.";
            // buscando as impressoras disponíveis (lookupPrintServices)
            for (PrintService p : PrinterJob.lookupPrintServices()) {
                // comparando a impressora encontrada com a solicitada
                if (p.getName().equalsIgnoreCase(printerName)) {
                    printService = p;
                    break;
                }
            }
            
            if (printService==null){
                mensagemErro = "A impressora " + printerName +  " não está disponível.";
                new Message().showError(mensagemErro);
                return false;
            }
            
            return true;
        }catch(Exception e){
            new Message().showError(mensagemErro,e);
            return false;
        }        
    }
   
    @Override
    public void sendToPrinter(ImpressaoBean impressaoBean) {
        
        buildOutputData(impressaoBean);
        
        mensagemErro = "PrintPS: Erro ao criar processos de impressão e adicionar trabalhos no pool.";
        // criando o processo de impressão, jogando na fila de impressao
        DocPrintJob docPrint = this.printService.createPrintJob();
        InputStream stream = new ByteArrayInputStream(stringToPrint.getBytes());

        try {
            stream.close();
        } catch (IOException e) {
            mensagemErro = "PrintPS: Erro a fechar 'stream' de impressao";
            new Message().showError(mensagemErro,e);
        }

        // tipo de arquivo a ser impresso
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc doc = new SimpleDoc(stream, flavor, null);
        // solicitando a impressão

        try {
            docPrint.print(doc, this.printRequestAttributeSet);
        } catch (PrintException e) {
            mensagemErro = "PrintPS: Erro ao enviar documento para impressora";
            new Message().showError(mensagemErro,e);
        }
    }
    
    /**
     * @author: maicon.silva 
     * @since: 17/05/2010
     *
     * @param impressaoSenhaBean
     */
    private void buildOutputData(ImpressaoBean impressaoBean) {
        try {
            
            final String LIGA_ENFATIZADO = "\u001B" + "E";
            final String DESLIGA_ENFATIZADO = "\u001B" + "F";
            
            // string a ser impressa
            stringToPrint = "\r\n";

            // nome Empresa
            stringToPrint += Utilidades.removerAcentos(impressaoBean.getDsEmpresa()) + "\r\n\r\n\r\n";

            if (impressaoBean.getDsFilaSenha() != null) {
                stringToPrint += Utilidades.removerAcentos(impressaoBean.getDsFilaSenha()) + "\r\n\r\n";
            }
            stringToPrint += LIGA_ENFATIZADO + Utilidades.removerAcentos(impressaoBean.getDsSenha()) + "\r\n\r\n" + DESLIGA_ENFATIZADO;
            stringToPrint += "CHAMADA: " + Utilidades.removerAcentos(impressaoBean.getTpChamada());
            stringToPrint += "\r\n\r\n\r\n";

            mensagemErro = "PrintPS: Erro ao montar comandos de impressão.";
            // data e hora
            stringToPrint += "    " + impressaoBean.getDsDataSenha();
            stringToPrint += "\r\n\r\n\r\n\r\n\0";
            // tag <ESC>m para acionar a guilhotina
            stringToPrint += "\u001B" + "m";
        } catch (Exception e) {
            new Message().showError(mensagemErro,e);
        }
    }
}