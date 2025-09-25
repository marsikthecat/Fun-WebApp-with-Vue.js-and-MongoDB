<script setup>
import {computed, onMounted, ref} from "vue";
import {customPopup} from "../../assets/features.js";

const questionList = ref([]);
const index = ref(0);
const correctAnswers = ref(0);

const responses = {
  '': 'white',
  'Correct!': '#46ff55',
  'Wrong!' : '#f67580',
  'Game finished' : 'white'
};
const response = ref('');
const containerColor = computed(() => responses[response.value]);
const finished = ref(false)

onMounted(async () => {
    try {
      const questionRequest = await fetch('http://localhost:8080/quiz');
      questionList.value = await questionRequest.json();
    } catch (e) {
      customPopup("Fatal error", e, true);
    }
  })
const evaluate = (b) => {
  if (b) {
    correctAnswers.value++;
  }
  response.value = b ? "Correct!" : "Wrong!";
  setTimeout(() => {
    if (index.value >= questionList.value.length - 1) {
      response.value = "Game finished";
      finished.value = true;
      customPopup("Game finished",
          "You got " + correctAnswers.value + " out of " + questionList.value.length + " correct!",
          false);
    } else {
      index.value++;
      response.value = "";
    }
  }, 2000)
}
const restartGame = () => {
  finished.value = false;
  index.value = 0;
  response.value = "";
}
</script>

<template>
  <div class="container" v-if="questionList.length !== 0" :style="{backgroundColor: containerColor }">
    <div id="question-container" class="hide">
      <h2 id="question" v-text="questionList[index].question" ></h2>
      <div id="answer-buttons" class="btn-grid">
        <button class="btn" v-for="(option, i) in questionList[index].options" :key="i"
                @click="evaluate(i === questionList[index].correctIndex)"
                :disabled="finished"> {{option}} </button>
      </div>
      <h2 id="notification" v-text="response"></h2>
    </div>
      <button id="restart-btn" v-if="finished" @click="restartGame">Try again</button>
  </div>
</template>

<script>
export default {
  name: 'Quiz',
}
</script>

<style scoped>
.container
{
  box-shadow: 0 0 10px 2px;
  text-align: center;
}
#question
{
  padding: 1em;
}
.btn
{
  background-color: mediumaquamarine;
  border-radius: 5px;
  padding: 8px 16px;
  width: 85%;
  max-width: 400px;
  color: white;
  font-size: 1.4em;
  display: block;
  margin: 25px auto;
}
.btn:hover
{
  background-color: #16f8ad;
}
#restart-btn
{
  border: none;
  padding: 10px 20px;
  margin: 10px 0;
  font-size: 1.2em;
  background-color: bisque;
}
#restart-btn:hover
{
  background-color: #ffd09c;
}
</style>