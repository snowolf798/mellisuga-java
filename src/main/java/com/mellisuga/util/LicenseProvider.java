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
package com.mellisuga.util;


public final class LicenseProvider {
	private static class Holder 
	{    
		public static final LicenseProvider instance = new LicenseProvider();  
		private static String _s_time = "2013/12/01 12:00:00";
		private static String _e_time = "9999/12/31 12:00:00";
		public static java.util.Date getStartTime(){
			java.util.Date time=new java.util.Date();		
			java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");		
			try{			 
				time = sd.parse(_s_time);		
			}		
			catch ( java.text.ParseException e) { 			
				System.out.println("日期格式有误！"); 		
			}		
			return time;
		}
		
		public static java.util.Date getEndTime(){
			java.util.Date time=new java.util.Date();		
			java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");		
			try{			 
				time = sd.parse(_e_time);		
			}		
			catch ( java.text.ParseException e) { 			
				System.out.println("日期格式有误！"); 		
			}		
			return time;
		}
		
		public static boolean validateTime(java.util.Date t){
			try{
				java.util.Date stime = getStartTime();
				java.util.Date etime = getEndTime();	
				
				java.util.Calendar s_c = java.util.Calendar.getInstance();
				s_c.setTime(stime);
				java.util.Calendar t_c = java.util.Calendar.getInstance();
				t_c.setTime(t);
				if (s_c.after(t_c)){	
					System.out.println("开始日期在当前日期之后"); 
					return false;
				}
				java.util.Calendar e_c = java.util.Calendar.getInstance();
				e_c.setTime(etime);
				if (e_c.before(t_c)){
					System.out.println("结束日期在当前日期之前");
					return false;
				}
				return true;
			}
			catch (Exception e) { 			
				System.out.println("日期格式有误！"); 		
			}		
			return false;
		}
	}    
	   
	public static LicenseProvider getInstance() 
	{    
		return Holder.instance;
	}
	
	LicenseProvider()
	{		
	}
	
	public Boolean connect()
    {            
		return true;
    }
	
	public String user()
    {
        return "";       
    }

    public String company()
    {
        return "";        
    }
        
    public String licenseServer()
    {
        return "";        
    }            

    public Boolean validate(String productName, String productVersion)
    {
/*    	
    	if (!Holder.validateTime(new java.util.Date())){    		
    		System.out.println("许可已过期！"); 
    		return false;
    	}*/
    	return true;
    }

    public void disconnect()
    {
    
    }
    
}
