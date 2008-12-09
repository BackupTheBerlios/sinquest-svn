/**
 * Copyright (c) 2003-2005, www.pdfbox.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of pdfbox; nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * http://www.pdfbox.org
 *
 */
package org.pdfbox.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.pdfbox.cos.COSDictionary;
import org.pdfbox.cos.COSName;

/**
 * This is a filter for the CCITTFax Decoder.
 * 
 * @author <a href="mailto:ben@benlitchfield.com">Ben Litchfield</a>
 * @author Marcel Kammer
 * @author Paul King
 * @version $Revision: 1.12 $
 */
public class CCITTFaxDecodeFilter implements Filter
{   
    // Filter will write 15 TAG's
    // If you add or remove TAG's you will have to modify this value
    private static final int TAG_COUNT = 15;

    // HEADERLENGTH(fix 8 Bytes) plus ImageLength(variable)
    private int offset = 8;

    // Bytecounter for Bytes that will be written after the TAG-DICTIONARY
    private int tailingBytesCount = 0;

    // Bytes to write after TAG-DICTIONARY
    private final ByteArrayOutputStream tailer = new ByteArrayOutputStream();

    /**
     * Constructor.
     */
    public CCITTFaxDecodeFilter()
    {
    }

    /**
     * This will decode some compressed data.
     * 
     * @param compressedData
     *            The compressed byte stream.
     * @param result
     *            The place to write the uncompressed byte stream.
     * @param options
     *            The options to use to encode the data.
     * 
     * @throws IOException
     *             If there is an error decompressing the stream.
     */
    public void decode(InputStream compressedData, OutputStream result, COSDictionary options) throws IOException
    {
        // log.warn( "Warning: CCITTFaxDecode.decode is not implemented yet,
        // skipping this stream." );
        

        // Get ImageParams from PDF
        COSDictionary dict = (COSDictionary) options.getDictionaryObject("DecodeParms");
        int width = options.getInt("Width");
        int height = options.getInt("Height");
        int length = options.getInt(COSName.LENGTH);
        int compressionType = dict.getInt("K");
        boolean blackIs1 = dict.getBoolean("BlackIs1", false);        
        
        
        // HEADER-INFO and starting point of TAG-DICTIONARY
        writeTagHeader(result, length);

        // IMAGE-DATA
        int i = 0;
        //int sum = 0;
        byte[] buffer = new byte[32768];
        int lentoread = length;
        
        while ((lentoread > 0) && ((i = compressedData.read(buffer, 0, Math.min(lentoread, 32768))) != -1))
        {
            //sum += i;
            result.write(buffer, 0, i);
            lentoread = lentoread - i;        
        }
        
        // If lentoread is > 0 then we need to write out some padding to equal the header
        // We'll use what we have in the buffer it's just padding after all
        while (lentoread > 0) 
        {
            result.write(buffer, 0, Math.min(lentoread, 32768));
            lentoread = lentoread - Math.min(lentoread, 32738);
        }
        //System.out.println("Gelesen: " + sum);

        // TAG-COUNT
        writeTagCount(result);

        // WIDTH 0x0100
        writeTagWidth(result, width);

        // HEIGHT 0x0101
        writeTagHeight(result, height);

        // BITSPERSAMPLE 0x0102
        // Always 1 for CCITTFax
        writeTagBitsPerSample(result, 1);

        // COMPRESSION 0x0103
        writeTagCompression(result, compressionType);

        // PHOTOMETRIC 0x0106
        writeTagPhotometric(result, blackIs1);

        // STRIPOFFSET 0x0111
        // HERE ALWAYS 8, because ImageData comes before TAG-DICTIONARY
        writeTagStripOffset(result, 8);

        // ORIENTATION 0x0112
        writeTagOrientation(result, 1);

        // SamplesPerPixel 0x0115
        writeTagSamplesPerPixel(result, 1);

        // RowsPerStrip 0x0116
        writeTagRowsPerStrip(result, height);

        // Stripcount 0x0117
        writeTagStripByteCount(result, length);

        // XRESOLUTION 0x011A
        // HERE: 200 DPI
        writeTagXRes(result, 200, 1);

        // YRESOLITION 0x011B
        // HERE: 200 DPI
        writeTagYRes(result, 200, 1);

        // ResolutionUnit 0x0128
        // HERE: DPI
        writeTagResolutionUnit(result, 2);

        // SOFTWARE 0x0131
        // minimum 4 chars
        writeTagSoftware(result, "pdfbox".getBytes());

        // DATE AND TIME 0x0132
        writeTagDateTime(result, new Date());

        // END OF TAG-DICT
        writeTagTailer(result);        
    }

