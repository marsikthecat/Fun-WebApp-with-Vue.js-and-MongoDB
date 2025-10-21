<script setup>
 import {ref} from "vue";

 const sectionContents = ["Introduction", "Use of the Website", "Login", "Main conditions",
   "Copyright", "What we expect from you", "Interaction with Marsik", "Technical framework conditions"];
 const superPopupShown = ref(false);
 const title = ref('');
 const mainText = ref('');
 const show = (index) => {
   superPopupShown.value ^= true;
   if (superPopupShown.value) {
     const content = document.querySelectorAll(".content")[index];
     title.value = sectionContents[index] ?? "";
     mainText.value = content?.textContent || "";
   }
 };
</script>

<template>
  <div id="overLay" v-if="superPopupShown"></div>
  <article>
    <div class="superPopUp" :class="{show: superPopupShown}" @click="superPopupShown = false">
      <h2>{{title}}</h2>
      <p>{{mainText}}</p>
    </div>
    <h1> Terms of use </h1>
    <button v-for="(section, index) in sectionContents" :key="index" class="collapsible" @click="show(index)">
      {{ section }}
    </button>
    <div style="display: none">
      <p class="content">
        This website is operated by Marsik the Kitty and his owner.
        The terms “we,” “us,” and “our” refer to Marsik the Kitty or his owner.
        The use of our website is subject to the following terms and conditions.
        These terms must be read carefully in conjunction with any additional terms, conditions, or rules provided on the webpages of Marsik the Kitty.
        Please review these terms carefully. They apply to all users of our website without exception.
        By accessing and using this website, you accept and agree to be bound by these terms and our rules.
        If you do not agree to the terms or our Privacy Policy, you are not authorized to access our website or use any services provided by Marsik the Kitty.
      </p>
      <p class="content">
        You agree to use our website for legitimate purposes and not for any illegal or unauthorized activities,
        including engaging in appropriate conversations with Marsik the Kitty or insulting him in the chat.
        By agreeing to these Terms, you confirm that you are at least the age of majority in your state or province
        of residence and are legally capable of interacting with Marsik the Kitty without complications.
        You agree not to use our website for any actions that could constitute a civil or criminal offense or cause harm to Marsik,
        such as yelling at him. Additionally, you must not attempt to interfere with our website’s network or security features,
        nor try to gain unauthorized access to Marsik’s bedroom.
      </p>
      <p class="content">
        Access to the content provided by Marsik the Kitty is only possible through proper login.
        Therefore, users must sign up and provide the necessary information, such as a valid username and a strong password.
        It is your responsibility to store your username and password securely to ensure proper login.
        Any attempt to bypass the login process or gain unauthorized access must be strictly avoided.
        Otherwise, Marsik may enter your bedroom and do his business there.
      </p>
      <p class="content">
        We reserve the right to yell on anyone, at any time, for any reason.
        Additionally, we reserve the right to modify any aspect of the website, including terminating,
        changing, suspending, or discontinuing its functionality at any time without prior notice.
        Marsik the Kitty may impose additional rules or terms regarding the use of his website.
        You agree to review the Terms regularly, and your continued access to our website signifies your acceptance of any changes to the content.
        Marsik the Kitty is not liable for unsatisfactory content or poor service.
        Furthermore, Marsik is not responsible for incorrect website functionalities resulting from browser malfunctions, system failures, or connectivity issues.
        While Marsik strives to provide access to all content on his website at all times, he does not guarantee uninterrupted availability.
        Likewise, Marsik assumes no liability for compatibility issues or other system-related disturbances on your end.
      </p>
      <p class="content">
        The use of publications by Marsik the Kitty, particularly the content, is permitted only for private, non-commercial purposes.
        It is strictly prohibited to download, copy, publish, manipulate, or make the digital content of the website publicly available in any other form.
        Recording, saving, and publishing the chat with Marsik the Chat-Cat is forbidden under any circumstances and will be severely punished by Angry-Marsik.
      </p>
      <p class="content">
        The visitor agrees to treat all content provided by Marsik the Kitty with respect. A positive and empathetic relationship between Marsik and the visitor
        is the fundamental foundation of a successful web experience. Additionally, you agree to treat Marsik with respect and courtesy,
        especially when engaging in chat or other forms of contact. Uncooperative, disrespectful, or offensive behavior towards Marsik
        can result in severe consequences. You agree not to enter the site with a significant reason.
        If you do not enjoy Marsik's web experience or lack the necessary humor to appreciate the site, you should leave the site and never comeback anymore.
      </p>
      <p class="content">
        One of the most unique services available is the opportunity to contact Marsik the Cat.
        When sending a message to Marsik, the visitor must ensure that all information provided is correct, accurate and truthful.
        The required data will be stored in Marsik's personal drawers.
        Once a message is sent to Marsik, no party or entity can access it.
        Therefore, it is not possible to modify, change, or delete sent messages.
        In exceptional cases, it might be possible to delete a message after it has been sent to Marsik the Message-Cat,
        for example, if there message is empty, with the approval of Marsik the Cat, his fur ball and his owner.
      </p>
      <p class="content">
        In order to ensure an optimal web experience through Marsik the Kitty's side,
        the following technical requirements must be met by the visitor: a modern and up-to-date browser, a functional and stable Internet connection,
        and a compatible device to visit the website.
        If the user's system requirements are inadequate, anomalies and negative effects may occur.
        In the worst case, the functionality of the website could be impaired to such an extent that
        it might be necessary to interrupt the web experience.
      </p>
    </div>
  </article>
</template>

<script>
export default {
  name: "TermsOfUse"
}
</script>

<style scoped>
h1
{
  color: #1f1f1f;
}

p
{
  padding: 0 1em 1em 1em;
}

.collapsible
{
  background-color: #505050;
  color: white;
  padding: 1em;
  width: 100%;
  text-align: left;
}
.collapsible:hover
{
  background-color: #444444;
}

#overLay
{
  z-index: 1;
  position: fixed;
  width: 100vw;
  height: 100vh;
  top: 0;
  background-color: rgba(0, 0, 0, 0.6);
}

.superPopUp
{
  visibility: hidden;
  z-index: 2;
  position: absolute;
  width: 100%;
  max-width: 500px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  background-color: #dfdfdf;
  text-align: center;
  transition: 0.4s ease-in-out;
  transition-property: opacity, visibility;
  opacity: 0;
}
.show
{
  visibility: visible;
  opacity: 1;
}
</style>