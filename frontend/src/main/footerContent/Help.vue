<script setup>
import {onMounted, ref} from "vue";

const sentences = "This page is designed to help you with whatever you need. /" +
    "Thank you so much for visiting the page, I am absolutely glad to help you. /" +
    "Unfortunately I cannot help you right now. /" +
    "I very much hope that my help was helpful. /" +
    "Anyway, I wish you a good and fantastic day and remember: /" +
    "If you ever need help I am always here to help you!";

const sentencesHTML = ref('');
onMounted(() => loadSentences())
function loadSentences() {
  for (let i = 0; i < sentences.length; i++) {
    setTimeout(() => {
      sentencesHTML.value += sentences.charAt(i) === "/" ? '<br>' : sentences.charAt(i)
    }, i * 50);
  }
}
</script>

<template>
  <h2>Help</h2>
  <main>
    <div id="headerContainer">
      <h1> Dear User</h1>
      <p v-html="sentencesHTML"></p>
    </div>
  </main>
</template>

<style scoped>
main
{
  height: 500px;
  background: #14182a;
  color: white;
  align-content: center;
}

#headerContainer
{
  text-align: center;
  padding: 10px;
}
</style>