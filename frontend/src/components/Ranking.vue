<template>
  <div class="d-flex flex-column justify-center">
    <v-row class="mx-1 mb-2 mt-16 mt-sm-0">
      <h4>Doit 그룹랭킹👑</h4>
    </v-row>
    <v-card class="px-4 mr-sm-3">
      <v-row v-for="(group, idx) in groups" :key="idx" class="my-3">
        <v-col cols="2" class="text-center">
          <span class="rank-num">{{idx+1}}</span>
        </v-col>
        <v-col cols="2" class="d-flex flex-column justify-center">
          <img class="rank-image box" :src="group.groupImage" alt="그룹이미지">
        </v-col>
        <v-col cols="8">
          <div class="group-name">{{group.groupname}}</div>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isHover:false,
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
        {
          groupname:'일찍 일어나는 새가 벌레를 잡아먹는다.',
          groupImage:'https://media.istockphoto.com/vectors/early-bird-with-worm-vector-id96612139?k=6&m=96612139&s=612x612&w=0&h=34bGxeM0JaF25Tl4ezvPt1eBst7Cnta5pNfRouwK3wk=',
        },
      ],
    }
  },
}
</script>

<style scoped>

.rank-num {
  font-size: x-large;
  height: 56px;
  line-height: 56px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .rank-num {
    font-size: x-large;
    height: 56px;
    line-height: 56px;
  }
}
  
.group-name {
  font-size: medium;
  vertical-align: middle;
  height: 56px;
  line-height: 56px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  cursor: pointer;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .group-name {
    font-size: medium;
    height: 56px;
    line-height: 56px;   
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap; 
    cursor: pointer;
  }
}

.rank-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  z-index : 1;
  position:relative;
  cursor: pointer;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .rank-image {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    cursor: pointer;
  }
}

.box:hover {
  position: relative;
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
  /* animation-delay: $randomNumber + s; */
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
    border-color:#5ff281;
  }
  100% {
    border-color:#5ff2f0;
  }
}

.spin {
  width: 100px;
  height: 100px;
  position:absolute;
  top: 0;
  left:0;
  z-index:10;
  border: transparent;
}


.spin:hover {
  border-top-color: orange;
  border-right-color: orange;
  border-bottom-color: orange;
  transition-duration: 5s;
  transition: border-top-color 0.15s linear, 
              border-right-color 0.15s linear 0.1s, 
              border-bottom-color 0.15s linear 0.2s;
  border-top: 5px solid orange;
  border-left-width: 5px;
  border-right-width: 5px;
  transform: rotate(8000deg);
  transition: transform 20s linear 0s, border-left-width 3s linear 0.35s;
}

.scale:hover {
  transform : scale(1.1);
}


</style>