    private void writeTagHeader(OutputStream result, int length) throws IOException
    {
        byte[] header = { 'M', 'M', 0, '*' };// Big-endian
        result.write(header);


        // Add imagelength to offset
        offset += length;

        // OFFSET TAG-DICTIONARY
        int i1 = offset/16777216;//=value/(256*256*256)
        int i2 = (offset-i1*16777216)/65536;
        int i3 = (offset-i1*16777216-i2*65536)/256;
        int i4 = offset % 256;
        result.write(i1);                
        result.write(i2);
        result.write(i3);        
        result.write(i4);        
    }

    private void writeTagCount(OutputStream result) throws IOException
    {
        result.write(TAG_COUNT / 256);
        result.write(TAG_COUNT % 256);// tagCount
    }

    private void writeTagWidth(OutputStream result, int width) throws IOException
    {
        // @todo width berechnen

        // TAG-ID 100
        result.write(1);
        result.write(0);


        // TAG-TYPE SHORT=3
        result.write(0);
        result.write(3);


        // TAG-LENGTH = 1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);


        // TAG-VALUE = width
        result.write(width/256);
        result.write(width%256);
        result.write(0);// SHORT=0
        result.write(0);// SHORT=0
    
    }

    private void writeTagHeight(OutputStream result, int height) throws IOException
    {
        //@todo height berechnen
        // TAG-ID 101
        result.write(1);
        result.write(1);
    

        // TAG-TYPE SHORT=3
        result.write(0);
        result.write(3);
        

        // TAG-LENGTH = 1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        

        // TAG-VALUE
        result.write(height/256);
        result.write(height%256);
        result.write(0);// SHORT=0
        result.write(0);// SHORT=0
        
    }

    private void writeTagBitsPerSample(OutputStream result, int value) throws IOException
    {
        // TAG-ID 102
        result.write(1);
        result.write(2);
        

        // TAG-TYPE SHORT=3
        result.write(0);
        result.write(3);
    
        // TAG-LENGTH = 1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        

        // TAG-VALUE
        result.write(value/256);
        result.write(value%256);
        result.write(0);//SHORT=0
        result.write(0);//SHORT=0
        
    }
    
    /**
     * Write the tag compression.
     * 
     * @param result The stream to write to.
     * @param type The type to write.
     * @throws IOException If there is an error writing to the stream.
     */
    public void writeTagCompression(OutputStream result, int type) throws IOException
    {
        // TAG-ID 103
        result.write(1);
        result.write(3);
        
        // TAG-TYPE SHORT=3
        result.write(0);
        result.write(3);
        

        // TAG-LEGNTH = 1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        
        // TAG-VALUE
        //@todo typ eintragen; hier immer 4
        result.write(0);
        if (type < 0)
        {
            result.write(4);// G4
        }
        else if (type == 0)
        {
            result.write(3);// G3-1D
        }
        else
        {
            result.write(2);// G3-2D
        }
        result.write(0);
        result.write(0);
        
    }

    private void writeTagPhotometric(OutputStream result, boolean blackIs1) throws IOException
    {
        // TAG-ID 106
        result.write(1);
        result.write(6);
        

        // TAG-TYPE SHORT
        result.write(0);
        result.write(3);
        

        // TAG-LENGTH = 1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        

        // TAG-VALUE
        result.write(0);
        if (blackIs1)
        {
            result.write(1);
        }
        else
        {
            result.write(0);
        }
        result.write(0);// SHORT=0
        result.write(0);// SHORT=0
        
    }

