<template>
  <div>
    <Header></Header>
    <v-container class="pa-3 pa-sm-16">
      <v-row class="py-3">
        <v-col 
          sm="3" 
          class="d-flex justify-center"
        >
          <img
            src="@/assets/logo/profile_temp.png"
            alt="profile-img"
            class="profile-img"
          />
        </v-col>
        <v-col 
          sm="9" 
          class="pa-8 text-h4"
        >
          <div>Nickname</div>
          <v-btn 
            color="white" 
            depressed 
            class="pa-0"
          > 
          회원정보 수정 > 
          </v-btn>
        </v-col>
      </v-row>
      <v-row class="py-3">
        <v-col class="grass mx-3 mx-sm-16"> 
          잔디🌱🌱🌱
        </v-col>
      </v-row>
      <v-row class="py-16">
        <v-col 
          cols="defined"
          sm="7"
          class="ml-3 ml-sm-16 mr-3 mr-sm-0 pa-0"
        > 
        <FeedCard></FeedCard>
        </v-col>
        <v-col 
          cols="defined"
          sm="3"
          class="mr-3 mr-sm-16 ml-3 ml-sm-13 mt-8 mt-sm-0 pa-0"
        > 
        <JoinedGroupList></JoinedGroupList>
        </v-col>
      </v-row>
    </v-container>
    <Footer></Footer>
  </div>
</template>

<script>
import "@/assets/css/profile.css";
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import FeedCard from "@/components/group/FeedCard.vue";
import JoinedGroupList from "@/components/group/JoinedGroupList.vue";

export default {
  name: "Profile",
  components: {
    Header,
    Footer,
    FeedCard,
    JoinedGroupList,
  },
};
</script>

<style>
</style>