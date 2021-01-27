<template>
  <div>
    <Header></Header>
     <v-card  class="d-flex align-center flex-column my-15 mx-auto py-15" width=50%>
    <h3 class="my-5">비밀번호 변경</h3>
    <div>
      <v-text-field
        v-model="password"
        :error-messages="passwordErrors"
        label="변경할 비밀번호"
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
      
      <v-card-actions class="d-flex align-center"> 
        <v-row>
          <v-col>
            <button @click="execute" class="join input col-12">변경하기</button>
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
import { required, sameAs, minLength } from 'vuelidate/lib/validators'
import http from '../../http-common'

export default {
    components: { Header, Footer },
    mixins: [validationMixin],
    validations: {
      password:{ required, minLength: minLength(8)},
      passwordConfirm:{ sameAsPassword: sameAs("password")},
    },
    data() {
        return {
            password : "",
            passwordConfirm : "",
        }
    },
    computed: {
        passwordErrors (){
        const errors = []
        if(!this.$v.password.$dirty) return errors
        !this.$v.password.minLength && errors.push('8자 이하입니다')
        !this.$v.password.required && errors.push('비밀번호는 필수입니다')
        return errors
      },
      passwordConfirmErrors (){
        const errors = []
        if(!this.$v.passwordConfirm.$dirty) return errors
        !this.$v.passwordConfirm.sameAsPassword && errors.push('비밀번호와 같지않습니다.')
        return errors
      }
    },
    methods: {
        changePW(){
            // 마이페이지에서 변경
            http.post('/user/changePw', {
                "password": this.password
            })
            .then((res)=>{
                alert(res.data.status);
                this.$router.push('/');
            })
        },
        FindPW(){ // 비밀번호 찾기로 변경
            http.post('/user/confirmPw', {
                "authKey": this.$route.query.authKey,
                "email": this.$route.query.email,
                "password": this.password
            })
            .then((res)=>{
                alert(res.data.status);
                this.$router.push('/');
            })
        },
        execute() {
            if(this.$store.getters.getAccessToken) this.changePW();
            else this.FindPW();
        },
    }
}
</script>

<style>

</style>