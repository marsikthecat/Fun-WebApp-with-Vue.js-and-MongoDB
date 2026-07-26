<script setup>
import {onMounted, ref, shallowRef} from "vue";
import axios from "axios";
import {customPopup} from "../../assets/features.js";
import router from "../router/router.js";

const users = ref([]);
const messagesList = ref([]);
const quizList = ref([]);

const headers = [
  {title: 'Question', key: 'question', align: 'start'},
  {title: 'Options', key: 'options', align: 'start'},
  {title: 'CorrectIndex', key: 'correctIndex', align: 'start'},
  {title: 'Actions', key: 'actions', align: 'end'},
];

const addQuestionDialog = ref(false);

onMounted(async () => {

  const token = sessionStorage.getItem("admin-token");
  if (token === null) {
    await router.push("/unauthorised");
    return;
  }
  try {
    const result = await axios.post(
        "http://localhost:8080/users/checkUser", {},
        {
          headers: {
            Authorization: token
          }
        }
    );
    if (result.status === 403) {
      await router.push("/unauthorised");
    }
  } catch (e) {
    customPopup("Big Fatal Error:", e, true);
    await router.push("/unauthorised");
  }

  try {
    const [userFetch, messagesFetch, quizListFetch] = await Promise.all([
      axios.get("http://localhost:8080/users",  { headers: {Authorization: token} }),
      axios.get("http://localhost:8080/message"), // TODO: auth for this point
      axios.get("http://localhost:8080/quiz"),
    ])
    users.value = userFetch.data;
    messagesList.value =  messagesFetch.data;
    quizList.value = await quizListFetch.data;
  } catch (error) {
    customPopup("Something went wrong: ", error.toString(), true);
  }
})

const remove = async (idToDelete) => {
  axios.delete(`http://localhost:8080/quiz/deleteQuestion?questionID=${idToDelete}`)
      .then(response => {
        if (response.status === 202) {
          quizList.value = quizList.value.filter((q => q.id !== idToDelete));
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

const focusOption = (num) => document.getElementsByClassName("input-field")[num].focus();

const isNotFilled = (...fields) => fields.some(f => f.length === 0);
</script>

<template>
  <div id="admin-dashboard">
    <h1 class="page-title">Welcome Admin</h1>
    <div id="dashboard">
      <div class="section">
        <h2 class="section-title">All signed up users: </h2>
        <v-table density="compact" hover>
          <thead>
          <tr style="font-size: large">
            <th class="text-left, label">
              Name
            </th>
            <th class="text-left, label">
              Password
            </th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in users" :key="user.id">
            <td> {{ user.name }}</td>
            <td> {{ user.password }}</td>
          </tr>
          </tbody>
        </v-table>
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
        <v-data-table :headers="headers" :items="quizList" hide-default-footer>
          <template v-slot:top>
            <v-toolbar flat color="red">
              <v-toolbar-title>Quiz App Management</v-toolbar-title>
              <v-btn class="me-2" prepend-icon="mdi-plus" rounded="lg" text="New Question" border @click="addQuestionDialog = true">
              </v-btn>
            </v-toolbar>
          </template>
          <template #item.actions="{ item }">
            <div class="d-flex ga-2 justify-end">
              <v-icon icon="mdi-pencil" color="medium-emphasis" size="small"></v-icon>
              <v-icon icon="mdi-delete" color="medium-emphasis" size="small"></v-icon>
            </div>
          </template>
        </v-data-table>

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

        <v-dialog max-width="500" v-show="addQuestionDialog">
          <v-card>Add new Question</v-card>
          <v-row>
            <v-col>
              <v-text-field>

              </v-text-field>
            </v-col>
          </v-row>
        </v-dialog>


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
  name: 'AdminPanel'
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