package com.xiaohu.fireworkssystem.utils;

import android.content.Context;
import android.os.Environment;

import com.xiaohu.fireworkssystem.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CopyData_File {
	//private static final String DB_PATH = "data/data/com.xiaohu.fireworkssystem/res/raw";
	private static final String DB_PATH = Environment.getExternalStorageDirectory()+"/wltlib/";
	private static final String ASSET_NAME_1 = "base.dat";
	private static final String ASSET_NAME_2 = "license.lic";
	private static final String DATAPATH_1 = DB_PATH + ASSET_NAME_1;
	private static final String DATAPATH_2 = DB_PATH + ASSET_NAME_2;
	private Context mContext;
	
	public CopyData_File(Context context){
		 this.mContext = context;
	}
	
	public void DoCopy(){
		try{
			 File file = new File(DB_PATH);
			 if(!file.exists()){
				   file.mkdir();
			 }
			 if(!(new File(DATAPATH_1).exists() || new File(DATAPATH_2).exists())){
				 InputStream ips = this.mContext.getResources().openRawResource(R.raw.base);
				 InputStream mips = this.mContext.getResources().openRawResource(R.raw.license);
				 FileOutputStream os = new FileOutputStream(DATAPATH_1);
				 FileOutputStream  mos = new FileOutputStream(DATAPATH_2);

				 byte[] buffer = new byte[1024*1024];
				 int count = 0;
				 
				 while((count = mips.read(buffer)) >0){
 				    mos.write(buffer,0,count);
 			     }
 			     mos.close();
 			     mips.close();
 			     
 			     while((count = ips.read(buffer)) > 0){
 				    os.write(buffer,0,count);    
 			     }
 			     os.close();
 			     ips.close();	   
			 }
			 
		}catch(IOException e){
			 e.printStackTrace();
		}
		
	}

}
