<template>
  <div>
    <Header></Header>
     <v-card  class="d-flex align-center flex-column my-15 mx-auto py-15" width=50%>
    <h3 class="my-5">비밀번호 찾기</h3>
    <!-- <form class=""> -->

      <v-container class="px-0">
        <v-row no-gutters class="d-flex flex-nowrap">
          <v-col md="12">
            <v-text-field
              v-model="email"
              :error-messages="emailErrors"
              label="E-mail"
              placeholder="비밀번호를 찾을 이메일을 입력해주세요"
              required
              clearable
              @input="$v.email.$touch()"
              @blur="$v.email.$touch()"
            ></v-text-field>
          </v-col>
            <v-btn text class="uncheck mt-4"> 
                <font-awesome-icon icon="check-circle"/> 
            </v-btn>
        </v-row>
      </v-container>
      <!-- <v-text-field
        v-model="password"
        :error-messages="passwordErrors"
        label="비밀번호"
        clearable
        required
        @input="$v.password.$touch()"
        @blur="$v.password.$touch()"
      ></v-text-field>
      <v-text-field
        v-model="passwordConfirm"
        :error-messages="passwordConfirmErrors"
        label="비밀번호 확인"
        clearable
        required
        @input="$v.passwordConfirm.$touch()"
        @blur="$v.passwordConfirm.$touch()"
      ></v-text-field>
      <v-checkbox
        v-model="checkbox"
        :error-messages="checkboxErrors"
        label="Do you agree?"
        required
        @change="$v.checkbox.$touch()"
        @blur="$v.checkbox.$touch()"
      ></v-checkbox> -->

      <button @click="sendEmail" class="join input">다음</button>
      <div class="mt-3 d-flex justify-end ml-auto mr-5">
        <span><router-link to="/">메인페이지로 돌아가기</router-link></span>
      </div>
    <!-- </form> -->
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
    sendEmail(){
      http.post('/user/sendChangePwEmail', {
        "email": this.email
      })
      .then((res)=> {
        if(res.data.status)
          this.$router.push(`/user/mailcheck?email=${this.email}&option=s`)
      })
    }
  }
}
</script>

<style>

</style>