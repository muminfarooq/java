
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;
import static java.net.SocketOptions.SO_BINDADDR;
import static java.net.SocketOptions.SO_LINGER;
import static java.net.SocketOptions.SO_RCVBUF;
import static java.net.SocketOptions.SO_SNDBUF;
import static java.net.SocketOptions.SO_TIMEOUT;
import static java.net.SocketOptions.TCP_NODELAY;
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
public class SocksSocketImpl implements SocksSocketConstant {
    
    boolean stream=true;
    private int port;
    Socket clientsocket=null;
    InetAddress proxyAddress=null;
    private int proxyPort=SOCKS_PORT;
    private InetAddress localAddress=null;
    private int localport;
   private ByteArrayOutputStream outputbuffer;
    Properties properties;
    private int version=SOCKS_VERSION_5;
    private byte[]username=null;
    private byte[]password=null;
    private byte[]remotePort=null;
    private byte[]remoteHost=null;
    private byte[]remoteAddress=null;
    private int remoteAddresstype;
    private int serverBoundAddressType;
    private InetAddress serverBoundAddress=null;
    private int serverBoundPort=0;
    private int remoteBoundAddressType;
    private byte[] remoteBoundAddress=null;
    private byte[] remoteBoundPort=null;
    private int command=COMMAND_CONNECT;
    private int timeout=15000;
    private boolean tcpNoDelay=false;
    private int soLinger=-1;
    private int soSndBuf=0;
    private int soRCVndBuf=0;
    private int IP_MULTICASE_IF=0;
    private DatagramSocket clientDatagramSocket=null;
    private InetAddress address;
       SocksSocketImpl()
       {
          
       
       }
       SocksSocketImpl(boolean stream)
       {
         this.stream=stream;
       }
       SocksSocketImpl(String proxyHost,int proxyport) throws UnknownHostException, UnknownHostException, UnknownHostException  {
         this(InetAddress.getByName(proxyHost),proxyport,null,true);
       }
       SocksSocketImpl(InetAddress proxyAddress,int proxyPort, Properties properties )
       {
         this(proxyAddress,proxyPort,properties,true);
       }
      
  SocksSocketImpl(InetAddress proxyAddress,int proxyPort,Properties properties,boolean straem)
    {
         this.proxyAddress=proxyAddress;
         this.proxyPort=proxyPort;
         this.stream=stream;
         
         if(proxyAddress!=null)
         {
           outputbuffer =new ByteArrayOutputStream(1024);
         }
         if(!stream)
             version=SOCKS_VERSION_5;
          if(properties!=null)
             initProperties(properties);
    }
   private void initProperties(Properties properties)
    {
         String value=properties.getProperty(SocksSocket.USERNAME);
         if(value!=null)username=value.getBytes();
             value=properties.getProperty(SocksSocket.PASSWORD);
              if(value!=null)password=value.getBytes();
         value=properties.getProperty(SocksSocket.VERSION);
          if(value!=null)
          {
            version=Integer.parseInt(value)==SOCKS_VERSION_5?SOCKS_VERSION_5:SOCKS_VERSION_4A;
          }
    }
       protected void create(boolean Stream)
       {
       
       }
 protected void bind(InetAddress address,int port)
       {
         this.localAddress=address;
         this.localport=port;
       }
  protected void connect (String host,int port) throws UnknownHostException, IOException
       {
         if(proxyAddress==null)
         {
           clientsocket=new Socket(InetAddress.getByName(host),port,localAddress,localport);
           localAddress=clientsocket.getLocalAddress();
         }
         else
         {
          initRemoteHost(host,port);
          doSocksConnect();
         }
       }
       
protected void sendUrgentdata(int data)
   {
         
   }
 protected InetAddress getProxyAddress()
 {
  return proxyAddress;
 }
 protected int getProxyport()
  {
    return proxyPort;
  }
 public InetAddress getLocalAddress() 
  {
    return localAddress;
  }
 public int getLocalport()
  {
    return localport;
  }
  public int getPort()
 {
    return port;
 }
  protected InetAddress getInetAddress()
  {
    return address;
  }
   
