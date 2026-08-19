<script setup>
defineProps({
  mode: {
    type: String,
    required: true,
  },
  loginState: {
    type: Object,
    required: true,
  },
  signUpState: {
    type: Object,
    required: true,
  },
  userNameInputRules: {
    type: Array,
    required: true,
  },
  passwordInputRules: {
    type: Array,
    required: true,
  },
});
</script>

<template>
  <template v-if="mode === 'login'">
    <v-text-field
      v-model="loginState.userNameInput"
      label="Your Name"
      autocomplete="username"
    />
    <v-text-field
      v-model="loginState.passwordInput"
      label="Your Password"
      autocomplete="current-password"
      :append-inner-icon="loginState.passwordHidden ? 'mdi-eye-off' : 'mdi-eye'"
      :type="loginState.passwordHidden ? 'password' : 'text'"
      @click:append-inner="loginState.passwordHidden = !loginState.passwordHidden"
    />
  </template>

  <template v-else>
    <v-text-field
      v-model="signUpState.newUserNameInput"
      :rules="userNameInputRules"
      label="Enter your name"
    />
    <v-text-field
      v-model="signUpState.newPasswordInput"
      :rules="passwordInputRules"
      label="Enter your Password"
      :append-inner-icon="signUpState.passwordHidden ? 'mdi-eye-off' : 'mdi-eye'"
      :type="signUpState.passwordHidden ? 'password' : 'text'"
      @click:append-inner="signUpState.passwordHidden = !signUpState.passwordHidden"
    />
    <v-text-field
      v-model="signUpState.newPasswordInputConfirmed"
      label="Repeat your Password"
      :append-inner-icon="signUpState.passwordConfirmedHidden ? 'mdi-eye-off' : 'mdi-eye'"
      :type="signUpState.passwordConfirmedHidden ? 'password' : 'text'"
      @click:append-inner="signUpState.passwordConfirmedHidden = !signUpState.passwordConfirmedHidden"
    />
  </template>
</template>

<script>
export default {
  name: "LoginFields",
};
</script>
