<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/userStore.js";
import { jwtDecode } from "jwt-decode";

// cookies
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const router = useRouter();

const userStore = useUserStore();
// const { isLogin } = storeToRefs(userStore);
const { userLoginStore } = userStore;

const userinfo = ref({
  userId: "",
  userPwd: "",
});

const login = async () => {
  console.log("login ing!!!! !!!");
  console.log("userinfo : ", userinfo.value.userId, userinfo.value.userPwd);
  await userLoginStore(userinfo.value);
};
</script>

<template>
  <div class="wholeLayout">
    <div id="title">
      <!-- <h2>LOGO</h2> -->
      <img src="../../assets/img/logo.png" alt="" />
    </div>
    <label for="userId" style="width: 500px; text-align: start; font-size: 25px">아이디</label>
    <div id="id">
      <input
        type="text"
        id="userId"
        name="userId"
        placeholder="아이디를 입력해주세요."
        style="width: 500px; height: 50px; padding-left: 15px"
        v-model="userinfo.userId"
      />
    </div>
    <label for="userPwd" style="width: 500px; text-align: start; font-size: 25px">비밀번호</label>
    <div id="password">
      <input
        type="password"
        id="userPwd"
        name="userPwd"
        placeholder="비밀번호를 입력해주세요."
        style="width: 500px; height: 50px; padding-left: 15px; font-family: sans-serif"
        v-model="userinfo.userPwd"
      />
    </div>
    <div id="forgetPassword">비밀번호가 가물가물...</div>
    <div id="submitLogin">
      <input type="submit" id="btn-login" value="로그인" @click="login" />
    </div>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "NanumSquare";
  src: url("../../assets/fonts/NanumSquareR.ttf") format("truetype");
}
@font-face {
  font-family: "NanumSquareB";
  src: url("../../assets/fonts/NanumSquareB.ttf") format("truetype");
}
* {
  margin: 0;
  padding: 0;
  font-family: "NanumSquare";
}
.wholeLayout {
  margin: 50px 0;
  width: 100%;
  height: 800px;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  align-items: center;
  /* background-color: thistle; */
}
#title {
  width: 200px;
  height: 200px;
  margin: 0 120px 70px 0;
  /* background-color: antiquewhite; */
}
#id {
  /* background-color: aquamarine; */
  font-size: 20px;
  width: 500px;
  margin-bottom: 30px;
}
#password {
  /* background-color: blueviolet; */
  font-size: 20px;
  width: 500px;
  margin-bottom: 15px;
}
#forgetPassword {
  /* background-color: darkorange; */
  margin-bottom: 30px;
  color: gray;
}
#btn-login {
  background-color: #00ae68;
  border-radius: 5px;
  width: 120px;
  height: 50px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px #007144;
}
#btn-login:hover {
  box-shadow: 0px 0px 0px 0px #007144;
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
