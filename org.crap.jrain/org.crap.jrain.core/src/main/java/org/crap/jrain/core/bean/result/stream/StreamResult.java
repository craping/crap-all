package org.crap.jrain.core.bean.result.stream;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.bean.result.Result;

public class StreamResult extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1091419979872311310L;
	
	protected StreamData data;
	
	public StreamResult() {}

	public StreamResult(Errcode errcode) {
		super(errcode);
	}
	
	public StreamResult(Errcode errcode, StreamData data) {
		super(errcode);
		this.data = data;
	}
	
	public StreamResult(Errcode errcode, String msg, StreamData data) {
		super(errcode, msg);
		this.data = data;
	}
	
	public StreamData getData() {
		return data;
	}

	public void setData(StreamData data) {
		this.data = data;
	}
}
