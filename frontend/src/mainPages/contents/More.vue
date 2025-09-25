<script setup>
import {ref} from "vue";
import {customPopup} from "../../../assets/features.js";

const text = ref('')
const isExtensionVisible = ref(false);

const insert = (value) => text.value += value;
const clear = () => text.value = '';
const wurzel = () => text.value = Math.sqrt(Number(text.value)).toString()
const pow = () =>  text.value = Math.pow(Number(text.value), 2).toString()
const equal = () => text.value = eval(text.value)
const remove = () => {
  let string = String(text.value);
  text.value = string.length > 0 ? string.substring(0,string.length - 1) : string;
}
const NthSqrt = () => {
  const a = prompt("Type in n:");
  if (isNaN(Number(a))) {
    customPopup("Error",a + " is not a number", false)
  } else {
    text.value = Math.pow(Number(text.value), 1/a).toFixed(3);
  }
}
const powNumber = (exp) => {
  const n = prompt("Type in n:");
  if (isNaN(Number(n))) {
    customPopup("Error",n + " is not a number", false)
  } else {
    text.value = Math.pow(exp, Number(n)).toFixed(3);
  }
}
function faculty(exp) {
  return exp < 1 ? 1 : exp * faculty(exp-1);
}

function calculateExpression(string) {
  const input = text.value;
  if (isNaN(Number(input))) {
    customPopup("Error",input + " is not a number", false)
  } else {
    switch (string) {
      case "sin": {
        text.value = Math.sin(text.value * Math.PI / 180).toFixed(3);
        break;
      }
      case "cos": {
        text.value = Math.cos(text.value * Math.PI / 180).toFixed(3)
        break;
      }
      case "tan": {
        text.value = Math.tan(text.value * Math.PI / 180).toFixed(3)
        break;
      }
      case "sin-1": {
        text.value = Math.asin(text.value * 180 / Math.PI).toFixed(3);
        break;
      }
      case "cos-1": {
        text.value = Math.acos(text.value * 180 / Math.PI).toFixed(3);
        break;
      }
      case "tan-1": {
        text.value = Math.atan(text.value * 180 / Math.PI).toFixed(3);
        break;
      }
      case "10^x": {
        text.value = Math.pow(10, text.value).toFixed(3);
        break;
      }
      case "e^x": {
        text.value = Math.pow(Math.E, text.value).toFixed(3);
        break;
      }
      case "ln": {
        text.value = Math.log(text.value).toFixed(3);
        break;
      }
      case "log": {
        text.value = Math.log2(text.value).toFixed(3);
        break;
      }
      case "x^-1": {
        text.value = Math.pow(text.value, -1).toFixed(3);
        break;
      }
      case "n√x": {
        NthSqrt(text.value);
        break;
      }
      case "x^n": {
        powNumber(text.value);
        break;
      }
      case "f": {
        text.value = faculty(text.value).toFixed(3);
        break;
      }
    }
  }
}
const toggleExtension = () => isExtensionVisible.value = !isExtensionVisible.value;
</script>

<template>
    <article>
      <section>
        <h2> Marsik's calculator </h2>
        <table class="Tabelle">
          <tr>
            <th colspan="4">
              <input type="text" id="textview" v-model="text" readonly>
            </th>
          </tr>
          <tr>
            <td> <input type="button" value="C" @click="clear()" @dblclick="toggleExtension"> </td>
            <td> <input type="button" value="<" @click="remove()"> </td>
            <td> <input type="button" value="√" @click="wurzel()"> </td>
            <td> <input type="button" value="x²" @click="pow()"> </td>
          </tr>
          <tr>
            <td> <input type="button" value="7" @click="insert(7)"> </td>
            <td> <input type="button" value="8" @click="insert(8)"> </td>
            <td> <input type="button" value="9" @click="insert(9)"> </td>
            <td> <input type="button" value="/" @click="insert('/')"> </td>
          </tr>
          <tr>
            <td> <input type="button" value="4" @click="insert(4)"> </td>
            <td> <input type="button" value="5" @click="insert(5)"> </td>
            <td> <input type="button" value="6" @click="insert(6)"> </td>
            <td> <input type="button" value="*" @click="insert('*')"> </td>
          </tr>
          <tr>
            <td> <input type="button" value="1" @click="insert(1)"> </td>
            <td> <input type="button" value="2" @click="insert(2)"> </td>
            <td> <input type="button" value="3" @click="insert(3)"> </td>
            <td> <input type="button" value="-" @click="insert('-')"> </td>
          </tr>
          <tr>
            <td> <input type="button" value="0" @click="insert(0)"> </td>
            <td> <input type="button" value="." @click="insert('.')"> </td>
            <td> <input type="button" value="=" @click="equal()"> </td>
            <td> <input type="button" value="+" @click="insert('+')"> </td>
          </tr>
        </table>
      </section>
      <br>
      <section>
        <h2> Marsik's clock </h2>
          <div class="clock">
            <div class="hour" :style="rotateHours"></div>
            <div class="minute" :style="rotateMinutes"></div>
            <div class="second" :style="rotateSeconds"></div>
            <div class="number number1">1</div>
            <div class="number number2">2</div>
            <div class="number number3">3</div>
            <div class="number number4">4</div>
            <div class="number number5">5</div>
            <div class="number number6">6</div>
            <div class="number number7">7</div>
            <div class="number number8">8</div>
            <div class="number number9">9</div>
            <div class="number number10">10</div>
            <div class="number number11">11</div>
            <div class="number number12">12</div>
          </div>
      </section>
    </article>
  <div class="mathSidebar" v-show="isExtensionVisible">
    <h2>Extensions (advanced Math)</h2>
    <input type="button" value="π" @click="insert(Math.PI)">
    <input type="button" value="n√x" @click="calculateExpression('n√x')">
    <input type="button" value="sin" @click="calculateExpression('sin')">
    <input type="button" value="cos" @click="calculateExpression('cos')">
    <input type="button" value="tan" @click="calculateExpression('tan')">
    <input type="button" value="sin-1" @click="calculateExpression('sin-1')">
    <input type="button" value="cos-1" @click="calculateExpression('cos-1')">
    <input type="button" value="tan-1" @click="calculateExpression('tan-1')">
    <input type="button" value="x^−1" @click="calculateExpression('x^-1')">
    <input type="button" value="x^n" @click="calculateExpression('x^n')">
    <input type="button" value="10^x" @click="calculateExpression('10^x')">
    <input type="button" value="e" @click="insert(Math.E)">
    <input type="button" value="Log&#178" @click="calculateExpression('log')">
    <input type="button" value="ln" @click="calculateExpression('ln')">
    <input type="button" value="e^x" @click="calculateExpression('e^x')">
    <input type="button" value="x!" @click="calculateExpression('f')">
    <input type="button" value="φ" @click="insert(1.6180339887499)">
  </div>
