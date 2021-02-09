<template>
  <div>

    <v-card
      class="mx-auto my-12 d-flex flex-column align-center member-card"
      width="600"
    >
        <div class="mx-6 my-4 align-self-end">
          팀 스코어 : 500   그룹원 수 : 5
        </div>
        <v-card
        width="80%"
        height="200"
        >
          소개글
        </v-card>
        <v-carousel :show-arrows="false"
        hide-delimiter-background
        light
        height="250">
          <v-carousel-item
            v-for="(page,i) in pageCount"
            :key="i"
          >
            <GroupMemberCarousel :page="page" :groupPk="groupPk"></GroupMemberCarousel>
          </v-carousel-item>
        </v-carousel>
    </v-card>
  </div>
</template>

<script>
import GroupMemberCarousel from '@/components/group/GroupMemberCarousel.vue'
import http from "../../http-common";

  export default {
    data: () => ({
      loading: false,
      selection: 1,
      users :[],
      category:'',
      content:'',
      endDate:'',
      createDate:'',
      leader:'',
      groupName:'',
      totalNum:'',
    }),
    components: {
      GroupMemberCarousel,
    },
    props: {
      groupPk: String,
    }, 
    computed: {
      pageCount () {
        let listLeng = this.users.length,
            listSize = 4,
            page = Math.floor(listLeng / listSize);
        if (listLeng % listSize > 0) page += 1;
        return page;
      },
    },
    created() {
      http.get(`/group/detailGroup?groupPk=${this.groupPk}`)
      .then((res)=>{
        console.log(res.data.object)
        this.users = res.data.object.users
        this.category = res.data.object.category
        this.content = res.data.object.content
        this.leader = res.data.object.leader
        this.groupName = res.data.object.name
        this.totalNum = res.data.object.totalNum
        this.endDate = res.data.object.endDate
        this.createDate = res.data.object.createDate
      })
    },
  }
</script>

<style scoped>


.member-card {
  border-radius: 20px;
}

</style>