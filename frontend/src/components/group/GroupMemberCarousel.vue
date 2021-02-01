<template>
  <v-row
    class="mx-auto my-12 d-flex align-center flex-wrap justify-center"
  >
    <v-col cols="5" class="profile-card" v-for="(item, idx) in paginatedData" :key="idx">
      <span>{{item.userimage}}</span>
      <span>{{item.username}}</span>
      <button>강퇴</button>
    </v-col>
  </v-row>
</template>

<script>
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
    props: {
      page:Number,
    }, 
    computed: {
      paginatedData () {
        console.log(this.page)
        const start = (this.page-1) * 4,
              end = start + 4;
        console.log(this.member.slice(start, end))
        return this.member.slice(start, end);
      }
    }
  }
</script>

<style scoped>

.profile-card {
  width: 300px;
  height: 50px;
}

</style>