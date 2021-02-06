<template>
<div>
  <Header></Header>
    <v-container class="d-flex flex-column justify-center align-center">
        <v-row>
          <h1>물품 등록</h1>
        </v-row>
        <hr style="width:570px;" class="my-4">
        <v-row class="d-flex flex-column content-wrapper mt-1">
          <v-text-field
            v-model="title"
            solo
            :label="title"
            clearable
          ></v-text-field>
        </v-row>
        <v-card
          elevation="2"
          outlined
          shaped
          tile
          class="content-card"
        >
          <v-row class="d-flex align-center">
            <v-col cols="4">카테고리 선택</v-col>
            <v-col cols="8">
              <v-select
                hide-details=""
                :items="categories"
                label="카테고리 선택"
                solo
              ></v-select>
            </v-col>
          </v-row>
          <v-row class="d-flex align-center">
            <v-col cols="4">판매 금액 설정</v-col>
            <v-col cols="8" class="d-flex align-center">
              <v-text-field
                hide-details=""
                :label="mileage"
                solo
              ></v-text-field>
              <span>마일리지</span>
            </v-col>
          </v-row>
          <v-row class="my-2">
            <v-col cols="12">내용 입력</v-col>
            <v-textarea
              solo
              name="input-7-4"
              label="Solo textarea"
              hide-details=""
            ></v-textarea>
          </v-row>
          <v-row class="my-2">
            <v-col cols="12">사진 첨부<span>(최대 5장)</span></v-col>
            <v-file-input
              chips
              small-chips
              truncate-length="15"
              hide-details=""
            ></v-file-input>
          </v-row>

        </v-card>

        <v-btn class="deal-btn" 
          large
          >
          등록하기
        </v-btn>
    </v-container>
  <Footer></Footer>

</div>
</template>

<script>
import "@/assets/css/profile.css";
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import http from "../../http-common";

export default {
  name: "ProductWrite",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      title : "상품명 및 제목을 입력하세요.",
      categories : ['카테고리1', '카테고리2', '카테고리3', '카테고리4', '카테고리5', ],
      mileage: 300,
    }
  },
  props:{
    product_id: Number,
  },
  created() {
    http.get(`/product/${this.product_id}`)
    .then((res)=>{
      console.log(res.data.object)
      this.product = res.data.object
    })
  },
  computed: {
  }
};
</script>

<style scoped>

.content-wrapper {
  width:560px;
}

.content-card {
  width:560px;
  padding : 30px;
}

.deal-btn {
  margin:30px;
  border: 2px solid orange;
  border-radius: 15px;
  background-color:white;

}

</style>