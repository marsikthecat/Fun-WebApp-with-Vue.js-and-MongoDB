<script setup>
import {onMounted, onUnmounted, ref} from "vue";
const images = [
  "/graphicComponents/main/home-slider1.jpg",
  "/graphicComponents/main/home-slider2.jpg",
  "/graphicComponents/main/home-slider3.jpg",
  "/graphicComponents/main/home-slider4.jpg",
]
const childhoodImages = [
  "/graphicComponents/main/childhood1.jpg",
  "/graphicComponents/main/childhood2.jpg",
  "/graphicComponents/main/childhood3.jpg",
  "/graphicComponents/main/childhood4.jpg",
  "/graphicComponents/main/childhood5.jpg",
]
const childhoodDelay = (index) => {
  return index === 0 ? "0s" : `-${(childhoodImages.length - index) * 3}s`
}

const showIndex = ref(0);
const interval = () => showIndex.value = (showIndex.value + 1) % images.length;

let intervalId;
onMounted(() => intervalId = setInterval(interval, 8000));
onUnmounted(() => clearInterval(intervalId));
</script>

<template>
  <article>
    <section>
      <h2> Who is Marsik </h2>
        <div class="image-slider" >
          <figure :style="{ transform: `translateX(-${showIndex * 100}%)`}">
            <img v-for="(image, index) in images" :key="index" :src="image" alt="slider image">
          </figure>
        </div>
      <p> Marsik is one of the cuttest cats that have ever existed in the universe.
        He is fat and cute. Moreover, he likes to be a cat. <br>
        His smells like a cat as usual and when he wants to lay down and sleep,
        he looks like a big rat. </p>
      <p> Besides, Marsik likes to eat and is always hungry.
        If he goes sleep, he goes sleep and there are some particularly important sleeping positions. <br>
        Here is a list of his most important sleeping positions: </p>
      <ul>
        <li> Sphere </li>
        <li> Sack </li>
        <li> Corps (Flat Cat) </li>
        <li> Basic </li>
      </ul>
    </section>
    <section>
      <h2> Marsik's childhood </h2>
      <div class="childhoodBox">
        <img v-for="(image, index) in childhoodImages"
            :key="image" :src="image" :style="{ '--delay': childhoodDelay(index) }"
            alt="Marsik's childhood">
      </div>
    </section>
  </article>
</template>

<style scoped>
.image-slider
{
  width: 100%;
  overflow: hidden;
  position: relative;
  margin-bottom: 10px;
}
figure
{
  display: flex;
  margin: 0;
  transition: transform 4s ease-in-out;
}

.childhoodBox
{
  position: relative;
  aspect-ratio: 4 / 3;
  overflow: hidden;
}
.childhoodBox img
{
  position: absolute;
  inset: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  opacity: 0;
  animation: childhoodFade 15s ease-in-out infinite;
  animation-delay: var(--delay);
}
@keyframes childhoodFade {
  0%
  {
    opacity: 0;
  }
  4%, 16%
  {
    opacity: 1;
  }
  20%, 100%
  {
    opacity: 0;
  }
}
</style>

<script>
export default {
  name: 'Home'
};
</script>
