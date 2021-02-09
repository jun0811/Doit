<template>
<div>
  <Header></Header>
    <v-container class="d-flex flex-column justify-center align-center pa-0" sm="10" md="6" style="max-width:600px;">
        <v-card
          elevation="2"
          outlined
          shaped
          tile
          class="content-card pt-3 pb-6"
        >
          <h3 class="my-4" style="width:100%;">물품 등록</h3>
          <v-divider style="width:100%;"></v-divider>
          <v-row class="d-flex flex-column mt-3" >
            <v-col cols="12">
              <v-text-field
                hide-details=""
                solo
                :label="title"
                clearable
                v-model="product.title"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row class="d-flex align-center mt-3">
            <v-col cols="4">카테고리 선택</v-col>
            <v-col cols="8">
              <v-select
                hide-details=""
                :items="categories"
                label="카테고리 선택"
                outlined
                v-model="product.category"
              ></v-select>
            </v-col>
          </v-row>
          <v-row class="d-flex align-center mt-3">
            <v-col cols="4">판매 금액 설정</v-col>
            <v-col cols="8" class="d-flex align-center">
              <v-text-field
                hide-details=""
                label="판매금액"
                outlined
                v-model="product.mileage"
              ></v-text-field>
              <span class="ml-2" style="word-break: keep-all">마일리지</span>
            </v-col>
          </v-row>
          <v-row class="my-3">
            <v-col cols="12">내용 입력</v-col>
            <v-textarea
              outlined
              name="input-7-4"
              label="판매 물품에 대한 설명"
              hide-details=""
              v-model="product.content"
            ></v-textarea>
          </v-row>
          <v-row class="my-2">
            <v-col cols="12">사진 첨부<span>(최대 5장)</span></v-col>
            <v-file-input
              chips
              small-chips
              truncate-length="15"
              hide-details=""
              v-on:change="product.image"
            ></v-file-input>
          </v-row>
        </v-card>

        <v-btn class="submit-btn mt-12" 
          outlined
          @click="write"
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
import { createProduct } from "@/api/mileage/index.js"

export default {
  name: "ProductWrite",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      title : "상품명 및 제목을 입력하세요.",
      categories : ['음식', '책', '운동', '카테고리4', '카테고리5', ],
      mileage: "300",
      product : {
        category : '',
        content : '',
        image : '',
        title : '',
        userPk : 0,
        mileage:'',
      },
    }
  },
  methods: {
    write() {
      createProduct(
        {
          "category": this.product.category,
          "content":  this.product.content,
          "image": this.product.image,
          "title":  this.product.title,
          "id":0,
          "mileage": this.product.mileage,
        },
        (res) =>{
          if (res.status){
          alert("물품을 등록했습니다.")
          console.log(res)
          // this.$router.push('/') // 어디로 보낼지 정하고 변경!
          }
        },
        (err) =>{
          console.log(err)
          alert("등록 실패")
        }
      )
    },
  }
};
</script>

<style scoped>

.title-wrapper {
  width:100%;
}



.content-card {
  width:100%;
  padding : 30px;
}

.submit-btn {
  margin:30px;
  border: 2px solid orange;
  border-radius: 15px;
  background-color:white;

}

</style>


