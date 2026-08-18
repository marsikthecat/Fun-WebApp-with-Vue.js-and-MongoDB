<script setup>
import "../../assets/styles.css";
import {onMounted} from "vue";
import {useRouter} from "vue-router";
import {useApi} from "../composables/useApi.js";

onMounted(() => {
  check();
});

const api = useApi();
const router = useRouter();

async function check() {
  const token = sessionStorage.getItem("token");
  if (token === null) {
    await router.push("/unauthorised");
    return;
  }
  try {
    const result = await api.checkUser(token);
    if (result.status === 403) {
      await router.push("/unauthorised");
    }
  } catch (e) {
    await router.push("/unauthorised");
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
</script>

<template>
  <div id="global">
    <header>
      <h1> Marsik the Kitty Cat </h1>
    </header>
    <article>
        <section>
            <router-view></router-view>
        </section>
    </article>
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
