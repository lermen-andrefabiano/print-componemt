package br.com.print.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import br.com.print.bean.ImpressaoBean;
import br.com.print.formatted.FormattedPrint;
import br.com.print.normal.NormalPrint;
import br.com.print.util.DominioTipoImpressao;

import com.lowagie.text.pdf.Barcode128;

public class PrintApplet extends Applet {

    private static final long serialVersionUID = -222354588L;
    
    public void init() {
        super.init();
        System.out.println("#############");
        System.out.println("## APPLET SACR ##");
        System.out.println("#############");
    }

	/**
	 * @author: andre.lermen
	 * @since: 17/01/2014
	 * 
	 * @return the list of printer names installed on client machine
	 */   
	public String getClientPrinters() {
		System.out.println("getClientPrinters");

		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				String retorno = "";
				// privileged code goes here, for example:
				PrintService[] services = PrintServiceLookup
						.lookupPrintServices(null, null);
				for (PrintService service : services) {
					retorno += service.getName() + "@P@";
				}

				return retorno;
			}
		});

	}
    
    public void print(String cdAtendimentoTriagem, String dsDataSenha, String dsEmpresa, String dsEspecialidade, String dsFilaSenha, String dsSenha, 
    		String dsTipoRisco, String nmImpressora, String nmImpressoraHomologada, String nrLinhasAvancoPapel, String tpChamada, String tpImpressao) throws Exception {

    	System.out.println("IMPRIMINDO SENHA...");
    	
    	BufferedImage bufferedImage = gerarBarCode(cdAtendimentoTriagem);
    	
    	ImpressaoBean impressaoBean = new ImpressaoBean(bufferedImage, dsDataSenha, dsEmpresa, dsEspecialidade, dsFilaSenha, dsSenha, 
    			dsTipoRisco, nmImpressora, nmImpressoraHomologada, new Long(nrLinhasAvancoPapel), tpChamada, DominioTipoImpressao.valueOf(tpImpressao));
    	 
        AbstractPrint abstractPrint = null;

        try { 
        	
        	if (impressaoBean.getTpImpressao().equals(DominioTipoImpressao.IMPRESSAO_FORMATADA)) {
        		abstractPrint = new FormattedPrint();           
            }else if(impressaoBean.getTpImpressao().equals(DominioTipoImpressao.IMPRESSAO_NORMAL)){
            	abstractPrint = new NormalPrint();       
            }
        	
            if (abstractPrint.configure(impressaoBean.getNmImpressora())) {
            	abstractPrint.sendToPrinter(impressaoBean);
            }

        } catch (Exception e) {
        	e.printStackTrace();
        	abstractPrint = null;
            throw e;
        }

        abstractPrint = null;
    }
    
    private BufferedImage gerarBarCode(String cdTriagemAtendimento){
    	
    	if(cdTriagemAtendimento==null) 
    		return null;
    	
        Barcode128 code39ext = new Barcode128();
        code39ext.setCode(cdTriagemAtendimento);
        code39ext.setStartStopText(false);
        code39ext.setExtended(true);
        
        Image rawImage = code39ext.createAwtImage(Color.BLACK, Color.WHITE);
        
        BufferedImage outImage = new BufferedImage(rawImage.getWidth(null), rawImage.getHeight(null), BufferedImage.TYPE_INT_RGB);        
        outImage.getGraphics().drawImage(rawImage, 0, 0, null);
        return outImage;
    }    

}