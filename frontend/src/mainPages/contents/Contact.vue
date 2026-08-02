<template>
    <article>
      <section>
        <h2> Contact with Marsik</h2>
        <p> You can get in touch with Marsik here: </p><br>
        <v-form>
          <v-container>
            <v-text-field v-model="userName" label="Your name" required bg-color="#ffffffc2"></v-text-field>
            <v-date-input label="Your age" required  bg-color="#ffffffc2"></v-date-input>
            <v-textarea v-model="userContentMessage" label="Your message" required counter clearable bg-color="#ffffffc2"></v-textarea>
            <v-btn @click="send" text="Send message"></v-btn>
          </v-container>
        </v-form>
      </section>
    </article>
</template>

<style scoped>
.v-container
{
  background: linear-gradient(135deg, #ccf241, #23a7ff);
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
}

.v-btn
{
  padding: 10px;
  font-weight: bold;
  color: white;
  background: linear-gradient(135deg, #f857a6, #ff5858);
  border-radius: 6px;
  transition: all 0.5s ease;
}
.v-btn:hover
{
  transform: scale(1.05);
}
</style>

<script>
import {customPopup} from "../../../assets/features.js";
export default {
  name: 'Contact',
  data() {
    return {
      userName: "",
      userAge: "",
      userContentMessage: "",
    }
  },
  methods: {
    async send() {
      if (this.userName === "" || this.userAge === "" || this.userContentMessage === "") {
        customPopup("The following message appeared:", "please fill in all fields", false);
        return false;
      } else {
        const mailContent = {
          kontaktname: this.userName,
          alter: this.userAge,
          kontaktnachricht: this.userContentMessage
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
    }
  }
};
</script>