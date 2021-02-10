<template>
  <v-container >
    <v-row
      class="my-6 px-6 d-flex align-center flex-wrap justify-start"
    > 
      <v-col
        cols="4"
        v-for="(group, idx) in groups" 
        :key="idx"
        class="d-flex justify-center px-4 py-4"
      >
          <v-card height="100%" width="100%" >
            <v-img
              src="https://images.unsplash.com/photo-1512621776951-a57141f2eefd?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"
              class="white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
            <v-card-title v-text="group.name" class="py-1"></v-card-title>
            <span v-for="(tag,idx) in group.tags" :key="idx" class="mx-1" ># {{tag}}</span>
            </v-img>
            <v-card-actions  class="card-text">
              <v-card-text v-if="token"  class="d-flex justify-space-around py-1">
                <v-btn plain class="btn" router-link :to="{name: 'Community', params: {groupPk:group.groupPk}}">상세보기</v-btn>
                <!-- <v-btn plain class="btn">가입신청</v-btn> -->
              </v-card-text>
              <v-card-text v-else class="d-flex justify-space-around py-1">
                <span>로그인을 하면 더 자세한 정보를 확인 하실수 있습니다.</span>
              </v-card-text>
            </v-card-actions>

          </v-card>

      </v-col>
    </v-row>

                  <!-- <span v-for="(tag,idx) in group.tags" :key="idx" class="mr-2"># {{tag}}</span> -->
   
  </v-container >
</template>

<script>
import { searchGroup } from "@/api/group/index.js"
export default {
    props:{
      page: Number,
      word: String,
    },
    data: () => ({
      date: null,
      trip: {
        name: '',
      },
      groups: [],
      pageCount:0,
    }),
    watch: {
      page:{
        handler: function () {
           searchGroup(
            {
              "direction":"ASC",
              "page":this.page,
              "size":1,
              "tag":this.word,
              "token": ""
            },
            (res) =>{
              if (res.status){
              this.groups = res.data.object.content // 배열로 집어넣기
              }
            },
            (err) =>{
              console.log(err)
              alert("검색 결과 가져오기 실패")
            }
          )
        }
      }
    },
    created() {
      this.token = this.$store.state.account.accessToken
      searchGroup(
        {
          "direction":"ASC",
          "page":this.page,
          "size":9,
          "tag":this.word,
        },
        (res) =>{
          console.log(res)
          if (res.status){
          this.groups = res.data.object.content // 배열로 집어넣기
          }
        },
        (err) =>{
          console.log(err)
          alert("검색 결과 가져오기 실패")
        }
      )
    },
}
</script>

<style scoped>
.group-image {
    width: 90px;
    height: 90px;
    border-radius: 70%;
    overflow: hidden;
    background-color: #ffffbb
}
.group-intro {
  width : 100%;
  height : 250px;
  border: 1px solid orange;
}

.btn {
  border-radius: 15px;

  color: grey;
}

.btn:hover {
  color: orange;
}
</style>