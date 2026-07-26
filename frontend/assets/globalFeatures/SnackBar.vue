<script setup>

import { ref } from "vue";

const snackBars = ref();
const messages = ref([]);

function pushSnackBar(message, mode) {
  let color;
  if (mode === "success" || mode === "warning" || mode === "error" || mode === "info") {
    color = mode;
  } else {
    color = "default";
  }
  messages.value.push({
    text: message, color
  });
}

defineExpose({
  pushSnackBar,
})
</script>

<template>
  <v-snackbar-queue
      ref="snackBars"
      v-model="messages"
      :total-visible="5"
      closable
      location="bottom right"
      timeout="5000"
  ></v-snackbar-queue>

</template>
<script>
export default {
  name: "SnackBar",
}
</script>