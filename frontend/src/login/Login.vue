<script setup>
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";
import {customPopup} from "../../assets/features.js";
import SnackBar from "../../assets/globalFeatures/SnackBar.vue"
import {useApi} from "../composables/useApi.js";

const snackBar = ref()
const api = useApi();

const router = useRouter();

const loginState = reactive({
  userNameInput: '',
  passwordInput: '',
  passwordHidden: true
})

const signUpState = reactive({
  newUserNameInput: '',
  newPasswordInput: '',
  newPasswordInputConfirmed: '',
  furBallAccepted: false,
  passwordHidden: true,
  passwordConfirmedHidden: true
})
const modelVisible = ref(false);

const login = async () => {
  try {
    const res = await api.login(loginState.userNameInput, loginState.passwordInput);
    if (res.status === 200) {
      sessionStorage.setItem("token", res.data.token);
      if (res.data.isAdmin) {
        await router.push("/admin");
      } else {
        await router.push('/main/home');
      }
    }
  } catch (error) {
    snackBar.value.pushSnackBar("Login failed: " + error.message, "error");
  }
}

const submit = async () =>  {
  if (!signUpState.furBallAccepted) {
    snackBar.value.pushSnackBar("Sign up failed: You need to allow Marsik to be a fur ball", "error");
    customPopup("Fatal Error", "Sign up failed: You need to allow Marsik to be a fur ball", true );
    return;
  }
  const username = signUpState.newUserNameInput;
  const password = signUpState.newPasswordInput;
    try {
      await api.register(username, password);
      snackBar.value.pushSnackBar("Sign up successful. Welcome " + username, "success")
    } catch (error) {
      if (error.status === 409) {
        snackBar.value.pushSnackBar("You are already registed", "error");
        return;
      }
      snackBar.value.pushSnackBar("Sign up failed: " + error.message, "error");
      customPopup("Fatal Error", "Sign up failed: " + error, true );
    }
}

const userNameInputRules = [
    newUserNameInput => {
      if (newUserNameInput.length < 5) {
        return 'Your username needs at least 5 characters'
      }
      return true;
    }
]
const passwordInputRules = [
    newPasswordInput => {
      if (newPasswordInput.length < 10) {
        return 'Your password needs at least 10 characters'
      }
      if (!/(?=(.*[A-Z]){3})/.test(newPasswordInput)) {
        return 'Your password needs at least 3 uppercase characters'
      }
      if (!/(?=(.*[a-z]){3})/.test(newPasswordInput)) {
        return 'Your password needs at least 3 lowercase characters'
      }
      if (!/(?=(.*\d){2})/.test(newPasswordInput)) {
        return 'Your password needs at least 3 numbers'
      }
      if (!/(?=.*[^A-Za-z0-9])/.test(newPasswordInput)) {
        return 'Your password needs at least one special character'
      }
      if (newPasswordInput !== signUpState.newPasswordInputConfirmed) {
        return 'Password and repeated Password are not equal'
      }
      submit();
      return true;
    }
]
</script>

<template>
  <div id="global">
    <SnackBar ref="snackBar" />
    <div id="container">
      <h2>Are you ready for Marsik?</h2>
        <v-form>
          <v-text-field v-model="loginState.userNameInput" label="Your Name" autocomplete="username"></v-text-field>
          <v-text-field :append-inner-icon="loginState.passwordHidden ? 'mdi-eye-off' : 'mdi-eye' " 
            :type="loginState.passwordHidden ? 'password' : 'text' " v-model="loginState.passwordInput" 
            label="Your Password" autocomplete="current-password"
             @click:append-inner="loginState.passwordHidden = !loginState.passwordHidden"></v-text-field>
          <v-btn @click="login()" text="Login"></v-btn>
        </v-form>
        <v-card-text class="text-center">
          Are you new here? <a @click="modelVisible = true" class="text-blue text-decoration-none cursor-pointer">Sign up</a>
        </v-card-text>
    </div>

    <div class="modal" v-if="modelVisible">
      <span @click="modelVisible = false" class="close" title="Close Sign in">&times;</span>
      <div class="modal-content">
        <div class="container">
          <h2>Sign Up</h2>
          <p>Please fill in this form to create an account.</p>
          <hr>
          <v-sheet>
            <v-form @submit.prevent validate-on="submit lazy">
              <v-text-field :rules="userNameInputRules" v-model="signUpState.newUserNameInput" label="Enter your name"></v-text-field>
              <v-text-field :rules="passwordInputRules" v-model="signUpState.newPasswordInput" label="Enter your Password"
                            :append-inner-icon="signUpState.passwordHidden ? 'mdi-eye-off' : 'mdi-eye' "
                            :type="signUpState.passwordHidden ? 'password' : 'text' "
                            @click:append-inner="signUpState.passwordHidden = !signUpState.passwordHidden"></v-text-field>
              <v-text-field v-model="signUpState.newPasswordInputConfirmed" label="Repeat your Password"
                            :append-inner-icon="signUpState.passwordConfirmedHidden ? 'mdi-eye-off' : 'mdi-eye' "
                            :type="signUpState.passwordConfirmedHidden ? 'password' : 'text' "
                            @click:append-inner="signUpState.passwordConfirmedHidden = !signUpState.passwordConfirmedHidden"></v-text-field>
              <v-checkbox color="primary" v-model="signUpState.furBallAccepted" label="By creating an account you allow Marsik the Cat to be a fur ball"></v-checkbox>
              <v-btn text="Sign up" type="submit" class="mb-3"></v-btn>
            </v-form>
          </v-sheet>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#global
{
  background: linear-gradient(to right, #6dd5ed, #2193b0);
  height: 100vh;
  align-content: center;
}
#container
{
  background-color: #fff;
  padding: 0 20px;
  border-radius: 15px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
  margin: 0 auto;
}
#container button, .modal .modal-content .container button
{
  padding: 10px 30px 10px 30px;
  background: blue;
  color: white;
  font-size: 1.25em;
  border-radius: 10px;
}
#container button:hover, .modal .modal-content .container button
{
  background: #2f58fa;
}

.modal
{
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: #474e5d;
  padding-top: 50px;
}
.close
{
   position: absolute;
   right: 20px;
   top: 15px;
   font-size: 2em;
   font-weight: bold;
   color: #f1f1f1;
   cursor: pointer;
}
.modal-content
{
  background-color: #fefefe;
  margin: 10px auto;
  width: 100%;
  max-width: 600px;
}
hr
{
  border: 1px solid #666666;
}
.modal .modal-content .container
{
  padding: 0 15px;
}
</style>
