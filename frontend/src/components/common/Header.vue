<template>
  <header class="header">

    <img 
      src="@/assets/logo/logo.png" 
      alt="Logo"
      class="logo"
      @click="logoClick"
    >
    <div>  

      <!-- 로그인창 시작 -->
      <v-dialog
        v-model="dialog"
        persistent
        max-width="600px"
        v-if="!this.$store.state.account.accessToken"
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
          <v-btn class="ml-auto"
          text
          @click="close"
          >
            <font-awesome-icon icon="times-circle"/>
          </v-btn>
          <v-card-title>
            <h3 class="my-7">로그인</h3>
          </v-card-title>
          <v-card-text>
            <v-form ref="form">
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
            </v-form>
        </v-card-text>
    
        <button class="login input" @click="login()">로그인</button>
          <v-card-actions > 
            <v-container class="d-flex justify-space-between mb-6">
              <v-btn
                color="#F9802D"
                @click="signup" 
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
        v-if="!this.$store.state.account.accessToken"
      >
        회원가입
      </v-btn>

      <v-btn
        text
        @click="mypage"
        class= "px-0"
        v-if="this.$store.state.account.accessToken"
      >
        마이페이지
      </v-btn>

      <v-btn
        text
        @click="logout"
        class="px-0"
        v-if="this.$store.state.account.accessToken"
      >
        로그아웃
      </v-btn>

      <!-- navigation drawer 시작-->
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-navigation-drawer
        v-model="drawer"
        absolute
        temporary
        right
        disable-route-watcher
      >
        <v-list-item>
          <v-list-item-avatar>
            <v-img src="@/assets/logo/profile_temp.png"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-if="nickname">{{ nickname }}</v-list-item-title>
            <v-list-item-title v-else>로그인 해주세요</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>

        <v-list v-if="nickname">
          <v-list-group
            v-for="item in items"
            :key="item.title"
            v-model="item.active"
            :prepend-icon="item.action"
            no-action
          >
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title v-text="item.title"></v-list-item-title>
              </v-list-item-content>
            </template>
    
            <v-list-item
              v-for="subItem in item.items"
              :key="subItem.groupPk"
              @click="group(subItem.groupPk)"
            >
              <v-list-item-content>
                <v-list-item-title> {{ subItem.name }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>
        </v-list>
        <div v-else class="my-auto">
          <v-btn
            class="login input"
            outlined
            @click="dialog=!dialog"
          >로그인</v-btn>
        </div>      
        </v-navigation-drawer>
      <!-- navigation drawer 끝-->  
    </div>
  </header>
</template>

<script>
import http from '../../http-common'
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
      nickname: "",
      items: [
        {
          action: '',
          title: '가입된 그룹',
          active: true,
          items: [
            
          ],
        },
      ],
      drawer: null,
      miniVariant: true,     
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
    created(){
      console.log(this.$store.state.account.accessToken)
      this.nickname = this.$store.getters.getName;

      // 현재 로그인 한사람의 가입 그룹 리스트
      if(this.$store.getters.getAccessToken){
          http.get('/group/currentUserGroup')
            .then((res)=>{
            this.items[0].items = res.data.object;
            console.log(this.items.items)
        })
      }
    },
    methods: {
      ...mapActions(['LOGIN', 'LOGOUT']),

      signup() {
        this.$router.push("/user/join")
      },
      logoClick() {
        this.$router.push("/")
      },
      mypage() {
        this.$router.push("/user/profile")
      },
      login() {
        if(this.$v.$invalid) alert("계정정보가 틀렸습니다.💥")
        else{this.LOGIN({
          "email": this.email,
          "password": this.password
        })
        .then((response)=>{
          console.log(response);
          if(response.data.status){
            this.dialog = false;  
            this.$router.go()
          }
          else alert("가입하지 않은 아이디거나 잘못된 비밀번호 입니다.");
        })}
      },
      group(no) {
        this.$router.push(`/group/community?no=${no}`);
      },
      logout() {
        this.LOGOUT()
        .then((response) => {
          alert("로그아웃 되었습니다😒");
          console.log(response)
          this.$router.push('/')
          this.$router.go()
        })
      },
      close(){
        this.dialog=false
        this.email=""
        this.password=""
        this.$v.$reset();
      },
      passwordFind(){
        this.$router.push( "/user/pwdfind")
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