  protected void connect()
  {
       
  }
  protected void connect(int a)
   {
       
   }
  protected  synchronized InputStream getInputStream () throws IOException
  {
      if(clientsocket!=null)
    {
        return clientsocket.getInputStream();
        
    }
    else 
    {
        throw new IOException ("tried to acces an unconnected or closed comnnection");
        
    }  
  }
  protected  synchronized  OutputStream getOutputStream() throws IOException
  {
     if(clientsocket!=null)
    {
        return clientsocket.getOutputStream();
        
    }
    else 
    {
        throw new IOException ("tried to acces an unconnected or closed comnnection");
        
    }
       
  }
  protected synchronized int available() throws IOException
  {
     return getInputStream().available();
  }
  private void initRemoteHost(String host,int port) throws UnknownHostException
  {
         this.port=port;
         remotePort=getPort(port);
         remoteAddresstype=IP_V4;
         remoteHost=host.getBytes();
         this.address=InetAddress.getByName(host);
          remoteAddress=getAddress(address);
         switch(version){
             case SOCKS_VERSION_4A:
                 remoteAddress=new byte[4];
                 remoteAddress[0]=remoteAddress[1]=remoteAddress[2]=0;
                 remoteAddress[3]=(byte)0xFF;
                 break;
                  case SOCKS_VERSION_5:
                 remoteAddress=remoteHost;
                 
                 remoteAddresstype=DOMAINNAME;
                 
                 remoteAddress[3]=(byte)0xFF;
                 break;
         
         }
  }
   
