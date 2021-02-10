<template>
  <div>
    <Header></Header>
    <v-container class="d-flex flex-column justify-center pa-0 pa-md-3">
      <v-card>
        <v-card-title>
          <!-- <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
          ></v-text-field> -->
        </v-card-title>
        <v-data-table
          :headers="headers"
          :items="mileageList"
          :search="search"
        ></v-data-table>
      </v-card>
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
  name: "MileageShop",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      search: '',
      headers: [
        {
          text: '구분',
          align: 'start',
          filterable: false,
          value: 'content',
        },
        { text: '적립날짜', value: 'date' },
        { text: '마일리지', value: 'mileage' },
      ],
      mileageList: [
        {
          content:'',
          date: '',
          mileage:'',
        },
      ],
    loginUser:'',
    }
  },
  created(){ 
    this.loginUser = this.$store.state.account.userpk
    this.getHistory()
    console.log(this.loginUser)
  },
  methods: {
    getHistory() {
      http.get(`/user/mileageList?userPk=${this.loginUser}`)
      .then((res)=>{
        this.mileageList = res.data.object
        console.log(this.mileageList)
      })
    },
  },
  computed : {
  },
};
</script>

<style scoped>

</style>