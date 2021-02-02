<template>
  <div>
    <v-row class="mx-3 mx-sm-16">
      Doit Ranking👑
    </v-row>
    <v-row class="py-10">
      <v-col class="mx-3 mx-sm-16 temp-box d-flex">
        <!-- Ranking👑 -->
        <div v-for="(group, idx) in groups" :key="idx" class="rank-box d-flex align-start" >
          <div style="height:40px; width:40px;">
            <h1 style="height:40px">{{idx+1}}</h1>
          </div>
          <div class="d-flex flex-column">
            <img class="rank-image" :src="group.groupImage" alt="그룹이미지">
            <p style="height:50px; text-align:center; display:inline">{{group.groupname}}</p>
          </div>
        </div>
      </v-col>
    </v-row>
  </div>
    
</template>

<script>
export default {
  data() {
    return {
      groups : [
        {
          groupname:'뚜잇!다이어트',
          groupImage:'https://t1.daumcdn.net/thumb/R720x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/2fG8/image/_WTaS6GwMibxPZE6BVl4emraqJE.jpg',
        },
        {
          groupname:'매일홈트',
          groupImage:'http://www.greenpostkorea.co.kr/news/photo/202005/117654_118598_566.jpg',
        },
        {
          groupname:'독서원정대',
          groupImage:'https://t1.daumcdn.net/cfile/tistory/120D63464ED498CA02',
        },
        {
          groupname:'달려달려',
          groupImage:'https://image.edaily.co.kr/images/Photo/files/NP/S/2016/09/PS16090800342.jpg',
        },
      ],
    }
  },
}
</script>

<style scoped>

.rank-box {
  width: 150px;
  height: 200px;
}



.rank-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border: 3px solid orange;
  display: inline;
}

</style>