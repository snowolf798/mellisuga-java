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
package com.mellisuga.core;

import com.mellisuga.processing.ContextImpl;
import com.mellisuga.processing.IContext;
import com.mellisuga.processing.IPMParser;
import com.mellisuga.processing.IProcessingManager;
import com.mellisuga.util.LicenseProvider;

/**
 * 管理器单件实例 
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:08:03
 */
public class InstanceManager {
	
	/**
	 * @version 1.0
	 * @created 10-1-2008 21:08:03
	 */
	private static class Holder 
	{    
		public static final InstanceManager instance = new InstanceManager();    
	}    
	   
	/**
	 * 获取唯一实例
	 * @return  {@linkplain com.mellisuga.core.InstanceManager InstanceManager}实例
	 */
	public static InstanceManager getInstance() 
	{    
		return Holder.instance;
	}
	
	/**
	 * 构造函数
	 */
	InstanceManager()
	{
		if (!LicenseProvider.getInstance().validate("", "")){
			return;
		}
		this.setContext(ContextImpl.instance());
		ContextImpl.instance().initialize();	
	}
	
	private IApplication _app = null;
	/**
	 * 获取应用程序接口
	 * @return 应用程序
	 */
	public IApplication getApplication()
	{
		return _app;
	}
	/**
	 * 设置应用程序接口
	 * @param value 应用程序实例
	 */
	public void setApplication(IApplication value)
	{
		_app = value;
	}
	
	/**
	 * 设置上下文
	 * @param value 上下文实例
	 */
	public void setContext(IContext value){
		_context = value;		
    }
	/**
	 * 获取上下文
	 * @return 上下文实例
	 */
	public IContext getContext(){        
        return _context;        
    }
	
	/**
	 * 获取流程管理器 
	 * @return 流程管理器
	 */
	public IProcessingManager processingManager(){
		if (!LicenseProvider.getInstance().validate("", "")){
			return null;
		}
		if (null == _app){
			return null;
		}
		return _app.processingManager();
    }

	/**
	 * 获取模型解析器
	 * @return 模型解析器
	 */
    public IPMParser getPMParser(){
    	if (null == _app){
			return null;
		}
		return _app.newPMParser();
    }

    /**
     * 生成系统唯一编号
     * @return 编号
     */
	public int makeUniqueId() {
		++_index;
    	return _index;
	}
	
	private IContext _context = null;
    private int _index = 0;
}
