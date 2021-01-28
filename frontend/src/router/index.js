import Vue from "vue"
import VueRouter from "vue-router"
import Main from "../views/Main.vue"
import FeedUpdate from "../views/Group/FeedUpdate.vue"
import FeedWrite from "../views/Group/FeedWrite.vue"
import Community from "../views/Group/Community.vue"
import GroupCreate from "../views/Group/GroupCreate.vue"
import GroupList from "../views/Group/GroupList.vue"
import Join from "../views/User/Join.vue"
import MailCheck from "../views/User/MailCheck.vue"
import PasswordChange from "../views/User/PasswordChange.vue"
import Profile from "../views/User/Profile.vue"
import Complete from "../views/User/Complete.vue"
import Update from "../views/User/Update.vue"
import MileageShop from "../views/MileageShop.vue"
import PasswordFind from "../views/User/PasswordFind.vue"


Vue.use(VueRouter)
const routes = [
  {
    path: "/",
    name: "Main",
    component: Main
  },
  {
    path: "/user/join",
    name: "Join",
    component: Join
  },
  {
    path: "/user/mailcheck",
    name: "MailCheck",
    component: MailCheck
  },
  {
    path: "/user/complete",
    name: "Complete",
    component: Complete
  },
  {
    path: "/user/pwchange",
    name: "PasswordChange",
    component: PasswordChange
  },
  {
    path: "/user/pwdfind",
    name: "PasswordFind",
    component: PasswordFind
  },
  {
    path: "/user/profile",
    name: "Profile",
    component: Profile
  },
  {
    path: "/user/update",
    name: "Update",
    component: Update
  },
  {
    path: "/mileageshop",
    name: "MileageShop",
    component: MileageShop
  },
  {
    path: "/group/grouplist",
    name: "GroupList",
    component: GroupList,
    props:true
  },
  {
    path: "/group/groupcreate",
    name: "GroupCreate",
    component: GroupCreate
  },
  {
    path: "/group/community",
    name: "Community",
    component: Community
  },
  {
    path: "/group/feedwrite",
    name: "FeedWrite",
    component: FeedWrite
  },
  {
    path: "/group/feedupdate",
    name: "FeedUpdate",
    component: FeedUpdate
  },
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
})

export default router