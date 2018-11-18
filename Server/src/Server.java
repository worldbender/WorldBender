class Server {

    public static void main(String args[]) throws Exception {

        TcpServer tcpServer = new TcpServer();
        tcpServer.start();

        UdpServer udpServer = new UdpServer();
        udpServer.start();

    }
}