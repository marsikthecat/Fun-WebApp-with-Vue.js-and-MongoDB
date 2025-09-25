<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {customPopup} from "../../assets/features.js";

const todos = ref([]);
const messagesList = ref([]);
const quizList = ref([]);

onMounted(async function() {
  try {
    const userList = await fetch("http://localhost:8080/users");
    todos.value = await userList.json();
  } catch (e) {
    customPopup("Something went wrong:", e.toString(), true);
  }
  try {
    const messageList = await fetch("http://localhost:8080/message");
    messagesList.value = await messageList.json();
  } catch (e) {
    customPopup("Something went wrong:", e.toString(), true);
  }
  try {
    const quizListRequest = await fetch("http://localhost:8080/quiz")
    quizList.value = await quizListRequest.json();
  } catch (e) {
    customPopup("Something went wrong:", e.toString(), true);
  }
})

const remove = async (idToDelete) => {
  axios.delete(`http://localhost:8080/quiz/deleteQuestion?questionID=${idToDelete}`)
      .then(response => {
        if (response.status === 202) {
          quizList.value = quizList.value.filter(q => q.ob !== idToDelete);
        }
      })
      .catch(error => {
        if (error.response && error.response.status === 404) {
          customPopup("Oh no, something went wrong ", error.response.data, true);
        } else {
          customPopup("Oh no, something went horribly wrong ", error, true);
        }
      });
}
const addQuestion = async () => {
  const questionText = document.getElementById("questionText").value;
  const options = [
    document.getElementById("option_one").value,
    document.getElementById("option_two").value,
    document.getElementById("option_three").value,
    document.getElementById("option_four").value
  ]
  const correctIndex = document.getElementById("correctIndex").value;
  if (isNotFilled(questionText, options[0], options[1], options[2],options[3], correctIndex)) {
    customPopup("The following message appeared: ", "please fill in all fields", false);
  } else {
    const addQuestionRequest = await axios.post(`http://localhost:8080/quiz/store?question=${questionText}&correctIndex=${correctIndex}`, options);
    if (addQuestionRequest.status === 200) {
      const newAddedQuestion = addQuestionRequest.data;
      quizList.value.push({id: newAddedQuestion.id, question: newAddedQuestion.question,
        options: newAddedQuestion.options,
        correctIndex: newAddedQuestion.correctIndex});
    } else {
      customPopup("Something went wrong: ", addQuestionRequest.statusText, true);
    }
  }
}
function isNotFilled(a, b, c, d, e, f) {
  return a.length === 0 || b.length === 0 || c.length === 0 || d.length === 0 ||
      e.length === 0 || f.length === 0;
}
</script>

<template>
  <div id="admin-dashboard">
    <h1 class="page-title">Welcome Admin</h1>
    <div id="dashboard">
      <div class="section">
        <h2 class="section-title">All signed up users: </h2>
        <div class="list">
          <p v-for="(user, index) in todos" :key="index" class="list-item">
            <span class="label">Name:</span> {{ user.name }}
            <br />
            <span class="label">Password:</span> {{ user.password }}
          </p>
        </div>
      </div>
      <div class="section">
        <h2 class="section-title">All messages: </h2>
        <div class="list">
          <p v-for="(message, index) in messagesList" :key="index" class="list-item">
            <span class="label">You got a new message from:</span> {{ message.kontaktname }}
            <br>
            <span class="label">Age:</span> {{ message.alter }}
            <br>
            <span class="label">He wrote the following to you:</span>
            <br>
            {{ message.kontaktnachricht }}
          </p>
        </div>
      </div>
      <div class="section">
        <h2 class="section-title"> Quiz App Management</h2>
        <div class="list">
          <p v-for="q in quizList" class="list-item">
            <span class="label"> Question id: {{q.id}} <br>
             Question: {{q.question}} <br>
             Options: {{q.options}} <br>
             Correct: {{q.correctIndex}} <br>
            </span>
            <button id="${{q.id}}" class="delete-btn" @click="remove(q.id)">Delete Question {{q.id}}</button>
          </p>
        </div>
        <h4>Add new question:</h4>
        <input type="text" id="questionText" class="input-field" placeholder="Enter question" @keydown.enter="focusOption(1)">
        <p>Add 4 options</p>
        <input type="text" id="option_one" class="input-field" placeholder="Option 1" @keydown.enter="focusOption(2)">
        <input type="text" id="option_two" class="input-field" placeholder="Option 2" @keydown.enter="focusOption(3)">
        <input type="text" id="option_three" class="input-field" placeholder="Option 3" @keydown.enter="focusOption(4)">
        <input type="text" id="option_four" class="input-field" placeholder="Option 4" @keydown.enter="addQuestion">
        <select id="correctIndex" class="input-field" aria-orientation="horizontal" size="1">
          <option v-for="n in 4" :key="n" :value="n - 1">{{n}}</option>
        </select>
        <button class="add-btn" @click="addQuestion">Add question</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminPanel',

  methods: {
    focusOption(num) {
      document.getElementsByClassName("input-field")[num].focus();
    }
  }
}
</script>

<style scoped>
#admin-dashboard
{
  background-color: #eaeced;
  padding: 20px;
}

.page-title
{
  color: black;
  text-align: center;
  font-weight: bold;
}

#dashboard
{
  display: flex;
  flex-direction: column;
  gap: 20px;
  max-width: 900px;
  margin: 0 auto;
}

.section
{
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.25);
  padding: 20px;
}

.list-item
{
  margin: 10px 0;
}

.label
{
  font-weight: bold;
  color: #16a085;
}

.delete-btn
{
  background-color: #e74c3c;
  color: #fff;
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 0.9em;
}
.delete-btn:hover
{
  background-color: #c0392b;
}

.input-field
{
  width: 90%;
  padding: 8px;
  margin: 5px 0;
  border: 1px solid #454545;
  border-radius: 10px;
  font-size: 0.8em;
}

.add-btn
{
  background-color: #2ecc71;
  color: white;
  padding: 8px 15px;
  border-radius: 5px;
  font-size: 1em;
  width: 90%;
}

@media screen and (max-width: 444px) {
  #admin-dashboard
  {
    padding: 0;
  }
}
</style>