package de.u808.simpleinquest.indexer.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Field;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.util.LittleEndian;

import de.u808.simpleinquest.indexer.Indexer;

public class PPTIndexer extends AbstractIndexer implements POIFSReaderListener{
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private POIFSReader reader = new POIFSReader();

    private ByteArrayOutputStream writer;

	@Override
	public void setContentsFild(File file) throws IndexerException {
		try {
			this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, new StringReader(this.extractContent(file))));
		} catch (Exception e) {
			throw new IndexerException(e.getMessage(), e);
		}
	}
	
	public String extractContent(File file) throws IOException {
		String contents = "";    	
		writer = new ByteArrayOutputStream();
		reader.registerListener(this);
		reader.read(new FileInputStream(file));
		contents = writer.toString();
		writer.close();
		writer = null;
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
			buffer = null;
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}		
	}

}
