package org.crap.jrain.core.bean.result.criteria;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.bean.result.Result;
import org.crap.jrain.core.error.support.Errors;

public class DataResult extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8884509861114561764L;
	
	protected Data data;
	
	public DataResult() {}

	public DataResult(Object info) {
		this(Errors.OK, new Data(info));
	}
	
	public DataResult(Object... info) {
		this(Errors.OK, new Data(info));
	}
	
	public DataResult(Errcode errcode, Data data) {
		super(errcode);
		this.data = data;
	}
	
	public DataResult(Errcode errcode, String msg, Data data) {
		super(errcode, msg);
		this.data = data;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
	
	
}
