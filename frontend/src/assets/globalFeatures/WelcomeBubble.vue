<template>
  <transition name="bubble-pop">
    <div v-if="showBubble" class="welcome-bubble-container">
      <div class="welcome-bubble">
        <div class="bubble-tail"></div>
        <div class="bubble-content">
          <v-icon class="bubble-icon" color="primary" size="x-large">mdi-hand-wave</v-icon>
          <p class="bubble-text">Welcome back, <strong>{{ username }}</strong>!</p>
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const showBubble = ref(false)
const username = ref('')

onMounted(() => {
  const welcomeShown = sessionStorage.getItem('welcomeShown')
  
  if (welcomeShown !== 'true') {
    username.value = sessionStorage.getItem('username') || 'Friend'
    showBubble.value = true
    sessionStorage.setItem('welcomeShown', 'true')
    
    // Auto-hide after 4 seconds
    setTimeout(() => {
      showBubble.value = false
    }, 6000)
  }
})
</script>

<style scoped>
.welcome-bubble-container 
{
  position: fixed;
  bottom: 100px;
  right: 30px;
  z-index: 1000;
  pointer-events: all;
}

.welcome-bubble 
{
  display: flex;
  align-items: flex-end;
  gap: 10px;
  animation: slideUp 0.5s cubic-bezier(0.34, 1.56, 0.64, 1) forwards;
}

.bubble-tail 
{
  width: 0;
  height: 0;
  border-left: 12px solid transparent;
  border-right: 0px solid transparent;
  border-top: 15px solid white;
  animation: tailAppear 0.3s ease-out 0.2s forwards;
  opacity: 0;
}

.bubble-content 
{
  background: white;
  border-radius: 20px;
  padding: 16px 20px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
  display: flex;
  align-items: center;
  gap: 12px;
  max-width: 280px;
  animation: popIn 0.5s cubic-bezier(0.34, 1.56, 0.64, 1) forwards;
}

.bubble-icon 
{
  flex-shrink: 0;
  animation: wave 0.6s ease-in-out 0.8s infinite;
  transform-origin: 70% 70%;
}

.bubble-text 
{
  margin: 0;
  font-size: 14px;
  color: #333;
  line-height: 1.4;
  font-weight: 500;
}

.bubble-text strong 
{
  color: #1976d2;
  font-weight: 600;
}

@keyframes slideUp {
  from {
    transform: translateY(20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes popIn {
  0% {
    transform: scale(0.8);
    opacity: 0;
  }
  70% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes tailAppear {
  from {
    opacity: 0;
    transform: scale(0);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

@keyframes wave {
  0%, 100% {
    transform: rotate(0deg);
  }
  25% {
    transform: rotate(20deg);
  }
  75% {
    transform: rotate(-20deg);
  }
}

.bubble-pop-enter-active 
{
  animation: slideUp 0.5s cubic-bezier(0.34, 1.56, 0.64, 1),
            popIn 0.5s cubic-bezier(0.34, 1.56, 0.64, 1);
}

.bubble-pop-leave-active 
{
  animation: bubbleFade 0.4s ease-out forwards;
}

@keyframes bubbleFade {
  from {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
  to {
    opacity: 0;
    transform: translateY(10px) scale(0.95);
  }
}

@media (max-width: 600px) {
  .welcome-bubble-container 
  {
    bottom: 80px;
    right: 15px;
    left: 15px;
  }

  .bubble-content 
  {
    max-width: 100%;
  }
}
</style>
