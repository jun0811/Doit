<template>
    <div>
        <Header></Header>
        <v-container class="pa-3 pa-sm-16" >
            <v-card  class="d-flex align-center flex-column mb-12 mx-sm-auto px-5 col-sm-10" width=100% max-width=600>
                <h3 class="my-4" style="width:100%">그룹 만들기</h3>
                <v-divider style="width:100%;"></v-divider>
                <v-container class="d-flex flex-column">
                  <v-row class="mb-5">
                    <v-col cols="12" sm="12" md="6" class="text-left">
                      <v-text-field 
                      v-model="name"
                      hide-details=""
                      label="그룹명"></v-text-field>
                    </v-col>
                    <!-- 달력 시작-->
                    <v-col cols="12" md="6" sm="12">
                      <!-- <span class="mr-3">|마감|</span> -->
                          <v-menu
                            v-model="menu"
                            :close-on-content-click="false"
                            transition="scale-transition"
                            offset-y
                            max-width="290px"
                            min-width="auto"
                            hide-details=""
                          >
                            <template v-slot:activator="{ on, attrs }">
                              <v-text-field
                                v-model="endDate"
                                hide-details=""
                                label="만료 날짜"
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
                  <v-row class="mb-3">
                    <v-col cols="12" md="6" sm="12">
                      <span>최대 멤버수: {{maxNum}}</span>
                      <v-slider
                        v-model="maxNum"
                        hide-details=""
                        color="orange"
                        min="3"
                        max="16"
                        thumb-label
                      ></v-slider>
                    </v-col>
                    <v-col class="pt-1">
                      <v-select
                        hide-details=""
                        :items="items"
                        label="카테고리"
                        v-model="tmp"
                      ></v-select>
                    </v-col>
                  </v-row>
                  <v-row class="mb-2">
                    <v-col cols="12">
                    <v-textarea
                      label="그룹 소개글"
                      v-model="content"
                      hide-details=""
                      auto-grow
                      outlined
                      rows="3"
                      row-height="23"
                    ></v-textarea>                            
                  </v-col>
                </v-row>
                <v-row >
                  <v-col cols="12" class="d-flex justify-start">
                    <v-text-field 
                      v-model="word"
                      hide-details=""
                      label="해쉬태그"></v-text-field>
                    <v-btn
                      outlined
                      small
                      @click="add"
                      class ="mt-4 ml-3"
                    >추가</v-btn>
                  </v-col>
                  <v-col cols="12" class="d-flex flex-wrap mb-5 py-0">
                    <ul>
                      <li v-for="(tag,idx) in hashtag" :key="idx" ># {{ tag }}
                        <button @click="remove(idx)" class="hashtag-del-btn">                     
                          <!-- |x| -->
                          <font-awesome-icon icon="times-circle"/>
                        </button>  
                      </li> 
                    </ul>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          <v-row>
            <v-col class="d-flex justify-center">
              <v-btn
                outlined
                @click="create"
                class="group-create-btn"
              >그룹 생성</v-btn>
            </v-col>
          </v-row>
        </v-container>
        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/components/common/Header";
import Footer from "@/components/common/Footer";
// import { createGroup } from "@/api/group/index.js"
import http from "../../http-common"
export default {
    name :"GroupCreate",
    components: {
        Header,
        Footer
    },
    data(vm){
      return{
      submit: false,
      menu: false,
      date: new Date().toISOString().substr(0,10),
      minLength : false,
      name: "",
      maxNum : 0,
      endDate: vm.formatDate(new Date().toISOString().substr(0,10)),
      content :"",
      word:"",
      hashtag : [],
      tmp: "",
      category : "",
      items: ['운동', '공부', '다이어트', '취미', '독서', '라이프', '그 외'],
      change :{
        "운동" : "exercise",
        "공부" : "study",
        "다이어트" : "diet",
        "취미" : "hobby",
        "독서" : "book",
        "라이프" : "life",
        "그 외" : "etc"
      }
      }
    },
    watch: {
      endDate(){
        let END = new Date(this.endDate)
        console.log(END)
        const TODAY = new Date()
        let check = Math.ceil((END.getTime() - TODAY.getTime())/(1000*3600*24));
        if (check<7){
          alert('최소 일주일 이후 날짜를 선택해주세요.')
          // this.endDate = `${}`
          this.endDate =""
          this.submit = false
        }else if(check>=7){
          this.submit = true
        } 
      },
      date(){
        this.endDate = this.formatDate(this.date)
      },
      content(){
        if (this.content.length>=20){
          this.minLength = true;
        }else this.minLength = false;
      },
      tmp(){
        this.category = this.change[this.tmp]
      }
    },
    methods: {
      formatDate (date){
        if(!date) return null
        const [year,month, day] = date.split('-')
        return `${year}-${month}-${day}`
      },
      parseDate (date) {
        if (!date) return null

        const [month, day, year] = date.split('-')
        return `${year}-${String(month).padStart(2, "0")}-${String(day).padStart(2, "0")}`
      },
      add(){
        if (this.word){
          this.hashtag.push(this.word)
          this.word=""
        }else{
          alert('단어를 입력!')
        }
      },
      remove(idx){
        this.hashtag.splice(idx,1)
      },
      
      create(){
        if (this.minLength && this.name &&this.submit){
          http.post('/group/createGroup',
          {
            "name": this.name,
            "maxNum": this.maxNum,
            "endDate": this.date,
            "content":this.content,
            "hashtags": this.hashtag,
            "category": this.category
          })
          .then((res) =>{
            console.log(res)
            alert('생성완료')
          })
        }else{
          if(this.name.length<1) alert('그룹명을 입력해주세요')
          else if(this.submit===false) alert('만료 날짜를 선택해주세요')
          else {alert(`그룹 소개글을 20자 이상 작성해주세요 ` )}
        }
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
ul{
   list-style:none;
   padding-left:0px;
}
ul li {
  float: left;
  margin-right: 15px;
}

.hashtag-del-btn {
  color:#FFE0B2;
  outline: transparent;
}

.group-create-btn {
  border: 2px solid #F9802D;
  color: #F9802D;
  border-radius: 15px;
}

</style>