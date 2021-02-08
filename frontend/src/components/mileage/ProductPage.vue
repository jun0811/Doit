<template>
  <v-container class="d-flex flex-column align-center">
    <v-row
      class="my-6 px-6 d-flex align-center flex-wrap justify-start"
    > 
      <v-col
        cols="4"
        v-for="(item, idx) in paginatedData"
        :key="idx"
        class="d-flex justify-center px-4 py-4"
      >
          <v-card height="100%" width="100%" router-link :to="{name: 'ProductDetail', params: {product_id: item.id}}">
            <v-img
              src="https://images.unsplash.com/photo-1512621776951-a57141f2eefd?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"
              class="white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
              <!-- <v-card-title v-text="item.title"></v-card-title> -->
            </v-img>
            <v-card-actions  class="card-text">
              <v-spacer></v-spacer>
              <v-card-text>
                {{item.title}}
              </v-card-text>
            </v-card-actions>

          </v-card>

      </v-col>
    </v-row>
    <v-row>
      <v-pagination
        color="orange"
        v-model="page"
        :length="pageCount"
        :total-visible="7"
      ></v-pagination>
    </v-row>
  </v-container>
  
</template>

<script>
import http from "../../http-common";

  export default {
    data: () => ({
      products:[],
    }),
    props: {
      page:Number,
      pageCount:Number,
      selectedCategory:String,
    }, 
    created() {
      http.get('/product/getAll')
      .then((res)=>{
        this.products = res.data.object
        console.log(this.products)
      })
    },
    watch: {
      selectedCategory: {
        handler : function () {
          console.log(this.selectedCategory)
          if(this.selectedCategory==='전체') {
              http.get('/product/getAll')
              .then((res)=>{
                this.products = res.data.object
              })
            } else {
              http.get(`product/searchCategory?category=${this.selectedCategory}`)
              .then((res)=>{
                this.products = res.data.object
              })
            }
          },
        deep: true,
      },
      products : {
        handler :function () {
          let listLeng = this.products.length,
            listSize = 9,
            page = Math.floor(listLeng / listSize);
          if (listLeng % listSize > 0) page += 1;
          this.pageCount = page
        }
      }
    },
    computed: {
      paginatedData () {
        const listSize = 9
        const start = (this.page -1) * listSize,
              end = start + listSize;
        return this.products.slice(start, end);
      }
    },
  }
</script>

<style scoped>

.card-text {
  background: linear-gradient(to bottom left,#FFD8CB 40%, #F9D29D 100%  );
}


</style>