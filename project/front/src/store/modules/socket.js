import sc from '@/utils/websocket/socket.js'
import dic from '@/const.js'
import SockJS from "sockjs-client"
import Stomp from "stompjs"
const ws = {
  state: {
  	wsMsg: null ,
  	socket:null ,
  	stompClient:null
  },
  mutations: {
  },
  actions: {
  	WebSocketInit: (data) => {
  		console.log(dic.log_websocket+"create websocket")
  		var ret =  new sc({url:'ws://localhost:8080/myHandler'})
  		ret.onopen((event)=>{
  			ws.state.socket.send("connected")
  			console.log("connected")
  		})
  		ret.onmessage((data)=>{
  		  ws.state.wsMsg = data
  		  console.log('receive:'+data)
  		},true)
  		ws.state.socket = ret 	
   	},
   	SockjsInit: () => {
   		var socket = new SockJS('http://localhost:8080/gs-guide-websocket');
      	ws.state.stompClient = Stomp.over(socket);
      	ws.state.stompClient.connect({}, function (frame) {
          	console.log('Connected: ' + frame);
         		ws.state.stompClient.subscribe('/topic/greetings', function (greeting) {
              var t = JSON.parse(greeting.body)
         			ws.state.wsMsg = t
         		});
      	});
    },
    SockjsSend: ({ commit }, data) => {
    	ws.state.stompClient.send("/app/hello", {}, data);
    	console.log("send :" + data)
    },
    SockjsClose: () => {
        if (ws.state.stompClient !== null) {
        	ws.state.stompClient.disconnect();
    	}
    	console.log("Disconnected");
    }
  }
};

export default ws
