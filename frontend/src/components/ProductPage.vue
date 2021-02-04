<template>
  <v-container class="d-flex flex-column align-center">
    <v-row
      class="my-6 px-6 d-flex align-center flex-wrap justify-start"
    > 
      <v-col
        cols="4"
        v-for="(item, idx) in paginatedData"
        :key="idx"
        class="d-flex justify-center px-6 py-6"
      >
        <v-card height="100%" width="100%">
          <v-img
            src="https://images.unsplash.com/photo-1519682337058-a94d519337bc?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"
            class="white--text align-end"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="200px"
          >
            <!-- <v-card-title v-text="item.title"></v-card-title> -->
          </v-img>
          <v-card-actions>
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
import http from "../http-common";

  export default {
    data: () => ({
      products:[],
    }),
    props: {
      page:Number,
      pageCount:Number,
    }, 
    created() {
      http.get('/product/getAll')
      .then((res)=>{
        this.products = res.data.object
        console.log(this.products)
      })
    },
    computed: {
       paginatedData () {
        const listSize = 9
        const start = (this.page -1) * listSize,
              end = start + listSize;
        return this.products.slice(start, end);
      }
    }
  }
</script>

<style scoped>


</style>