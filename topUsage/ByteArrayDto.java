package fe.cmn.data;

import cson.core.CsonPojo;

public class ByteArrayDto extends CsonPojo
{
    private static final long serialVersionUID = 4705889979615893947L;
    
    byte[] data;

    public ByteArrayDto()
    {
        
    }
    
    public ByteArrayDto(byte[] data)
    {
        this.data = data;
    }
    
    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

}
