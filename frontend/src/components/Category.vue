<template>
  <v-container class="px-3 px-sm-16"> 
    <v-row class="mx-3 mx-sm-16 mb-5">
      <h4>카테고리</h4>
    </v-row>
    <v-card class="mx-auto mx-3 mx-sm-16">
      <v-row class="py-8 px-5 d-flex justify-space-around">
        <v-col cols="4" sm="1" class="d-flex flex-column align-center">
          <img src="@/assets/icon/study.png" alt="study-icon" class="category-icon">
          <div class="group-name mt-2">공부</div>
        </v-col>
        <v-col cols="4" sm="1" class="d-flex flex-column align-center">
          <img src="@/assets/icon/diet.png" alt="diet-icon" class="category-icon">
          <div class="group-name mt-2">다이어트</div>
        </v-col>
        <v-col cols="4" sm="1" class="d-flex flex-column align-center">
          <img src="@/assets/icon/exercise.png" alt="exercise-icon" class="category-icon">
          <div class="group-name mt-2">운동</div>
        </v-col>
        <v-col cols="3" sm="1" class="d-flex flex-column align-center">
          <img src="@/assets/icon/book.png" alt="book-icon" class="category-icon">
          <div class="group-name mt-2">책</div>
        </v-col>
        <v-col cols="3" sm="1" class="d-flex flex-column align-center">
          <img src="@/assets/icon/life.png" alt="life-icon" class="category-icon">
          <div class="group-name mt-2">생활습관</div>
        </v-col>
        <v-col cols="3" sm="1" class="d-flex flex-column align-center">
          <img src="@/assets/icon/hobby.png" alt="hobby-icon" class="category-icon">
          <div class="group-name mt-2">취미</div>
        </v-col>
        <v-col cols="3" sm="1" class="d-flex flex-column align-center">
          <img src="@/assets/icon/more.png" alt="etc-icon" class="category-icon">
          <div class="group-name mt-2">그 외</div>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
export default {

}
</script>

<style scoped>

.category-icon {
  list-style: none;
  display: inline-block;
  width: 50px;
  height: 50px;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .category-icon {
    list-style: none;
    display: inline-block;
    width: 30px;
    height: 30px;
  }
}

.group-name {
  text-align:center;
  font-size: x-small;
}

@media only screen and (min-width: 300px) and (max-width: 599px) {
  .group-name {
    text-align:center;
    font-size: xx-small;
  }
}

</style>