    private void writeTagStripOffset(OutputStream result, int value) throws IOException
    {
        // TAG-ID 111
        result.write(1);
        result.write(17);
        
        // TAG-TYPE LONG=4
        result.write(0);
        result.write(4);
        

        // TAG-LENGTH=1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        

        // TAG-VALUE = 8 //VOR TAG-DICTIONARY
        int i1 = value/16777216;//=value/(256*256*256)
        int i2 = (value-i1*16777216)/65536;
        int i3 = (value-i1*16777216-i2*65536)/256;
        int i4 = value % 256;
        result.write(i1);                
        result.write(i2);
        result.write(i3);        
        result.write(i4);        
    
    }

    private void writeTagSamplesPerPixel(OutputStream result, int value) throws IOException
    {
        // TAG-ID 115
        result.write(1);
        result.write(21);
        

        // TAG-TYPE SHORT=3
        result.write(0);
        result.write(3);
        

        // TAG-LENGTH=1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        

        // TAG-VALUE
        result.write(value / 256);
        result.write(value % 256);
        result.write(0);// SHORT=0
        result.write(0);// SHORT=0
        
    }

    private void writeTagRowsPerStrip(OutputStream result, int value) throws IOException
    {
        // TAG-ID 116
        result.write(1);
        result.write(22);
        

        // TAG-TYPE SHORT=3
        result.write(0);
        result.write(3);
    

        // TAG-LENGTH=1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
    

        // TAG-VALUE
        result.write(value / 256);
        result.write(value % 256);
        result.write(0);// SHORT=0
        result.write(0);// SHORT=0
        
    }

    private void writeTagStripByteCount(OutputStream result, int value) throws IOException
    {
        //@todo value auswerten
        // TAG-ID 117
        result.write(1);
        result.write(23);
    
        // TAG-TYPE LONG=4
        result.write(0);
        result.write(4);
    

        // TAG-LENGTH = 1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        
        // TAG-VALUE
        int i1 = value/16777216;//=value/(256*256*256)
        int i2 = (value-i1*16777216)/65536;
        int i3 = (value-i1*16777216-i2*65536)/256;
        int i4 = value % 256;
        result.write(i1);                
        result.write(i2);
        result.write(i3);        
        result.write(i4);        
        
    }

    private void writeTagXRes(OutputStream result, int value1, int value2) throws IOException
    {
        // TAG-ID 11A
        result.write(1);
        result.write(26);
        
        // TAG-TYPE RATIONAL=5
        result.write(0);
        result.write(5);
        
        // TAG-LENGTH=1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        

        // TAG-VALUE=OFFSET TO RATIONAL
        int valueOffset = offset + 6 + 12 * TAG_COUNT + tailer.size();
        int i1 = valueOffset/16777216;//=value/(256*256*256)
        int i2 = (valueOffset-i1*16777216)/65536;
        int i3 = (valueOffset-i1*16777216-i2*65536)/256;
        int i4 = valueOffset % 256;
        result.write(i1);                
        result.write(i2);
        result.write(i3);        
        result.write(i4);        
        
        i1 = value1 /16777216;
        i2 = (value1-i1*16777216)/65536;
        i3 = (value1-i1*16777216 - i2*65536)/256;
        i4 = value1 % 256;
        tailer.write(i1);
        tailer.write(i2);
        tailer.write(i3);
        tailer.write(i4);
        
        i1 = value2 /16777216;
        i2 = (value2-i1*16777216)/65536;
        i3 = (value2-i1*16777216 - i2*65536)/256;
        i4 = value2 % 256;
        tailer.write(i1);
        tailer.write(i2);
        tailer.write(i3);
        tailer.write(i4);
        
        tailingBytesCount += 8;
    }

    private void writeTagYRes(OutputStream result, int value1, int value2) throws IOException
    {
        // TAG-ID 11B
        result.write(1);
        result.write(27);
        

        // TAG-TYPE RATIONAL=5
        result.write(0);
        result.write(5);
        
        // TAG-LENGTH=1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        

        // TAG-VALUE=OFFSET TO RATIONAL
        int valueOffset = offset + 6 + 12 * TAG_COUNT + tailer.size();
        int i1 = valueOffset/16777216;//=value/(256*256*256)
        int i2 = (valueOffset-i1*16777216)/65536;
        int i3 = (valueOffset-i1*16777216-i2*65536)/256;
        int i4 = valueOffset % 256;
        result.write(i1);                
        result.write(i2);
        result.write(i3);        
        result.write(i4);        
        
        i1 = value1 /16777216;
        i2 = (value1-i1*16777216)/65536;
        i3 = (value1-i1*16777216 - i2*65536)/256;
        i4 = value1 % 256;
        tailer.write(i1);
        tailer.write(i2);
        tailer.write(i3);
        tailer.write(i4);
        
        i1 = value2 /16777216;
        i2 = (value2-i1*16777216)/65536;
        i3 = (value2-i1*16777216 - i2*65536)/256;
        i4 = value2 % 256;
        tailer.write(i1);
        tailer.write(i2);
        tailer.write(i3);
        tailer.write(i4);
        
        tailingBytesCount += 8;
    }

