<script setup>
import {onMounted, onUnmounted, ref} from "vue";
import {customPopup} from "../../../assets/features.js";

onMounted(() => {
  customPopup("Important", "In order to receive important Information about the" +
      " privacy policy, click the button 'Get privacy information' ", false);
})
onUnmounted(() => document.body.removeAttribute("style"));

const container = ref();
const stage = ref();

const x = ref(50);
const y = ref(50);

function doSomething() {
    const width = container.value.offsetWidth;
    const height = container.value.offsetHeight;
    const maxX = stage.value.offsetWidth - width - 20;
    const maxY = stage.value.offsetHeight - height - 15;
    x.value = Math.random() * maxX;
    y.value = Math.random() * maxY;
}
</script>

<template>
  <h2>Privacy</h2>
  <main ref="stage">
      <button id="funnyContainer" ref="container" :style="{top: y + 'px', left: x + 'px'} "
              @mousemove="doSomething()">Get privacy information</button>
  </main>
</template>

<style scoped>
main
{
  height: 500px;
  background-image: linear-gradient(135deg, rgb(179, 255, 170), rgb(97, 216, 240), rgb(255, 172, 245));
}
#funnyContainer
{
  position: relative;
  background-color: #0b6940;
  color: white;
  border-radius: 30px;
  padding: 10px 15px;
}
</style>

<script>
export default {
  name: "Privacy"
}
</script>