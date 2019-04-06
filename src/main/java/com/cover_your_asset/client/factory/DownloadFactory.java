package com.cover_your_asset.client.factory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DownloadFactory {
	
	private static final int BUFFER_SIZE = 4 * 1024;
	
	private DownloadFactory() {}
	
	public static void downloadFile(Socket socket, String filename) throws IOException { 
		
    	if (socket != null) {
    		sendDownloadRequest(socket, filename);
    		download(socket, filename);
    	}
    	socket.close();
    }
	
	public static List<String> getFileText(Socket socket, String filename) throws IOException { 
		List<String> lines = null;
    	if (socket != null) {
    		sendDownloadRequest(socket, filename);
    		lines = downloadText(socket, filename);
    	}
    	socket.close();
    	return lines;
    }
	
	private static void download(Socket socket, String filename) throws IOException {
		//File file = getFile(filename);
		final File file = new File("resources/cards/" + filename);
		if (!file.exists()) {
			byte[] data = new byte[BUFFER_SIZE];
	    	BufferedInputStream bis = new BufferedInputStream(socket.getInputStream(), BUFFER_SIZE);
	    	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
	    	int bytesRead;
	    	while ((bytesRead = bis.read(data)) != -1) {
	    		bos.write(data, 0, bytesRead);
	    	}
	    	bos.flush();
	    	bos.close();
	    	bis.close();
		}
    }
	
	private static List<String> downloadText(Socket socket, String filename) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()), BUFFER_SIZE);
    	List<String> lines = new ArrayList<String>(); 
    	String line;
    	while ((line = br.readLine()) != null) {
    		lines.add(line);
    	}
    	br.close();
    	return lines;
    }

	private static void sendDownloadRequest(Socket socket, String filename) throws IOException {
    	BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()), BUFFER_SIZE);
		String requestString = RequestFactory.generateDownloadFileRequestString(filename);
		System.out.println(requestString);
		bufferedWriter.write(requestString);
		bufferedWriter.flush();
    }

	public static void createFolder(String path) {
		File file = new File("resources/cards/images/");
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
