/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mumin-PC
 */
public interface SocksSocketConstant {
     public static final int SOCKS_PORT=1080;
    public static final int SOCKS_VERSION_4A=4;
    public static final int SOCKS_VERSION_5=5;
      
      
         public static final int COMMAND_CONNECT=1;
         public static final int COMMAND_BIND=2;
         public static final int COMMAND_UDP_ASSOCIATE=3;
           
          public static final int NO_AUTHENTICATION_REQUIRED=0;
          public static final int GSSAPI=1;
          public static final int USERNAME_AND_PASSWORD=2;
          public static final int CHAP=3;
          public static final int NO_ACCEPTABLE_METHODS=0xff;
          
           public static final int IP_V4=1;
           public static final int DOMAINNAME=3;
           public static final int IP_V6=4;
          
            public static final int NULL=0;
            
                    public static final int REQUEST_GRANTED=90;
          public static final int REQUEST_REJECTED=91;
          public static final int REQUEST_REJECTED_NO_IDENTD=92;
          public static final int REQUEST_REJECTED_NO_DIFF=93;
          
          
                  public static final int SUCCEDED=0;
          public static final int FAILURE=1;
          public static final int NOT_ALLOWED=2;
          public static final int NETWORK_UNREACHABLE=3;
          
          
                  public static final int HOST_UNREACHABLE=4;
          public static final int REFUSED=5;
          public static final int TTL_EXPIRED=6;
          public static final int COMMAND_NOT_SUPPORTED=7;
           
          
          
          public static final int ADDRESS_TYPE_NOT_SUPPORTED=8;
          public static final int INVALID_ADDRESS=9;
          
          
          public static final int INTERFACE_REQUEST=1;
          public static final int USECLIENTSPORT=4;
}
