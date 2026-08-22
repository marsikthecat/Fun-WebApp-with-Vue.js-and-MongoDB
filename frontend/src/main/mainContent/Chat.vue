<template>
  <article>
    <section>
      <div class="chatShell">
        <h2>Chat with Marsik</h2>
        <v-sheet id="chatDiv" class="chatWindow" elevation="2" rounded="lg">
          <div
              v-for="(item, index) in chatMessages"
              :key="item.id || index"
              class="messageRow"
              :class="{ 'messageRowUser': item.sender === 'user' }"
          >
            <div class="chatMsgStyle" :class="{ 'chatMsgUser': item.sender === 'user' }">
              <p>{{ item.text }}</p>
              <span>{{ formatMessageTime(item.createdAt) }}</span>
            </div>
          </div>
        </v-sheet>
        <v-sheet id="createMessagediv" class="messageComposer" elevation="2" rounded="lg">
          <v-text-field
              v-model.trim="currentMessage"
              autocomplete="off"
              bg-color="white"
              density="comfortable"
              hide-details
              placeholder="Type in your message..."
              rounded="pill"
              variant="solo"
              @keyup.enter="chatSubmit"
          ></v-text-field>
          <v-btn
              id="chatSend"
              color="primary"
              icon="mdi-send"
              :disabled="!currentMessage"
              @click="chatSubmit"
          ></v-btn>
        </v-sheet>
      </div>
    </section>
  </article>

</template>

<style scoped>
.chatShell
{
  max-width: 760px;
  margin: 0 auto;
}
#chatDiv
{
  height: min(62vh, 560px);
  min-height: 400px;
  background:
      radial-gradient(circle at 18px 18px, rgba(255, 255, 255, 0.55) 0 2px, transparent 2px 40px),
      linear-gradient(135deg, #efe7db 0%, #d8eee6 100%);
  overflow-y: auto;
  padding: 18px;
}
#createMessagediv
{
  background-color: #f4f6f5;
  padding: 10px;
  margin-top: 10px;
}
.messageComposer
{
  display: grid;
  grid-template-columns: 1fr 48px;
  gap: 10px;
  align-items: center;
}
.messageRow
{
  display: flex;
  justify-content: flex-start;
  margin: 8px 0;
}
.messageRowUser
{
  justify-content: flex-end;
}
.chatMsgStyle
{
  max-width: min(76%, 520px);
  border-radius: 18px 18px 18px 4px;
  background: #ffffff;
  box-shadow: 0 2px 10px rgba(40, 55, 70, 0.12);
  overflow-wrap: anywhere;
  padding: 9px 12px 7px;
  color: #1f2a30;
}
.chatMsgUser
{
  border-radius: 18px 18px 4px 18px;
  background: #d7f8c6;
}
.chatMsgStyle p
{
  margin: 0;
  line-height: 1.35;
}
.chatMsgStyle span
{
  display: block;
  margin-top: 3px;
  color: #667078;
  font-size: 0.72rem;
  line-height: 1;
  text-align: right;
}
#chatSend
{
  width: 48px;
  height: 48px;
}
</style>

<script>
import {customPopup} from "../../../assets/features.js";

export default {
  name: "Chat",
  data() {
    return {
      subscriptionRequired: false,
      currentMessage: "",
      chatMessages: [],
    }
  },
  watch: {
    chatMessages: {
      deep: true,
      handler() {
        localStorage.setItem("chatData", JSON.stringify(this.chatMessages));
      }
    }
  },
  methods: {
    chatSubmit() {
      if (this.subscriptionRequired) {
        customPopup("Subscription required",
            "You need a subscription to continue chating with Marsik the Chat-Cat", false);
        return;
      }
      if (!this.currentMessage) {
        return;
      }
      this.addMessage(this.currentMessage, "user");
      this.currentMessage = "";
      setTimeout(() => {
        const dumbAnswer = ["I am not able to answer you", "Sorry I am busy", "I dont understand what ur saying"];
        this.addMessage(dumbAnswer[Math.floor(Math.random() * dumbAnswer.length)], "marsik");
      }, 4000)
      this.subscriptionRequired = true;
    },
    addMessage(text, sender) {
      this.chatMessages.push({
        id: `${Date.now()}-${Math.random().toString(16).slice(2)}`,
        text,
        sender,
        createdAt: new Date().toISOString()
      });
      this.scrollToLatestMessage();
    },
    formatMessageTime(createdAt) {
      return new Intl.DateTimeFormat("de-DE", {
        hour: "2-digit",
        minute: "2-digit"
      }).format(new Date(createdAt));
    },
    normalizeMessages(messages) {
      return messages.map((message) => {
        if (typeof message === "string") {
          return {
            id: `${Date.now()}-${Math.random().toString(16).slice(2)}`,
            text: message,
            sender: "marsik",
            createdAt: new Date().toISOString()
          };
        }
        return {
          id: message.id || `${Date.now()}-${Math.random().toString(16).slice(2)}`,
          text: message.text || "",
          sender: message.sender || "marsik",
          createdAt: message.createdAt || new Date().toISOString()
        };
      });
    },
    scrollToLatestMessage() {
      this.$nextTick(() => {
        const chatDiv = document.getElementById("chatDiv");
        if (chatDiv) {
          chatDiv.scrollTop = chatDiv.scrollHeight;
        }
      });
    }
  },
  mounted() {
    const savedChatData = localStorage.getItem("chatData");
    if (savedChatData) {
      this.chatMessages = this.normalizeMessages(JSON.parse(savedChatData));
      this.scrollToLatestMessage();
      return;
    }
    this.addMessage("Hello, how can I help you", "marsik");
  }
}
</script>
