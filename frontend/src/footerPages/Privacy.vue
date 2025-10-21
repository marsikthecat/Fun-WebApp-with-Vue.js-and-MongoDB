<script setup>
import {onMounted, onUnmounted} from "vue";
import {customPopup} from "../../assets/features.js";

onMounted(() => {
  customPopup("Important", "In order to receive important Information about the" +
      " privacy policy, click the button 'Get privacy information' ", false);
})
onUnmounted(() => document.body.removeAttribute("style"));
</script>

<template>
  <main>
      <button id="funnyContainer" ref="container" :style="{top: y + 'px', left: x + 'px'} "
              @mousemove="doSomething()">Get privacy information</button>
  </main>
</template>

<style scoped>
main
{
  width: 100vw;
  height: 100vh;
  background-image: linear-gradient(135deg, rgb(179, 255, 170), rgb(97, 216, 240), rgb(255, 172, 245));
}
#funnyContainer
{
  position: absolute;
  background-color: #0b6940;
  color: white;
  font-size: 1.2em;
  border-radius: 40px;
  padding: 15px 20px;
}
</style>

<script>
export default {
  name: "Privacy",

  data() {
    return {
      x: 50,
      y: 50,
    }
  },
  methods: {
    doSomething() {
      const elem = this.$refs.container;
      const width = elem.offsetWidth;
      const height = elem.offsetHeight;
      const maxX = window.innerWidth - width;
      const maxY = window.innerHeight - height;
      this.x = Math.random() * maxX;
      this.y = Math.random() * maxY;
    },
  }
}
</script>