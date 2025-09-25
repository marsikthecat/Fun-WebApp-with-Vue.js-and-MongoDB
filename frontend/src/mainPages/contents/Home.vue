<script setup>
import {onMounted, onUnmounted, ref} from "vue";
const images = ["/graphicComponents/pictures/home-slider1.jpg",
                "/graphicComponents/pictures/home-slider2.jpg",
                "/graphicComponents/pictures/home-slider3.jpg",
                "/graphicComponents/pictures/home-slider4.jpg",]

const showIndex = ref(0);
const interval = () => showIndex.value = (showIndex.value + 1) % images.length;

onMounted(() => setInterval(() => interval(), 8000));
onUnmounted(() => clearInterval(interval()));
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
    <br>
    <section>
      <h2> Marsik wants to say something to you: </h2>
      <div class="Willkommen">
        <img src="/graphicComponents/pictures/welcome.jpg" style="box-shadow: none;" alt="willkommen" class="willkommen">
        <br>
        <h2> Marsik is: </h2>
        <ul>
          <li> A Cat </li>
          <li> A Cat</li>
          <li> A Cat </li>
          <li> And a Cat </li>
        </ul>
      </div>
    </section>
    <br>
    <section>
      <h2> Marsik's childhood </h2>
      <div id="childhoodBox">
        <img src="/graphicComponents/pictures/childhood1.jpg" alt="childhood">
        <img src="/graphicComponents/pictures/childhood2.jpg" alt="childhood">
        <img src="/graphicComponents/pictures/childhood3.jpg" alt="childhood">
        <img src="/graphicComponents/pictures/childhood4.jpg" alt="childhood">
        <img src="/graphicComponents/pictures/childhood5.jpg" alt="childhood">
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
}
figure
{
  display: flex;
  margin: 0;
  transition: transform 4s ease-in-out;
}

#childhoodBox
{
  position: relative;
  height: calc(70px + 30vw);
}
#childhoodBox img
{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  animation: fadeOut cubic-bezier(0.3, 1, 0.7, 4) infinite;
  animation-duration: 14s;
}
#childhoodBox img:nth-child(1)
{
  animation-delay: 4s;
}
#childhoodBox img:nth-child(2)
{
  animation-delay: 6s;
}
#childhoodBox img:nth-child(3)
{
  animation-delay: 8s;
}
#childhoodBox img:nth-child(4)
{
  animation-delay: 10s;
}
#childhoodBox img:nth-child(5)
{
  animation-delay: 12s;
}
@keyframes fadeOut {
  0%
  {
    opacity: 0;
  }
  50%
  {
    opacity: 1;
  }
  100%
  {
    opacity: 0;
  }
}
</style>

<script>
export default {
  name: 'Home',
};
</script>