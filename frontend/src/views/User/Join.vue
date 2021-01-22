<template>
  <div>
    <Header></Header>
  <v-card  class="d-flex align-center flex-column my-15 mx-auto py-15" width=50%>
    <div class="text-h4 my-5">회원가입</div>
    <form class="">
      <v-container class="px-0 ">
        <v-row no-gutters class="d-flex flex-nowrap">
          <v-col md="12">
            <v-text-field
              v-model="name"
              :error-messages="nameErrors"
              :counter="8"
              label="닉네임"
              required
              clearable
              @input="$v.name.$touch()"
              @blur="$v.name.$touch()"
            ></v-text-field>
          </v-col>
          <v-btn text class="uncheck mt-4 mx-0"> 
            <font-awesome-icon icon="check-circle"/> 
          </v-btn>
        </v-row>
      </v-container>
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
            <v-btn text class="uncheck mt-4"> 
                <font-awesome-icon icon="check-circle"/> 
            </v-btn>
        </v-row>
      </v-container>
      <v-text-field
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
      ></v-checkbox>

      <button class="join input">가입하기</button>
      <div class="mt-3 d-flex justify-end">
        <span><router-link to="/">메인페이지로 돌아가기</router-link></span>
      </div>
    </form>
  </v-card>
  </div>
</template>

<script>
import Header from '@/components/common/Header.vue'
import { validationMixin } from 'vuelidate'
import { required, maxLength, sameAs, minLength, email } from 'vuelidate/lib/validators'

export default {
  name: 'HelloWorld',
  components: { Header},
  mixins: [validationMixin],
    validations: {
      name: { required, maxLength: maxLength(8) },
      email: { required, email },
      password:{ required, minLength: minLength(8)},
      passwordConfirm:{ sameAsPassword: sameAs("password")},
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
      checkid: false,
      checkemail: false
    }),

    computed: {
      checkboxErrors () {
        const errors = []
        if (!this.$v.checkbox.$dirty) return errors
        !this.$v.checkbox.checked && errors.push('You must agree to continue!')
        return errors
      },
      selectErrors () {
        const errors = []
        if (!this.$v.select.$dirty) return errors
        !this.$v.select.required && errors.push('Item is required')
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
      submit () {
        this.$v.$touch()
      },
      clear () {
        this.$v.$reset()
        this.name = ''
        this.email = ''
        this.select = null
        this.checkbox = false
      },
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
</style>
