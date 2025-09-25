<template>
    <article>
      <section>
        <h2> Contact with Marsik</h2>
        <p> You can get in touch with Marsik here: </p><br>
        <div id="kontaktformular">
          <b> Name: </b>
          <input id="kontaktname" name="userName" type="text" placeholder="Your Name"/>
          <b> Your age: <span id="A"></span></b>
          <select id="ageSelect" aria-orientation="horizontal" name="alter" size="10" onchange="document.getElementById('A').innerHTML = this.value;">
             <option v-for="n in 100" :key="n">{{n}}</option>
          </select>
          <b> Your message: </b>
          <textarea id="kontaktnachricht" name="message" placeholder="type in your message..."> </textarea>
          <button id="messageSendBtn" name="messageSubmit" @click="send">Send Message</button>
        </div>
      </section>
      <br>
      <section>
        <h2>Chat with Marsik</h2>
          <div id="chatDiv">
            <div v-for="(item, index) in chatMessages" :key="index">
              <div class="chatMsgStyle" v-if="usersTurn"> <p> {{item}} </p> </div>
            </div>
          </div>
          <div id="createMessagediv">
            <input type="text" v-model="currentMessage" autocomplete="off" placeholder="Type in your message...">
            <button id="chatSend" @click="chatSubmit"><i class="fa fa-paper-plane"></i></button>
          </div>
      </section>
    </article>
</template>

