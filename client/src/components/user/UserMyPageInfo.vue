<script setup>
import http from "@/util/http-common.js";
import { ref, watch, onMounted } from "vue";
import { useRouter } from "vue-router";

import { useUserStore } from "@/stores/userStore.js";

import { info } from "@/api/userApi";
import { httpStatusCode } from "@/util/http-status";

// cookies
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const userStore = useUserStore();
const { userDeleteStore } = userStore;

const router = useRouter();

const newUser = ref([]);

const nowPwd = ref("");
const checkPwd = ref("");
const pwdFlag = ref(false);
const pwdText = ref("");

watch(checkPwd, (newVal, oldVal) => {
  if (nowPwd.value == checkPwd.value) {
    pwdFlag.value = true;
    pwdText.value = "똑같네요...";
  } else {
    pwdFlag.value = false;
    pwdText.value = "다르네요...";
  }
});

watch(nowPwd, (newVal, oldVal) => {
  if (nowPwd.value == checkPwd.value) {
    pwdFlag.value = true;
    pwdText.value = "똑같네요...";
  } else {
    pwdFlag.value = false;
    pwdText.value = "다르네요...";
  }
});

// temp
const profileImg = ref("https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg");
//

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
        if (newUser.value.profileImage == "" || newUser.value.profileImage == null) {
          newUser.value.profileImage =
            "https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg";
        }
      } else {
        console.log("유저 정보 없음!!!!");
      }
    },
    async (error) => {
      console.error(
        "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
        error.response.status
      );
    }
  );
};

const viewPlans = () => {
  router.push({ name: "UserMyPage" });
};

const deleteUser = async () => {
  await userDeleteStore(cookies.get("userId"));
  router.replace({ name: "HomeView" });
};

const updateUser = () => {
  if (nowPwd.value.length < 4) {
    alert("비밀번호는 최소 4자리부터 시작해야 합니다.");
  } else if (nowPwd.value != checkPwd.value) {
    alert("비밀 번호가 다릅니다. 다시 확인해주세요");
  } else if (
    newUser.value.userName == "" ||
    newUser.value.emailId == "" ||
    newUser.value.emailDomain == ""
  ) {
    alert("모든 회원 정보를 기입해주세요.");
  } else {
    http
      .post("/userapi/update", {
        userId: newUser.value.userId,
        userName: newUser.value.userName,
        userPwd: nowPwd.value,
        emailId: newUser.value.emailId,
        emailDomain: newUser.value.emailDomain,
        profileImage: newUser.value.profileImage,
      })
      .then(({ data }) => {
        let msg = "사용자 정보 수정에 문제가 발생했습니다.";
        if (data === 1) {
          msg = "사용자 정보 수정이 완료되었습니다.";
          router.go();
        }
        alert(msg);
      });
  }
};

const imageUpload = async (files) => {
  imageToBase64(files[0]);
};

function imageToBase64(f) {
  var reader = new FileReader();
  reader.readAsDataURL(f);
  reader.onload = function (e) {
    newUser.value.profileImage = e.target.result;
  };
}
</script>

<template>
  <div class="wholeLayout">
    <div id="title">
      <img v-bind:src="newUser.profileImage" style="width: 100%; height: 100%; object-fit: cover" />
      <div style="justify-content: center; margin: 10px 0 0 55px">
        <label
          for="uploadImage"
          style="
            cursor: pointer;
            border: 1px solid gray;
            border-radius: 30px;
            padding: 5px;
            background-color: skyblue;
            color: #292929;
            font-weight: bold;
          "
          >이미지 변경</label
        >
        <input
          multiple
          @change="imageUpload($event.target.files)"
          accept="image/*"
          type="file"
          id="uploadImage"
          style="display: flex; justify-content: center; visibility: hidden"
        />
      </div>
    </div>
    <label for="username" style="width: 500px; text-align: start; font-size: 25px">이름</label>
    <div id="name">
      <input
        type="text"
        id="userName"
        name="userName"
        style="width: 500px; height: 50px; padding-left: 15px"
        v-model="newUser.userName"
      />
    </div>
    <label for="userId" style="width: 500px; text-align: start; font-size: 25px">아이디</label>
    <div id="id">
      <input
        type="text"
        id="userId"
        name="userId"
        style="width: 500px; height: 50px; padding-left: 15px"
        v-model="newUser.userId"
        disabled
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
        v-model="nowPwd"
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
          v-model="newUser.emailId"
        />
        <span style="margin-left: 10px; font-size: 20px">@</span>
      </div>
      <select
        id="emaildomain"
        name="emailDomain"
        placeholder="이메일 도메인 선택"
        style="width: 500px; height: 50px; padding-left: 15px"
        v-model="newUser.emailDomain"
      >
        <option selected>선택</option>
        <option value="ssafy.com">싸피</option>
        <option value="gmail.com">구글</option>
        <option value="naver.com">네이버</option>
        <option value="kakao.com">카카오</option>
      </select>
    </div>

    <div id="submits">
      <button type="submit" class="submit" id="btn-plan" @click="viewPlans">보관함</button>
      <button type="submit" class="submit" id="btn-update" @click="updateUser">수정하기</button>
      <button type="submit" class="submit" id="btn-delete" @click="deleteUser">탈퇴하기</button>
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
  margin-bottom: 40px;
  /* background-color: antiquewhite; */
  border-radius: 70%;
}
#name {
  font-size: 20px;
  width: 500px;
  margin-bottom: 20px;
}
#id {
  /* background-color: aquamarine; */
  font-size: 20px;
  width: 500px;
  margin-bottom: 20px;
}
#password {
  /* background-color: blueviolet; */
  font-size: 15px;
  width: 500px;
  margin-bottom: 20px;
}
#pwdcheck {
  font-size: 15px;
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
#submits {
  width: 30%;
  display: flex;
  flex-wrap: wrap;
  align-content: center;
  justify-content: space-around;
  align-items: center;
}
.submit {
  width: 100px;
  padding: 10px;
  border: 2px solid #212121;
  border-radius: 10px;
}
#btn-plan {
  background: none;
  box-shadow: 0px 0px 0px 0px rgba(0, 0, 0, 0.5);
  background-color: darksalmon;
}
#btn-update {
  background: none;
  box-shadow: 0px 0px 0px 0px rgba(0, 0, 0, 0.5);
  background-color: rgb(122, 172, 213);
}
#btn-delete {
  background: none;
  box-shadow: 0px 0px 0px 0px rgba(0, 0, 0, 0.5);
  background-color: rgb(253, 230, 59);
}
#btn-plan:hover {
  margin-top: -2px;
  margin-left: 0px;
  transform: scale(1.1, 1.1);
  -ms-transform: scale(1.1, 1.1);
  -webkit-transform: scale(1.1, 1.1);
  box-shadow: 0px 5px 5px -2px rgba(0, 0, 0, 0.25);
  transition: 0.2s;
}
#btn-update:hover {
  margin-top: -2px;
  margin-left: 0px;
  transform: scale(1.1, 1.1);
  -ms-transform: scale(1.1, 1.1);
  -webkit-transform: scale(1.1, 1.1);
  box-shadow: 0px 5px 5px -2px rgba(0, 0, 0, 0.25);
  transition: 0.2s;
}
#btn-delete:hover {
  margin-top: -2px;
  margin-left: 0px;
  transform: scale(1.1, 1.1);
  -ms-transform: scale(1.1, 1.1);
  -webkit-transform: scale(1.1, 1.1);
  box-shadow: 0px 5px 5px -2px rgba(0, 0, 0, 0.25);
  transition: 0.2s;
}
</style>
