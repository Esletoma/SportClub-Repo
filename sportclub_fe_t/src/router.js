import { createRouter, createWebHistory } from "vue-router";
import App from './App.vue';
import LogIn from './components/LogIn.vue'
import SignUp from './components/SignUp.vue'
import Home from './components/Home.vue'
import Centro from './components/Centro.vue'
import Actividad from './components/Actividad.vue'
import CreateActividad from './components/CreateActividad.vue'
import UpdateActividad from './components/UpdateActividad.vue'

const routes = [{
    path: '/',
    name: 'root',
    component: App
  },
  {
    path: '/user/logIn',
    name: "logIn",
    component: LogIn
  },
  {
    path: '/user/signUp',
    name: "signUp",
    component: SignUp
  },
  {
    path: '/user/home',
    name: "home",
    component: Home
  },
  {
    path: '/user/centro',
    name: "centro",
    component: Centro
  },
  {
    path: '/user/actividad',
    name: "actividad",
    component: Actividad
  },
  {
    path: '/user/createactividad',
    name: "createactividad",
    component: CreateActividad
  },
  {
    path: '/user/updateactividad',
    name: "updateactividad",
    component: UpdateActividad
  },
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});
export default router;