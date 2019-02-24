package org.crap.jrain.mvc.netty.decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.crap.jrain.core.util.FileUtil;

public class Part {

	private String filename;

	private String contentType;

	private byte[] buffer;

	private long size;

	public void write(String path) throws FileNotFoundException, IOException {
		File file = new File(path);
		File fileParent = file.getParentFile();
		if (fileParent != null && !fileParent.exists()) {
			if (fileParent.mkdirs() == false) {
	            throw new IOException("Cannot create directories = " + fileParent.getPath());
	        }
		}
		FileUtil.createFile(this.buffer, path);
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
