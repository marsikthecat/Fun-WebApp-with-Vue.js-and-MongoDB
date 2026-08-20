<script setup>
import { useWindowScroll } from '@vueuse/core';
import {onMounted} from "vue";

const {y} = useWindowScroll();

onMounted(() => window.scrollTo(0, 0));
</script>

<template>
  <div>
    <h2> Legal notice </h2>
    <div class="tag" :class="{ show: y > 30}">
      <h3>Address:</h3>
      <p>- An unknown street</p>
      <p>- Unknown village</p>
      <p>- Somewhere in Germany (I not gonna tell you where)</p>
    </div>
    <div class="tag" :class="{ show: y > 90}" >
      <h3>Contact:</h3>
      <p>- Phone: No phone number</p>
      <p>- Mail: No e-mail</p>
      <p>- Helicopter: Not available right now</p>
    </div>
    <div class="tag" :class="{ show: y > 150}">
      <h3>Responsibilities:</h3>
      <p>- Marsik the Kitty (Supreme Leader)</p>
      <p>- Marsik's fur ball (Director)</p>
      <p>- Marsik Owner (Web development)</p>
      <p>- Marsik the Kitty (Cuteness)</p>
      <p>- Marsik's tail (Management)</p>
    </div>
    <div class="tag" :class="{ show: y > 210}">
      <h3>Further Information:</h3>
      <p>- Kitty's Website is protected by copyright.</p>
      <p>- Kitty's Website is protected by the strength of his tail</p>
      <p>- Kitty's Website is created with the greatest of care. </p>
      <p>- Kitty's Website is available without giving any personal information.</p>
      <p>- Kitty's Website is full with Kitty's.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "LegalNotice"
}
</script>

<style scoped>
.tag
{  
  background-color: rgb(225, 225, 225);
  padding: 10px 10px 10px 20px;
  opacity: 0;
  transform: translateY(18px) scale(0.96);
  transition: opacity 0.45s ease, transform 0.45s cubic-bezier(0.22, 1, 0.36, 1);
}
.show
{
  opacity: 1;
  transform: translateY(0) scale(1);
}

@media (prefers-reduced-motion: reduce) {
  .tag {
    transition: none;
    transform: none;
  }
}
</style>