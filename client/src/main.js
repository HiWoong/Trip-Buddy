import { createApp} from "vue";
import { createPinia } from "pinia";
import { VueCookies } from "vue-cookies";

import App from "./App.vue";
import router from "./router";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

const app = createApp(App);

app.use(createPinia());
app.use(router);

// Vue.use(VueCookies);

app.mount("#app");