    private void writeTagResolutionUnit(OutputStream result, int value) throws IOException
    {
        // TAG-ID 128
        result.write(1);
        result.write(40);
        
        // TAG-TYPE SHORT=3
        result.write(0);
        result.write(3);
        
        // TAG-LENGTH = 1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        
        // TAG-VALUE
        result.write(value/256);
        result.write(value%256);
        result.write(0);// SHORT=0
        result.write(0);// SHORT=0
        
    }

    private void writeTagOrientation(OutputStream result, int value) throws IOException
    {
        // TAG-ID 112
        result.write(1);
        result.write(18);
        
        // TAG-TYPE SHORT = 3
        result.write(0);
        result.write(3);
        

        // TAG-LENGTH=1
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(1);
        
        // TAG-VALUE
        result.write(value / 256);
        result.write(value % 256);
        result.write(0);// SHORT=0
        result.write(0);// SHORT=0
        
    }

    private void writeTagTailer(OutputStream result) throws IOException
    {
        // END OF TAG-DICTIONARY
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(0);
        
        // TAILER WITH VALUES OF RATIONALFIELD's
        result.write(tailer.toByteArray());
    }

    private void writeTagSoftware(OutputStream result, byte[] text) throws IOException
    {
        // TAG-ID 131
        result.write(1);
        result.write(49);
        
        // TAG-TYPE ASCII=2
        result.write(0);
        result.write(2);
        

        // TAG-LENGTH=id.length+1
        result.write(0);
        result.write(0);
        result.write((text.length + 1) / 256);
        result.write((text.length + 1) % 256);
        
        // TAG-VALUE
        int valueOffset = offset + 6 + 12 * TAG_COUNT + tailer.size();
        int i1 = valueOffset/16777216;//=value/(256*256*256)
        int i2 = (valueOffset-i1*16777216)/65536;
        int i3 = (valueOffset-i1*16777216-i2*65536)/256;
        int i4 = valueOffset % 256;
        result.write(i1);                
        result.write(i2);
        result.write(i3);        
        result.write(i4);        
        

        tailer.write(text);
        tailer.write(0);
        tailingBytesCount += text.length + 1;
    }

    private void writeTagDateTime(OutputStream result, Date date) throws IOException
    {
        // TAG-ID 132
        result.write(1);
        result.write(50);
        

        // TAG-TYPE ASCII=2
        result.write(0);
        result.write(2);
        

        // TAG-LENGTH=20
        result.write(0);
        result.write(0);
        result.write(0);
        result.write(20);
    

        // TAG-VALUE
        int valueOffset = offset + 6 + 12 * TAG_COUNT + tailer.size();
        int i1 = valueOffset/16777216;//=value/(256*256*256)
        int i2 = (valueOffset-i1*16777216)/65536;
        int i3 = (valueOffset-i1*16777216-i2*65536)/256;
        int i4 = valueOffset % 256;
        result.write(i1);                
        result.write(i2);
        result.write(i3);        
        result.write(i4);        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        String datetime = sdf.format(date);
        tailer.write(datetime.getBytes());
        tailer.write(0);

        tailingBytesCount += 20;
    }

    /**
     * This will encode some data.
     * 
     * @param rawData
     *            The raw data to encode.
     * @param result
     *            The place to write to encoded results to.
     * @param options
     *            The options to use to encode the data.
     * 
     * @throws IOException
     *             If there is an error compressing the stream.
     */
    public void encode(InputStream rawData, OutputStream result, COSDictionary options) throws IOException
    {
        System.err.println("Warning: CCITTFaxDecode.encode is not implemented yet, skipping this stream.");
    }
}