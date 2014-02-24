package br.com.print.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

public class Message {

	private String errorMessage;
	
	private Exception exception;	

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return this.exception;
	}

	/**
	 * @author: maicon.silva
	 * @since: 17/05/2010
	 * 
	 * @param errorMessage
	 * @param exception
	 */
	public void showError(String errorMessage, Exception exception) {
		setErrorMessage(errorMessage);
		if (getException() != null) {
			setException(exception);
			StringWriter swriter = new StringWriter();
			PrintWriter pwriter = new PrintWriter(swriter);
			getException().printStackTrace(pwriter);
			JOptionPane.showMessageDialog(null, getErrorMessage() + " : "
					+ swriter.toString());
		} else {
			JOptionPane.showMessageDialog(null, getErrorMessage());
		}
	}

	/**
	 * @author: maicon.silva
	 * @since: 18/05/2010
	 * 
	 * @param errorMessage
	 */
	public void showError(String errorMessage) {
		showError(getErrorMessage(), getException());
	}

	/**
	 * @author: maicon.silva
	 * @since: 18/05/2010
	 * 
	 * @param exception
	 */
	public void showError(Exception exception) {
		showError(getErrorMessage(), getException());
	}
}