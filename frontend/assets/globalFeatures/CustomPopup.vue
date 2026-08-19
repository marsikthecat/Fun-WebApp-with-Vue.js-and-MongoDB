<script setup>
import {isPopupVisible, handlePopup, popup} from "../features.js";

const popupIcons = {
  info: "mdi-information",
  warning: "mdi-alert",
  error: "mdi-alert-circle",
};
</script>

<template>
  <v-dialog v-model="isPopupVisible" max-width="520" persistent>
    <v-card :color="popup.isCritical ? 'error' : undefined" rounded="lg">
      <v-card-title class="d-flex align-center ga-3 text-wrap">
        <v-icon :icon="popupIcons[popup.icon]" :color="popup.isCritical ? 'white' : popup.icon" />
        <span>{{ popup.title }}</span>
        <v-spacer />
        <v-btn
          aria-label="Close message"
          icon="mdi-close"
          variant="text"
          :color="popup.isCritical ? 'white' : undefined"
          @click="handlePopup(false)"
        />
      </v-card-title>
      <v-card-text class="text-body-1 text-wrap">{{ popup.content }}</v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn
          :color="popup.isCritical ? 'white' : popup.icon"
          variant="text"
          @click="handlePopup(false)"
        >
          OK
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "CustomPopup",
}
</script>