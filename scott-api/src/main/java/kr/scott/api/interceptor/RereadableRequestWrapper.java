package kr.scott.api.interceptor;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 *
 *
 * Created by yhlee on 2017-08-21.
 *
 * @author Y.Han Lee
 * @version
 * @see
 */
public class RereadableRequestWrapper extends HttpServletRequestWrapper
{

    private final String body;

    public RereadableRequestWrapper(HttpServletRequest request) throws IOException
    {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;

        try{
            inputStream =  request.getInputStream();

            if(inputStream != null){
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while((bytesRead = bufferedReader.read(charBuffer)) > 0){
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
            else{
                stringBuilder.append("");
            }
        }
        catch(IOException ex) {
            throw ex;
        }
        finally {
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }
                catch(IOException ex){
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
    }


    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new CustomServletInputStream(body.getBytes());
        return servletInputStream;
    }


    @Override
    public BufferedReader getReader() throws IOException
    {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }


    public String getBody()
    {
        return this.body;
    }



    private static class CustomServletInputStream extends ServletInputStream
    {

        private ByteArrayInputStream buffer;

        public CustomServletInputStream(byte[] contents)
        {
            this.buffer = new ByteArrayInputStream(contents);
        }

        @Override
        public int read() throws IOException
        {
            return buffer.read();
        }

        @Override
        public boolean isFinished()
        {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady()
        {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener)
        {
            throw new RuntimeException("Not implemented");
        }
    }

}
