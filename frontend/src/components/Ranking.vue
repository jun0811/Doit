<template>
  <div>
    <v-row class="mx-3 mx-sm-16">
      Doit Ranking👑
    </v-row>
    <v-row class="py-10">
      <v-col class="mx-3 mx-sm-16 temp-box d-flex justify-space-around">
        <div v-for="(group, idx) in groups" :key="idx" class="rank-box d-flex" >
          <div class="">
            <h1 class="rank-num">{{idx+1}}</h1>
          </div>
          <div class="d-flex flex-column justify-space-around">
            <div class="rank-wrapper">
              <img class="rank-image box" :src="group.groupImage" alt="그룹이미지">
            </div>
            <div class="group-name">{{group.groupname}}</div>
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
  height: 100%;
}

.rank-num {
  height:40px;
  width:40px;
}

  
.group-name {
  height:30px; 
  text-align:center;
}
.rank-wrapper {
  border-radius: 50%;
}


.rank-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
}

/* --- */
.box {
  position: relative;
  width: 100px;
  height: 100px;
  border: 5px solid orange;
  display: block;
  border-color: orange;
  animation-name: flash_border;
  animation-duration: 2s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  -webkit-animation-name: flash_border;
  -webkit-animation-duration: 2s;
  -webkit-animation-timing-function: linear;
  -webkit-animation-iteration-count: infinite;
  -moz-animation-name: flash_border;
  -moz-animation-duration: 2s;
  -moz-animation-timing-function: linear;
  -moz-animation-iteration-count: infinite;
}

@keyframes flash_border {
  0% {
    border-color: #5f86f2;
  }
  14% {
    border-color:#a65ff2;
  }
  28% {
    border-color: #f25fd0;
  }
  42% {
    border-color: #f25f61;
  }
  56% {
    border-color: #f2cb5f;
  }
  70% {
    border-color:#abf25f;
  }
  84% {
    border-color:#5ff281,;
  }
  100% {
    border-color:#5ff2f0;
  }
}




</style>