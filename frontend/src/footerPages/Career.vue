<template>
  <div class="card">
    <h2> Shape your Future and become an slave of Marsik the kitty:</h2>
    <img src="/graphicComponents/footer/Stellenangebot.jpg" alt="careerPicture">
    <p class="title"> We are desperately searching a: <br><b> Cat Litter Box Management Specialist</b> </p>
    <button @click="customPopup('Fatal error:', 'Could not find a job', true)">Apply now</button>
  </div>
  <div id="containerLeft">
    <h3> Your qualifications:</h3>
    <ul>
      <li> You are flexible </li>
      <li> You are very resilient </li>
      <li> You are reliable </li>
      <li> You have the general university entrance qualification </li>
      <li> You have many years of experience in garbage collection </li>
      <li> You have a driver's license </li>
    </ul>
  </div>
  <div id="containerMiddle">
    <h3> What we offer you: </h3>
    <ul>
      <li> Smelly working atmosphere </li>
      <li> 200 cans of cat food for you </li>
      <li> Regular bank account deductions </li>
      <li> Penalty interest </li>
      <li> Good chances of descent </li>
      <li> 30 days holiday in the desert (without water) </li>
      <li> Well-founded exploitation for career changers </li>
    </ul>
  </div>
  <div id="containerRight">
    <h3> We are not looking forward to receiving your application </h3>
    <p> If you have any questions, please contact Marsik by the helicopter</p>
  </div>
</template>

<script>
import {customPopup} from "../../assets/features.js";

export default {
  name: "Career",
  methods: {customPopup}
}
</script>

<style scoped>
ul
{
  margin: 0 20px 0 25px;
}
h2
{
  font-size: 1.85em;
}
.card
{
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 900px;
  margin: auto;
  text-align: center;
}
.title
{
  color: #4f4f4f;;
  font-size: 1.25em;
  padding: 2px 8px;
}
h3
{
  text-align: center;
}
img
{
  width: 100%;
  max-width: 600px;
  transform-origin: center center;
}
button
{
  font-size: 1.2em;
  padding: 8px;
  color: white;
  background-color: #000;
  cursor: pointer;
  width: 100%;
  margin-top: 0.5em;
}
#containerLeft, #containerMiddle, #containerRight
{
  width: 33.3%;
  float: left;
}
#containerRight p
{
  padding: 0 8px 10px 20px;
}
@media screen and (max-width: 780px)
{
  #containerLeft, #containerMiddle, #containerRight
  {
    width: 100%;
    float: none;
    padding: 10px 0 10px 0;
  }
}
</style>