<script setup>
import http from "@/util/http-common.js";
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
// import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/userStore.js";
// import http from "@/util/http-common.js";

const userStore = useUserStore();
// const { isLogin } = storeToRefs(userStore);
const { userJoinStore } = userStore;
const checkPwd = ref("");
const beforeId = ref("");
const idFlag = ref(false);
const pwdFlag = ref(false);
const pwdText = ref("비밀번호가 보고싶어요...");

const router = useRouter();

const user = ref({
  userId: "",
  userName: "",
  userPwd: "",
  emailId: "",
  emailDomain: "",
});

watch(checkPwd, (newVal, oldVal) => {
  if (user.value.userPwd == "" || user.value.userPwd == null) {
    pwdFlag.value = false;
    pwdText.value = "비밀번호가 보고싶어요...";
  } else {
    if (user.value.userPwd == checkPwd.value) {
      pwdFlag.value = true;
      pwdText.value = "똑같네요...";
    } else {
      pwdFlag.value = false;
      pwdText.value = "다르네요...";
    }
  }
});

watch(user.value, (newVal, oldVal) => {
  if (beforeId.value != user.value.userId) {
    idFlag.value = false;
    beforeId.value = user.value.userId;
  }

  if (user.value.userPwd == "" || user.value.userPwd == null) {
    pwdFlag.value = false;
    pwdText.value = "비밀번호가 보고싶어요...";
  } else {
    if (user.value.userPwd == checkPwd.value) {
      pwdFlag.value = true;
      pwdText.value = "똑같네요...";
    } else {
      pwdFlag.value = false;
      pwdText.value = "다르네요...";
    }
  }
});

const joinUser = () => {
  if (
    user.value.userId == "" ||
    user.value.userName == "" ||
    user.value.userPwd == "" ||
    user.value.emailId == "" ||
    user.value.emailDomain == ""
  ) {
    alert("흠.. 빈 칸이 있네요?");
  } else if (!pwdFlag.value) {
    alert("왜 혼자 비밀번호를 두 개 쓰려고 하세요?");
  } else if (!idFlag.value) {
    alert("중복확인 해주세요...");
  } else {
    userJoinStore(user.value);
  }
};

const checkId = () => {
  if (user.value.userId.length < 4 || user.value.userId == null)
    alert("4자리 미만의 ID는 우리와 함께 갈 수 없습니다.");
  else {
    http.get(`/userapi/check/${user.value.userId}`).then(({ data }) => {
      console.log(data);
      if (data == 1) {
        idFlag.value = true;
      } else alert("4자리 이상이지만 참신하지 않은 ID입니다.");
    });
  }
};

const alreadyCheck = () => {
  alert("이미 체크되셨으니까 나머지 채우십쇼");
};
</script>
<template>
  <div class="wholeLayout">
    <div id="title">
      <!-- <h2>LOGO</h2> -->
      <img src="../../assets/img/logo.png" alt="" />
    </div>
    <label for="username" style="width: 500px; text-align: start; font-size: 25px">이름</label>
    <div id="name">
      <input
        type="text"
        id="userName"
        name="userName"
        placeholder="이름을 입력해주세요."
        style="width: 500px; height: 50px; padding-left: 15px"
        v-model="user.userName"
      />
    </div>
    <label for="userId" style="width: 500px; text-align: start; font-size: 25px">아이디</label>
    <div id="id">
      <input
        type="text"
        id="userId"
        name="userId"
        placeholder="아이디를 입력해주세요."
        style="width: 500px; height: 50px; padding-left: 15px"
        v-model="user.userId"
      />
      <span>
        <template v-if="idFlag">
          <button
            @click="alreadyCheck"
            style="
              height: 50px;
              width: 100px;
              margin-left: 24px;
              border: 1px solid black;
              border-radius: 5px;
              background-color: lightgoldenrodyellow;
            "
          >
            확인완료
          </button>
        </template>
        <template v-else>
          <button
            @click="checkId"
            style="
              height: 50px;
              width: 100px;
              margin-left: 24px;
              border: 1px solid black;
              border-radius: 5px;
              background-color: azure;
            "
          >
            중복확인
          </button>
        </template>
      </span>
    </div>
    <label for="userPwd" style="width: 500px; text-align: start; font-size: 25px">비밀번호</label>
    <div id="password">
      <input
        type="password"
        id="userPwd"
        name="userPwd"
        placeholder="비밀번호를 입력해주세요."
        style="width: 500px; height: 50px; padding-left: 15px; font-family: sans-serif"
        v-model="user.userPwd"
      />
    </div>

    <label for="pwdCheck" style="width: 500px; text-align: start; font-size: 25px"
      >비밀번호확인
    </label>
    <div id="pwdcheck">
      <input
        type="password"
        id="pwdCheck"
        placeholder="다시 한 번 비밀번호를 입력해주세요."
        style="width: 500px; height: 50px; padding-left: 15px; font-family: sans-serif"
        v-model="checkPwd"
      />
    </div>
    <div style="margin-bottom: 10px">
      <template v-if="pwdFlag">
        <div style="width: 500px; text-align: start; font-size: 17px; color: green">
          {{ pwdText }}
        </div>
      </template>
      <template v-else>
        <div style="width: 500px; text-align: start; font-size: 17px; color: red">
          {{ pwdText }}
        </div>
      </template>
    </div>
    <label for="emailid" style="width: 500px; text-align: start; font-size: 25px">이메일</label>
    <div id="emailInfo">
      <div style="margin-bottom: 10px; width: 530px">
        <input
          type="text"
          id="emailid"
          name="emailId"
          placeholder="이메일아이디"
          style="width: 500px; height: 50px; padding-left: 15px"
          v-model="user.emailId"
        />
        <span style="margin-left: 10px; font-size: 20px">@</span>
      </div>
      <select
        id="emaildomain"
        name="emailDomain"
        placeholder="이메일 도메인 선택"
        style="width: 500px; height: 50px; padding-left: 15px"
        v-model="user.emailDomain"
      >
        <option selected>선택</option>
        <option value="ssafy.com">싸피</option>
        <option value="google.com">구글</option>
        <option value="naver.com">네이버</option>
        <option value="kakao.com">카카오</option>
      </select>
    </div>

    <div id="submitJoin">
      <input type="submit" id="btn-join" value="회원가입" @click="joinUser" />
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
  margin: 20px 0;
  width: 100%;
  height: 1000px;
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
#name {
  font-size: 20px;
  width: 500px;
  margin-bottom: 20px;
}
#id {
  /* background-color: aquamarine;   */
  font-size: 20px;
  width: 750px;
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-end;
}
#password {
  /* background-color: blueviolet; */
  font-size: 20px;
  width: 500px;
  margin-bottom: 20px;
}
#pwdcheck {
  font-size: 20px;
  width: 500px;
  margin-bottom: 5px;
}
#forgetPassword {
  /* background-color: darkorange; */
  margin-bottom: 30px;
  color: gray;
}
#emailInfo {
  font-size: 20px;
  width: 500px;
  margin-bottom: 40px;
}
#btn-join {
  background-color: #01a3cb;
  border-radius: 5px;
  width: 120px;
  height: 50px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px #01a3cb;
}
#btn-join:hover {
  box-shadow: 0px 0px 0px 0px #01a3cb;
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
