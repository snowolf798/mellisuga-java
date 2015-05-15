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
 * 两个相连数据处理构件之间的参数传递指定
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:06:45
 */
public class Assignment  extends java.util.Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**	
	 * 前一数据处理的输出参数类型		
	 */
	private String _sourceType = null;
	/**	 
	 * 前一数据处理的输出参数
	 */
	private String _sourceVariable = null;
	/**	
	 * 后一数据处理的输出参数类型
	 * 
	 */
	private String _targetType = null;
	/**	 
	 * 后一数据处理的输出参数		
	 */
	private String _targetVariable = null;

	/**
	 * 构造函数
	 */
	public Assignment(){
	}

	/**
	 * 设置前一数据处理构件的输出参数类型			 
	 * @param value 前一构件输出参数类型	
	 */
	public void setSourceType(String value){
		_sourceType = value;
	}
    /**
     * 获得前一数据处理构件的输出参数类型	
     * @return 前一构件输出参数类型
     */
	public String getSourceType(){
		return _sourceType;
	}
	
	/** 
	 * 设置前一数据处理构件的输出参数对象 
	 * @param value 前一构件输出参数
	 */
	public void setSourceVariable(String value){
		_sourceVariable = value;
	}
	/**
	 * 获得前一数据处理构件的输出参数对象 
	 * @return 前一构件输出参数
	 */
	public String getSourceVariable(){
		return _sourceVariable;
	}

	/**
	 * 设置后一数据处理构件的输出参数类型
	 * 	 
	 * @param value 后一构件输出参数类型
	 */
	public void setTargetType(String value){
		_targetType = value;
	}
	/**
	 * 获得后一数据处理构件的输出参数类型
	 * @return 后一构件输出参数类型
	 */
	public String getTargetType(){
		return _targetType;
	}

	/** 
	 * 设置后一数据处理构件的输出参数对象
	 * @param value 后一构件输出参数
	 */
	public void setTargetVariable(String value){
		_targetVariable = value;
	}
    /**
     * 获得后一数据处理构件的输出参数对象
     * @return 后一构件输出参数
     */
	public String getTargetVariable(){
		return _targetVariable;
	}
}