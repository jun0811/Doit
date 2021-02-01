<template>
  <v-card
    class="mx-auto my-12 d-flex flex-column align-center"
    width="700"
  >
      <div class="mx-6 my-4 align-self-end">
        팀 스코어 : 500   그룹원 수 : 5
      </div>
      <v-card
      width="75%"
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

<style>

</style>