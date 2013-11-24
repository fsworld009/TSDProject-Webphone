



public class WebMiddleMan implements TcpSocketEventListener {
    private TcpSocket socket;
    private int tcpPort=10002;
    WebUI uiRef;
    
    int status;
    
    public WebMiddleMan(WebUI ref){
        uiRef = ref;
    }
    
    public void start(){
        socket = new TcpSocket();
        socket.registerEventListener(this);
        socket.connect("localhost", 10002); //need improve
    }

    @Override
    public void onAccept() {

    }

    @Override
    public void onConnect() {
        uiRef.appendMsg("Connected to remote server\n");
    }

    @Override
    public void onReceive(String msg) {
        
    }
    
    public void call(String ip,int port){
        socket.send(String.format("CALL %s %d",ip,port));
        uiRef.appendMsg("Calling "+String.format("%s:%d",ip,port));
    }
    
    public void send(String msg){
        socket.send(msg);
    }
    
    public void closeCall(){
        
    }
    
}
