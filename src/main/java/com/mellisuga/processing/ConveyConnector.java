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

import com.mellisuga.designpattern.IConnectableObject;

/**
 * 赋值连接器类<br>
 * 
 * <p>赋值连接器用来连接不需要进行数据转换，即数据类型一样的一对参数。<br>
 * 
 * <p>赋值连接器是一种简单的连接器，它不对写入连接器中的数据进行数据类型的转换，
 * 即写入连接器的数据在从连接器中取出时，数据类型保持不变，这种连接器只负责数据处理间一对参数的数据传递，即赋值，而不能进行数据转换。<br>
 * 
 * <p>连接器可以形象地理解为连接两个数据处理构件的数据通道，数据处理构件的输出数据可以通过与该数据处理构件连接的连接器，传递到连接器另一端所连接的数据处理构件，作为这个数据处理构件的输入数据。
 * 因此，连接器可以承载数据，同时连接着具有数据联系的数据处理构件，并负责数据的传递以及必要的数据类型的转换。<br>
 * <p>一个数据处理构件具有多个输入参数和输出参数，但是一个连接器只能对应两个数据处理构件间的一对参数关系，
 * 因此，要建立两个数据处理构件间的多对参数对应关系就需要多个连接器来连接。<br>
 * <p>连接器为连接两个数据处理构件间参数传递的通道，起到承载数据、传递数据或者转换数据的作用，
 * 因此，在一个数据处理构件执行完成后，可以将该数据处理构件的某个输出参数对应的输出结果数据写入与该数据处理构件连接的连接器中，而与该连接器相连的另一个数据处理构件，就可以通过获取连接器中已放置的上一个数据处理构件的输出数据，作为这个数据处理构件的一个输入参数，即为该数据处理构件提供输入数据；
 * 按照这种方式类推，就可以将多个数据处理构件通过连接器构成一个数据处理工作流，实现数据处理的流程化和自动化。<br>
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:07:21
 */
public class ConveyConnector implements IConnector {

	private String _guid = "";
	private int _id = 0;
	private Object _variable = null;

	/**
	 * 默认构造函数
	 */
	public ConveyConnector(){

	}

	public String getGuid() {
		return this._guid;
	}
	
	public void setGuid(String value){
		this._guid = value;
	}
	
	
	public IConnectableObject getInputConnectableObject(){
		return null;
	}
	public void setInputConnectableObject(IConnectableObject value){
		
	}

    
    public IConnectableObject getOutputConnectableObject(){
    	return null;
    }
    public void setOutputConnectableObject(IConnectableObject value){
    	
    }
    
	//获取或设置连接器的 ID 号，ID 号用来唯一标识一个连接器，即用来唯一标识连接器对应的一对空间处理间的参数关系。	 
	public void setid(int value){
		_id = value;
	}
	
	public int getid()
	{
		return _id;
	}
	
	public String inputParaType(){
		return "any";
	}

	public String outputParaType(){
		return "any";
	}

	/**
	 * 返回已经写入连接器中的数据<br>
	 * 
	 * <p>连接器为连接两个数据处理构件间参数传递的通道，起到承载数据、传递数据或者转换数据的作用，因此，在一个数据处理构件执行完成后，可以将该数据处理构件的某个输出参数对应的输出结果数据写入与该数据处理构件连接的连接器中，而与该连接器相连的另一个数据处理构件，就可以通过获取连接器中已放置的上一个数据处理构件的输出数据，作为这个数据处理构件的一个输入参数，即为该数据处理构件提供输入数据；按照这种方式类推，就可以将多个数据处理构件通过连接器构成一个数据处理工作流，实现数据处理的流程化和自动化。
     * 从连接器中获取数据时，如果之前写入连接器的数据的数据类型与连接器可以取出的数据的数据类型不一致，那么在取出连接中的数据时，连接器将根据连接器指定的可以取出的数据的数据类型进行相应的数据转换，从而，从连接器获取到与连接器本身定义的取出数据的数据类型一致的数据。
     * 但是，这里对于赋值连接器，写入连接器和从连接器取出的数据的数据类型是一样，不存在数据转换的操作。<br>
     * 
     * @returns 返回已经写入连接器中的数据
	 */
	public Object pop(){
		return _variable;
	}

	/**
	 * 向连接器中写入数据<br>
	 * 
	 * <p>连接器为连接两个数据处理构件间参数传递的通道，起到承载数据、传递数据或者转换数据的作用，因此，在一个数据处理构件执行完成后，可以将该数据处理构件的某个输出参数对应的输出结果数据写入与该数据处理构件连接的连接器中，而与该连接器相连的另一个数据处理构件，就可以通过获取连接器中已放置的上一个数据处理构件的输出数据，作为这个数据处理构件的一个输入参数，即为该数据处理构件提供输入数据；按照这种方式类推，就可以将多个数据处理构件通过连接器构成一个数据处理工作流，实现数据处理的流程化和自动化。
     * 该方法即用来向从连接器中写入数据，与连接器相连的数据处理构件可以从该连接器中获取相应的数据。写入连接器中数据的数据类型要与连接器本身定义的写入数据的数据类型一致，否则，在从连接器中取出数据时，将返回 null。<br>
	 * 
	 * @param value    要写入连接器中的数据
	 */
	public void push(Object value){
		_variable = value;
	}

	
	public IConnector clone(){
		ConveyConnector connector = new ConveyConnector();
        connector._id = this._id;
        connector._variable = this._variable;

        return connector;
	}
}