<template>
    <div>
        <Header></Header>

        <v-container class="pa-3 pa-sm-16">
            <v-card  class="d-flex align-center flex-column mb-15 mx-sm-auto px-5 col-sm-10" width=100% max-width="600">
                <h3 class="my-4" style="width:100%">피드 작성</h3>
                <v-divider style="width:100%;"></v-divider>
                 <v-container cols="12" lg="8" xl="6" class="my-2">
                    <v-row class="text-left">
                        <v-col cols="3" sm="2" class="d-flex align-center">
                            <v-img
                            id="profile"
                            src="@/assets/img/profile_temp.png"
                            alt="profile-img"
                            class="profile-img my-auto mx-auto"
                            max-height="44"
                            max-width="44"
                            />
                        </v-col>
                        <v-col cols="9" sm="10" class="pa-0 my-auto">
                            <div>
                                {{this.$store.getters.getName}}
                            </div>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-container fluid class="py-5">
                            <v-textarea
                                label="인증 내용 입력"
                                hide-details=""
                                v-model="content"
                                auto-grow
                                outlined
                                rows="3"
                                row-height="50"
                            ></v-textarea>                            
                        </v-container>
                    </v-row>
                    <v-row class="d-flex mt-5">
                        <v-col cols="12" sm="6" class="my-auto">
                        <v-select
                            :items="items"
                            hide-details=""
                            label="피드 종류 선택"
                            dense
                            outlined
                            v-model="selected"
                        ></v-select>                            
                        </v-col>                        
                        <v-col cols="12" sm="6" class="mb-5">
                            <v-file-input
                                class="py-0"
                                hide-details=""
                                truncate-length="50"
                                label="첨부파일 선택"
                            ></v-file-input>
                        </v-col>
                    </v-row>
                </v-container>
            </v-card>
            <v-row>
                <v-col class=" d-flex justify-center">
                    <v-btn
                    @click="write"
                    outlined
                    class="complete-btn text-size"
                    >작성 완료</v-btn>
                </v-col>
            </v-row>
        </v-container>
        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/components/common/Header";
import Footer from "@/components/common/Footer";
import { createFeed } from "@/api/feed/index.js"
// import http from "../../http-common";

export default {
    name :"FeedWrite",
    props:{
        groupPk: String,
    },
    components: {
        Header,
        Footer
    },
    data() {
        return {
            selected : '',
            content :'',
            items : [
                '인증',
                '정보 공유'
            ], 
            feedPk:'',
            feedType: true,
            media: "",
            status: true,
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
        write() {
            createFeed(
                {
                    "content": this.content,
                    "feedType": this.feedType,
                    "groupPk": this.groupPk,
                    // "media": this.media,
                    // "status": this.status,
                },
                (res) =>{
                    if (res.data.status){
                    alert("피드가 생성되었습니다.")
                    console.log(res)
                    this.$router.go(-1)

                    }
                },
                (err) =>{
                    console.log(err)
                    console.log(this.createDate)
                    alert("생성 실패")
                }
            )
        },
    }
}
</script>

<style scoped>
.complete-btn {
    border: 2px solid #F9802D;
    color: #F9802D;
    border-radius: 15px;
}

.text-size {
    font-size: 80%;
}
</style>