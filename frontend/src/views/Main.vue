<template>
    <v-app class = "Main">
        <div >
            <Header></Header>
            <WordCloud></WordCloud>
            <Aside></Aside>
            <Footer></Footer>
        </div>
    </v-app>
</template>

<script>
import "../assets/css/main.css";
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import Aside from "@/components/common/Aside.vue";
import WordCloud from "@/components/WordCloud.vue";

export default {
    name: "Main",
    components: {
        Header,
        Footer,
        Aside,
        WordCloud
    }
}
</script>

<style>

</style>