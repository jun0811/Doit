<template>
  <div>
    <Header></Header>
     <v-card  class="d-flex align-center flex-column my-15 mx-auto py-15" width=50%>
    <h3 class="my-5">비밀번호 찾기</h3>
    <h5 class="mb-5">비밀번호를 찾고자 하는 이메일을 입력해주세요</h5>
    <div>
      <v-container class="px-0">
        <v-row no-gutters class="d-flex flex-nowrap">
          <v-col md="12">
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
            <v-btn @click="checkEmail" text class="uncheck mt-4"> 
                <font-awesome-icon icon="check-circle"/> 
            </v-btn>
        </v-row>
      </v-container>
     
      <v-card-actions class="d-flex align-center"> 
        <v-row>
          <v-col>
            <button @click="sendEmail" class="join input col-12">다음</button>
            <div class="col-12 text-center">
              <span><router-link to="/">메인페이지로 돌아가기</router-link></span>
            </div>
          <br>

          </v-col>
        </v-row>
        </v-card-actions>
    </div>
  </v-card>

    <Footer></Footer>
  </div>
</template>

<script>
import Header from '../../components/common/Header.vue'
import Footer from '../../components/common/Footer.vue'
import { validationMixin } from 'vuelidate';
import { required, email } from 'vuelidate/lib/validators'
import http from '../../http-common'

export default {
  components: { Header, Footer },
  mixins: [validationMixin],
  validations: {
    email: { required, email },
    error:{
      email: false,
    },
  },
  data() {
    return {
      email: "",
      c_Email: false,
      error: {
        email: false,
      }
    }
  },
  created: {
    emailErrors () {
        const errors = []
        if (!this.$v.email.$dirty) return errors
        !this.$v.email.email && errors.push('이메일 양식을 입력해주세요.')
        !this.$v.email.required && errors.push('이메일을 입력해주세요.')
        return errors
      },
  },
  methods: {
    check() {
        // 수정시에 다시 체크
        this.c_Email = false
    },

    sendEmail(){
      http.post('/user/sendChangePwEmail', {
        "email": this.email
      })
      .then((res)=> {
        if(res.data.status)
          this.$router.push(`/user/mailcheck?email=${this.email}&option=p`)
      })
    },

    checkEmail(){
        http.post("/user/checkEmail", this.email)
        .then((res)=>{
          console.log(res);
          if(!res.data.status) this.c_Email = true
          else
            alert("가입하지 않은 이메일입니다.")
        })
      }
  }
}
</script>

<style>

</style>