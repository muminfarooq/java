
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketImplFactory;
import java.net.UnknownHostException;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mumin-PC
 */
class SocksSocketImplFactory implements SocketImplFactory {
    private boolean stream=true;
    Properties properties=null;
    InetAddress proxyaddress;
    int proxyport=SocksSocketConstant.SOCKS_PORT;
        SocksSocketImplFactory()
        {
        
        }
          SocksSocketImplFactory(boolean stream)
        {
           this.stream=stream;
        }
            SocksSocketImplFactory(String proxyhost,int proxyport) throws UnknownHostException
        {
           this(proxyhost,proxyport,null,true);
        }
       SocksSocketImplFactory(String proxyhost,int proxyport,boolean stream) throws UnknownHostException
        {
           this(proxyhost,proxyport,null,stream);
        }
       SocksSocketImplFactory(String proxyhost,int proxyport,boolean stream,Properties properties) throws UnknownHostException
        {
           this(proxyhost,proxyport,properties,true);
        }
       SocksSocketImplFactory(String proxyhost,int proxyport,Properties properties,boolean stream) throws UnknownHostException
        {
           this(proxyhost==null?null:InetAddress.getByName(proxyhost),proxyport,properties,true);
        }
          SocksSocketImplFactory(InetAddress proxyaddress,int proxyport)
        {
           this(proxyaddress,proxyport,null,true);
        }
            SocksSocketImplFactory(InetAddress proxyaddress,int proxyport,boolean stream)
        {
           this(proxyaddress,proxyport,null,stream);
        }
              SocksSocketImplFactory(InetAddress proxyaddress,int proxyport,Properties properties)
        {
           this(proxyaddress,proxyport,properties,true);
        }
                SocksSocketImplFactory(InetAddress proxyaddress,int proxyport,Properties properties,boolean stream)
        {
          this.proxyaddress=proxyaddress;
          this.proxyport=proxyport;
          this.properties=properties;
        }
               

    @Override
    public SocketImpl createSocketImpl() {
            return new SocksSocketImpl(proxyaddress,proxyport,properties,stream) ;
                    
}
}