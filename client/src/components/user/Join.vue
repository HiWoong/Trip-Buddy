<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import http from "@/util/http-common.js";
const router = useRouter();
const user = ref({
  userId: "",
  userName: "",
  userPwd: "",
  emailId: "",
  emailDomain: "",
});

const joinUser = () => {
  http.post("/userapi/join", user.value).then(({ data }) => {
    let msg = "사용자 정보 등록에 문제가 발생했습니다.";
    if (data === 1) {
      msg = "사용자 정보 등록이 완료되었습니다.";
      router.push("/");
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
          <mark class="orange">회원가입</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="mb-3">
          <label for="username" class="form-label">이름 : </label>
          <input
            type="text"
            class="form-control"
            id="userName"
            name="userName"
            placeholder="이름..."
            v-model="user.userName"
          />
        </div>
        <div class="mb-3">
          <label for="userid" class="form-label">아이디 : </label>
          <input
            type="text"
            class="form-control"
            id="userid"
            name="userId"
            placeholder="아이디..."
            v-model="user.userId"
          />
        </div>
        <div id="result-view" class="mb-3"></div>
        <div class="mb-3">
          <label for="userpwd" class="form-label">비밀번호 : </label>
          <input
            type="password"
            class="form-control"
            id="userpwd"
            name="userPwd"
            placeholder="비밀번호..."
            v-model="user.userPwd"
          />
        </div>
        <div class="mb-3">
          <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
          <input
            type="password"
            class="form-control"
            id="pwdcheck"
            placeholder="비밀번호확인..."
            v-model="user.userPwd"
          />
        </div>
        <div class="mb-3">
          <label for="emailid" class="form-label">이메일 : </label>
          <div class="input-group">
            <input
              type="text"
              class="form-control"
              id="emailid"
              name="emailId"
              placeholder="이메일아이디"
              v-model="user.emailId"
            />
            <span class="input-group-text">@</span>
            <select
              class="form-select"
              id="emaildomain"
              name="emailDomain"
              aria-label="이메일 도메인 선택"
              v-model="user.emailDomain"
            >
              <option selected>선택</option>
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
            class="btn btn-outline-primary mb-3"
            value="회원가입"
            @click="joinUser"
          />
          <input type="reset" class="btn btn-outline-success mb-3" value="초기화" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
