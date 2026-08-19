<template>
  <v-app id="app">
    <CustomPopup />
    <router-view></router-view>
  </v-app>
</template>

<script>
import CustomPopup from "../assets/globalFeatures/CustomPopup.vue";

export default {
  name: 'App',
  components: {CustomPopup},
};
</script>