  private void initRemoteHost(InetAddress address,int port)
     {
        this.address=address;
        this.port=port;
        remoteAddress=InetAddress.getByName(Host);
        remoteAddresstype=IP_V4;
        remotePort=getPort(port);
     
     }
private static final byte[]getAddress(InetAddress ia)
 {
       if(ia==null)
        return new byte[4];
       else
           return ia.getAddress();
       
 }
private static final byte[]getAddress(int addresstype,byte[]b,int offset)
{
         int length;
     switch(addresstype)
     {
         case DOMAINNAME:
             length=b[offset];
             offset++;
             break;
         case IP_V4:
             length=4;
             break;
         default:
               length=16;
               break;
             
     }
       byte[] address=new byte [length];
      for(int i=0;i<length;i++,offset++)
          address[i]=b[offset];
                  return address;
       
 }
 private static final byte[]getPort(int p)
  {
         byte[] port=new byte[2];
         port[0]=(byte)((p>>>8)&0xFF);
            port[1]=(byte)(p&0xFF);
            return port;
  }
 private static final byte[]getPort(byte[]b,int offset)
  {
         byte[] port=new byte[2];
         port[0]=b[offset];
            port[1]=b[offset+1];
            return port;
  }
 private static final int getPortValue(int offset,byte[]b)
  {
        int port=b[offset]<0?(short)b[offset]+256:b[offset];
        port<<=8;
        offset++;
        port+=b[offset]<0?(short)b[offset]+256:b[offset];
        return port;
 }
 private static final InetAddress getInetAddressbyte(int addressType,byte[]b,int offset) throws UnknownHostException
  {
         String host = null;
         switch(addressType)
         {
             case IP_V4:
                 break;
             case DOMAINNAME:
                 break;
             case IP_V6:
                 break;
                 
           
         }
         
       return InetAddress.getByName(host);
       
  }
  private synchronized void doSocksConnect() throws IOException
 {
         if(remoteAddress==null||remotePort==null)
         {
           throw new IOException ("ERROR");
         }
       for(int i=0;i<5;i++)
       {
           try
           {
            if(stream)
          {
             clientsocket=new Socket(proxyAddress,proxyPort,localAddress,localport);
             localAddress=clientsocket.getLocalAddress();
             localport=clientsocket.getLocalPort();
           
          }
          else
          {
           clientsocket=new Socket(proxyAddress,proxyPort,localAddress,0);
          }
           clientsocket.setSoTimeout(timeout);
         break;
           }
           catch(IOException e)
           {
              if(i<4)
              {
                  try {
                      Thread.sleep(2000);
                      } 
                     catch (InterruptedException ex) 
                     {
                     }
              }
              else
              {
               close();
               throw e;
              }
           
           }
       }
       switch(version)
       {
           case SOCKS_VERSION_4A:
               break;
           case SOCKS_VERSION_5:
               break;
       
       }
  }
    private void request_of_V4A(int command,byte[] destinationaddress,byte[]destinationport,byte[]destinationhost) throws IOException
    {
       outputbuffer.reset();
        outputbuffer.write(SOCKS_VERSION_4A);
        outputbuffer.write(command);
        outputbuffer.write(destinationport);
        outputbuffer.write(destinationaddress);
        if(password!=null)
            outputbuffer.write(password);
        outputbuffer.write(NULL);
        if(destinationaddress[0]==0)
        {
            outputbuffer.write(destinationhost);
            outputbuffer.write(NULL);
        }
        OutputStream out=clientsocket.getOutputStream();
        outputbuffer.writeTo(out);
        out.flush();

    }
    private void reply_of_V4A(boolean isGetRemoteBoundParameters) throws IOException
    {
       int count;
    String errorMessage=null;
    byte[] buffer=new byte[8];
    InputStream in=clientsocket.getInputStream();
    while((count=in.read(buffer))>=0)
    {
        if(count==0)
            continue;
        else if(count<8||buffer[0]!=NULL)
            errorMessage="failed to parse the malformed reply from the socks server";
        else
        {
            switch(buffer[1])
                    {
                case REQUEST_GRANTED:
                    if(isGetRemoteBoundParameters)
                    {
                        remoteBoundPort=getPort(buffer,2);
                        remoteBoundAddress=getAddress(IP_V4,buffer,4);
                    }
                    else
                    {
                        serverBoundPort=getPortValue(buffer,2);
                        serverBoundAddress=getInetAddress(IP_V4,buffer,4);
                    }
                    break;
                case REQUEST_REJECTED:
                    errorMessage="request rejected or failed";
                    break;
                case REQUEST_REJECTED_NO_IDENTD:
                    errorMessage="request rejected because SOCKES server cannot connect to identd on the client";
                    break;
                case REQUEST_REJECTED_NO_DIFF:
                    errorMessage="request rejected because the client program and identd report different user-ids";
                    break;
            }
        }
        if(errorMessage!=null)
        {
            close();
            throw new IOException("("+proxyAddress.getHostAddress()+":"+proxyPort+")"+errorMessage);
        }
        break;
    }
    if(command!=COMMAND_BIND)return;
    {
    }

    }
    private String Username_AND_Password_Authentication_OF_V5() throws IOException
    {
        String errorMessage=null;
         outputbuffer.reset();
          outputbuffer.write(0x01);
          if(username==null)
               outputbuffer.write(0x0);
          else
          {
            outputbuffer.write(username.length);
             outputbuffer.write(username);
          }
             if(password==null)
               outputbuffer.write(0x0);
          else
          {
            outputbuffer.write(password.length);
             outputbuffer.write(password);
          }
             OutputStream out=clientsocket.getOutputStream();
               outputbuffer.writeTo(out);
               out.flush();
               int count;
        
        byte[] buffer=new byte[2];
        InputStream in=clientsocket.getInputStream();
        while((count=in.read(buffer))>=0)
        {
          if (count==0)
              continue;
        
        else if(count<2||buffer[0]!=0x01)
               errorMessage="faild to parse the reply from socks server";
        else if(buffer[1]!=0x0)
        {
                  errorMessage="faild to parse the reply from socks server"; 
        }
             if (errorMessage!=null)
        {
       
            close();
            throw new IOException("("+proxyAddress.getHostAddress()+":"+proxyPort+")"+errorMessage);
        }
                
        }
       return errorMessage;
    }
    private String methodSelection_of_v5() throws IOException
    {
        ////aise hi likha h for error
        outputbuffer.reset();
        outputbuffer.write(SOCKS_VERSION_5);
        outputbuffer.write(2);
        outputbuffer.write(NO_AUTHENTICATION_REQUIRED);
         outputbuffer.write(GSSAPI);
          outputbuffer.write(USERNAME_AND_PASSWORD);
           outputbuffer.write(CHAP);
           OutputStream out=clientsocket.getOutputStream();
            outputbuffer.writeTo(out);
            out.flush();
      int method;
      if(username!=null||password!=null)
      {
        method=USERNAME_AND_PASSWORD;
      }
      else
      {
        method=NO_AUTHENTICATION_REQUIRED;
        int count;
        String errorMessage=null;
        byte[] buffer=new byte[2];
        InputStream in=clientsocket.getInputStream();
        while((count=in.read(buffer))>=0)
        {
          if (count==0)
              continue;
        
        else if(count<2||buffer[0]!=SOCKS_VERSION_5)
               errorMessage="faild to parse the reply from socks server";
        else
                {
                  switch(buffer[i])
                {
                      case NO_AUTHENTICATION_REQUIRED:
                                  break;
                      case GSSAPI:
                            errorMessage="Gssapi negotiation hasnt been completed";
                               break;
                     case USERNAME_AND_PASSWORD:
                               errorMessage="username and password auth of v5";
                                 break;
                       case CHAP:
                               errorMessage="CHAP negotiation hasnt been completed";
                                 break;
                       case (byte)NO_ACCEPTABLE_METHODS:
                                  errorMessage="no acceptable methods negotiation ";
                                 break;
                       default:
                                errorMessage="default neggotiation methods ";
                     
                           break;
                                 
                }
                }
        if (errorMessage!=null)
        {
       
            close();
            throw new IOException("("+proxyAddress.getHostAddress()+":"+proxyPort+")"+errorMessage);
        }
        break;
                }
                
                }
      return ""; 
      
    }
    private void request_of_v5(int command,int flag,int addressType,byte[]destinationAddress,byte[] destinationPort) throws IOException
    {
      outputbuffer.reset();;
      outputbuffer.write(SOCKS_VERSION_5);
      outputbuffer.write(command);
      outputbuffer.write(flag);
      if(command!=COMMAND_UDP_ASSOCIATE)
      {
        outputbuffer.write(addressType);
        if(addressType==DOMAINNAME)
        {
            outputbuffer.write(destinationAddress.length);
        outputbuffer.write(destinationAddress);
        outputbuffer.write(destinationPort);
        }
        else
        {
          outputbuffer.write(IP_V4);
          outputbuffer.write(getAddress(localAddress));
          outputbuffer.write(getPort(localport));

        }
        OutputStream out=clientsocket.getOutputStream();
      
      outputbuffer.writeTo(out);
      out.flush();
    
      }
    }
    private void reply_of_v5(int command,boolean isgetRemoteBoundParameteres) throws IOException
    {
     int count;
     String errorMessage=null;
     byte[] buffer=new byte[64];
     InputStream in=clientsocket.getInputStream();
     while((count=in.read(buffer))>=0)
     {
       if(count==0)
            continue;
       else if(count<8||buffer[0]!=SOCKS_VERSION_5)
           errorMessage="failed to parse reply from the socket";
       else
       {
       switch(buffer[1])
       {
         
          case SUCCEDED:
         
	int tempBoundAddressType=buffer[3];
	int offset=4;
	InetAddress tempBoundAddressType=getInetAddress(tempBoundAddressType,buffer,offset);
	switch(tempBoundAddressType)
     {
	case DOMAINNAME:
	offset+=(buffer[offset+1]<0?buffer[offset+1]+256:buffer[offset+1])+1;
		break;
	case IP_V4:
	offset+=4;
	break;
	case IP_V6:
	default:
	offset+=6;
        }
int tempBoundPort=getPortValue(buffer,offset);
if(isGetRemoteBoundParamaters)
{
	remoteBoundAddressType=tempBoundAddressType;
	remoteBoundAddress=tempBoundAddress;
                   remoteBoundPort=tempBoundPort;
}
else
{
serverBoundAddressType=tempBoundAddressType;
serverBoundAddress=tempBoundAddress;
remoteBoundPort=tempBoundPort;
}
break;

case FAILURE:
errorMessage="general SOCKS server failure";
break;
case NOT_ALLOWED:
errorMessage="connection not allowed by ruleset";
break;
case NETWORK_UNREACHABLE:
errorMessage="Network unreachable";
break;
case HOST_UNREACHABLE:
errorMessage="Host unreachable";
break;
case REFUSED:
errorMessage="Connection refused";
break;
case TTL_EXPIRED:
errorMessage="TTL expired";
break;
case COMMAND_NOT_SUPPORTED:
errorMessage="Command not supported";
break;
case ADDRESS_TYPE_NOT_SUPPORTED:
errorMessage="Address type not supported";
break;
case INVALID_ADDRESS:
break;
default:
errorMessage="unknown reply code ("+(int)buffer[1]+")";
break;
       }
       }
if(errorMessage!=null)
{
close();
throw new IOException("("+proxyAddress.getHostAddress()+":"+proxyPort+") "+errorMessage);
}
break;
       }
       
       
 }
    
   
    
