<template>
    <v-app class = "Main">
        <div>
            <Header></Header>
            <SearchBar></SearchBar>
            <WordCloud></WordCloud>
            <Ranking></Ranking>
            <Category></Category>
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
import SearchBar from "@/components/common/SearchBar.vue";
import WordCloud from "@/components/WordCloud.vue";
import Category from "@/components/Category.vue";

export default {
    name: "Main",
    components: {
        Header,
        Footer,
        Aside,
        WordCloud,
        SearchBar,
        Category,
    }
}
</script>

<style>

</style>