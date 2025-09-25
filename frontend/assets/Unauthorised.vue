<script setup>
import {useRouter} from "vue-router";
import {customPopup} from "./features.js";

const router = useRouter();
const confrontation = () => customPopup("Fatal Error:", "Could not find a confrontation", true);
</script>
<template>
  <div class="unauthorized-page">
    <div class="container">
      <h1>Unauthorised access detected</h1>
      <p>Marsik the Cat does not allow you to enter this page without properly logging in!</p>
      <p class="warning-message">To avoid further complications or even a confrontation with Angry Marsik, <br>
      you are strongly advised to return to the Login Page and login or sign in if you haven't done this yet!</p>
      <button @click="router.push('/login')" class="back-button">Return to the Login (strongly advised)</button>
      <button @click="confrontation" class="back-button">Get a confrontation (not recommended)</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Unauthorised"
}
</script>

<style scoped>
.unauthorized-page
{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #ff0000;
  color: white;
}

.container
{
  text-align: center;
  width: 100%;
  padding: 1em;
}
.container p
{
  margin-top: 10px;
}

h1
{
  font-weight: 700;
  line-height: normal;
  padding: 10px 0 10px 0;
  text-shadow: 3px 3px 5px rgba(0, 0, 0, 1);
}
p
{
  font-size: 1.2em;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 1);
}

.warning-message
{
  font-size: 1.2em;
  margin-top: 10px;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
}

.back-button
{
  background-color: #b30000;
  color: white;
  padding: 10px 20px;
  margin: 20px 15px 0 15px;
  font-size: 1.2em;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(255, 255, 255, 0.8);
}
.back-button:hover
{
  background-color: #cc0000;
  box-shadow: 0 0 20px rgba(255, 255, 255, 0.8);
}
</style>