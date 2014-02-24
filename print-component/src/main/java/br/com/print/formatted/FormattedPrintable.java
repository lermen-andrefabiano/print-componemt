package br.com.print.formatted;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import br.com.print.bean.ImpressaoBean;
import br.com.print.util.DominioImpressorasHomologadas;

class FormattedPrintable implements Printable {

    private PageFormat pageFormat;

    private static final String FONT_STYLE = "Arial";

    private ImpressaoBean impressaoBean;

    private Graphics2D g2D;

    private float posicao;

    private int paperWidth = 198;

    private int textWidth = 0;

    private int fontPequenaHora = 9;

    private int fontPequena = 9;

    private int fontGrande = 18;

    /**
     * @author: maicon.silva
     * @since: 17/05/2010
     * 
     * @param ImpressaoBean
     */
    public void setImpressaoBean(ImpressaoBean impressaoBean) {
        this.impressaoBean = impressaoBean;
    }

    /**
     * @author: maicon.silva
     * @since: 17/05/2010
     * 
     * @param pageFormat
     */
    public FormattedPrintable(PageFormat pageFormat) {
        this.pageFormat = pageFormat;
    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }
        g2D = (Graphics2D) g;
        this.pageFormat = pageFormat;
        
        try {
        	buildOutputDataDefault();
        } catch (Exception e) {
            throw new PrinterException();        
        }
        