</template>
<script>
export default {
  name: 'More',

  data() {
    return {
      angleSecond: 0,
      angleMinute: 0,
      angleHour: 0,
      interval: null,
    };
  },
  computed: {
    rotateSeconds() {
      return {
        transform: `rotate(${this.angleSecond}deg)`,
      };
    },
    rotateMinutes() {
      return {
        transform: `rotate(${this.angleMinute}deg)`,
      };
    },
    rotateHours() {
      return {
        transform: `rotate(${this.angleHour}deg)`,
      };
    }
  },
  methods: {
    rotateForSeconds() {
      this.interval = setInterval(() => {
        const date = new Date();
        const currentSeconds = date.getSeconds() + 1;
        this.angleSecond = currentSeconds * 6
        this.angleMinute = date.getMinutes() * 6 + (currentSeconds / 6);
        this.angleHour = date.getHours() * 30 + (date.getMinutes() / 2);
      }, 1000)
    }
  },
  mounted() {
    this.rotateForSeconds();
  }
};
</script>

<style scoped>
.Tabelle
{
  background-color: black;
  width: 100%;
  max-width: 250px;
  margin: 15px auto;
}
#textview
 {
   width: 97%;
   font-size: 2em;
   padding: 5px 1%;
   border: none;
   background-color: gray;
   color: white;
 }
.Tabelle tr td input
{
  width: 100%;
  cursor: pointer;
  background-color: orange;
  font-size: 2em;
}
.Tabelle tr td input:hover
{
  border: 2px solid red;
}
.Tabelle tr td input:active
{
  background-color: yellow;
}

.mathSidebar
{
  width: 100%;
  text-align: center;
  position: fixed;
  z-index: 2;
  bottom: 0;
  background-color: rgb(255 255 255 / 78%);
}
.mathSidebar input
{
  font-size: 1.5em;
  padding: 10px;
  cursor: pointer;
  background-color: salmon;
  border-radius: 7px;
  margin: 5px;
}
.mathSidebar input:hover
{
  border: 2px solid red;
}
.mathSidebar input:active
{
  background-color: sandybrown;
}

.clock
{
  width: 80vw;
  height: 80vw;
  max-width: 350px;
  max-height: 350px;
  background-color: aqua;
  border-radius: 50%;
  border: 2px solid black;
  position: relative;
  margin: 15px auto;
}
.clock .number
{
  --rotation: 0;
  position: absolute;
  width: 100%;
  height: 100%;
  font-size: 1.5em;
  font-weight: 700;
  text-align: center;
  transform: rotate(var(--rotation))
}
.clock .number1
{
  --rotation: 30deg;
}
.clock .number2
{
  --rotation: 60deg;
}
.clock .number3
{
  --rotation: 90deg;
}
.clock .number4
{
  --rotation: 120deg;
}
.clock .number5
{
  --rotation: 150deg;
}
.clock .number6
{
  --rotation: 180deg;
}
.clock .number7
{
  --rotation: 210deg;
}
.clock .number8
{
  --rotation: 240deg;
}
.clock .number9
{
  --rotation: 270deg;
}
.clock .number10
{
  --rotation: 300deg;
}
.clock .number11
{
  --rotation: 330deg;
}
.second, .minute, .hour
{
  --rotation: 0;
  position: absolute;
  bottom: 50%;
  left: 50%;
  background-color: black;
  z-index: 1;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  transform-origin: bottom;
  transition: 0.3s ease-in-out;
}
.second
{
  width: 3px;
  height: 45%;
  background-color: red
}
.minute
{
  width: 7px;
  height: 40%;
}
.hour
{
  width: 10px;
  height: 35%;
}
@media (max-width: 288px)
{
  .hour
  {
    width: 6px;
  }
  .minute
  {
    width: 5px;
  }
}
</style>