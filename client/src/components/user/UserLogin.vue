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
  // let accessToken = cookies.get("accessToken");
  // console.log("111. ", accessToken);
  // console.log("isLogin: ", isLogin.value);
  // if (userId) {
  //   console.log("로그인 성공");
  //   makeUserIdCookieStore(accessToken);
  // }
  // router.push("/");
};
</script>

<template>
  <div class="wholeLayout">
    <div>
      <h2>로그인</h2>
    </div>
    <div>
      <label for="userId">아이디</label><br />
      <input
        type="text"
        id="userId"
        name="userId"
        placeholder="아이디를 입력해주세요."
        v-model="userinfo.userId"
      />
    </div>
    <div>
      <label for="userPwd">비밀번호</label><br />
      <input
        type="password"
        id="userPwd"
        name="userPwd"
        placeholder="비밀번호를 입력해주세요."
        v-model="userinfo.userPwd"
      />
    </div>
    <div>
      <input type="submit" id="btn-login" value="로그인" @click="login" />
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
}
.wholeLayout {
  margin: 160px 0;
  width: 100%;
  height: 500px;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
}
</style>
