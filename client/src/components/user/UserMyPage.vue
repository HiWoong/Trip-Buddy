<script setup>
import http from "@/util/http-common.js";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/userStore.js";

import { info } from "@/api/userApi";
import { httpStatusCode } from "@/util/http-status";

// cookies
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const userStore = useUserStore();
const { userDeleteStore } = userStore;

// import { useUserStore} from "@/stores/userStore.js";
// const userStore = useUserStore();

// const { userInfo } = storeToRefs(userStore);
// const { getUserInfo } = userStore;

const router = useRouter();

// const newUser = ref({
//   userId: userInfo.userId,
//   userName: userInfo.userName,
//   userPwd: userInfo.userPwd,
//   emailId: userInfo.emailId,
//   emailDomain: userInfo.emailDomain,
//   joinDate: userInfo.joinDate,
// });

const newUser = ref([]);

onMounted(async () => {
  console.log("gogo getUserInfo : ", cookies.get("userId"));
  getUserInfo(cookies.get("userId"));
});

const getUserInfo = async (userId) => {
  info(
    userId,
    (response) => {
      console.log(response.status);
      if (response.status === httpStatusCode.OK) {
        console.log("3. getUserInfo data >> ", response.data.userInfo);
        newUser.value = response.data.userInfo;
        console.log("new User : ", newUser.value);
      } else {
        console.log("유저 정보 없음!!!!");
      }
    },
    async (error) => {
      console.error(
        "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
        error.response.status
      );
      // isValidToken.value = false;

      // await tokenRegenerate();
    }
  );
};

const deleteUser = async() => {
  await userDeleteStore(cookies.get("userId"));
  router.replace({ name: "HomeView" });
}

// const deleteUser = () => {
//   http.get("/userapi/delete/" + newUser.value.userId).then(({ data }) => {
//     let msg = "사용자 정보 삭제에 문제가 발생했습니다.";
//     console.log("delete status : ", data.request.status);
//     if (data.request.status === httpStatusCode.OK) {
//       msg = "사용자 정보 삭제가 완료되었습니다.";
//       isLogin.value = false;
//       cookies.remove("userId");
//       cookies.remove("accessToken");
//       cookies.remove("refreshToken");
//       router.push({ name: "HomeView" });
//     }
//     alert(msg);
//   });
// };

const updateUser = () => {
  /* 
    여기에서 사용자 정보란이 꽉 찼는지 확인하는 if 문을 넣어야됨.
    아래 post 는 else 
  */
  console.log(newUser.value);
  http.post("/userapi/update", newUser.value).then(({ data }) => {
    let msg = "사용자 정보 수정에 문제가 발생했습니다.";
    if (data === 1) {
      msg = "사용자 정보 수정이 완료되었습니다.";
      // userInfo.value = newUser.value;
      router.go();
    }
    alert(msg);
  });
};

</script>

<template>
  <div class="container mt-5 pt-5">
    <div class="row justify-content-center mt-5">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">마이페이지</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <!-- 파일 업로드 -->
        <!-- <div class="mb-3"></div>
          <img
              class="avatar me-2 float-md-start bg-light p-2"
              src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
            />
        <div class="mb-3">
          <input multiple @change="imageUpload()" ref="images" type="file" />
        </div> -->
        <!--  -->
        <div class="mb-3">
          <label for="username" class="form-label">이름 : </label>
          <input
            type="text"
            class="form-control"
            id="userName"
            name="userName"
            placeholder="이름..."
            v-model="newUser.userName"
          />
        </div>
        <div class="mb-3">
          <label for="userid" class="form-label">아이디 : </label>
          <input
            type="text"
            class="form-control"
            id="userId"
            name="userId"
            placeholder="아이디..."
            v-model="newUser.userId"
            readonly
            disabled
          />
        </div>
        <div id="result-view" class="mb-3"></div>
        <div class="mb-3">
          <label for="userpwd" class="form-label">비밀번호 : </label>
          <input
            type="password"
            class="form-control"
            id="userPwd"
            name="userPwd"
            placeholder="비밀번호..."
            v-model="newUser.userPwd"
          />
        </div>
        <div class="mb-3">
          <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
          <input
            type="password"
            class="form-control"
            id="pwdCheck"
            placeholder="비밀번호확인..."
            v-model="newUser.userPwd"
          />
        </div>
        <div class="mb-3">
          <label for="emailid" class="form-label">이메일 : </label>
          <div class="input-group">
            <input
              type="text"
              class="form-control"
              id="emailId"
              name="emailId"
              placeholder="이메일아이디"
              v-model="newUser.emailId"
            />
            <span class="input-group-text"></span>
            <select
              class="form-select"
              id="emailDomain"
              name="emailDomain"
              aria-label="이메일 도메인 선택"
              v-model="newUser.emailDomain"
            >
              <option selected>{{ newUser.emailDomain }}</option>
              <option value="ssafy.com">싸피</option>
              <option value="google.com">구글</option>
              <option value="naver.com">네이버</option>
              <option value="kakao.com">카카오</option>
            </select>
          </div>
        </div>
        <div class="col-auto text-center">
          <input
            type="submit"
            id="btn-join"
            class="btn btn-outline-primary mb-3 p-auto"
            value="수정하기"
            @click="updateUser"
          />
          <input
            type="button"
            id="btn-mv-join"
            class="btn btn-outline-success mb-3 p-auto"
            value="탈퇴하기"
            @click="deleteUser"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
