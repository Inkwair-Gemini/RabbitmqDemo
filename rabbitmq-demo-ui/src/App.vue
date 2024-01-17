<script>
import Stomp from 'stompjs'
import axios from "axios"
export default {
  name: 'App',
  data () {
    return {
      clientId: "0001",
      client: null,
      announcements:[],
      news:[]
    }
  },
  created() {
  //初始化连接
      this.connect();
  },
  methods: {
    //webSocket连接成功后回调函数
    onConnected() {
        this.client.subscribe(
          `/queue/announcement${this.clientId}`,
          frame =>{
            const announcement = JSON.parse(frame.body);
            this.announcements.push(announcement)
          }
        );
        this.client.subscribe(
          `/queue/news${this.clientId}`,
          frame=>{
            const news = JSON.parse(frame.body)
            if(news.receiver === this.clientId){
              this.news.push(news)
            }
          },
        );
    },
    connect() {
        this.client= Stomp.client("ws://localhost:15674/ws")
        var headers = {
            "login": "guest",
            "passcode": "guest",
        };
        //创建连接,放入连接成功回调函数
        this.client.connect(headers, this.onConnected);
    },
    sendMessage(){
      const news = {
        sender: this.clientId,
        receiver: "0002",
        context: "随机消息" + (new Date()).toLocaleString(),
      }
      axios.post("http://localhost:8803/sendMessage",news)
    },
    sendAnnouncement(){
      const announcement = {
        id: Math.floor(Math.random()*10000),
        title: "公告标题",
        time: (new Date()).toLocaleString(),
        context: "临时公告" + Math.floor(Math.random()*10000)
      }
      axios.post("http://localhost:8803/sendAnnouncement",announcement);
    }
  },
  mounted(){
    axios.get(`http://localhost:8803/createAndBindQueue/${this.clientId}`)
    axios.get("http://localhost:8803/getAnnouncements").then(
      response =>{
        this.announcements = response.data
      }
    ).catch(
      error =>{
        console.log(error)
      }
    )
  },
  beforeDestroy(){
    this.client.disconnect(()=>{
      alert("See you next time!")
    })
  }
}
</script>

<template>
  <button @click="sendAnnouncement">发送公告</button>
  <div style="background-color: rgb(167, 224, 205);">
    <span>公告</span>
    <br><br>
    <div v-for="(item, index) in announcements" :key="item.id">
      <span>标题：{{ item.title }}</span><br>
      <span>时间：{{ item.time }}</span><br>
      <span>内容：{{ item.context }}</span>
      <br><br>
    </div>
  </div>
  <br>
  <button @click="sendMessage">发送消息</button>
  <div style="background-color: rgb(149, 149, 193);">
    <span>消息</span>
    <br><br>
    <div v-for="(item, index) in news" :key="index">
      <span>发送者：{{ item.sender }}</span><br>
      <span>接受者：{{ item.receiver }}</span><br>
      <span>内容：{{ item.context }}</span>
      <br><br>
    </div>
  </div>
  <br><br>
</template>
