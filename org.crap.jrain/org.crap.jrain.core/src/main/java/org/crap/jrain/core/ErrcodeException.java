package org.crap.jrain.core;

import java.text.MessageFormat;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.bean.result.Result;

  
/**  
* @ClassName: ErrcodeException  
* @Description: 错误集异常
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public class ErrcodeException extends Exception {
	
	private static final long serialVersionUID = -3275698556861545365L;
	
	protected Object[] marks;
	
	protected final Errcode errcode;
	
	
	public ErrcodeException(Errcode errcode) {
		super(errcode.getMsg());
		this.errcode = errcode;
	}
	
	public ErrcodeException(Errcode errcode, Throwable cause) {
		super(errcode.getMsg(), cause);
		this.errcode = errcode;
	}
	
	public ErrcodeException(Errcode errcode, Object... marks) {
		super(MessageFormat.format(errcode.getMsg(), marks));
		this.errcode = errcode;
	}
	
	public ErrcodeException(Errcode errcode, Throwable cause, Object... marks) {
		super(MessageFormat.format(errcode.getMsg(), marks), cause);
		this.errcode = errcode;
	}
	
	public Errcode getErrcode() {
		return errcode;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	public Result toResult() {
		return new Result(this.errcode, getMessage());
	}
}
