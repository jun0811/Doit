<template>
  <v-container class="px-0 px-md-16">
    <v-row
      class="d-flex align-center flex-wrap justify-center justify-sm-start justify-md-start justify-lg-start justify-xl-start"
    > 
      <v-col
        xs="12"
        sm="4"
        md="4"
        v-for="(product, idx) in products"
        :key="idx"
        class="d-flex justify-center pa-4"
      >
          <v-card 
          router-link :to="{name: 'ProductDetail', params: {product_id: product.id}}">
            <v-img
              :src="`http://ssafydoit.s3.ap-northeast-2.amazonaws.com/` + product.image"
              class="white--text align-end product-image"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
              <!-- <v-card-title v-text="item.title"></v-card-title> -->
            </v-img>
            <v-card-actions  class="card-text">
              <v-card-text>
                {{product.title}}
              </v-card-text>
            </v-card-actions>

          </v-card>

      </v-col>
    </v-row>
  </v-container>
  
</template>

<script>
import http from "../../http-common";

  export default {
    data: () => ({
      products:[],
      direction:'DESC',
    }),
    props: {
      page:Number,
      keyword:String,
      option:String,
    }, 
    created() {
      this.getProducts()
    },
    methods: {
      getProducts() {
        http.get(`/product/search?direction=${this.direction}&keyword=${this.keyword}&option=${this.option}&pg=${this.page}`)
        .then((res)=>{
          this.products = res.data.object.content
        })
      }
    },
    watch: {
      keyword: function () {
        this.getProducts()
      }
    }
  }
</script>

<style scoped>
.product-image {
  width: 100%;
  height:100%;

}
</style>