<style scoped>
#kontaktformular
{
  padding: 15px;
  background: linear-gradient(135deg, #ccf241, #23a7ff);
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  text-align: center;
}
b
{
  font-size: 1em;
  color: #fff;
}
#kontaktformular #kontaktname, select
{
  width: 90%;
  font-size: 0.9em;
  padding: 10px;
  margin: 18px 0 14px;
  border-radius: 6px;
  border: none;
  background: rgba(255, 255, 255, 0.8);
}
#kontaktformular #kontaktname::placeholder
{
  color: rgba(0, 0, 0, 0.5);
}
#ageSelect
{
  width: 98%;
  height: 45px;
  outline: none;
  overflow-y: hidden;
  overflow-x: auto;
}
#ageSelect option
{
  display: inline-block;
  margin: 0 2px
}
#kontaktformular textarea
{
  width: 90%;
  height: 70px;
  padding: 7px;
  font-size: 0.9em;
  margin: 14px 0 14px;
  border-radius: 6px;
  background: rgba(255, 255, 255, 0.8);
  resize: none;
  transition: all 0.3s ease;
}
#kontaktformular textarea:focus
{
  background: #fff;
}
#messageSendBtn
{
  padding: 10px;
  font-size: 1em;
  font-weight: bold;
  color: white;
  background: linear-gradient(135deg, #f857a6, #ff5858);
  border-radius: 6px;
  transition: all 0.5s ease;
}
#messageSendBtn:hover
{
  background: linear-gradient(135deg, #ff5858, #f857a6);
  transform: scale(1.05);
}

#chatDiv
{
  height: 400px;
  background-color: #e3e3e3;
  overflow-y: auto;
}
#createMessagediv
{
  background-color: #9e9e9e;
  text-align: center;
  padding: 10px 0;
}
#createMessagediv input
{
  width: 75%;
  border: none;
  padding: 8px 6px 8px 6px;
}
#createMessagediv input:focus
{
  background-color: #d5ffff;
}
#chatSend
{
  padding: 8px 9px 8px 9px;
  color: white;
  background-color: #3fe697;
}
#chatSend:hover
{
  background-color: #03ca6a;
}
.chatMsgStyle
{
  width: 80%;
  border-radius: 50px 50px 50px 0;
  background: #646464;
  overflow-wrap: anywhere;
  padding: 10px;
  margin: 15px auto;
  color: white;
}
</style>

<script>
import {customPopup} from "../../../assets/features.js";
export default {
  name: 'Contact',
  data() {
    return {
      usersTurn: true,
      kontaktname: "",
      alter: "",
      kontaktnachricht: "",
      currentMessage: "",
      responseMessage: "",
      chatMessages: [],
    }
  },
  methods: {
    async send() {
        const kontaktname = document.getElementById('kontaktname').value;
        const alter = document.getElementById("ageSelect").value;
        const kontaktnachricht = document.getElementById('kontaktnachricht').value;
        if (kontaktname === "" ||  alter === "" || kontaktnachricht === "") {
          customPopup("The following message appeared:", "please fill in all fields", false );
          return false;
        }
        else {
          const mailContent = {
          kontaktname: document.getElementById('kontaktname').value,
          alter: document.getElementById("ageSelect").value,
          kontaktnachricht: document.getElementById('kontaktnachricht').value
          };
          try {
            const response = await fetch('http://localhost:8080/message/send', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(mailContent)
            });
            const result = await response.text();
            customPopup("Thank you!", result, false);
          } catch (error) {
            customPopup("An Error occurred", error.toString(), true);
          }
        }
      },
    chatSubmit() {
      this.usersTurn = false;
      this.chatMessages.push(this.currentMessage);
      setTimeout(() => {
        const date = new Date();
        const start = ["Hello","Hey","Here you are again","Hey bro","Hello man","Hello bro"];
        const marsik_name = ["My name is Marsik", "I'am Marsik", "I am Marsik the Kitty", "Find it out", "You still don't know who I am"];
        const how_are_you = ["I am fine","I am not fine right now","I am fine, bro", "I am sad today", "I feel very good today"];
        const plans = ["I am going to go to the toilet","I don't know","Today I gonna do nothing","There are no plans for today","Today I will eat a lot","I will probably sleep","Sleeping","Why do you wanna know that"];
        const hobbies_activities = ["I like sleeping","I don't have hobbies","My favourite one is sleeping","I don't know","What do you do in your free time?"];
        const bye = ["See you later","Okay bye","Bye see you soon","Bye, bro","See you later, bro","bye bye","bye bye, bro"];
        const where = ["I am sitting on my sofa","There is no need to tell you my location","That's a secret","On the sofa","Somewhere","Somewhere on my sofa"];
        const why_busy = ["Because I am busy", "Because I am a Kitty Cat", "Because I need much time to sleep","Because I am busy, man","Write me later", "Write me later, I am busy"];
        const consequences = ["that is what you have to find out","Unfortunately, I can't tell you that","I don't know", "I don't know, bro","I am not able to tell you","ask me again","ask me again, bro","ask somebody different","Something","Nothing","Nothing much",
          "Nothing much, bro", "That is what I also like to know","Something might happen","Something bad"];
        const feelings = ["Nice to hear", "Excellent", "Excellent, bro", "I am glad to hear that", "Good"];
        const advice = ["I don't know","If you want","Try it","Do it if you want","Don't ask me, bro","Don't ask me","Yes"];
        const compliment = ["Nice to hear","Thank you","Thanks","Nice to hear, bro", "You too","Are you sure?","Are you sure, bro?","No I am not","No, bro","Maybe you are but I am not","Nice to hear that, bro"];
        const can = ["Yes I can","No I can't", "Sorry but I can't","No bro","Perhaps","Not now"];
        const how_to = ["No bro, I don't know how to do that","I don't know it, sorry","Maybe your mother knows it","Ask your mother","First, open the door. Then put the key inside your door and after that, yell at your mom"];
        const thanks = ["Ur welcome","You are welcome","You're welcome","you're welcome bro","No Problem","No Problem, bro"];
        const no_talk= ["Write me later I am busy","I don't have time to talk with you","Don't disturb me I don't have time right know"];
        const something = ["I don't understand you","I can't understand","What do you mean?","Can you explain it to me more detailed?","Which language do you speak?","Which language do you speak, bro?","What did you say?",
          "I don't know what you mean","What do you mean?","What are you talking about?","Marsik the Kitty doesn't know what you mean","Oh","Are you sure?"];
        const currentMessageFromUser = this.currentMessage;
        this.currentMessage = "";
        if(currentMessageFromUser.includes("hello") || currentMessageFromUser.includes("Marsik")){
          this.responseMessage = start[Math.floor(Math.random()*start.length)];
        }
        else if (currentMessageFromUser.includes("how are you")){
          this.responseMessage = how_are_you[Math.floor(Math.random()*how_are_you.length)];
        }
        else if (currentMessageFromUser.includes("I am") || currentMessageFromUser.includes("i am")|| currentMessageFromUser.includes("I'm") && currentMessageFromUser.includes("fine")){
          this.responseMessage = feelings[Math.floor(Math.random()*feelings.length)];
        }
        else if(currentMessageFromUser.includes("what is") || currentMessageFromUser.includes("whats") && currentMessageFromUser.includes("name")){
          this.responseMessage = marsik_name[Math.floor(Math.random()*marsik_name.length)];
        }
        else if(currentMessageFromUser.includes("what time")){
          this.responseMessage = date.toLocaleTimeString();
        }
        else if(currentMessageFromUser.includes("+") || currentMessageFromUser.includes("-") || currentMessageFromUser.includes("*") || currentMessageFromUser.includes("/")){
          try {
            this.responseMessage = eval(currentMessageFromUser);
          } catch (error) {
            this.responseMessage = error;
          }
        }
        else if(currentMessageFromUser.includes("what are you doing") || currentMessageFromUser.includes("what do you do") || currentMessageFromUser.includes("your plan") || currentMessageFromUser.includes("going to do")){
          this.responseMessage = plans[Math.floor(Math.random()*plans.length)];
        }
        else if(currentMessageFromUser.includes("where") && currentMessageFromUser.includes("are you")){
          this.responseMessage = where[Math.floor(Math.random()*where.length)];
        }
        else if(currentMessageFromUser.includes("hobbys")){
          this.responseMessage = hobbies_activities[Math.floor(Math.random()*hobbies_activities.length)];
        }
        else if(currentMessageFromUser.includes("why") && currentMessageFromUser.includes("busy")){
          this.responseMessage = why_busy[Math.floor(Math.random()*why_busy.length)];
        }
        else if(currentMessageFromUser.includes("what happens if") || currentMessageFromUser.includes("What happens if") || currentMessageFromUser.includes("What would happen")){
          this.responseMessage = consequences[Math.floor(Math.random()*consequences.length)];
        }
        else if(currentMessageFromUser.includes("can I")){
          this.responseMessage = advice[Math.floor(Math.random()*advice.length)];
        }
        else if(currentMessageFromUser.includes("you are") || currentMessageFromUser.includes("You are") ||currentMessageFromUser.includes("you're") || currentMessageFromUser.includes("You're")){
          this.responseMessage = compliment[Math.floor(Math.random()*compliment.length)];
        }
        else if(currentMessageFromUser.includes("can you") || currentMessageFromUser.includes("could you")){
          this.responseMessage = can[Math.floor(Math.random()*can.length)];
        }
        else if(currentMessageFromUser.includes("Do you know how to") || currentMessageFromUser.includes("do you know how to")){
          this.responseMessage = how_to[Math.floor(Math.random()*how_to.length)];
        }
        else if(currentMessageFromUser.includes("Thank you") || currentMessageFromUser.includes("thank you") ||currentMessageFromUser.includes("Thanks") || currentMessageFromUser.includes("thanks")){
          this.responseMessage = thanks[Math.floor(Math.random()*thanks.length)];
        }
        else if(currentMessageFromUser.includes("bye") || currentMessageFromUser.includes("Bye") || currentMessageFromUser.includes("See you later") || currentMessageFromUser.includes("see you later") || currentMessageFromUser.includes("see you soon")){
          this.responseMessage = bye[Math.floor(Math.random()*bye.length)]
        }
        else if(currentMessageFromUser.includes("are you")){
          this.responseMessage = no_talk[Math.floor(Math.random()*no_talk.length)];
        }
        else if(currentMessageFromUser.includes("which") || currentMessageFromUser.includes("what") && currentMessageFromUser.includes("day")){
          let today;
          switch (date.getDay()) {
            case 0: today = "Sunday"; break;
            case 1: today = "Monday"; break;
            case 2: today = "Tuesday"; break;
            case 3: today = "Wednesday"; break;
            case 4: today = "Thursday"; break;
            case 5: today = "Friday"; break;
            default: today = "Saturday"; break;
          }
          this.responseMessage = "Today is: " + today;
        }
        else{
          this.responseMessage = something[Math.floor(Math.random()*something.length)];
        }
        this.chatMessages.push(this.responseMessage);
      }, 3000)
      this.usersTurn = true;
    },
  },
  mounted() {
    const savedChatData = localStorage.getItem("chatData");
    if (savedChatData) {
      this.chatMessages = JSON.parse(savedChatData);
    }
  },
  unmounted() {
    localStorage.setItem("chatData", JSON.stringify(this.chatMessages));
  }
};
</script>