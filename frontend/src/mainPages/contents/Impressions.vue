<script setup>
import {onMounted, onUnmounted, ref, shallowRef, watchEffect} from "vue";
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
  if (intervalId) clearInterval(intervalId);
});
watchEffect(() => {
  if (document.visibilityState === "hidden" && intervalId) {
    clearInterval(intervalId);
  }
});

const gallery = [
  {
    src: "/graphicComponents/pictures/supergalerie1.jpg",
    label: "Marsik is a night-cat"
  },
  {
    src: "/graphicComponents/pictures/supergalerie2.jpg",
    label: "Marsik the hidden fir tree kitten"
  },
  {
    src: "/graphicComponents/pictures/supergalerie3.jpg",
    label: "Marsik lies like a corpse"
  },
  {
    src: "/graphicComponents/pictures/supergalerie4.jpg",
    label: "Marsik wants to poop"
  },
  {
    src: "/graphicComponents/pictures/supergalerie5.jpg",
    label: "Marsik wants something from you"
  }
]
const currentIndex = shallowRef(0);
const dailyRoutine = [
  {
    time: "At 6:00",
    action: "Getting up, walking around, meowing after food, farting",
  },
  {
    time: "6:30-12:30",
    action: "Walking outside, catching mice or birds or sleeping like a sphere at home all day",
  },
  {
    time: "12:30-13:00",
    action: "Meowing after food, lunch",
  },
  {
    time: "13:00-16:00",
    action: "Sleeping or playing outside all the time",
  },
  {
    time: "16:00-20:00",
    action: "Laying on the sofa like a sphere",
  },
  {
    time: "20:00-5:00",
    action: "Brushing your teeth, undressing, taking a shower and secretly sneaking to the owner's bed and sleeping on it",
  },
  {
    time: "5:00-6:00",
    action: "Going away and continue sleeping somewhere else",
  },
];
</script>

<template>
    <article>
      <section>
        <h2>Impressions from Marsik´s life </h2>
        <p> Marsik has an exciting daily routine. You can get an insight into Marsik's everyday life by looking at the pictures below. </p> <br>
        <img :src="images[i]" id="slideImage" alt="Slideshow Image">
      </section>
        <br>
      <section>
        <h2>Marsik's daily routine  </h2>
        <p> If you would like to know how Marsik's exciting and exciting daily routine is structured,
          just take a look at the table below to understand Marsik's daily routine. </p> <br>
        <v-table class="routineTable" density="compact">
          <thead>
            <tr>
              <th class="timeHeader">Time</th>
              <th class="actionHeader">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in dailyRoutine" :key="item.time">
              <td class="td1">{{ item.time }}</td>
              <td class="td2">{{ item.action }}</td>
            </tr>
          </tbody>
        </v-table>
      </section>
       <br>
      <section>
        <h2>Impressions from Marsik's daily routine</h2>
        <v-carousel v-model="currentIndex" hide-delimiter-background height="auto">
          <template v-slot:prev="{ props }">
            <v-btn id="galleryBtnBack" @click="props.onClick"><i class="fa fa-arrow-left"></i></v-btn>
          </template>
          <template v-slot:next="{ props }">
            <v-btn id="galleryBtnFront" @click="props.onClick"><i class="fa fa-arrow-right"></i></v-btn>
          </template>
          <v-carousel-item v-for="(item, index) in gallery" :key="index" :src="item.src" cover :alt="item.label">
            <v-overlay :scrim="false" content-class="w-100 h-100 d-flex flex-column align-center justify-space-between pointerpass-through py-3"
            contained model-value no-click-animation persistent>
              <v-sheet :key="currentIndex" color="#222222" rounded="pill">
                <v-list-item :title="item.label" base-color="white" >
                </v-list-item>
              </v-sheet>
            </v-overlay>
          </v-carousel-item>
        </v-carousel>
      </section>
    </article>
</template>

<script>
export default {
  name: 'About'
};
</script>

<style scoped>
.routineTable
{
  border: 1px solid black;
}
.routineTable :deep(table)
{
  border-collapse: collapse;
}
.routineTable :deep(th),
.routineTable :deep(td)
{
  border: 1px solid black;
  padding: 8px;
}
.timeHeader
{
  background-color: #198620;
}
.actionHeader
{
  background-color: #f29b18;
}
.td1
{
  background-color: #2afa3d;
}
.td2
{
  background-color: #e1ff1b;
}
#galleryBtnBack, #galleryBtnFront
{
  height: 50px;
  min-width: 20px;
  font-size: 1.3em;
  width: 20px;
  color: white;
  background: #5a5a5a;
  margin-left: -15px;
}
#galleryBtnFront
{
  margin-right: -15px;
}
#galleryBtnBack:hover, #galleryBtnFront:hover
{
  background: #6a6a6a;
}
#galleryBtnBack i, #galleryBtnFront i
{
  font-size: 0.8em;
}
</style>