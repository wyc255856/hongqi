package com.faw.e115.util;import android.content.Context;import android.util.Log;import java.io.BufferedReader;import java.io.ByteArrayOutputStream;import java.io.File;import java.io.FileInputStream;import java.io.IOException;import java.io.InputStream;import java.io.InputStreamReader;/** * @author henzil.jack E-mail:henzil.jack@gmail.com * @version ����ʱ�䣺2013-7-2 ����5:09:35 * @Description �����������ʹ�� */public class TestUtil {	final static int BUFFER_SIZE = 4096;	// 解析raw里的json	public static String readTextFileFromRawResourceId(Context context, int resourceId) {		StringBuilder builder = new StringBuilder();		BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(				resourceId)));		try {			for (String line = reader.readLine(); line != null; line = reader.readLine()) {				builder.append(line).append("\n");			}		} catch (Exception e) {			throw new RuntimeException(e);		}		return builder.toString();	}	@SuppressWarnings("resource")	public static String readTextFile(Context context, String path) {		StringBuilder builder = new StringBuilder();		File file = new File(path);		if (!file.exists()) {//			Toast.makeText(context, "json文件不存在", Toast.LENGTH_SHORT).show();//			return null;		}		BufferedReader reader = null;		try {//gb2312			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));		} catch (Exception e1) {//			Toast.makeText(context, "json解析失败", Toast.LENGTH_SHORT).show();			e1.printStackTrace();		}		try {			for (String line = reader.readLine(); line != null; line = reader.readLine()) {				builder.append(line).append("\n");			}		} catch (Exception e) {			Log.e("tag", "exciption="+e);			throw new RuntimeException(e);		}		return builder.toString();	}	public static String getJson(String fileName) {		StringBuilder stringBuilder = new StringBuilder();		try {			@SuppressWarnings("resource")            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));			String line;			while ((line = bf.readLine()) != null) {				stringBuilder.append(line);			}		} catch (IOException e) {			e.printStackTrace();		}		return stringBuilder.toString();	}	public static void deleteFile(String path) {		File file = new File(path);		if (file.exists()) {			file.delete();		}	}	public static String InputStreamTOString(InputStream in) throws Exception {		ByteArrayOutputStream outStream = new ByteArrayOutputStream();		byte[] data = new byte[BUFFER_SIZE];		int count = -1;		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)			outStream.write(data, 0, count);		data = null;		return new String(outStream.toByteArray(), "ISO-8859-1");	}	public static String getJspString(String path) {		String resultString = "";		File fileVersion = new File(path);		try {			InputStream in = new FileInputStream(fileVersion);			resultString = TestUtil.InputStreamTOString(in);		} catch (Exception e) {			return null;		}		return resultString;	}}