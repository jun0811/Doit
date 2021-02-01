<template>
  <v-card
    class="mx-auto my-12 d-flex flex-column align-center"
    width="700"
  >
    <div v-for="(item, idx) in paginatedData" :key="idx">
      <div>{{item.username}}</div>
      <div>{{item.userimage}}</div>
    </div>
  </v-card>
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

<style>

</style>