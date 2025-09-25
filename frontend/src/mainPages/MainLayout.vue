<script setup>
import "../../assets/styles.css";
import {isPopupVisible, handlePopup, setupMenuToggle, customPopup} from "../../assets/features.js";
import { onMounted } from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import {useWindowScroll} from "@vueuse/core";
onMounted(() => {
  check();
  setupMenuToggle();
});

const {y} = useWindowScroll();
const router = useRouter();
async function check() {
  const id = sessionStorage.getItem("token");
  if (id === null) {
    await router.push("/unauthorised");
    return;
  }
  try {
    const result = await axios.get(`http://localhost:8080/users/checkUser?userId=${id}`);
    if (!result.data) {
      await router.push("/unauthorised");
    }
  } catch (e) {
    customPopup("Big Fatal Error:", e, true);
    await router.push("/unauthorised");
  }
}
async function logout() {
  const id = sessionStorage.getItem("token");
  if (id === null) {
    customPopup("Big Fatal Error: ", "Logout-token not found", true);
    return;
  }
  try {
    const result = await axios.get(`http://localhost:8080/users/logout?userId=${id}`);
    if (result.data) {
      sessionStorage.removeItem("token");
      customPopup("Logout successful!", "Marsik wants to say something to you: " +
          "See you later!", false);
      await router.push("/login");
    } else {
      customPopup("Error: ", "Logout-token not valid", true);
    }
  } catch (e) {
    customPopup("Fatal Error:", e, true);
  }
}
</script>
<template>
  <transition name="popup">
    <div id="popup" v-if="isPopupVisible">
      <h2> The following message appears:</h2>
      <p> Sorry, the link is not available at the moment! </p>
      <button @click="handlePopup(false)">Ok</button>
    </div>
  </transition>
  <div id="global">
    <audio ref="audioPlayer" src="/audio/meow.mp4"></audio>
    <button @click="logout" class="logoutBtn" :class="{ showLogout: y > 400}">Logout</button>
    <div id="menubar">
      <button> <i class="fa fa-bars"> </i> </button>
    </div>
    <header>
      <h1> Marsik the Kitty Cat </h1>
    </header>
    <nav>
      <router-link to="home">Home</router-link>
      <router-link to="about">About</router-link>
      <router-link to="videos">Videos</router-link>
      <router-link to="contact">Contact</router-link>
      <router-link to="more">More</router-link>
    </nav>
    <router-view> </router-view> <!-- article with the specific side content -->
    <footer>
      <section>
        <h2> About the Page </h2>
        <p> Marsik is a kitten who likes to be a kitten. He is a cat who is extraordinary and likes to show his kitten-like nature to other people. That's why Marsik, with the help of his owner, created this page to show others what kind of kitten Marsik is. </p>
        <p> Contact: </p>
        <p> <i class="fa fa-phone"> </i> Don't Call Marsik </p>
        <p> <i class="fa fa-envelope"> </i> No Mail, bro </p>
      </section>
      <section>
        <h2> Useful links: </h2>
        <ul>
          <li> <a @click="handlePopup(true)"> To another page </a> </li>
          <li> <a @click="handlePopup(true)"> Secret page </a> </li>
          <li> <a @click="customPopup('The following message appeared:', 'This link was blocked by Marsik the Link-Cat', false)"> Hunting Video </a> </li>
        </ul>
      </section>
      <section>
        <h2> Feedback about the page </h2>
        <form method="POST">
          <input name="userName" type="text" placeholder="Your name" autocomplete="off" /> <br> <br>
          <textarea name="message"> </textarea> <br> <br>
          <input class="messageSubmit" name="messageSubmit" value="Send" type="button" onclick="alert('Marsik the Kitty does not care about your feedback')"/>
        </form>
      </section>
    </footer>
    <div id="unterFooter">
      <ul>
        <li> <router-link to="contact"> Contact </router-link> </li>
        <li> │ </li>
        <li> <router-link to="../privacy"> Privacy policy </router-link> </li>
        <li> │ </li>
        <li> <router-link to="../termsOfUse"> Terms of use </router-link> </li>
        <li> │ </li>
        <li> <router-link to="../legalNotice"> Legal notice </router-link> </li>
        <li> │ </li>
        <li> <router-link to="../service"> Service </router-link> </li>
        <li> │ </li>
        <li> <router-link to="../quiz"> Quiz </router-link> </li>
        <li> | </li>
        <li> <router-link to="../help"> Help </router-link> </li>
        <li> │ </li>
        <li> <router-link to="../news"> News </router-link> </li>
        <li> │ </li>
        <li> <router-link to="../career"> Career </router-link>  </li>
      </ul>
    </div>
    <div id="unterFooter2">
      <ul>
        <li> &copy; 2019 Marsik the Cat </li>
        <li> │ </li>
        <li> All rights reserved </li>
      </ul>
    </div>
  </div>
</template>
<script>
import {customPopup} from "../../assets/features.js";

export default {
  name: 'Main',
  mounted() {
    window.addEventListener('dblclick', this.meow);
    window.addEventListener("beforeprint", this.handleBeforePrint);
  },
  beforeUnmount() {
    window.removeEventListener('dblclick', this.meow);
    window.removeEventListener("beforeprint", this.handleBeforePrint);
  },
  methods: {
    meow() {
      this.$refs.audioPlayer.play();
    },
    handleBeforePrint() {
      customPopup("The following message appeared:",
          "This page has just been approved for printing by Marsik the Print-Cat!", false);
    }
  }
};
</script>