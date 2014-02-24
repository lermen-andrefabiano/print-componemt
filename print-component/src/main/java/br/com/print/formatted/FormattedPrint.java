package br.com.print.formatted;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Locale;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.PrinterName;

import br.com.print.applet.AbstractPrint;
import br.com.print.bean.ImpressaoBean;

public class FormattedPrint extends AbstractPrint{

    private PrinterJob printJob = PrinterJob.getPrinterJob();

    private PageFormat pageFormat = printJob.defaultPage();

    private HashPrintRequestAttributeSet as;

    @Override
    public boolean configure(String printerName) throws PrinterException {
        try {
            PageFormat pageFormat = printJob.defaultPage();
            Paper paper = pageFormat.getPaper();
            paper.setImageableArea(0, 0, 0, 0);
            pageFormat.setPaper(paper);

            as = new HashPrintRequestAttributeSet();
            as.add(new MediaPrintableArea(0, 0, 500, 500, MediaPrintableArea.MM));

            PrintService printService = null;
            PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(new PrinterName(printerName, Locale.getDefault()));
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, printServiceAttributeSet);

            if (services.length > 0) {
                printService = services[0];
            } else {
                return false;
            }

            printJob.setPrintService(printService);
            return true;
        } catch (PrinterException e) {
            throw e;
        }
    }

    @Override
    public void sendToPrinter(ImpressaoBean impressaoBean) throws Exception  {
        FormattedPrintable formattedPrintable = new FormattedPrintable(pageFormat);
        formattedPrintable.setImpressaoBean(impressaoBean);        
        
        printJob.setPrintable(formattedPrintable);

        try {
            printJob.print(as);
        } catch (Exception e) {
            formattedPrintable = null;
            throw e;
        }
        
        formattedPrintable = null;
    }
}
