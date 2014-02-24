package br.com.print.applet;

import java.awt.print.PrinterException;

import br.com.print.bean.ImpressaoBean;

public abstract class AbstractPrint {

	public abstract boolean configure(String printerName)
			throws PrinterException;

	public abstract void sendToPrinter(ImpressaoBean impressaoBean)
			throws Exception;
}
