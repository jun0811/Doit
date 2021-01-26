<template>
  <header class="header">

    <img 
      src="@/assets/logo/logo.png" 
      alt="Logo"
      class="logo"
      @click="logoClick"
    >

    <div>  
      <v-dialog
        v-model="dialog"
        persistent
        max-width="600px"
      >
        <template v-slot:activator="{on}">
          <v-btn
          text
          v-on="on"
          class= "px-0"
          >
          로그인
          </v-btn>
        </template>
        <v-card class="d-flex align-center flex-column mx-auto">
          <v-btn
          text
          @click="dialog=false"
          >
            <font-awesome-icon icon="times-circle"/>
          </v-btn>
          <v-card-title>
            <h3 class="my-7">로그인</h3>
          </v-card-title>
          <v-card-text>
           <v-container>
            <v-row class="d-flex align-center flex-column">
              <v-col cols="8">
                <v-text-field
                v-model="email"
                :error-messages="emailErrors"
                label="E-mail"
                required
                clearable
                @input="$v.email.$touch()"
                @blur="$v.email.$touch()"
                ></v-text-field>
              </v-col>
            <v-col cols="8">
              <v-text-field
                v-model="password"
                :error-messages="passwordErrors"
                label="비밀번호"
                clearable
                type="password"
                required
                @input="$v.password.$touch()"
                @blur="$v.password.$touch()"
              ></v-text-field>
            </v-col>
          </v-row>
          </v-container>
        </v-card-text>
    
        <button class="login input" @click="login()">로그인</button>
          <v-card-actions > 
            <v-container class="d-flex justify-space-between mb-6">
              <v-btn
                color="#F9802D"
                text
              >회원 가입</v-btn>
              <v-btn
                color="#F9802D"
                text
                @click="passwordFind"
              >비밀번호 찾기</v-btn>
            </v-container>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <!-- 로그인창 끝 -->

      <v-btn
        text
        @click="signup"
        class= "px-0"
      >
        회원가입
      </v-btn>
      <v-btn
        text
        @click="mypage"
        class= "px-0"
      >
        마이페이지
      </v-btn>
    </div>
  </header>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, minLength, email } from 'vuelidate/lib/validators'
import { mapActions } from 'vuex'

export default {
    name: "Header",
    mixins: [validationMixin],
    validations: {
      email: { required, email },
      password:{ required, minLength: minLength(8)},
    },

    data: () => ({
      dialog: false,
      email: "",
      password: "",
      users: [
        {id: 1, name: "LSJ",email:"nate1994@naver.com", password:"12345678"},
        {id: 2, name: "KSJ",email:"itoggi0328@naver.com", password:"12345678"}
      ],
    }),
    computed: {
      emailErrors () {
        const errors = []
        if (!this.$v.email.$dirty) return errors
        !this.$v.email.email && errors.push('이메일 양식을 입력해주세요.')
        !this.$v.email.required && errors.push('이메일을 입력해주세요.')
        return errors
      },
      passwordErrors (){
        const errors = []
        if(!this.$v.password.$dirty) return errors
        !this.$v.password.minLength && errors.push('8자 이하입니다')
        !this.$v.password.required && errors.push('비밀번호는 필수입니다')
        return errors
      },
    },

    methods: {
      ...mapActions(['LOGIN']),

      signup() {
        this.$router.push("/user/join")
      },
      logoClick() {
        this.$router.push("/")
      },
      mypage() {
        this.$router.push("/user/profile")
      },
      login(){
        this.LOGIN({
          "email": this.email,
          "password": this.password
        })
        .then((response)=>{
          console.log(response);
          if(response.data.status)
            this.dialog = false;
        })
      }
    }
}
</script>

<style scoped>
  .login.input{
    width:50%;
    border:2px solid #F9802D;
    border-radius: 8px;
    color: #F9802D;
  }
  .btn-header {
    padding : 0
  }
  
</style>


