



public class WebMiddleMan implements TcpSocketEventListener {
    private TcpSocket socket;
    private int tcpPort=10002;
    public void start(){
        socket = new TcpSocket();
        socket.registerEventListener(this);
        socket.connect("localhost", 10002);
    }

    @Override
    public void onAccept() {

    }

    @Override
    public void onConnect() {
        
    }

    @Override
    public void onReceive(String msg) {
        
    }
    
}
