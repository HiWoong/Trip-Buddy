<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/userStore.js";
import { jwtDecode } from "jwt-decode";

const router = useRouter();
const userStore = useUserStore();

const { isLogin } = storeToRefs(userStore);
const { userLogin, loginUserId } = userStore;

const userinfo = ref({
  userId: "",
  userPwd: "",
});


// const loginUser = () => {
//   http
//     .post("/userapi/login", {
//       userId: userinfo.value.userId,
//       userPwd: userinfo.value.userPwd,
//       savdId: "",
//     })
//     .then(({ data }) => {
//       console.log(data);
//       let msg = "로그인 처리시 문제가 발생했습니다.";
//       if (data != null) {
//         msg = "로그인이 완료되었습니다.";
//         sessionStorage.setItem("userinfo", JSON.stringify(data));
//         sessionStorage.setItem("isLogin", "true");
//         router.replace({ name: "HomeView" });
//       }
//       alert(msg);
//     });
// };

const login = async () => {
  console.log("login ing!!!! !!!");
  console.log("userinfo : ", userinfo.value.userId, userinfo.value.userPwd);
  await userLogin(userinfo.value);
  // let token = sessionStorage.getItem("accessToken");
  let accessToken = this.$cookies.get("accessToken");
  console.log("111. ", accessToken);
  console.log("isLogin: ", isLogin.value);
  if (isLogin) {
    console.log("로그인 성공");
    // getUserInfo(token);
    // changeMenuState();
  }
  router.push("/");
};
</script>

<template>
  <div class="container mt-5 pt-5">
    <div class="row justify-content-center mt-5">
      <div class="col-lg-8 col-md-10 col-sm-12 mt-5 pt-5">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">로그인</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <!-- <div class="form-check mb-3 float-end">
          <input class="form-check-input" type="checkbox" value="ok" id="saveId" name="saveId" />
          <label class="form-check-label" for="saveId"> 아이디저장 </label>
        </div> -->
        <div class="mb-3">
          <label for="userId" class="form-label">아이디 : </label>
          <input
            type="text"
            class="form-control"
            id="userId"
            name="userId"
            placeholder="아이디..."
            v-model="userinfo.userId"
          />
        </div>
        <div class="mb-3">
          <label for="userPwd" class="form-label">비밀번호 : </label>
          <input
            type="password"
            class="form-control"
            id="userPwd"
            name="userPwd"
            placeholder="비밀번호..."
            v-model="userinfo.userPwd"
          />
        </div>
        <div class="col-auto text-center">
          <input
            type="submit"
            id="btn-login"
            class="btn btn-outline-primary mb-3"
            value="로그인"
            @click="login"
          />
          <input
            type="button"
            id="btn-mv-join"
            class="btn btn-outline-success mb-3"
            value="회원가입"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
