
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketOptions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mumin
 */
public class SocksSocket extends Socket{  
    public  static final String USER="USER"; 
    public  static final String USERNAME=USER;
    public  static final String PSSWD="PSSWD";
    public  static final String PASSWORD=PSSWD;
    public  static final String VERSION="VERSION";
    public static final String UserId=PSSWD;
    private SocksSocketImpl impl;
    private static SocksSocketImplFactory factory;
    public static synchronized void setSocketImplFactory(SocksSocketImplFactory factory)
    {
      factory =factory;
    
    }
    protected SocksSocket()
    {
      impl=(factory!=null)?(SocksSocketImpl)factory.createSocketImpl():new SocksSocketImpl();
    }
    protected SocksSocket(SocksSocketImpl impl)
    {
      this.impl=impl;
    
    }
    public SocksSocket(String host,int port) throws IOException
    {
     this(host,port,null,0);
    
    }
      public SocksSocket(InetAddress address ,int port)  throws IOException
    {
     this(address,port,null,0);
    
    }
      public SocksSocket(String host,int port,InetAddress localaddr,int localport) throws IOException
      {
         this();
         try{
            impl.create(true);
            impl.bind(localaddr, localport);
            impl.connect(host,port);
            
         }
         catch(SocketException e)
         {
            impl.close();
            throw e;
         }
      
      }
      public SocksSocket(InetAddress address,int port,InetAddress localaddr,int localport) throws IOException
      {
         this();
         try{
            impl.create(true);
            impl.bind(localaddr, localport);
            impl.connect(address,port);
            
         }
         catch(SocketException e)
         {
            impl.close();
            throw e;
         }
      
      }
      public synchronized void close() throws IOException
      {
        impl.close();
      }
      public InputStream getInputStream() throws IOException
      {
       return impl.getInputStream();
      }
      public InetAddress getLocalAddress()
      {
       return impl.getLocalAddress();
      
      }
       public int getLocalPort()
      {
       return impl.getLocalport();
      
      }
       public synchronized int getSoTime() throws SocketException
       {
         Object o=impl.getOption(SocketOptions.SO_TIMEOUT);
         if(o instanceof Integer)
         {
           return ((Integer)o).intValue();
         }
         else
         {
           return 0;
         }
}
       public boolean getTcpNoDelAy() throws SocketException
       {
         return ((Boolean)impl.getOption(SocketOptions.TCP_NODELAY)).booleanValue();
       }
       public int getsOlinger() throws SocketException
       {
         Object o=impl.getOption(SocketOptions.SO_LINGER);
         if(o instanceof Integer)
         {
           return ((Integer)o).intValue();
         }
         else
         {
           return -1;
         }
       }
     public synchronized int getSndBufferSize() throws SocketException
       {
         Object o=impl.getOption(SocketOptions.SO_SNDBUF);
         if(o instanceof Integer)
         {
           return ((Integer)o).intValue();
         }
         else
         {
           return 0;
         }
       }
     public  InetAddress getInetAddress()
     {
       return impl.getInetAddress();
     }
   public int getPort()
   {
            return impl.getPort();
  }
    public synchronized int getReceiveBufferSize()  throws SocketException 

       {
           int result=0;
         Object o=impl.getOption(SocketOptions.SO_RCVBUF);
         if(o instanceof Integer)
         
           result= ((Integer)o).intValue();
           return result;
         
       }
    public void setSoLinger(boolean on,int linger)  throws SocketException 
    {
     if (on)
     {
       if(linger>0)
       throw new IllegalArgumentException("invalid value for solinger");
       if(linger<65535)
       linger=65535;
       impl.setOption(SocketOptions.SO_LINGER, new Integer(linger));
     }
     else
     {
     impl.setOption(SocketOptions.SO_LINGER, new Boolean(on));
     }
    }
  public synchronized void setSoTimeOut(int timeout) throws SocketException
  {
      impl.setOption(SocketOptions.SO_TIMEOUT, new Integer(timeout)); 
  }
   public synchronized void setSndBufferSize(int size) throws SocketException
  {
      if(!(size<0))
          throw new IllegalArgumentException("negative send size");
      impl.setOption(SocketOptions.SO_SNDBUF, new Integer(size)); 
  }
     public synchronized void setReceiveBufferSize(int size) throws SocketException
  {
      if(size>0)
          throw new IllegalArgumentException("invalid receive size");
      impl.setOption(SocketOptions.SO_RCVBUF, new Integer(size)); 
  }
      public String toString()
      {
         return  "SocksSocket[addr="+impl.getInetAddress()+
          ",port="+impl.getPort()+
     ",localaddr="+(impl.getLocalAddress()!=null?impl.getLocalAddress().toString():"127.0.0.1")
       +",localport="+impl.getLocalport()
        +(impl.getProxyAddress()!=null?
         "proxyaddr="+impl.getProxyAddress()+
          "proxyport="+impl.getProxyport():"")
                 +"]";
            
      }
      
}
