



public class WebMiddleMan implements TcpSocketEventListener {
    private TcpSocket socket;
    private int tcpPort=10002;
    private int rtpPort=10003;
    WebUI uiRef;
    VoiceChat voiceChat;
    
    int status;
    
    public WebMiddleMan(WebUI ref){
        uiRef = ref;
    }
    
    public void start(){
        socket = new TcpSocket();
        socket.registerEventListener(this);
        socket.connect("192.168.2.5", tcpPort); //need improve
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
        if(msg.equals("ACCEPTED")){
            uiRef.appendMsg("The call is accepted\n");
            initVoiceChat();
        }else if(msg.equals("REFUSED")){
            uiRef.appendMsg("The call is refused\n");
        }
    }
    
    private void initVoiceChat(){
        System.out.println("me");
            if(voiceChat==null){
                voiceChat = new VoiceChat();
            }
            voiceChat.init("192.168.2.5", rtpPort); //need improve
            voiceChat.start();
    }
    
    public void call(String ip,int port){
        socket.send(String.format("CALL %s %d",ip,port));
        uiRef.appendMsg("Calling "+String.format("%s:%d",ip,port)+"\n");
    }
    
    public void send(String msg){
        socket.send(msg);
    }
    
    public void closeCall(){
        socket.send("CANCEL");
    }
    
}
