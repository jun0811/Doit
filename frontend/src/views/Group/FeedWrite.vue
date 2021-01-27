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
            <v-card  class="d-flex align-center flex-column my-15 mx-auto px-5" width=70%>
                <h3 class="mt-5 mb-3">피드 작성</h3>
                <v-divider style="width:100%;"></v-divider>
                 <v-col cols="12" lg="8" xl="6" class="my-5">
                    <v-row class="text-left">
                        <v-col cols="5" sm="3" md="2">
                            <v-img
                            id="profile"
                            src="@/assets/logo/profile_temp.png"
                            alt="profile-img"
                            class="profile-img my-auto mx-auto"
                            max-height="60"
                            max-width="60"
                            />
                        </v-col>
                        <v-col cols="7" sm="5" md="6" class="pa-0 my-auto">
                            <div>
                                {{name}}
                            </div>
                        </v-col>
                        <v-col cols="12" sm="4" class="my-auto">
                            <v-select
                            :items="items"
                            v-model="selected"
                            dense
                            outlined
                            ></v-select>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-container fluid class="py-0">
                            <v-textarea
                            v-model="content"
                            placeholder="인증 결과물을 올려주세요"
                            ></v-textarea>
                        </v-container>
                    </v-row>
                    <v-row>
                        <v-file-input
                        class="py-0"
                        truncate-length="50"
                        ></v-file-input>
                    </v-row>
                    <v-row justify="center" class="mt-5">
                        <v-btn
                        @click="write"
                        outlined
                        >게 시</v-btn>
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

export default {
    name :"FeedWrite",
    components: {
        Header,
        Footer
    },
    data() {
        return {
            name: 'Nickname',
            selected : '인증',
            content :'',
            items : [
                '인증',
                '정보 공유'
            ]
        }
    },
    methods: {
        write() {

        },
    }
}
</script>

<style>

</style>