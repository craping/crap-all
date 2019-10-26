package org.crap.jrain.core.validate.support.param;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.validate.exception.ValidationException;
import org.crap.jrain.core.validate.support.Param;

import com.alibaba.fastjson.JSONArray;

public class CollectionParam extends Param<JSONArray> {
	
	public CollectionParam() {
		this.multi = false;
	}

	@Override
	protected Errcode validateValue(Object param) throws ValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean validateMin(Object param) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean validateMax(Object param) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected JSONArray cast0(Object param) {
		// TODO Auto-generated method stub
		return null;
	}
}
