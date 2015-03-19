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

import java.util.ArrayList;
import java.util.Collection;
import com.mellisuga.designpattern.IConnectableObject;

/**
 * 连接器集合
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:06:45
 *
 */
public class Connectors extends ArrayList<IConnector> implements
		IConnectableObject {
	private static final long serialVersionUID = 1L;

	 private IConnectableObject _inputConnectableObject = null;
     private IConnectableObject _outputConnectableObject = null;
     
     /**
      * 默认构造函数
      */
     public Connectors(){
     }
    /**
     * 拷贝构造函数
     * @param c 拷贝对象
     */
    public Connectors(Collection<IConnector> c){
    	if (null != c){
    		this.addAll(0, c);    		
    	}
    }
     
	@Override
	public String getGuid() {
		return null;
	}

	@Override
	public void setGuid(String value) {

	}

	@Override
	public IConnectableObject getInputConnectableObject() {
        return this._inputConnectableObject;
	}

	@Override
	public void setInputConnectableObject(IConnectableObject value) {
		this._inputConnectableObject = value;
	}

	@Override
	public IConnectableObject getOutputConnectableObject() {
        return this._outputConnectableObject;
	}

	@Override
	public void setOutputConnectableObject(IConnectableObject value) {
		this._outputConnectableObject = value;
	}

}
