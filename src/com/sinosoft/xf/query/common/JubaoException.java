package com.sinosoft.xf.query.common;

/**
 * 信访系统业务异常
 * @author hjh
 *
 * 2015-1-22下午05:16:03
 */
public class JubaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JubaoException(){
		super();
	}
	public JubaoException(String errorMsg){
		super(errorMsg);
	}
}