    private void close() throws IOException
    {
     if(clientsocket!=null)
     {
        try{
          clientsocket.close();
           }
        catch(IOException e){}
        clientsocket=null;
     }
     else if(clientDatagramSocket!=null)
     {
       clientDatagramSocket.close();
       clientDatagramSocket=null;
     }
    }
    protected synchronized void listen(int backlog) throws IOException
    {
       ///khaaali 
    }
      protected synchronized void accept(SocketImpl s) throws IOException
    {
      //khaaali
        
    }
      public String toString()
      {
         return  "SocksSocket[addr="+getInetAddress()+
          ",port="+getPort()+
     ",localaddr="+(getLocalAddress()!=null?getLocalAddress().toString():"127.0.0.1")
       +",localport="+getLocalport()
        +(getProxyAddress()!=null?
         "proxyaddr="+getProxyAddress()+
          "proxyport="+getProxyport():"")
                 +"]";
            
      }
      public void finalize() throws IOException
      {
          close();
      
      }
      public Object getOption(int opt) throws SocketException
      {
           boolean flag=true;
          switch(opt)
        {
          case SO_LINGER:
             
              if(clientsocket!=null)
              soLinger=clientsocket.getSoLinger();
              return new Integer(soLinger);
                 
          case SO_TIMEOUT:
               if(clientsocket!=null)  
              timeout=clientsocket.getSoTimeout();
               return new Integer(timeout);

          case SO_BINDADDR:
               return localAddress;
          case TCP_NODELAY:
            
               if(clientsocket!=null)
               tcpNoDelay=clientsocket.getTcpNoDelay();
               break;
          case SO_SNDBUF: 
             if(clientsocket!=null)
                 soSndBuf=clientsocket.getSendBufferSize();
             return new Integer(soSndBuf);
             
          case SO_RCVBUF:    
                if(clientsocket!=null)
                 soRCVndBuf= clientsocket.getReceiveBufferSize();
                     return new Integer(soRCVndBuf);
 
     
       //  case IP_MULTICASE_IF:
           
           default:   
             throw new SocketException("UNRECOGNIZABLE TCP OPTION "+opt);
       }
        return null;
      
      }
      public void setOption(int opt,Object val) throws SocketException
      {
       boolean flag=true;
       switch(opt)
       {
         case SO_LINGER:
               if((val==null)||(!(val instanceof Integer)&&!(val instanceof Boolean)))
               
             
                 throw new SocketException("bad parameter for option");
                 if (val instanceof Boolean)
                 {
                  flag=false;
                 }
                 if(clientsocket!=null)
                 {
                    if(flag)
                        soLinger=((Integer)val).intValue();
                    else
                        soLinger=((Integer)val).intValue();
                         clientsocket.setSoLinger(flag,soLinger);
                 
                 }
              break;
         case SO_TIMEOUT:
               if(val==null|(!(val instanceof Integer)))
                   throw new SocketException("badd");
                 int t=((Integer)val).intValue();
                 timeout=(t<0)?0:t;
                 if(clientsocket!=null)
                     clientsocket.setSoTimeout(timeout);
                 return;
         case SO_BINDADDR:
               throw new SocketException("cant re-bind socket");
         case TCP_NODELAY:
                 if((val==null)||!((val instanceof Boolean)))
                 throw new SocketException("bad parameter for option");
                 flag=((Boolean)val).booleanValue();
                 tcpNoDelay=flag;
                 if(clientsocket!=null)
                 clientsocket.setTcpNoDelay(tcpNoDelay);
                 break;
         case SO_SNDBUF:
         case SO_RCVBUF:    
                if(val==null||!(val instanceof Integer)&&!(((Integer)val).intValue()>0 ))
                 {
                   throw new SocketException("bad parameter for so_SNDBUF");
                  }
                if(clientsocket!=null)
                {
                  int size=((Integer)val).intValue();
                  if(opt==SO_SNDBUF)
                  {
                     soSndBuf=size;
                     clientsocket.setSendBufferSize(size);
                  }
                  else
                  {
                     soRCVndBuf=size;
                     clientsocket.setReceiveBufferSize(size);
                  
                  }
                }
                break;
         case SO_RESUADDR:
              if(val==null||!(val instanceof Integer))
             {
              throw new SocketException("bad parameter for SO_RESUEADDR");
             }
             break;
         case IP_MULTICASE_IF:
               if(val==null||!(val instanceof InetAddress))
               throw new SocketException("bad parameter for IP_MULTICASE_IF");
               break;
         default:   
             throw new SocketException("UNRECOGNIZABLE TCP OPTION "+opt);
          
       }
               
      
      }
      public void create() throws SocketException
      {
      ///khaaaaliiii
      
      }
      protected void bind(int localport,InetAddress localaddress) throws SocketException
      {
        this.localAddress=localaddress;
        this.localport=localport;
      }
      protected void send(DatagramPacket p) throws IOException, IllegalAccessException
      {
         InetAddress packetAddress=p.getAddress();
         if (address==null&&packetAddress==null)
         {
           throw new IllegalAccessException("Both of remote adddress and socket address are null");
           
         }
          else
             if(packetAddress!=null&& !(packetAddress.equals(address)&&p.getPort()==port))
             {
               connect(packetAddress,p.getPort());
             }
             else if(clientDatagramSocket==null)
                 connect(address,port);
         if(proxyAddress==null)
         {
           clientDatagramSocket.send(p);
           return;
         }
         outputbuffer.reset();
         outputbuffer.write(NULL);
         outputbuffer.write(NULL);
         outputbuffer.write(NULL);
         outputbuffer.write(remoteAddresstype);
         outputbuffer.write(remoteAddress);
          outputbuffer.write(p.getData());
          byte[] data=outputbuffer.toByteArray();
          p=new DatagramPacket(data,data.length,serverBoundAddress,serverBoundPort);
          clientDatagramSocket.send(p);
          
   }
      protected void disconnect()
      {
         if(stream) 
             throw new IllegalAccessError("Illegal called a specified method for datagram socket when using blah blah"); 
         if(clientDatagramSocket!=null)
         {
           this.address=null;
           this.port=1;
           clientDatagramSocket.disconnect();
         }
         else
             initRemoteHost((InetAddress)null,-1);
         
      }
      protected void peek() throws IOException
      {
        throw new IOException("NOT IMPLEMENTED"); 
        
      }
      protected void receive (DatagramPacket p)  throws IOException
      {
       InetAddress packetAddress=p.getAddress();
       if(address== null&&packetAddress==null)
       {
         throw new IllegalArgumentException("Both of remote address and packetaddress aree null");
         
       }
       else if(packetAddress!=null &&!(packetAddress.equals(address)&&p.getPort()==port))
       {
         connect(packetAddress,p.getPort());
         
       }
       else if(clientDatagramSocket==null)
           connect(address,port);
             if(proxyAddress==null) 
             {
                 clientDatagramSocket.receive(p);
                 return;
             }
             p.setAddress(serverBoundAddress);
             p.setPort(serverBoundPort);
             clientDatagramSocket.receive(p);
             byte[] data=p.getData();
             if(data.length<10||data[0]!=NULL||data[1]!=NULL||data[2]!=NULL||data[3]!=IP_V4)
                 throw new IOException ("unknown socks diagram packet:"+new String(data));
             InetAddress sourceInetAddress =getInetAddress(data[3],data[4]);
             if(!sourceInetAddress.equals(address)||getPortValue(data,8)!=port)
             {
               receive(p);
               return;
             }
             byte[] ba=new byte[data.length-10];
             System.arraycopy(data,10,ba,0,ba.length);
             p.setData(ba);
             p.setAddress(address);
             p.setPort(port);
      }
      protected void setTTL(byte ttl ) throws IOException
      {
        throw new IOException("Not implmented deprecated function");
      }
       protected void getTTL(byte ttl ) throws IOException
      {
        throw new IOException("Not implmented deprecated function");
      }
       protected void getTimeToLive() throws IOException
       {
         throw new IOException("NOt implemented");
       
       }
         protected void join(InetAddress inetaddr) throws IOException
       {
         throw new IOException("NOt implemented");
       
       }
       protected void leave(InetAddress inetaddr) throws IOException
       {
         throw new IOException("NOt implemented");
       
       }
       protected FileDescriptor getFileDescriptor() throws IOException
       {
        return null;
       
       }
}
