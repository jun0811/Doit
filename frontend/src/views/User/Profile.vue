<template>
  <div>
    <Header></Header>
    <v-container class="pa-3 pa-sm-16">
      <v-row class="py-3 ma-0" style="width:100%">
        <v-col
          cols="6" sm="3" md="3" lg="3" xl="3"
          class="d-flex justify-space-around group-image"
        >
          <img v-if="image==null_image" src="@/assets/img/profile_temp.png" class="profile-img">
          <img v-else
            :src="image"
            alt="profile-img"
            class="profile-img"
          />
        </v-col>
        <v-col 
          cols="5" sm="9" md="9" lg="9" xl="9"
          class="pa-8 text-h4"
        >
          <v-row class="mt-2 mb-4 ml-1">{{user.nickname}}</v-row>
          <v-row class="d-flex">
            <v-btn 
              text
              @click="moveUpdate"
              class=" ma-2 px-5 mr-sm-8 profile-btn"
            > 
            회원정보 수정
            </v-btn>
            <v-btn 
              text
              @click="moveChatList"
              class=" ma-2 px-5 mr-sm-8 profile-btn"
            > 
            채팅 리스트
            </v-btn>
            <v-btn 
              text
              @click="moveMileage"
              class="ma-2 px-5 mr-sm-8 profile-btn"
            > 
            마일리지 내역
            </v-btn>


          </v-row>
        </v-col>
      </v-row>
      <v-row class="py-3">
        <v-col class="mx-3 mx-sm-16" > 
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
      null_image: "http://ssafydoit.s3.ap-northeast-2.amazonaws.com/null",
      user: "",
      feed: "",
      image: "http://ssafydoit.s3.ap-northeast-2.amazonaws.com/" 
    }
  },
  created() {
      http.get('/user/detailUser')
      .then((res)=>{
          this.user = res.data.object;
          this.image = this.image + this.user.image
          console.log(this.image)
      })
  },
  methods: {
    moveUpdate(){
      this.$router.push('/user/update');
    },
    moveMileage() {
      this.$router.push('/user/mileagehistory')
    },
    moveChatList() {
      this.$router.push('/chatlist')
    }
  }
};
</script>

<style scoped>
  .group-image {
    width: 200px;
    height: 200px;
    border-radius: 70%;
    overflow: hidden;
    border: 1px solid #FFB685
  }
.profile-btn {
  border : 2px solid #F9802D;
  border-radius: 15px;
}
.profile-img{
    width:100%;
    height:100%;
    object-fit: cover;
  }

</style>