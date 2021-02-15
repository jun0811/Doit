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
            src="@/assets/img/profile_temp.png"
            alt="profile-img"
            class="profile-img"
          />
        </v-col>
        <v-col 
          sm="9" 
          class="pa-8 text-h4"
        >
          <div>{{user.nickname}}</div>
          <v-btn
            @click="moveUpdate"
            color="white" 
            depressed 
            class="pa-0"
          > 
          회원정보 수정 > 
          </v-btn>
        </v-col>
      </v-row>
      <v-row class="py-3">
        <v-col class="mx-3 mx-sm-16"> 
          <Grass></Grass>
        </v-col>
      </v-row >
      <v-row class="py-16">
        <v-col 
          cols="defined"
          sm="7"
          md="6"
          class="ml-3 ml-sm-16 mr-3 mr-sm-0 pa-0"
        > 
        <div class="mb-2">
          내가 작성한 피드
        </div>  
          <FeedList></FeedList>
        </v-col>
        <v-col 
          cols="defined"
          sm="3"
          class="mr-3 mr-sm-16 ml-3 ml-sm-13 mt-8 mt-sm-0 pa-0"
        > 
        <div class="mb-2">
          가입한 그룹 리스트
        </div>
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
import FeedList from "@/components/group/FeedList.vue";
import JoinedGroupList from "@/components/group/JoinedGroupList.vue";
import http from "../../http-common";
import Grass from '../../components/Grass.vue';

export default {
  name: "Profile",
  components: {
    Header,
    Footer,
    FeedList,
    JoinedGroupList,
    Grass,
  },data() {
    return {
      user: "",
      feed: "",
    }
  },
  created() {
      http.get('/user/detailUser')
      .then((res)=>{
          this.user = res.data.object;
      })
  },
  methods: {
    moveUpdate(){
      this.$router.push('/user/update');
    }
  }
};
</script>

<style>
</style>