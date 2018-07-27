package org.crap.jrain.core;

import java.text.MessageFormat;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.bean.result.Result;

  
/**  
* @ClassName: ErrcodeRuntimeException  
* @Description: 运行时错误集异常类
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public class ErrcodeRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1376483386393288855L;

	protected final Errcode errcode;

	public ErrcodeRuntimeException(Errcode errcode) {
		super(errcode.getMsg());
		this.errcode = errcode;
	}
	
	public ErrcodeRuntimeException(Errcode errcode, Object... marks) {
		super(MessageFormat.format(errcode.getMsg(), marks));
		this.errcode = errcode;
	}
	
	public ErrcodeRuntimeException(Errcode errcode, Throwable cause, Object... marks) {
		super(MessageFormat.format(errcode.getMsg(), marks), cause);
		this.errcode = errcode;
	}
    
    public Result toResult() {
		return new Result(this.errcode);
	}
}
