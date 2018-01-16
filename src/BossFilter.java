import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BossFilter extends FilterInputStream {

	BossFilter(InputStream in) { super(in); }

	// Get a value from the array 'buffer' at the given 'position'
	// and convert it into short big-endian format
	public short getSample(byte[] buffer, int position)
	{
		return (short) (((buffer[position+1] & 0xff) << 8) |
					     (buffer[position] & 0xff));
	}

	// Set a short value 'sample' in the array 'buffer' at the
	// given 'position' in little-endian format
	public void setSample(byte[] buffer, int position, short sample)
	{
		buffer[position] = (byte)(sample & 0xFF);
		buffer[position+1] = (byte)((sample >> 8) & 0xFF);
	}

	public int read(byte [] sample, int offset, int length) throws IOException
	{
		// Get the number of bytes in the data stream
		int bytesRead = super.read(sample,offset,length);
		// Create a new buffer the same size as the old buffer
		byte[] buffer = new byte [bytesRead];
		byte[] revBuffer = new byte [bytesRead];
		short amp;
		int	p;

		// Copy the sample to the buffer
		for (p =0; p < bytesRead; p++){ buffer[p] = sample[p]; revBuffer[p] = sample[p];}
		
		
		//	Loop through the sample 2 bytes at a time
		for (p=0; p<bytesRead; p = p + 4)
		{
			
			amp = getSample(buffer, p);
			amp = (short) (amp * 4);
			setSample(sample,p,amp);
		}
		
		return length;
	}
}
