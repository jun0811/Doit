<template>
  <v-expansion-panels class="px-2 py-2 px-sm-16" >
    <v-expansion-panel class="panel">
      <v-expansion-panel-header>
        <template v-slot>
          <v-row no-gutters>
            <v-col cols="2">
              <div class="group-image">
                <img src="" alt="">
              </div>
            </v-col>

            <v-col
              cols="8"
              class="text--secondary ml-sm-0 ml-11"
            >
              <p class="my-6">Group1</p>
              <span class="mr-2">#홈트</span>
              <span class="mr-2">#운동</span>
              <span class="mr-2">#의지박약</span>
            </v-col>
          </v-row>
        </template>
      </v-expansion-panel-header>
      <v-expansion-panel-content>
        <v-row no-gutters>
          <v-col class= "mx-auto" cols="10">
            <p class="my-5"> 그룹 목표 및 소개</p>
            <div class="group-intro">

            </div>
          </v-col>
        

          
        </v-row>
        <v-card-actions class= "pr-16">
          <v-spacer></v-spacer>
          <v-btn
            text
            color="secondary"
          >
            그룹으로 이동
          </v-btn>
          <v-btn
            text
            color="primary"
          >
            가입신청
          </v-btn>
        </v-card-actions>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
export default {
    data: () => ({
      date: null,
      trip: {
        name: '',
        location: null,
        start: null,
        end: null,
      },
    }),
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
</style>