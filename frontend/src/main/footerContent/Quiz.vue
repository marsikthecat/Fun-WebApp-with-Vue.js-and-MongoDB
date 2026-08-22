<script setup>
import {computed, onMounted, ref} from "vue";
import {customPopup} from "../../../assets/features.js";
import {useApi} from "../../composables/useApi.js";
import {useAuth} from "../../composables/useAuth.js";

const api = useApi();
const {check} = useAuth();
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
const quizStarted = ref(false);
const finished = ref(false)

onMounted(async () => {
    try {
      const auth = await check();
      if (!auth.authorized) {
        return;
      }
      const questionRequest = await api.getQuiz(auth.token);
      questionList.value = questionRequest.data;
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
    } else {
      index.value++;
      response.value = "";
    }
  }, 2000)
}
const startQuiz = () => {
  quizStarted.value = true;
  index.value = 0;
  correctAnswers.value = 0;
  response.value = "";
  finished.value = false;
}
const restartGame = () => {
  startQuiz();
}
</script>

<template>
  <h2>Quiz</h2>
  <div class="quiz-intro" v-if="questionList.length !== 0 && !quizStarted">
    <p>Ready to test your Marsik knowledge?</p>
    <button class="start-btn" @click="startQuiz">Start quiz</button>
  </div>
  <div class="container" v-if="questionList.length !== 0 && quizStarted" :style="{backgroundColor: containerColor }">
    <div v-if="!finished" id="question-container">
      <h3 id="question" v-text="questionList[index].question" ></h3>
      <div id="answer-buttons" class="btn-grid">
        <button class="btn" v-for="(option, i) in questionList[index].options" :key="i"
                @click="evaluate(i === questionList[index].correctIndex)"
                :disabled="finished"> {{option}} </button>
      </div>
      <h2 id="notification" v-text="response"></h2>
    </div>
    <div v-else class="result-panel">
      <h3>Quiz complete!</h3>
      <p>You got <strong>{{ correctAnswers }}</strong> out of <strong>{{ questionList.length }}</strong> correct.</p>
      <button id="restart-btn" @click="restartGame">Try again</button>
    </div>
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
  text-align: center;
  padding: 28px 20px;
}
.quiz-intro
{
  text-align: center;
  padding: 30px 20px;
  height: 300px;
}
.start-btn
{
  border: none;
  padding: 12px 28px;
  border-radius: 6px;
  color: white;
  background-color: #198620;
  font-size: 1.2em;
}
.result-panel
{
  padding: 30px 20px;
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
