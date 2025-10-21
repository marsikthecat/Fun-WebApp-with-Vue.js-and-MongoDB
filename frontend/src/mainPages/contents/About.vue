<script setup>
import {onMounted, onUnmounted, ref, watchEffect} from "vue";
const images = ref([
  "/graphicComponents/pictures/20150814_120245.jpg",
  "/graphicComponents/pictures/20150828_120900.jpg",
  "/graphicComponents/pictures/20150814_120317.jpg",
  "/graphicComponents/pictures/20150828_120939.jpg",
  "/graphicComponents/pictures/255.jpg",
  "/graphicComponents/pictures/254.jpg"
]);

const i = ref(0);
let intervalId = null;

onMounted(() => {
  intervalId = setInterval(() => i.value = (i.value + 1) % images.value.length, 4000);
});
onUnmounted(() => {
  if (intervalId) {
    clearInterval(intervalId);
  }
});
watchEffect(() => {
  if (document.visibilityState === "hidden" && intervalId) {
    clearInterval(intervalId);
  }
});

const index = ref(0);
const urlSuperGallery = [
  "/graphicComponents/pictures/supergalerie1.jpg",
  "/graphicComponents/pictures/supergalerie2.jpg",
  "/graphicComponents/pictures/supergalerie3.jpg",
  "/graphicComponents/pictures/supergalerie4.jpg",
];
const front = () => index.value = (index.value + 1) % urlSuperGallery.length;
const back = () => index.value = index.value !== 0 ? (index.value - 1) : urlSuperGallery.length - 1;
</script>

<template>
    <article>
      <section>
        <h2> Marsik´s life </h2>
        <p> Marsik has an exciting daily routine. You can get an insight into Marsik's everyday life by looking at the pictures below. </p> <br>
        <img :src="images[i]" id="slideImage" alt="Slideshow Image">
      </section>
        <br>
      <section>
        <h2>Marsik's daily routine  </h2>
        <p> If you would like to know how Marsik's exciting and exciting daily routine is structured,
          just take a look at the table below to understand Marsik's daily routine. </p> <br>
        <table>
          <tr>
            <th style="background-color:#198620"> Time</th>
            <th style="background-color:#f29b18"> Action </th>
          </tr>
          <tr>
            <td class="td1"> At 6:00 </td>
            <td class="td2"> Getting up, walking around, meowing after food, farting </td>
          </tr>
          <tr>
            <td class="td1"> 6:30-12:30 </td>
            <td class="td2"> Walking outside, catching mice or birds or sleeping like a sphere at home all day</td>
          </tr>
          <tr>
            <td class="td1"> 12:30-13:00  </td>
            <td class="td2"> Meowing after food, lunch </td>
          </tr>
          <tr>
            <td class="td1"> 13:00-16:00 </td>
            <td class="td2"> Sleeping or playing outside all the time </td>
          </tr>
          <tr>
            <td class="td1"> 16:00-20:00 </td>
            <td class="td2"> Laying on the sofa like a sphere </td>
          </tr>
          <tr>
            <td class="td1"> 20:00-5:00 </td>
            <td class="td2"> Brushing your teeth, undressing, taking a shower and secretly sneaking to the owner's bed and sleeping on it</td>
          </tr>
          <tr>
            <td class="td1"> 5:00-6:00  </td>
            <td class="td2"> Going away and continue sleeping somewhere else </td>
          </tr>
        </table>
      </section>
       <br>
      <section>
        <h2>Impressions from Marsik's daily routine</h2>
          <img :src="urlSuperGallery[index]" id="superGallery" alt="">
          <div id="galleryBtnBar">
            <button @click="back" id="galleryBtnBack"><i class="fa fa-arrow-left"></i></button>
            <button @click="front" id="galleryBtnFront"><i class="fa fa-arrow-right"></i></button>
          </div>
      </section>
    </article>
</template>

<script>
export default {
  name: 'About'
};
</script>

<style scoped>
table
{
  border-collapse: collapse;
  border: 1px solid black;
}
th,td
{
  border: 1px solid black;
  padding: 8px;
}
.td1
{
  background-color: #2afa3d;
}
.td2
{
  background-color: #e1ff1b;
}
#galleryBtnBar
{
  text-align: center;
}
#galleryBtnBack, #galleryBtnFront
{
  border-radius: 16px;
  width: 2em;
  font-size: 2.5em;
  background: burlywood;
  margin: 0.5em 0.3em 0 0.3em;
  box-shadow: 4px 4px 10px -5px;
}
#galleryBtnBack:hover, #galleryBtnFront:hover
{
  background: #d59f5b;
}
#galleryBtnBack i, #galleryBtnFront i
{
  font-size: 0.8em;
}
</style>