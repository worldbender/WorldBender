class Server {

    public static void main(String args[]) throws Exception {

        TCPServer tcpServer = new TCPServer();
        tcpServer.start();

        UDPServer udpServer = new UDPServer();
        udpServer.start();

    }
}