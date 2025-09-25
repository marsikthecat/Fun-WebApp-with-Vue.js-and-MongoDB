<script setup>
import {onMounted, onUnmounted} from "vue";
import {customPopup} from "../../assets/features.js";

onMounted(() => {
  document.body.setAttribute("style", "overflow: hidden");
  customPopup("Important", "In order to receive important Information about the" +
      " privacy policy, click the button 'Get privacy information' ", false);
})
onUnmounted(() => document.body.removeAttribute("style"));
</script>

<template>
  <main>
      <button id="funnyContainer" ref="container" :style="{top: x + '%', left: y + '%'}"
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
  transform: translate(-50%, -50%);
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
      const width = this.$refs.container.offsetWidth;
      const height = this.$refs.container.offsetHeight;
      const windowW = window.innerWidth;
      const windowH = window.innerHeight;
      const rightBound = windowW - width * 0.5;
      const bottomBound = windowH - height * 0.5;
      const leftBound = width * 0.5;
      const topBound = height * 0.5;
      const randomX = Math.floor(Math.random() * (rightBound - leftBound)) + leftBound;
      const randomY = Math.floor(Math.random() * (bottomBound - topBound)) + topBound;
      const percentX = randomX/window.innerWidth * 100;
      const percentY = randomY/window.innerHeight * 100;
      this.x = percentX;
      this.y = percentY;
    },
  }
}
</script>