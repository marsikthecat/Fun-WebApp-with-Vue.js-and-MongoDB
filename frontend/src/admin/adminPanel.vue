<script setup>
import {onMounted, ref} from "vue";
import router from "../router/router.js";
import SnackBar from "../../assets/globalFeatures/SnackBar.vue";
import {useApi} from "../composables/useApi.js";

const snackBar = ref();
const api = useApi();

const users = ref([]);
const messagesList = ref([]);
const quizList = ref([]);
const questionForm = ref({
  question: "",
  options: ["", "", "", ""],
  correctIndex: 0,
});
const questionToDelete = ref(null);

const headers = [
  {title: 'Question', key: 'question', align: 'start'},
  {title: 'Options', key: 'options', align: 'start'},
  {title: 'CorrectIndex', key: 'correctIndex', align: 'start'},
  {title: 'Actions', key: 'actions', align: 'end'},
];

const addQuestionDialog = ref(false);
const deleteQuestionDialog = ref(false);

onMounted(async () => {
  const token = sessionStorage.getItem("token");
  if (token === null) {
    await router.push("/unauthorised");
    return;
  }
  try {
    const result = await api.checkUser(token);
    if (result.status === 403) {
      await router.push("/unauthorised");
    }
  } catch (e) {
    snackBar.value.pushSnackBar("Big Fatal Error:" + e, "error");
    await router.push("/unauthorised");
  }

  try {
    const [userFetch, messagesFetch, quizListFetch] = await Promise.all([
      api.getUsers(token),
      api.getMessages(), // TODO: auth for this point
      api.getQuiz(),
    ])
    users.value = userFetch.data;
    messagesList.value = messagesFetch.data;
    quizList.value = await quizListFetch.data;
  } catch (error) {
    snackBar.value.pushSnackBar("Something went wrong: " + error.toString(), "error");
  }
})

const openDeleteDialog = (question) => {
  questionToDelete.value = question;
  deleteQuestionDialog.value = true;
}

const remove = async (idToDelete) => {
  return api.deleteQuizQuestion(idToDelete)
      .then(response => {
        if (response.status === 200) {
          quizList.value = quizList.value.filter((q => q.id !== idToDelete));
        }
      })
      .catch(error => {
        if (error.response && error.response.status === 404) {
          snackBar.value.pushSnackBar("Oh no, something went wrong " + error.response.data, "error");
        } else {
          snackBar.value.pushSnackBar("Oh no, something went horribly wrong " + error, "error");
        }
      });
}
const confirmRemove = async () => {
  if (!questionToDelete.value) {
    return;
  }
  await remove(questionToDelete.value.id);
  questionToDelete.value = null;
  deleteQuestionDialog.value = false;
}

const resetQuestionForm = () => {
  questionForm.value = {
    question: "",
    options: ["", "", "", ""],
    correctIndex: 0,
  };
}

const addQuestion = async () => {
  const questionText = questionForm.value.question.trim();
  const options = questionForm.value.options.map(option => option.trim());
  const correctIndex = questionForm.value.correctIndex;
  if (isNotFilled(questionText, options[0], options[1], options[2],options[3], correctIndex)) {
    snackBar.value.pushSnackBar("please fill in all fields", "error");
  } else {
    const addQuestionRequest = await api.storeQuizQuestion(questionText, correctIndex, options);
    if (addQuestionRequest.status === 200) {
      const newAddedQuestion = addQuestionRequest.data;
      quizList.value.push({id: newAddedQuestion.id, question: newAddedQuestion.question,
        options: newAddedQuestion.options,
        correctIndex: newAddedQuestion.correctIndex});
      resetQuestionForm();
      addQuestionDialog.value = false;
    } else {
      snackBar.value.pushSnackBar("Something went wrong: " + addQuestionRequest.statusText, "error");
    }
  }
}

const focusOption = (num) => document.getElementsByClassName("input-field")[num].focus();

const isNotFilled = (...fields) => fields.some(f => f === null || f === undefined || f.toString().length === 0);
</script>

<template>
  <SnackBar ref="snackBar"></SnackBar>
  <div id="admin-dashboard">
    <h1 class="page-title">Welcome Admin</h1>
    <div id="dashboard">
      <div class="dashboard-row">
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
              <v-icon icon="mdi-delete" color="medium-emphasis" size="small" @click="openDeleteDialog(item)"></v-icon>
            </div>
          </template>
        </v-data-table>

        <v-dialog v-model="addQuestionDialog" max-width="560">
          <v-card>
            <v-card-title>Add new Question</v-card-title>
            <v-card-text>
              <v-text-field
                  v-model="questionForm.question"
                  label="Question"
                  variant="outlined"
                  autofocus
              />
              <v-text-field
                  v-for="(_, optionIndex) in questionForm.options"
                  :key="optionIndex"
                  v-model="questionForm.options[optionIndex]"
                  :label="`Option ${optionIndex + 1}`"
                  variant="outlined"
              />
              <v-select
                  v-model="questionForm.correctIndex"
                  :items="[
                      { title: 'Option 1', value: 0 },
                      { title: 'Option 2', value: 1 },
                      { title: 'Option 3', value: 2 },
                      { title: 'Option 4', value: 3 },
                  ]"
                  label="Correct answer"
                  variant="outlined"
              />
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn variant="text" @click="addQuestionDialog = false">Cancel</v-btn>
              <v-btn color="primary" variant="flat" @click="addQuestion">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>

        <v-dialog v-model="deleteQuestionDialog" max-width="500">
          <v-card>
            <v-card-title>Are you sure?</v-card-title>
            <v-card-text>
              Are you sure that you want to delete this question?
              <strong v-if="questionToDelete">{{ questionToDelete.question }}</strong>
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn variant="text" @click="deleteQuestionDialog = false">Cancel</v-btn>
              <v-btn color="error" variant="flat" @click="confirmRemove">Delete</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
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
  max-width: 1200px;
  margin: 0 auto;
}

.dashboard-row
{
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(0, 1fr);
  gap: 20px;
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

@media screen and (max-width: 800px) {
  #admin-dashboard
  {
    padding: 0;
  }
  .dashboard-row
  {
    grid-template-columns: 1fr;
  }
}
</style>