        return Printable.PAGE_EXISTS;
    }
    
    /**
     * @author: maicon.silva
     * @since: 17/05/2010
     * 
     * @author: fernando.wahl
     * @throws Exception 
     * @since: 21/07/2011
     */
    private void buildOutputDataDefault() throws Exception{       
        posicao = 10f;
        g2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // define qual o modelo da impressora
        this.configuracaoImpressora();
        // seta o nome do hospital
        this.setNmHospital();

        this.setDsFila();

        // seta a senha
        this.setSenha();
        // seta o tipo de chamada
        this.setDsSequenciaFila();
        
        FontMetrics fontMetricsBarCode = this.setDsDataSenha();        
        
        if(impressaoBean.getBarCode()!=null){
        	fontMetricsBarCode = buildOutputDataBarCode();
        }         

        // cria avanco de linhas conforme configurado
        if (impressaoBean.getNrLinhasAvancoPapel() != null && impressaoBean.getNrLinhasAvancoPapel() > 0) {
            this.setNrLinhasAvancoPapel(fontMetricsBarCode);
        }
    }
    
    private FontMetrics buildOutputDataBarCode() throws Exception{ 
    	
    	FontMetrics fontMetricsBarCode = null;

        if(impressaoBean.getDsEspecialidade()!=null){
            this.setDsEspecialidade();
        }
        
        if(impressaoBean.getDsTipoRisco()!=null){
            this.setDsPrioridade();
        }          
        
        if(impressaoBean.getBarCode()!=null){
        	fontMetricsBarCode = this.setBarCode();        	
        }      
        
        return fontMetricsBarCode;       
    }

    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     * 
     *         Metodo usado para identificar qual o padrão de impressora utilizada na impressão
     */
    private void configuracaoImpressora() {
        if (impressaoBean.getNmImpressoraHomologada().equals(DominioImpressorasHomologadas.BEMATECH.getDescricao())
                || impressaoBean.getNmImpressoraHomologada() == null) {
            g2D.scale(1, 1);
            //fontPequenaHora = 9;
        }

        if (impressaoBean.getNmImpressoraHomologada().equals(DominioImpressorasHomologadas.DIEBOLD.getDescricao())) {
            //g2D.scale(0.60, 0.60);
            g2D.scale(1, 0.70);
            //fontPequenaHora = 11;
        }
    }

    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     */
    private void setNmHospital() throws Exception {

        String[] textRows = null;
        Font fontHospital = new Font(FONT_STYLE, Font.BOLD, fontPequena);
        g2D.setFont(fontHospital);
        FontMetrics fontMetricsHospital = g2D.getFontMetrics();

        /*
         * Aqui está codificado apenas para considerar o caso de o nome do hospital precisar de 2 linhas... caso precise
         * de 3 ou mais ocorrerá uma Exception
         */
        textWidth = fontMetricsHospital.stringWidth(impressaoBean.getDsEmpresa());
        if (textWidth > paperWidth) {
            textRows = new String[(int) Math.ceil((double) textWidth / paperWidth)];
            int meio = (int) Math.floor((double) (impressaoBean.getDsEmpresa().length() / 2));
            for (int i = meio; i >= 0; i--) {
                if (new Character(impressaoBean.getDsEmpresa().charAt(i)).toString().equals("-")
                        || Character.isWhitespace(impressaoBean.getDsEmpresa().charAt(i))) {
                    textRows[0] = impressaoBean.getDsEmpresa().substring(0, i);
                    textRows[1] = impressaoBean.getDsEmpresa().substring(i + 1);
                    break;
                }
            }
        }

        if (textRows != null) {
            for (int i = 0; i < textRows.length; i++) {
                g2D.drawString(textRows[i], (float) (0.5 * (paperWidth - fontMetricsHospital.stringWidth(textRows[i]))), posicao);
                posicao += fontMetricsHospital.getHeight() + 10;
            }
        } else {
            g2D.drawString(impressaoBean.getDsEmpresa(),
                    (float) (0.5 * (paperWidth - fontMetricsHospital.stringWidth(impressaoBean.getDsEmpresa()))), posicao);
            posicao += fontMetricsHospital.getHeight() + 10;
        }

    }

    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     */
    private void setDsFila() throws Exception {
        Font fontDsFila = new Font(FONT_STYLE, Font.PLAIN, fontPequena);
        g2D.setFont(fontDsFila);
        FontMetrics fontMetricsDsFila = g2D.getFontMetrics();
        g2D.drawString(impressaoBean.getDsFilaSenha(),
                (float) (0.5 * (paperWidth - fontMetricsDsFila.stringWidth(impressaoBean.getDsFilaSenha()))), posicao);

        posicao += fontMetricsDsFila.getHeight() + 10;
    }

    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     */
    private void setSenha() throws Exception {
        Font fontSenha = new Font(FONT_STYLE, Font.BOLD, fontGrande);
        g2D.setFont(fontSenha);
        FontMetrics fontMetricsSenha = g2D.getFontMetrics();
        String dsSenha = impressaoBean.getDsSenha();
        g2D.drawString(dsSenha, (float) (0.5 * (paperWidth - fontMetricsSenha.stringWidth(dsSenha))), posicao);

        posicao += fontMetricsSenha.getHeight();
    }

    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     */
    private void setDsSequenciaFila() throws Exception {

        Font fontTpChamada = new Font(FONT_STYLE, Font.PLAIN, fontPequena);
        g2D.setFont(fontTpChamada);
        FontMetrics fontMetricsTpChamada = g2D.getFontMetrics();
        g2D.drawString(impressaoBean.getTpChamada(),
                (float) (0.5 * (paperWidth - fontMetricsTpChamada.stringWidth(impressaoBean.getTpChamada()))), posicao);

        posicao += fontMetricsTpChamada.getHeight() + 10;
    }

    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     */
    private FontMetrics setDsDataSenha() throws Exception {    	
        Font fontDsDataSenha = new Font(FONT_STYLE, Font.PLAIN, fontPequenaHora);
        g2D.setFont(fontDsDataSenha);
        
        FontMetrics fontMetricsDsDataSenha = g2D.getFontMetrics();
        g2D.drawString(impressaoBean.getDsDataSenha(),
                (float) (0.5 * (paperWidth - fontMetricsDsDataSenha.stringWidth(impressaoBean.getDsDataSenha()))), posicao);

        if(impressaoBean.getBarCode()!=null){
        	posicao += fontMetricsDsDataSenha.getHeight() + 10;
        }        
        
        return fontMetricsDsDataSenha;
    }
    
    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     */
    private void setDsEspecialidade() throws Exception {
        Font fontDsEspecialidade = new Font(FONT_STYLE, Font.PLAIN, fontPequena);
        g2D.setFont(fontDsEspecialidade);
        FontMetrics fontMetricsDsEspecialidade = g2D.getFontMetrics();
        g2D.drawString(impressaoBean.getDsEspecialidade(),
                (float) (0.5 * (paperWidth - fontMetricsDsEspecialidade.stringWidth(impressaoBean.getDsEspecialidade()))), posicao);

        posicao += fontMetricsDsEspecialidade.getHeight() + 10;
    }

    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     */
    private void setDsPrioridade() throws Exception {
        Font fontDsPrioridade = new Font(FONT_STYLE, Font.PLAIN, fontPequena);
        g2D.setFont(fontDsPrioridade);
        FontMetrics fontMetricsDsPrioridade = g2D.getFontMetrics();
        g2D.drawString(impressaoBean.getDsTipoRisco(),
                (float) (0.5 * (paperWidth - fontMetricsDsPrioridade.stringWidth(impressaoBean.getDsTipoRisco()))), posicao);

        posicao += fontMetricsDsPrioridade.getHeight() + 10;
    }

    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     */
    private FontMetrics setBarCode() throws Exception{    
        FontMetrics fontMetricsBarCode = g2D.getFontMetrics();       
        
        BufferedImage image = impressaoBean.getBarCode();
        
        int x = (int) ((paperWidth - image.getWidth(null)) / 2);   
        
        g2D.drawImage(image, x, (int) posicao, null);     
        
        return fontMetricsBarCode;
    }    

    /**
     * @author: fernando.wahl
     * @since: 21/07/2011
     */
    private void setNrLinhasAvancoPapel(FontMetrics fontNrLinhasAvancoPapel) throws Exception {
        for (int i = 1; i <= impressaoBean.getNrLinhasAvancoPapel(); i++) {
            posicao += fontNrLinhasAvancoPapel.getHeight() + 10;
        }

        Font fontTeste = new Font(FONT_STYLE, Font.PLAIN, fontPequena);
        g2D.setFont(fontTeste);
        FontMetrics fontMetricsTeste = g2D.getFontMetrics();
        g2D.drawString("--------------", (float) (0.5 * (paperWidth - fontMetricsTeste.stringWidth("--------------"))), posicao);
    }
}