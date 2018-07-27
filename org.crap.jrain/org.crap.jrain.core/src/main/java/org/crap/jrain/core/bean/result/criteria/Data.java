package org.crap.jrain.core.bean.result.criteria;

import java.util.ArrayList;
import java.util.List;

public class Data extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3827967228536008000L;
	
	protected Object info;
	
	public Data() {}
	
	public Data(Object... info) {
		List<Object> list = new ArrayList<>();	
		for (Object object : info) {
			list.add(object);
		}
		this.info = list;
	}
	
	public Data(Object info) {
		this.info = info;
	}
	
	public Data(Object info, Page page) {
		this(info);
		setPage(page);
	}
	
	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public void setInfo(Object... info) {
		List<Object> list = new ArrayList<>();
		for (Object object : info) {
			list.add(object);
		}
		this.info = list;
	}
	
	public void setPage(Page page) {
		this.totalnum = page.getTotalnum();
		this.totalpage = page.getTotalpage();
		this.num = page.getNum();
		this.page = page.getPage();
	}
}
