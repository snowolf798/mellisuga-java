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
package com.mellisuga.independent;

import com.mellisuga.core.IApplication;
import com.mellisuga.processing.IPMParser;
import com.mellisuga.processing.IProcessingManager;
import com.mellisuga.processing.PMParserImpl;
import com.mellisuga.processing.ProcessingManagerImpl;

/**
 * 通用应用程序，不和任一特定应用平台绑定
 * 
 * <p>应用程序和某一平台绑定模式下，如果两个平台有相同的功能，那么可以通过平台切换实现程序动态在两个基础平台之上运行，
 * 而在通用应用程序中，不和任一平台绑定，如果多个平台的功能在一个应用程序中共存，那么则必须通过平台标识作为前缀实现功能的区别。
 * @author snowolf798@gmail.com
 * @version 1.0
 * @created 10-1-2008 21:04:35
 *
 */
public class UniApplication implements IApplication {

	private IProcessingManager _gpManager = ProcessingManagerImpl.instance();

	@Override
	public void initialize() {

	}

	@Override
	public IProcessingManager processingManager() {
		return _gpManager;
	}

	public IPMParser newPMParser(){
		return new PMParserImpl();
	}
}
