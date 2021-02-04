<template>
    <div>
        <Header></Header>

        <!-- 그룹 간략 소개 시작 -->

        <hr>
        <v-container>
            <v-row class="d-flex justify-center">
            <v-col cols="4">
                <h3># Group 1</h3>
                <p class="ma-0"> 멤버 : 6/10</p>
                <p class="ma-0"> 2020.01.29 ~ 2021.01.29</p>
                <p class="ma-0"> #홈트 #운동 #의지</p>
            </v-col>
            <v-col cols="4" sm-cols="2" class="d-flex align-center justify-space-around">
                <div class="group-image">
                    <img src="" alt="">
                </div>
            </v-col>
            </v-row>
        </v-container>
        <hr>
        <!-- 그룹 소개 끝 -->

        <v-container class="pa-3 pa-sm-16">
            <v-card  class="d-flex align-center flex-column my-15 mx-sm-auto px-5 col-sm-10" width=100%>
                <h3 class="mt-5 mb-3">피드 수정</h3>
                <v-divider style="width:100%;"></v-divider>
                 <v-col cols="12" lg="8" xl="6" class="my-5">
                    <v-row class="text-left">
                        <v-col cols="3" sm="2">
                            <v-img
                            id="profile"
                            src="@/assets/logo/profile_temp.png"
                            alt="profile-img"
                            class="profile-img my-auto mx-auto"
                            max-height="50"
                            max-width="50"
                            />
                        </v-col>
                        <v-col cols="9" sm="10" class="pa-0 my-auto">
                            <div>
                                {{name}}
                            </div>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-container fluid class="py-5">
                            <v-textarea
                                label="인증 내용 입력"
                                v-model="content"
                                auto-grow
                                outlined
                                rows="3"
                                row-height="23"
                            ></v-textarea>                            
                        </v-container>
                    </v-row>
                    <v-row class="d-flex mt-5">
                        <v-col cols="12" sm="4" class="my-auto">
                        <v-select
                            :items="items"
                            label="피드 종류 선택"
                            dense
                            outlined
                            v-model="selected"
                        ></v-select><!-- 기존피드값으로 기본값 설정하기-->                            
                        </v-col>                        
                        <v-col cols="12" sm="5">
                            <v-file-input
                                class="py-0"
                                truncate-length="50"
                                label="첨부파일 선택"
                            ></v-file-input>
                        </v-col>
                        <v-col cols="12" sm="2" class="ml-auto">
                            <v-btn
                            @click="update"
                            outlined
                            class="complete-btn text-size"
                            >수정 완료</v-btn>
                        </v-col>
                    </v-row>
                </v-col>
            </v-card>
        </v-container>
        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/components/common/Header";
import Footer from "@/components/common/Footer";
import { createFeed } from "@/api/feed/index.js"

export default {
    name :"FeedWrite",
    components: {
        Header,
        Footer
    },
    data() {
        return {
            name: 'Nickname',
            selected : '',
            content :'', // 기존피드가 가지고있던 컨텐트로 엮어주기
            items : [
                '인증',
                '정보 공유'
            ], 
            authCheck: false,
            authCnt: 0,
            authDate: '',
            createDate: new Date(), // 변경X
            feedPk:'',
            feedType: true,
            groupPk: '5', // 변경필요!
            media: "",
            status: true,
            updateDate: new Date(), // 이게 업데이트 후 변경! 
            userPk: '',
        }
    },
    watch: {
        selected: function() {
            if (this.selected == "인증") {
                this.feedType = true
            } else {
                this.feedType = false
            }
        }
    },
    methods: {
        update() {
            createFeed( //if 백엔드 URL 완성되면 그 이름에 맞춰서 변경예정
                {
                    "authCheck": this.authCheck,
                    "authCnt": this.authCnt,
                    "authDate": this.authDate,
                    "content": this.content,
                    "createDate": this.createDate,
                    "feedPk": this.feedPk,
                    "feedType": this.feedType,
                    "groupPk": this.groupPk,
                    "media": this.media,
                    "status": this.status,
                    "updateDate": this.updateDate,
                    "userPk": this.$store.state.userpk,
                },
                (res) =>{
                    if (res.status){
                    alert("피드가 수정되었습니다.")
                    console.log(res)
                    this.$router.push('/') // 어디로 보낼지 정하고 변경!
                    }
                },
                (err) =>{
                    console.log(err)
                    console.log(this.createDate)
                    alert("수정 실패")
                }
            )
        },
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
</style>