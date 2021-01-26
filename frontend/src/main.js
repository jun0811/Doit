import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './store/index'

Vue.config.productionTip = false;

Vue.use(VueRouter);

export const router = new VueRouter({
    routes,
});

new Vue({
    router,
    store,
    render: h => h(App),
    created() {
        const accessToken = sessionStorage.getItem("accessToken");
        console.log(accessToken);
        if (accessToken) {
            this.$store.commit("SET_ACCESSTOKEN", accessToken);
        }
    }
}).$mount('#app');


