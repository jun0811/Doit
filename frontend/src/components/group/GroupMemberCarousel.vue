<template>
  <v-row
    class="my-6 mx-12 d-flex align-center flex-wrap justify-space-between"
  >
    <v-col cols="5" class="profile-card d-flex align-center" v-for="(item, idx) in paginatedData" :key="idx">
      <img class="profile-image" :src="item.userimage">
      <span class="mx-2">{{item.username}}</span>
      <v-btn normal x-small class="px-0">강퇴</v-btn>
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
          userimage:'https://icons.iconarchive.com/icons/custom-icon-design/flatastic-4/512/User-orange-icon.png',
        },
        {
          username:'user2',
          userimage:'https://icons.iconarchive.com/icons/custom-icon-design/flatastic-4/512/User-orange-icon.png',
        },
        {
          username:'user3',
          userimage:'https://icons.iconarchive.com/icons/custom-icon-design/flatastic-4/512/User-orange-icon.png',
        },
        {
          username:'user4',
          userimage:'https://icons.iconarchive.com/icons/custom-icon-design/flatastic-4/512/User-orange-icon.png',
        },
        {
          username:'user5',
          userimage:'https://icons.iconarchive.com/icons/custom-icon-design/flatastic-4/512/User-orange-icon.png',
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
  /* width: px; */
  height: 50px;
  margin: 10px 20px;
  padding: 0px 20px;
}

.profile-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 1px solid orange;
}

</style>