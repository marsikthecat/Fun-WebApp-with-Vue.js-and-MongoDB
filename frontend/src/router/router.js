import { createRouter, createWebHistory } from 'vue-router';

import Login from '../login/Login.vue';
import AdminPanel from "../admin/adminPanel.vue";
import Unauthorised from "../../assets/Unauthorised.vue";
// Wrapper For Main Pages
import Main from "../mainPages/MainLayout.vue";
// Main Pages
import Home from '../mainPages/contents/Home.vue';
import About from '../mainPages/contents/About.vue';
import Videos from '../mainPages/contents/Videos.vue';
import Contact from "../mainPages/contents/Contact.vue";
import More from "../mainPages/contents/More.vue";
// Footer Pages
import Career from "../footerPages/Career.vue";
import Help from "../footerPages/Help.vue";
import LegalNotice from "../footerPages/LegalNotice.vue";
import News from "../footerPages/News.vue";
import Privacy from "../footerPages/Privacy.vue";
import Quiz from "../footerPages/Quiz.vue";
import Service from "../footerPages/Service.vue";
import TermsOfUse from "../footerPages/TermsOfUse.vue";

const routes = [
    {   path: '/', redirect: '/login'},
    {   path: '/login', name: 'Login', component: Login, meta: { title: 'Login' }},
    {   path: '/admin', name: 'AdminPanel', component: AdminPanel, meta: { title: 'Admin Panel'}},
    {   path: '/unauthorised', name: 'Unauthorised', component: Unauthorised, meta: {title: 'Unauthorised'}},
    {   path: '/main',
        name: 'main',
        component: Main,
        meta: { title: 'Marsik the cat'},
        children: [
            {   path: 'home', name: 'Home', component: Home},
            {   path: 'about', name: 'About',  component: About},
            {   path: 'videos', name: 'Videos',  component: Videos},
            {   path: 'contact', name: 'Contact',  component: Contact},
            {   path: 'more', name: 'More',  component: More},
        ]
    },
    {   path: '/career', name: 'Career', component: Career},
    {   path: '/help', name: 'Help', component: Help},
    {   path: '/legalNotice', name: 'LegalNotice', component: LegalNotice},
    {   path: '/news', name: 'News', component: News},
    {   path: '/privacy', name: 'Privacy', component: Privacy},
    {   path: '/quiz', name: 'Quiz', component: Quiz},
    {   path: '/service', name: 'Service', component: Service},
    {   path: '/termsOfUse', name: 'TermsOfUse', component: TermsOfUse}

];
const router = createRouter({
    history: createWebHistory(),
    routes
});
export default router;