<script setup>
import {ref} from "vue";

const signUpVisible = ref(false);
const showRegistration = (b) => signUpVisible.value = b;
</script>
<template>
  <div id="global">
    <div id="signUpBar" v-if="!loggedIn"><p> Are you new?</p><button @click="showRegistration(true)">Sign up</button></div>
    <div v-if="!loggedIn" id="container">
      <h2>Are you ready for Marsik?</h2>
      <p><b>Name: </b></p>
      <input v-model="user_name" type="text" autofocus placeholder="your name" @keydown.enter="focusPassword()"/>
      <p><b> Password: </b></p>
      <input v-model="user_password" type="password" id="p" autocomplete="off" placeholder="your password" @keydown.enter="login"/>
      <button @click="login()">login</button>
      <transition name="fade">
        <p v-if="msgVisible"> {{confirmation}} </p>
      </transition>
    </div>

    <div class="modal" v-if="signUpVisible">
      <span @click="showRegistration(false)" class="close" title="Close Modal">&times;</span>
      <div class="modal-content" v-if="signUpVisible">
        <div class="container">
          <h2>Sign Up</h2>
          <p>Please fill in this form to create an account.</p>
          <hr>
          <p><b>Name:</b></p>
          <input v-model="user_name_registration" type="text" autocomplete="off" placeholder="Enter your name">
          <p><b>Password:</b></p>
          <input v-model="user_password_registration" type="password" autocomplete="off" placeholder="Enter your Password">
          <p><b>Repeat Password:</b></p>
          <input v-model="user_password_registration_confirmed" type="password" placeholder="Repeat Password">
          <p>By creating an account you allow Marsik the Cat to fart on you.</p>
          <div>
            <button @click="showRegistration(false)" class="cancelBtn">Cancel</button>
            <button class="signup" @click="register()">Sign Up</button>
          </div>
          <transition name="fade">
          <p v-if="signupMsgVisible"> {{signupMessage}} </p>
        </transition>
        </div>
      </div>
    </div>
  <div v-if="loggedIn">
    <h1> Welcome {{user_name}}</h1>
  </div>
  </div>
</template>

<script>
import axios from 'axios';
import App from "../App.vue";
import {customPopup} from "../../assets/features.js";

export default {
  name: 'Login',
  components: {App},
  data() {
    return {
      user_name: "",
      user_password: "",
      confirmation: "",
      loggedIn: false,
      msgVisible: false,
      user_name_registration: "",
      user_password_registration: "",
      user_password_registration_confirmed: "",
      signupMessage: "",
      signupMsgVisible: false,
    };
  },
  methods: {
    focusPassword() {
      document.getElementById("p").focus();
    },
    async login() {
      try {
        const response = await axios.post(`http://localhost:8080/users/login`, {
              name: this.user_name,
              password: this.user_password
        });
        if (response.data === "admin-login") {
          this.$router.push('/admin');  // Weiterleitung zum Admin Panel
        }
        else {
          sessionStorage.setItem("token", response.data);
          this.loggedIn = true;
          setTimeout(() => {
            this.$router.push('/main/home');  // Weiterleitung zu /home nach erfolgreichem Login
          }, 3000);
        }
      } catch (error) {
        if (error.response) {
          this.notifyUser("Login failed: Please check your data or sign in!", true);
        } else {
          this.notifyUser("Fatal error: Server is not working", true);
        }
      }
    },
    async register() {
      const username = this.user_name_registration.trim();
      const password = this.user_password_registration.trim()
      const password_confirmed = this.user_password_registration_confirmed.trim();
      const validation = this.validateData(username, password, password_confirmed);
      if (validation) {
        try {
          const result = await axios.post(`http://localhost:8080/users/registering?`,
              { name: username, password: password});
          if (result.data) {
            this.notifyUser("Registration successful", false);
          } else {
            this.notifyUser("You are already registered.", false);
          }
        } catch (e) {
          customPopup("Fatal Error", "Registration failed:" + e, true );
        }
      }
    },
    notifyUser(message, login) {
      if (login) {
        this.msgVisible = true
        this.confirmation = message;
        setTimeout(() => {
          this.msgVisible = false;
        }, 3000)
      } else {
        this.signupMsgVisible = true
        this.signupMessage = message;
        setTimeout(() => {
          this.signupMsgVisible = false;
        }, 5000)
      }
    },
    validateData(username, password, password_confirmed) {
      const rules = [
        { check: () => username.length === 0 || password.length === 0, message: "You need to fill in a username and a password!" },
        { check: () => username.length < 5, message: "Your username needs at least 5 characters" },
        { check: () => password.length < 10, message: "Your password needs at least 10 characters" },
        { check: () => !/(?=(.*[A-Z]){3})/.test(password), message: "Your password needs at least 3 uppercase characters" },
        { check: () => !/(?=(.*[a-z]){3})/.test(password), message: "Your password needs at least 3 lowercase characters" },
        { check: () => !/(?=(.*\d){2})/.test(password), message: "Your password needs at least 3 numbers" },
        { check: () => !/(?=.*[^A-Za-z0-9])/.test(password), message: "Your password needs at least one special character" },
        { check: () => password !== password_confirmed, message: "Password and repeated Password are not equal!"}
      ];
      for (let rule of rules) {
        if (rule.check()) {
          this.notifyUser(rule.message, false);
          return false;
        }
      }
      return true;
    }
  }
};
</script>

