<template>
  <div>
    <v-container class="title-style">
      <v-row class="d-flex flex-column align-center pa-10 pa-sm-0">
        <div class="title-size">만들고 싶은 습관, 바꾸고 싶은 습관이 있나요?</div>
        <br>
        <div class="word-break-style mb-2">꾸준히 운동, 독서, 아침 일찍 기상 등 이뤄내고 싶은 목표가 있는 사용자들에게,</div>
        <div class="word-break-style">다른사람들과 함께 습관을 만들어가는 참여형 플랫폼을 <span class="keycolor">Doit</span>이 제안합니다!</div>
      </v-row>
    </v-container>
    <v-container class="content-width">
      <v-row class="d-flex justify-space-between content-reverse">
        <v-col cols="12" sm="4">
          <img src="@/assets/img/career.png" alt="group-img" class="img-style">
        </v-col>
        <v-col cols="12" sm="7">
          <div class="font-size1 pb-8">01 만들고 싶은 습관을 고르세요.</div>
          <div class="font-size2 pl-sm-16 mb-2">카테고리별 그룹 보기 및 검색을 통해 원하는 그룹을 찾아보실 수 있습니다.</div>
          <div class="font-size2 pl-sm-16 mb-2">각 그룹의 설명을 둘러보고 원하는 그룹을 선택하세요.</div>
          <div class="font-size3 red-color pl-sm-16">Tip! 원하는 그룹이 없다면 '그룹 만들기'로 직접 만들어 보세요.</div>
        </v-col>
      </v-row>
    </v-container>
    <v-container class="content-width">
      <v-row class="d-flex justify-space-between">
        <v-col cols="12" sm="8">
          <div class="font-size1 pb-8">02 그룹 규칙에 따라 습관 인증을 해보세요.</div>
          <div class="font-size2 pl-sm-16 mb-2">각 그룹의 규칙에 따라 습관 인증 피드를 올려보세요.</div>
          <div class="font-size2 pl-sm-16">그룹원의 70% 이상에게 인증을 받으면 인증 완료!</div>
        </v-col>
        <v-col cols="12" sm="4">
          <img src="@/assets/img/career.png" alt="group-img" class="img-style">
        </v-col>
      </v-row>
    </v-container>
    <v-container class="content-width">
      <v-row class="d-flex justify-space-between content-reverse">
        <v-col cols="12" sm="4">
          <img src="@/assets/img/career.png" alt="group-img" class="img-style">
        </v-col>
        <v-col cols="12" sm="8">
          <div class="font-size1 pb-8">03 인증 할수록 채워지는 잔디를 확인하세요.</div>
          <div class="font-size2 pl-sm-16 mb-2">일별로 인증된 피드들을 체크하여 자신의 잔디가 채워집니다.</div>
          <div class="font-size2 pl-sm-16">알록달록하게 자신의 잔디를 채우는 재미!</div>
        </v-col>
      </v-row>
    </v-container>
    <v-container class="content-width">
      <v-row class="d-flex justify-space-between">
        <v-col cols="12" sm="7">
          <div class="font-size1 pb-8">04 그룹 내 정보 공유도 가능해요.</div>
          <div class="font-size2 pl-sm-16 mb-2">습관 만들기를 위한 자료 공유 등 자유롭게 피드를 올릴 수 있는 공간도 마련되어 있습니다. 팀원들과 자유롭게 소통하여 정보를 공유해보세요!</div>
        </v-col>
        <v-col cols="12" sm="4">
          <img src="@/assets/img/career.png" alt="group-img" class="img-style">
        </v-col>
      </v-row>
    </v-container>
    <v-container class="content-width">
      <v-row class="d-flex justify-space-between content-reverse">
        <v-col cols="12" sm="4">
          <img src="@/assets/img/career.png" alt="group-img" class="img-style">
        </v-col>
        <v-col cols="12" sm="8">
          <div class="font-size1 pb-8">05 마일리지를 모아 필요한 물품을 사보세요!</div>
          <div class="font-size2 pl-sm-16 mb-2">그룹 활동을 통해 얻은 결과물을 SHOP에서 마일리지로 사고 팔 수 있습니다.</div>
          <div class="font-size2 pl-sm-16">실시간 채팅으로 판매자와 구매자가 거래할 수 있어요!</div>
        </v-col>
      </v-row>
    </v-container>
    
  </div>
</template>

<script>

export default {
  name: 'Introduction',
  components: { 
  },
}
</script>

<style scoped>
.red-color {
  color: red;
}

.keycolor {
  color: #F9802D;
}

.content-width {
  width: 85%;
  margin-top: 20%;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
.content-width {
  width: 85%;
  margin-top: 30%;
}
}

.img-style {
  width: 100%;
}

.title-size {
  font-size: 250%;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .title-size {
    font-size: 150%;
    word-break: keep-all;
  }
}

.font-size1 {
  font-size: 250%;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .font-size1 {
    font-size: 130%;
    word-break: keep-all;

  }
}

.font-size2 {
  font-size: 120%;
}
@media only screen and (min-width: 300px) and (max-width: 599px) {
  .font-size2 {
    font-size: 90%;
  }
}

.font-size3 {
  font-size: 100%;
}
@media only screen and (min-width: 300px) and (max-width: 599px) {
  .font-size3 {
    font-size: 70%;
  }
}

.title-style {
  /* margin-top: 10%; */
  margin-bottom: 20%;
  font-size: 130%;
}
@media only screen and (min-width: 300px) and (max-width: 599px) {
  .title-style {
    margin-top: 10%;
    margin-bottom: 20%;
    font-size: 120%;
    text-align: center;
  }
}
@media only screen and (min-width: 300px) and (max-width: 599px) {
  .content-reverse {
    flex-direction: column-reverse;
  }
}

.word-break-style {
  word-break: keep-all;
}
</style>