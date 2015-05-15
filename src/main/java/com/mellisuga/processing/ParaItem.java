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
 * 数据处理构件的参数定义类<br>
 * 
 * <p>数据处理构件的输入参数和输出参数的的参数名称、参数类型、参数是否可选等信息是通过ParaItem类对象进行规范化存储的。<br>    
 * 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:09:40
 */
public class ParaItem extends java.util.Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _abstract = null;
	private String _title = null;
	private String _name = null;
	private boolean _option = false;
	private String _type = null;
	private Object _value = null;
	private String _uiType = null;

	/**
	 * 默认构造函数
	 */
	public ParaItem(){

	}

	/**
	 * 根据指定的参数，初始化类的新实例        
	 * 
	 * @param name 指定的数据处理构件的参数的参数名称。
	 * @param type 指定的数据处理构件的参数的参数类型。
	 * 	
	 */
	public ParaItem(String name, String type){
		this._name = name;
		this._type = type;
	}

	/**
	 * 根据指定的参数，初始化类的新实例
	 * 
	 * @param name 指定的数据处理构件的参数的参数名称。
	 * @param type 指定的数据处理构件的参数的参数类型。
	 * @param title 指定的数据处理构件的参数的标题。
	 * @param option 指定的数据处理构件的参数是否可选。
	 * @param controlType 指定的数据处理构件的参数控件类型。
	 */
	public ParaItem(String name, String type, String title, Boolean option, String controlType){
		this._name = name;
		this._type = type;
		this._title = title;
		this._option = option;
		this._uiType = controlType;
	}

	/**
	 * 获取数据处理构件的参数的参数名称。
	 * @returns 参数名称
	 */
	public String getName(){
		return this._name;
	}
	/**
	 * 设置数据处理构件的参数的参数名称。
	 * @param value 参数名称
	 */
	public void setName(String value){
		this._name = value;
	}

	/**
	 * 获取数据处理构件的参数的标题信息。
	 * @return 参数标题
	 */
	public String getTitle(){
		return this._title;
	}
    /**
     * 设置数据处理构件的参数的标题信息。
     * @param value 参数标题
     */
	public void setTitle(String value){
		this._title = value;
	}
	/**
	 * 获取数据处理构件的参数的详细描述信息。
	 * @return 参数详细描述
	 */
	public String getAbstracts(){
		return this._abstract;
	}
	/**
	 * 设置数据处理构件的参数的详细描述信息。
	 * @param value 参数详细描述
	 */
	public void setAbstracts(String value){
		this._abstract = value;
	}

	/**
	 * 获取数据处理构件的参数的参数类型。<br>
	 * 
	 * <p>参数类型是通过代表数据类型的字符串来指定的。为了使模型中使用的数据类型不依赖与程序语言环境，采用了以代表特定数据类型的特定字符串的方式来指定数据类型，便于模型的迁移。当在模型执行时，会将这些代表数据类型的字符串转换为程序语言真正的数据类型，特定的代表数据类型的字符串最终将转换为那种真正的数据类型，必须事先进行定义，也就是建立一个表格，表格的内容为代表数据类型的字符串与真正的依赖于语言的数据类型的对照表，因此，在模型执行时，会通过查这个对照表，将用字符串表示的数据类型转换为真正的数据类型。<br>
	 * @return 参数类型
	 */
	public String getDataType(){
		return this._type;
	}
	/**
	 * 设置数据处理构件的参数的参数类型。
	 * @param value 参数类型
	 */
	public void setDataType(String value){
		this._type = value;
	}

	/**
	 * 获取数据处理构件的参数是否为可选参数。<br>
	 * 
	 * <p>数据处理构件的参数为可选参数，表示数据处理构件执行时不需要用户必须设置该参数的值；如果参数为非可选参数，用户在执行该数据处理构件时，必须正确设置该参数的值，否则数据处理构件执行将失败。<br>
	 * @return 参数是否可选
	 */
	public boolean getOption(){
		return this._option;
	}
	/**
	 * 设置数据处理构件的参数是否为可选参数。
	 * @param value 参数是否可选
	 */
	public void setOption(boolean value){
		this._option = value;
	}

	/**
	 * 获取参数对应的界面输入控件类型。
	 * 
	 * <p>在数据流程建模工具中，进行数据处理参数的设置是通过一个参数设置窗口来完成的，该属性用来获取和设置，该数据处理的参数出现在参数设置窗口中时，采用哪种类型的控件与用户实现参数值设置的交互，例如，如果参数的控件类型设置为文本框，则在数据流程建模工具中的参数设置窗口中，该参数将对应一个文本框，通过文本框中用户输入的数值来接收该参数的设置值。
	 * @return 参数控件类型
	 */
	public String getUIType(){
		return this._uiType;
	}
	/**
	 * 设置参数对应的界面输入控件类型。
	 * @param value 参数控件类型
	 */
	public void setUIType(String value){
		this._uiType = value;
	}
    /**
     * 获取参数对应的对象值
     * @return 参数值
     */
	public Object getValue(){
		return this._value;
	}
    /**
     * 设置参数对应的对象值
     * @param value 参数值
     */
	public void setValue(Object value){		
		this._value = value;
	}
}