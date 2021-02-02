<template>
    <div>
        <Header></Header>
        <v-container class="pa-3 pa-sm-16">
            <v-card  class="d-flex align-center flex-column my-15 mx-sm-auto px-5 col-sm-10" width=100%>
                <h3 class="mt-5 mb-3">그룹 만들기</h3>
                <v-divider style="width:100%;"></v-divider>
                <v-container class="d-flex flex-column">
                  <v-row class="my-5">
                    <v-col cols="8" sm="7" md="6" class="text-left">
                      <v-text-field 
                      v-model="name"
                      label="그룹명"></v-text-field>
                    </v-col>
                    <!-- 달력 시작-->
                    <v-col cols="10" md="6">
                      <!-- <span class="mr-3">|마감|</span> -->
                          <v-menu
                            v-model="menu"
                            :close-on-content-click="false"
                            transition="scale-transition"
                            offset-y
                            max-width="290px"
                            min-width="auto"
                          >
                            <template v-slot:activator="{ on, attrs }">
                              <v-text-field
                                v-model="endDate"
                                label="Date"
                                prepend-icon="mdi-calendar"
                                v-bind="attrs"
                                autocomplete="off"
                                @blur="date = parseDate(endDate)"
                                v-on="on"
                              ></v-text-field>
                            </template>
                            <v-date-picker
                              v-model="date"
                              no-title
                              @input="menu = false"
                            ></v-date-picker>
                          </v-menu>
                      <!-- 달력 끝 -->
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="10" sm="10" md="6">
                      <span>최대 멤버수: {{maxNum}}</span>
                      <v-slider
                        v-model="maxNum"
                        color="orange"
                        min="4"
                        max="16"
                        thumb-label
                      ></v-slider>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12">
                    <v-textarea
                      label="그룹 소개글"
                      v-model="content"
                      auto-grow
                      outlined
                      rows="3"
                      row-height="23"
                    ></v-textarea>                            
                  </v-col>
                </v-row>
                <v-row >
                  <v-col cols="4">
                    <v-text-field 
                      v-model="word"
                      label="해쉬태그"></v-text-field>
                    <v-btn
                      outlined
                      small
                      @click="add"
                    >추가</v-btn>
                  </v-col>
                  <v-col class="d-flex flex-wrap">
                    <ul v-for="(tag,idx) in hashtag" :key="idx" class="col-4"># {{ tag }}</ul>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="d-flex justify-center">
                    <v-btn
                      outlined
                      @click="create"
                    >그룹 생성</v-btn>
                  </v-col>
                </v-row>
                
              </v-container>
            </v-card>
        </v-container>
        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/components/common/Header";
import Footer from "@/components/common/Footer";
import { createGroup } from "@/api/group/index.js"
export default {
    name :"GroupCreate",
    components: {
        Header,
        Footer
    },
    data(vm){
      return{
      menu: false,
      date: new Date().toISOString().substr(0,10),
      name: "",
      maxNum : 0,
      endDate: vm.formatDate(new Date().toISOString().substr(0,10)),
      content :"",
      word:"",
      hashtag : []
      }
    },
    watch: {
      date(){
        this.endDate = this.formatDate(this.date)
      },
    },
    methods: {
      formatDate (date){
        if(!date) return null
        const [year,month, day] = date.split('-')
        console.log(year, month,day)
        return `${month}/${day}/${year}`
      },
      parseDate (date) {
        if (!date) return null

        const [month, day, year] = date.split('-')
        return `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`
      },
      add(){
        if (this.word){
          this.hashtag.push(this.word)
          this.word=""
        }else{
          alert('단어를 입력!')
        }
      },
      
      create(){
        createGroup(
          {
            "name": this.name,
            "maxNum": this.maxNum,
            "endDate": this.date,
            "content":this.content,
            "hashtags": this.hashtag
          },
          (res) =>{
            if (res.status){
              alert("그룹이 생성되었습니다.")
              this.$router.push('/')
            }
          },
          (err) =>{
            console.log(err)
            alert("생성 실패")
          }
        )
      }
  }
    
}
</script>

<style scoped>
.complete-btn {
    width: 100%;
    border: 1px solid #F9802D;
    color: #F9802D;
}

.text-size {
    font-size: 80%;
}
.calender {
  border: 1px groove;
}
.numberBox {
  width:"50%";
  border: 1px solid
}
</style>