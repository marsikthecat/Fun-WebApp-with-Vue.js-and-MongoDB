<script setup>
import { useWindowScroll } from '@vueuse/core';
import {onMounted} from "vue";

const {y} = useWindowScroll();

onMounted(() => window.scrollTo(0, 0));
</script>

<template>
  <div>
    <h1> Legal notice </h1>
    <section class="tag" :class="{ show: y > 30}">
      <h2>Address:</h2>
      <p>- An unknown street</p>
      <p>- Unknown village</p>
      <p>- Somewhere in Germany (I not gonna tell you where)</p>
    </section>
    <section class="tag" :class="{ show: y > 90}" >
      <h2>Contact:</h2>
      <p>- Phone: No phone number</p>
      <p>- Mail: No e-mail</p>
      <p>- Helicopter: Not available right now</p>
    </section>
    <section class="tag" :class="{ show: y > 150}">
      <h2>Responsibilities:</h2>
      <p>- Marsik the Kitty (Supreme Leader)</p>
      <p>- Marsik's fur ball (Director)</p>
      <p>- Marsik Owner (Web development)</p>
      <p>- Marsik the Kitty (Cuteness)</p>
      <p>- Marsik's tail (Management)</p>
    </section>
    <section class="tag" :class="{ show: y > 210}">
      <h2>Further Information:</h2>
      <p>- Kitty's Website is protected by copyright.</p>
      <p>- Kitty's Website is protected by the strength of his tail</p>
      <p>- Kitty's Website is created with the greatest of care. </p>
      <p>- Kitty's Website is available without giving any personal information.</p>
      <p>- Kitty's Website is full with Kitty's.</p>
    </section>
  </div>
</template>

<script>
export default {
  name: "LegalNotice"
}
</script>

<style scoped>
h1
{
  color: black;
  text-align: center;
}
section
{
  background-color: rgb(225, 225, 225);
  padding: 10px 10px 10px 20px;
}
.tag
{
  opacity: 0;
  scale: 0.8;
  transition: all 1.5s ease-in-out;
}
.show
{
  opacity: 1;
  scale: 1;
}
</style>