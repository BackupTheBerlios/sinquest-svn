package de.u808.simpleinquest.indexer.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.util.LittleEndian;

import de.u808.simpleinquest.indexer.ContentHandler;

public class PPTContentHandler implements ContentHandler, POIFSReaderListener{
	
	Log log = LogFactory.getLog(this.getClass());

    private ByteArrayOutputStream writer;

	public String extractContent(File file) throws IOException {
		String contents = "";
    	POIFSReader reader = new POIFSReader();
		writer = new ByteArrayOutputStream();
		reader.registerListener(this);
		reader.read(new FileInputStream(file));
		contents = writer.toString();
		return contents;
	}

	public void processPOIFSReaderEvent(POIFSReaderEvent event) {
		try {
			if (!event.getName().equalsIgnoreCase("PowerPoint Document"))
				return;
			DocumentInputStream input = event.getStream();
			byte[] buffer = new byte[input.available()];
			input.read(buffer, 0, input.available());
			for (int i = 0; i < buffer.length - 20; i++) {
				long type = LittleEndian.getUShort(buffer, i + 2);
				long size = LittleEndian.getUInt(buffer, i + 4);
				if (type == 4008L) {
					writer.write(buffer, i + 4 + 1, (int) size + 3);
					i = i + 4 + 1 + (int) size - 1;
				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}		
	}

}
