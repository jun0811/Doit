<template>
  <div>
    <Header></Header>
    <v-container class="pa-8 pa-sm-16">
        <form>
            <v-col cols="12">
                <v-row justify="center">
                    <v-col cols="12" md="3" lg="2">
                        <div class="text-center">
                            <img
                                src="@/assets/logo/profile_temp.png"
                                alt="profile-img"
                                class="profile-img"
                            />
                            
                            <div class="pt-3 text-center">
                                <v-btn 
                                    @click="changhPhoto"
                                    outlined
                                    rounded
                                >사진 변경</v-btn>
                            </div>
                        </div>
                    </v-col>

                    <v-col cols="12" md="5" lg="3">
                        <v-row no-gutters class="d-flex flex-nowrap m" >
                            <v-subheader>닉네임</v-subheader>
                            <v-text-field
                                v-model="name"
                                required
                                clearable
                                :error-messages="nameErrors"
                                :counter="8"
                                @input="$v.name.$touch()"
                                @blur="$v.name.$touch()"
                            ></v-text-field>
                        </v-row>
                        <v-row no-gutters class="d-flex flex-nowrap m" >
                            <v-subheader>이메일</v-subheader>
                            <v-text-field
                                v-model="email"
                                required
                                disabled
                                @input="$v.name.$touch()"
                                @blur="$v.name.$touch()"
                            ></v-text-field>
                        </v-row>
                        <v-row no-gutters class="d-flex flex-nowrap m" >
                            <v-subheader>비밀번호</v-subheader>
                            <div class="pt-3 text-center">
                                <v-btn 
                                    @click="changePW"
                                    outlined
                                    rounded
                                >비밀번호 변경</v-btn>
                            </div>
                        </v-row>
                    </v-col>
                </v-row>
                <v-row justify="center">
                    <v-col cols="12" md="9" lg="6" class="text-right">
                        <v-btn text x-large>변경사항 저장 ></v-btn>
                    </v-col>
                </v-row>
            </v-col>
        </form>
    </v-container>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
export default {
    name : "Update",
    components :{
        Header,
        Footer
    },
    data() {
        return {
            name : "test",
            email : "test@test.com"
        }
    },
    methods: {
        changhPhoto(){

        },
        changePW(){
            this.$router.push('/user/pwchange');
        }
    }
}
</script>

<style>

</style>