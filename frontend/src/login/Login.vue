<script setup>
import {reactive} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import {customPopup} from "../../assets/features.js";

const router = useRouter();

const state = reactive({
  user: { name: "", password: "" },
  reg: { name: "", pass: "", confirm: "" },
  loggedIn: false,
  msg: { text: "", visible: false },
  signup: { text: "", visible: false, modal: false },
});

const showMsg = (target, text, duration = 3000) => {
  state[target].text = text;
  state[target].visible = true;
  setTimeout(() => (state[target].visible = false), duration);
};

const focusPassword = () => document.getElementById("p")?.focus();

const login = async () => {
  try {
    const res = await axios.post(`http://localhost:8080/users/login`, {
      name: state.user.name,
      password: state.user.password
    });
    if (res.data === "admin-login") return router.push('/admin');  // Redirect to admin panel.
    sessionStorage.setItem("token", res.data);
    state.loggedIn = true;
    setTimeout(() => router.push('/main/home'), 3000); // Redirect to Home after successful login.
  } catch (error) {
    const errorNotification = error.response?.status === 401 ? error.response.data : "Fatal error: Server is not working";
    showMsg("msg", errorNotification);
  }
}

const register = async () =>  {
  const username = state.reg.name.trim();
  const password = state.reg.pass.trim();
  const password_confirmed = state.reg.pass.trim();
  if (validateData(username, password, password_confirmed)) {
    try {
      const result = await axios.post(`http://localhost:8080/users/registering?`, { name: username, password: password});
      const registrationMsg = result.data ? "Registration successful" : "You are already registered.";
      showMsg("msg", registrationMsg, 5000);
    } catch (e) {
      customPopup("Fatal Error", "Registration failed: " + e, true );
    }
  }
}

const validateData = (username, password, password_confirmed) => {
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
      showMsg("signup", rule.message);
      return false;
    }
  }
  return true;
}
</script>

<template>
  <div id="global">
    <div id="signUpBar"><p> Are you new?</p>
      <button @click="state.signup.modal = true">Sign up</button>
    </div>
    <div v-if="!state.loggedIn" id="container">
      <h2>Are you ready for Marsik?</h2>
      <p><b>Name: </b></p>
      <input v-model="state.user.name" type="text" autofocus placeholder="your name" @keydown.enter="focusPassword()"/>
      <p><b> Password: </b></p>
      <input v-model="state.user.password" type="password" id="p" autocomplete="off" placeholder="your password" @keydown.enter="login"/>
      <button @click="login()">login</button>
      <transition name="fade">
        <p v-if="state.msg.visible"> {{state.msg.text}} </p>
      </transition>
    </div>

    <div class="modal" v-if="state.signup.modal">
      <span @click="state.signup.modal = false" class="close" title="Close Modal">&times;</span>
      <div class="modal-content" v-if="state.signup.modal">
        <div class="container">
          <h2>Sign Up</h2>
          <p>Please fill in this form to create an account.</p>
          <hr>
          <p><b>Name:</b></p>
          <input v-model="state.reg.name" type="text" autocomplete="off" placeholder="Enter your name">
          <p><b>Password:</b></p>
          <input v-model="state.reg.pass" type="password" autocomplete="off" placeholder="Enter your Password">
          <p><b>Repeat Password:</b></p>
          <input v-model="state.reg.confirm" type="password" placeholder="Repeat Password">
          <p>By creating an account you allow Marsik the Cat to be a fur ball</p>
          <div>
            <button @click="state.signup.modal = false" class="cancelBtn">Cancel</button>
            <button class="signup" @click="register()">Sign Up</button>
          </div>
          <transition name="fade">
          <p v-if="state.signup.visible"> {{state.signup.text}} </p>
        </transition>
        </div>
      </div>
    </div>
  <div v-if="state.loggedIn">
    <h1> Welcome {{state.user.name}}</h1>
  </div>
  </div>
</template>

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
  margin: 0.5em auto;
  padding: 0.5em;
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
  margin: 20px 10px 10px 10px;
  padding: 8px 40px 8px 40px;
  background: blue;
  color: white;
  font-size: 1.25em;
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