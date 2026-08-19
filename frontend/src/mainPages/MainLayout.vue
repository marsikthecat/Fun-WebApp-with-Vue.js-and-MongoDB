<script setup>
import "../../assets/styles.css";
import {customPopup} from "../../assets/features.js";
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import {useApi} from "../composables/useApi.js";
import {useAuth} from "../composables/useAuth.js";
import WelcomeBubble from "../../assets/WelcomeBubble.vue";

onMounted(async () => {
  const auth = await check();
  if (!auth.authorized && auth.error) {
    customPopup("Big Fatal Error:", auth.error, true);
  }
});

const api = useApi();
const router = useRouter();
const {check} = useAuth();
async function logout() {
  const token = sessionStorage.getItem("token");
  if (token === null) {
    customPopup("Big Fatal Error: ", "Session token not found", true);
    return;
  }
  try {
    const result = await api.logout(token);
    if (result.status === 200) {
      sessionStorage.removeItem("token");
      sessionStorage.removeItem("username");
      sessionStorage.removeItem("welcomeShown");
      customPopup("Logout successful!", "Marsik wants to say something to you: " + "See you later!", false);
      await router.push("/login");
    } else {
      customPopup("Error: ", "Logout-token not valid", true);
    }
  } catch (e) {
    customPopup("Fatal Error: ", e, true);
  }
}

const links = [
  { name: "Privacy Policy", href: "/privacy" },
  { name: "Terms of Use", href: "/termsOfUse" },
  { name: "Legal notice", href: "/legalNotice" },
  { name: "Service", href: "/service" },
  { name: "Quiz", href: "/quiz" },
  { name: "Help", href: "/help" },
  { name: "News", href: "/news" },
  { name: "Career", href: "/career" },
]
const drawer = ref(false)
</script>
<template>
  <WelcomeBubble />
  
  <v-app-bar color="primary" height="40">
    <v-app-bar-nav-icon variant="text" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
    <v-spacer></v-spacer>
    <v-btn icon="mdi-logout" variant="text" @click="logout"></v-btn>
  </v-app-bar>
  <v-navigation-drawer color="blue" v-model="drawer" location="left">
    <v-list>
      <v-list-item title="Home" to="home"></v-list-item>
      <v-list-item title="Impressions" to="impressions"></v-list-item>
      <v-list-item title="Videos" to="videos"></v-list-item>
      <v-list-item title="Contact" to="contact"></v-list-item>
      <v-list-item title="Chat" to="chat"></v-list-item>
    </v-list>
  </v-navigation-drawer>

  <div id="global">
    <audio ref="audioPlayer" src="/audio/meow.mp4"></audio>
    <div id="menubar"></div>
    <header>
      <h1> Marsik the Kitty Cat </h1>
    </header>
    <div class="navTabs">
      <v-tabs bg-color="rgb(0 161 141)" align-tabs="center" height="40">
        <v-tab to="home" text="Home"></v-tab>
        <v-tab to="impressions" text="Impressions"></v-tab>
        <v-tab to="videos" text="Videos"></v-tab>
        <v-tab to="contact" text="Contact"></v-tab>
        <v-tab to="chat" text="Chat"></v-tab>
      </v-tabs>
      <v-btn
          class="navLogout"
          icon="mdi-logout"
          variant="text"
          color="white"
          @click="logout"
      ></v-btn>
    </div>
    <router-view> </router-view>
  </div>
  <v-footer class="d-flex align-center justify-center ga-2 flex-wrap flex-grow-1 py-3" color="surface-light">
    <v-btn v-for="link in links"
        :key="link" :text="link.name"
        variant="text" color="white" :to="link.href" rounded
    ></v-btn>
    <v-divider color="grey"></v-divider>
    <div class="flex-1-0-100 text-center mt-6 text-white">
      &copy; {{ new Date().getFullYear() }} Marsik the Cat │ All rights reserved
    </div>
  </v-footer>
</template>
<style>
.v-tab
{
  min-width: 50px !important;
}
.navTabs
{
  position: relative;
  background: rgb(0 161 141);
}
.navLogout
{
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
}
.v-app-bar
{
  display: none;
}

@media screen and (min-width: 480px)
{
  .v-navigation-drawer
  {
    display: none !important;
  }
}

@media screen and (max-width: 480px)
{
  .v-tabs
  {
    display: none !important;
  }
  .navLogout
  {
    display: none;
  }
  .v-app-bar
  {
    display: block;
  }
  .v-navigation-drawer
  {
    display: block !important;
    background-color: rgb(0 161 141);
  }
}
</style>
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
