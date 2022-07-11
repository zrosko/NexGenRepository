package com.rbc.nexgen.batch.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;

import org.springframework.http.HttpStatus;

/**
 * The infrastructure common exception class. The concept of handling the
 * exceptions is to catch the exception in the current layer (service, data
 * access, controller) and then rethrow current layer exception with the lower
 * layer exception embeded in the current one by using addCausedException.
 * 
 * @version 1.0
 * @date May 25, 2022
 * @author NexGen
 * @update May 27, 2022. Changed to inherit from RuntimeException instead from
 *         Exception.
 */
public class RBCNexGenException extends RuntimeException implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	private String _stackTrace = "";
	private String _errorCode = "";
	private String _errorDescription = null;
	private String _resourceBundle = "com.rbc.nexgen.template.exception.RBCNexGenDefaultResourceBundle";
	private int _severity = 1;
	// A list of all other exceptions that cause this exception.
	private Vector<Throwable> _causeExceptions;
	public Exception _originalException;

	public RBCNexGenException() {
		super("RBCNexGenException has occured.");
	}

	public RBCNexGenException(Exception e) {
		super(e.toString());
		_originalException = e;
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		_stackTrace = sw.toString();
	}

	public RBCNexGenException(String errorCode) {
		this(null, errorCode, null, 0);
	}

	public RBCNexGenException(HttpStatus status, String errorCode) {
		this(status, errorCode, null, 0);
	}

	public RBCNexGenException(HttpStatus status, String errorCode, String resourceBundle, int severity) {
		super(errorCode);
		this.status = status;
		setResourceBundle(resourceBundle);
		setErrorCode(errorCode);
		setSeverity(severity);
	}

	public void addCauseException(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		_stackTrace = sw.toString();
		if (_causeExceptions == null)
			_causeExceptions = new Vector<Throwable>();

		if (e != null) {
			// String exceptionInfo = e.getClass().getName() + ": " + e.toString();
			_causeExceptions.addElement(e);
		}
	}

	public Enumeration<Throwable> getCauseExceptions() {

		Vector<Throwable> tempVec = _causeExceptions;
		if (tempVec == null)
			tempVec = new Vector<Throwable>();

		return tempVec.elements();
	}

	public HttpStatus getStatus() {
		return this.status;
	}

	public String getCauseExceptionsAsString() {
		String causeExceptionString = " ";

		if (_causeExceptions == null)
			causeExceptionString = " ";
		else {
			if (_causeExceptions.isEmpty() == true)
				causeExceptionString = " ";
			else {
				Enumeration<Throwable> list = _causeExceptions.elements();
				causeExceptionString = " ";
				while (list.hasMoreElements()) {
					causeExceptionString = causeExceptionString + "\n " + "" + (String) list.nextElement().toString();
				}
				causeExceptionString = "\n " + causeExceptionString;
			}
		}
		return causeExceptionString;
	}

	public String getDetailDescription() {
		String causeExceptionString = "\n " + _errorCode + " " + _errorDescription;

		if (_causeExceptions != null) {
			if (_causeExceptions.isEmpty() == false) {
				Enumeration<Throwable> list = _causeExceptions.elements();

				while (list.hasMoreElements()) {
					Object e = list.nextElement();
					if (e instanceof RBCNexGenException) {
						RBCNexGenException rbce = (RBCNexGenException) e;
						causeExceptionString = causeExceptionString + rbce.getDetailDescription();
					}
				}
			}
		}
		return causeExceptionString;
	}

	public String getErrorCode() {
		return _errorCode.trim();
	}

	public String getErrorDescription() {
		if (_errorDescription != null) {
			_errorDescription = _errorDescription.replaceAll("_", " ");
			_errorDescription = _errorDescription.toLowerCase();
		}
		return _errorDescription;
	}

	public String getResourceBundle() {
		if (_resourceBundle == null)
			return "com.rbc.nexgen.template.exception.RBCNexGenDefaultResourceBundle";
		return _resourceBundle;
	}

	public int getSeverity() {
		return _severity;
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public synchronized void printStackTrace(java.io.PrintStream s) {
		synchronized (s) {
			s.print(getClass().getName() + ": ");
			s.print(_stackTrace);
			for (int i = 0; i < _causeExceptions.size(); i++) {
				_causeExceptions.elementAt(i).printStackTrace();
			}
		}
	}

	public void printStackTrace(java.io.PrintWriter s) {
		synchronized (s) {
			s.print(getClass().getName() + ": ");
			s.print(_stackTrace);
		}
	}

	public void rethrow() throws Exception {
		throw _originalException;
	}

	public void setCauseExceptions(Vector<Throwable> value) {

		_causeExceptions = value;

	}

	public void setErrorCode(String value) {
		if (value != null)
			_errorCode = value;
		try {
			ResourceBundle messages = ResourceBundle.getBundle(getResourceBundle());
			setErrorDescription(messages.getString(_errorCode));
		} catch (Exception e) {
			_errorDescription = value;
		}
	}

	public void setErrorDescription(String value) {
		if (value != null)
			_errorDescription = value;
	}

	public void setResourceBundle(String value) {
		if (value != null)
			_resourceBundle = value;

	}

	public void setSeverity(int value) {
		_severity = value;
	}

	public String toString() {

		return (super.toString() 
			+ "\n Message:					" + getMessage() 
			+ "\n ErrorCode:				" + getErrorCode() 
			+ "\n ErrorDescription:			" + getErrorDescription()
			+ "\n ResourceBundle:			" + getResourceBundle() 
			+ "\n Severity:					" + getSeverity() 
			+ "\n CauseExceptions:			" + getCauseExceptionsAsString() + "\n");
	}
}