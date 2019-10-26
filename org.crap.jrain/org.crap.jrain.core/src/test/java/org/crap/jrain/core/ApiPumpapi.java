package org.crap.jrain.core;

import java.util.Map;

import org.crap.jrain.core.bean.result.Errcode;

public class ApiPumpapi extends MapPump {

	@Override
	public Errcode execute(Map<?, ?> params) {
		return api(params);
	}
}