/**
 * Copyright (c) 2008-2015 snowolf798@gmail.com. All rights reserved.
 *
 *
 * Mellisuga is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mellisuga.processing;

/**
 * 日志对象
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:13:28
 */
public interface IRunningLogger {
	//logLevel
	//0-不输出信息
	//1-fatal
	//2-error,默认
	//3-warn
	//4-info
	//5-debug

	/**
	 * 输出致命错误
	 * @param message 致命错误信息
	 */
	public void fatal(String message);

	/**
	 * 输出错误
	 * @param message 错误信息
	 */
	public void error(String message);

	/**
	 * 输出警告信息
	 * @param message 警告信息
	 */
	public void warn(String message);
	
	/**
	 * 输出提示信息
	 * @param message 输出信息
	 */
	public void info(String message);

	/**
	 * 输出调试信息
	 * @param message 调试信息
	 */
	public void debug(String message);

}