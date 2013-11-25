



public class WebMiddleMan implements TcpSocketEventListener {
    private TcpSocket socket;
    private int tcpPort=10002;
    private int rtpPort=10003;
    WebUI uiRef;
    VoiceChat voiceChat;
    
    int status=0;   //0=idle 1=active
    
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
        uiRef.appendLog("<<< "+msg+"\n");
        if(msg.equals("ACCEPTED")){
            uiRef.appendMsg("The call is accepted\n");
            initVoiceChat();
        }else if(msg.equals("REFUSED")){
            uiRef.appendMsg("The call is refused\n");
        }else if(msg.equals("BYE")){
            uiRef.appendMsg("The call is ended\n");
            closeVoiceChat();
        }else if(msg.contains("INVITE FROM")){
            String[] split = msg.split("\\s+");
            uiRef.called(split[2]);
        }
    }
    
    public void acceptCall(){
        sendRaw("ACCEPT");
    }
    
    public void refuseCall(){
        sendRaw("REFUSE");
    }
    
    private void initVoiceChat(){
        
        System.out.println("me");
        if(voiceChat==null){
            voiceChat = new VoiceChat();
        }
        voiceChat.init("192.168.2.5", rtpPort); //need improve
        voiceChat.start();
        status=1;
    }
    
    public void call(String ip,int port){
        sendRaw(String.format("CALL %s %d",ip,port));
        uiRef.appendMsg("Calling "+String.format("%s:%d",ip,port)+"\n");
    }
    
    private void closeVoiceChat(){
        voiceChat.close();
        System.out.println("Call close");
        status=0;
    }
    
    public void logout(){
        
        sendRaw("LOGOUT");
    }
    
    public void sendRaw(String msg){
        uiRef.appendLog(">>> "+msg+"\n");
        socket.send(msg);
    }
    
    public void closeCall(){
        
        if(status==1){
            sendRaw("CLOSE");
            closeVoiceChat();
        }else{
            sendRaw("CANCEL");
        }
    }
    
}
