package org.crap.jrain.core.bean.result.criteria;

import java.io.Serializable;

public class Page implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6737728243969517019L;

	protected Integer totalnum;		//总条数
	
	protected Integer totalpage;	//总页数
	
	protected Integer num;			//查询条数
	
	protected Integer page;			//当前页数
	
	public Page() {
	}
	
	public Page(Integer page) {
		this.page = page;
	}
	
	public Page(Integer page, Integer num) {
		this(page);
		this.num = num;
	}
	
	public Integer getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(Integer totalnum) {
		this.totalnum = totalnum;
	}

	public Integer getTotalpage() {
		if(totalnum != null && num != null){
			totalpage = totalnum / num + ((totalnum % num) > 0 ? 1 : 0);
			totalpage = (totalpage == 0) ? 1 : totalpage;
		}
		return totalpage;
	}

	public void setTotalpage(Integer totalpage) {
		this.totalpage = totalpage;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
