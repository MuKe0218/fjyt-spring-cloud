package com.fjyt.common.exception;

/**
 * @author keQiLong
 * @date 2023年04月20日 16:19
 *  工具类异常
 */
public class UtilException extends RuntimeException {
    private static final long serialVersionUID = 8247610319171014183L;

    public UtilException(Throwable e)
    {
        super(e.getMessage(), e);
    }

    public UtilException(String message)
    {
        super(message);
    }

    public UtilException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
