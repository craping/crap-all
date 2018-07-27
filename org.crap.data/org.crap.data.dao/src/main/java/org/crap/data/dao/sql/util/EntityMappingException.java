package org.crap.data.dao.sql.util;

import org.crap.jrain.core.ErrcodeRuntimeException;
import org.crap.jrain.core.error.support.Errors;

/** 
 * @project Crap
 * 
 * @author Crap
 * 
 * @Copyright 2013 - 2014 All rights reserved. 
 * 
 * @email 422655094@qq.com
 * 
 * RowMpperException异常
 */
public class EntityMappingException extends ErrcodeRuntimeException {

	private static final long serialVersionUID = -7333552725998641151L;
	
	public EntityMappingException(Class<?> cls) {
		super(Errors.DATA_TYPE_ERROR, "Unknown entity: " + cls.getName());
	}
	
}
