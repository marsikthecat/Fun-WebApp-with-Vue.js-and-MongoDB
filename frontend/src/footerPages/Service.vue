<script setup>
import {ref} from "vue";
import {customPopup} from "../../assets/features.js";

const date = new Date();
date.setFullYear(date.getFullYear() + 2);
date.setMonth(date.getMonth() + 4);
const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "Oktober", "November", "Dezember"];
const dateTxt = ref("Expiration date: " + monthNames[date.getMonth()] + " " + date.getFullYear());
</script>

<template>
  <div class="coupon">
    <div class="container">
      <h3>Marsik presents...</h3>
    </div>
    <img src="/graphicComponents/footer/Bildungskätzchen.jpg" title="Marsik der Bildungskätzchen" alt="education-cat">
    <div class="container">
      <h2><b>Education by Kitty</b></h2>
      <p> This brand new project was initiated by Marsik with the goal of providing the future generation with sufficient <strong>education</strong> so that the future,
        prosperity, economy and well-being of all people is guaranteed and secured, even in times of crisis.
        This service available from Marsik the educational kitten will help you acquire basic <strong>programming skills</strong>
        as well as deepen your <strong>language skills.</strong></p>
    </div>
    <div class="container">
      <button type="button" class="block" @click="customPopup('Fatal Error:', 'Could not find education', true)"> Start your learning adventure! </button>
      <p class="expire">{{dateTxt}}</p>
    </div>
  </div>
</template>

<style scoped>
.coupon
{
  border: 3px dotted #9b9b9b;
  border-radius: 9px;
  margin: 1em auto;
  max-width: 600px;
  background-color: #f1f1f1;
}
h2
{
  padding: 0.5em 0;
}
.container
{
  padding: 0 15px;
}
.expire
{
  color: red;
}
.block
{
  width: 100%;
  background-color: #2bef33;
  padding: 10px 15px;
  margin: 10px 0;
  font-weight: 700;
  font-size: 1.2em;
}
.block:hover
{
  background-color: #1dd700;
}
img
{
  transform: rotate(180deg);
}
</style>

<script>
export default {
  name: "Service"
}
</script>