<style scoped>
#global
{
  background: linear-gradient(to right, #6dd5ed, #2193b0);
  height: 100vh;
  align-content: center;
}

#signUpBar
{
  background-color: whitesmoke;
  position: fixed;
  top: 0;
  right: 0;
  padding: 8px;
}
#signUpBar button
{
  font-size: 0.9em;
  padding: 6px 10px;
  background-color: #216df7;
  width: inherit;
  margin: 0 10px;
  border-radius: 8px;
  color: white;
}
#signUpBar button:hover
{
  background-color: #103def;
}
#signUpBar p
{
  display: inline;
}

#container
{
  background-color: #fff;
  padding: 10px 0;
  border-radius: 15px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
  margin: 0 auto;
}
#container input
{
  display: block;
  margin: 0.7em auto;
  padding: 0.4em;
  font-size: 1em;
  border: 2px solid #ccc;
  border-radius: 10px;
  transition: border-color 0.4s;
}
#container input:focus
{
  border-color: #8e44ad;
}
#container button
{
  margin: 10px;
  padding: 8px 0 8px 0;
  background: blue;
  width: 140px;
  color: white;
  font-size: 1.2em;
  border-radius: 8px;
}
#container button:hover
{
  background: #2f58fa;
}

.fade-enter-active, .fade-leave-active
{
  transition: opacity 0.5s ease-in-out;
}
.fade-enter-from, .fade-leave-to
{
  opacity: 0;
}

.modal
{
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: #474e5d;
  padding-top: 50px;
}
.close
{
   position: absolute;
   right: 20px;
   top: 15px;
   font-size: 2em;
   font-weight: bold;
   color: #f1f1f1;
   cursor: pointer;
}
.modal-content
{
  background-color: #fefefe;
  margin: 10px auto;
  width: 100%;
  max-width: 600px;
}
.modal .modal-content .container input
{
  width: 90%;
  padding: 7px;
  margin: 5px 0;
  background: #f1f1f1;
  font-size: 0.9em;
  border: 2px solid #929191;
  border-radius: 10px;
}
.modal .modal-content .container input:focus
{
  background-color: #ddd;
  border-color: #6e6e6e;
}
hr
{
  border: 1px solid #666666;
}
.modal .modal-content .container button
{
  font-size: 1em;
  padding: 10px 0;
  color: white;
}
.cancelBtn, .signup
{
  width: 35%;
}
.signup
{
  background-color: #04AA6D;
  margin: 15px 10% 12px 5%;
}
.cancelBtn
{
  background-color: #f44336;
  margin: 15px 5% 12px 10%;
}
.modal .modal-content .container
{
  padding: 5px 12px;
}
</style>