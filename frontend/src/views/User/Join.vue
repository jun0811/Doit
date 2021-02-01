<template>
  <div>
    <Header></Header>
  <v-container class="pa-3 pa-sm-16">
  <v-card  class="d-flex align-center flex-column mx-3 mx-sm-16 py-16">
    <h3 class="my-5">회원가입</h3>
      <v-container class="input-width">
        <v-row no-gutters class="d-flex flex-nowrap" >
          <v-col cols="9" sm="11">
            <v-text-field
              v-model="name"
              :error-messages="nameErrors"
              :counter="8"
              maxlength="8"
              label="닉네임"
              required
              clearable
              @input="$v.name.$touch()"
              @blur="$v.name.$touch()"
            ></v-text-field>
          </v-col>
          <v-col cols="3" sm="1">
            <v-btn 
              text 
              @click="checkNick" 
              v-bind:class="{check : c_Nick}"
              class="mt-4 mx-0"
            >
              <font-awesome-icon icon="check-circle"/>
            </v-btn>
          </v-col>
        </v-row>
        <v-row no-gutters class="d-flex flex-nowrap">
          <v-col cols="9" sm="11">
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
          <v-col cols="3" sm="1">
            <v-btn text @click="checkEmail"  v-bind:class="{check : c_Email }" class="mt-4"> 
                <font-awesome-icon icon="check-circle"/> 
            </v-btn>
          </v-col>
        </v-row>
        <v-row no-gutters class="d-flex flex-nowrap">
        <v-text-field
          v-model="password"
          :error-messages="passwordErrors"
          label="비밀번호"
          clearable
          required
          type="password"
          @input="$v.password.$touch()"
          @blur="$v.password.$touch()"
        ></v-text-field>
        </v-row>
        <v-row no-gutters class="d-flex flex-nowrap">
        <v-text-field
          v-model="passwordConfirm"
          :error-messages="passwordConfirmErrors"
          label="비밀번호 확인"
          clearable
          required
          type="password"
          @input="$v.passwordConfirm.$touch()"
          @blur="$v.passwordConfirm.$touch()"
        ></v-text-field>
        </v-row>
        <v-row no-gutters class="d-flex flex-nowrap">
        <v-checkbox
          v-model="checkbox"
          :error-messages="checkboxErrors"
          label="약관에 동의합니다."
          required
          @change="$v.checkbox.$touch()"
          @blur="$v.checkbox.$touch()"
          class="mt-0"
        ></v-checkbox>
        </v-row>
        <v-row no-gutters class="d-flex flex-nowrap">
          <v-col cols="12">
            <v-btn 
              type="submit" 
              @click="signup()" 
              class="join input mt-2"
              outlined
            >
            가입하기
            </v-btn>
          </v-col>
        </v-row>
        <v-row no-gutters class="d-flex justify-end flex-nowrap mt-3 font-sytle">
          <span><router-link to="/">메인페이지로 돌아가기</router-link></span>
        </v-row>
      </v-container>
    </v-card>
    </v-container>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from '@/components/common/Header.vue';
import Footer from '@/components/common/Footer.vue';
import { validationMixin } from 'vuelidate';
import { required, maxLength, sameAs, minLength, email } from 'vuelidate/lib/validators'
import http from '../../http-common'

export default {
  name: 'Join',
  components: { 
    Header,
    Footer,  
  },
  mixins: [validationMixin],
  validations: {
      name: { required, maxLength: maxLength(8) },
      email: { required, email },
      password:{ required, minLength: minLength(8)},
      passwordConfirm:{ sameAsPassword: sameAs("password")},
      error:{
        email: false,
        nickname: false
      },
      checkbox: {
        checked (val) {
          return val
        },
      },
    },

    data: () => ({
      name: '',
      email: '',
      password: '',
      passwordConfirm: '',
      checkbox: false,
      c_Nick: false,
      c_Email: false,
      error: {
        email: false,
        passowrd: false
      }
      }
    ),
    watch: {
      name(){
        this.check1();
      },
      email(){
        this.check2()
      }
    },
    computed: {
      checkboxErrors () {
        const errors = []
        if (!this.$v.checkbox.$dirty) return errors
        !this.$v.checkbox.checked && errors.push('약관에 동의하셔야 회원가입이 가능합니다.')
        return errors
      },
      nameErrors () {
        const errors = []
        if (!this.$v.name.$dirty) return errors
        !this.$v.name.maxLength && errors.push('닉네임은 8자이하입니다.')
        !this.$v.name.required && errors.push('닉네임이 필요합니다.')
        return errors
      },
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
      passwordConfirmErrors (){
        const errors = []
        if(!this.$v.passwordConfirm.$dirty) return errors
        !this.$v.passwordConfirm.sameAsPassword && errors.push('비밀번호와 같지않습니다.')
        return errors
      }
    },

    methods: {
      check1() {
        // 수정시에 다시 체크
        this.c_Nick = false
      },
      check2() {
        // 수정시에 다시 체크
        this.c_Email = false
      },


      signup (){
        if(this.$v.$invalid || this.c_Nick===false || this.c_Email===false){
          alert("가입 정보를 정확히 기입해주세요! 🙏")
        }
        else{
          console.log(this.$v.$invalid )
          http.post('/user/signup', {
            "email": this.email,
            "password": this.password,
            "nickname": this.name
          })
            .then((response)=>{
              console.log(response);
              this.$router.push(`/user/mailcheck?email=${this.email}&option=s`);
            })
            .catch((error)=>{
              console.log(error)
            })}
      },
      checkEmail(){
        http.post("/user/checkEmail", this.email)
        .then((res)=>{
          console.log(res);
          if(res.data.status) this.c_Email = true
          else{
            alert("중복메일입니다.")}
        })
      },
      checkNick(){
        http.post("/user/checkNick", this.name)
        .then((res)=>{
          console.log(res);
          if(res.data.status) this.c_Nick = true
          else{
            alert("중복 닉네임입니다")
           }
        })
      }
    },
  }
</script>
<style scoped>
  .check {
    color: #F9802D;
  }
  .uncheck {
    color: black;
  }
  .join.input{
    width:100%;
    border:2px solid #F9802D;
    border-radius: 8px;
    color: #F9802D;
  }

  .input-width {
    width: 60%;
  }
  
  @media only screen and (min-width: 300px) and (max-width: 599px) {
      .input-width {
        width: 80%;
      }
  }

  .font-sytle {
    font-size: 80%;
  }
</style>
