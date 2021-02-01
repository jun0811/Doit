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
            <GroupMemberCarousel :page="page"></GroupMemberCarousel>
          </v-carousel-item>
        </v-carousel>
    </v-card>
    <div class="col-12 d-flex align-center justify-center">
      <span class="mx-12"><router-link to="/">가입하기</router-link></span>
      <span class="mx-12"><router-link to="/">탈퇴하기</router-link></span>
    </div>
  </div>
</template>

<script>
import GroupMemberCarousel from '@/components/group/GroupMemberCarousel.vue'

  export default {
    data: () => ({
      loading: false,
      selection: 1,
      member:[
        {
          username:'user1',
          userimage:'',
        },
        {
          username:'user2',
          userimage:'',
        },
        {
          username:'user3',
          userimage:'',
        },
        {
          username:'user4',
          userimage:'',
        },
        {
          username:'user5',
          userimage:'',
        },
      ],
    }),
    components: {
      GroupMemberCarousel,
    },
    computed: {
      pageCount () {
        let listLeng = this.member.length,
            listSize = 4,
            page = Math.floor(listLeng / listSize);
        if (listLeng % listSize > 0) page += 1;
        return page;
      },
    }
  }
</script>

<style scoped>


.member-card {
  border-radius: 20px;
}

</style>