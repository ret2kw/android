package com.example.debug_all;

import java.lang.reflect.Method;

import android.util.Log;

import com.saurik.substrate.*;




public class debugHook {
	
	public static String _TAG = "debugHook";
	
	public static void initialize() {
		
		Log.i(_TAG, "PLUGIN INITILIAZED");
		
		MS.hookClassLoad("android.os.Process", 
				new MS.ClassLoadHook() {
			
			public void classLoaded(Class<?> process) {
				Method start;
				
				Log.i(_TAG, "INSIDE HOOKING CLASS");
				
				try {
					start = process.getMethod("start", String.class, String.class, Integer.TYPE, Integer.TYPE, int[].class, Integer.TYPE, Integer.TYPE,
                            Integer.TYPE, String.class, String[].class);
					
				} catch (NoSuchMethodException e) {
					start = null;
					Log.i(_TAG, "COULDNT FIND START METHOD!!!!!");
				}
				
				if (start != null) {
					MS.hookMethod(process, start, new MS.MethodAlteration() {
						public Object invoked(Object process, Object... args)
						throws Throwable
						{
							
							args[5] = ((Integer) args[5] | 0x1);
							return invoke(process, args);
							
						}
						
					});
				}
				
			};
			
		});
		
		
		
	}

}
