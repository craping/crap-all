package org.crap.jrain.core.bean.result;

import java.io.Serializable;

  
/**  
* @ClassName: Errcode  
* @Description: 标准数据返回接口
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public interface Errcode extends Serializable {
	/** 
	 * 数据结果集
	 */
	public int getResult();
	/** 
	 * 数据错误码
	 */
	public int getErrcode();
	/** 
	 * 结果描述
	 */
	public String getMsg();
}
