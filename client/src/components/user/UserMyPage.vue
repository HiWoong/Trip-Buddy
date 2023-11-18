<script setup>
import http from "@/util/http-common.js";
import { ref, onMounted } from "vue";
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

// temp
const profileImg = ref(
  "https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
);
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
        if (
          newUser.value.profileImage == "" ||
          newUser.value.profileImage == null
        ) {
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
  router.push({ name: "UserPlan" });
};

const deleteUser = async () => {
  await userDeleteStore(cookies.get("userId"));
  router.replace({ name: "HomeView" });
};

const updateUser = () => {
  console.log(newUser.value);
  http.post("/userapi/update", newUser.value).then(({ data }) => {
    let msg = "사용자 정보 수정에 문제가 발생했습니다.";
    if (data === 1) {
      msg = "사용자 정보 수정이 완료되었습니다.";
      router.go();
    }
    alert(msg);
  });
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
      <img
        v-bind:src="newUser.profileImage"
        style="width: 100%; height: 100%; object-fit: cover"
      />
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
    <label
      for="username"
      style="width: 500px; text-align: start; font-size: 25px"
      >이름</label
    >
    <div id="name">
      <input
        type="text"
        id="userName"
        name="userName"
        style="width: 500px; height: 50px; padding-left: 15px"
        v-model="newUser.userName"
      />
    </div>
    <label for="userId" style="width: 500px; text-align: start; font-size: 25px"
      >아이디</label
    >
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
    <label
      for="userPwd"
      style="width: 500px; text-align: start; font-size: 25px"
      >비밀번호</label
    >
    <div id="password">
      <input
        type="password"
        id="userPwd"
        name="userPwd"
        style="
          width: 500px;
          height: 50px;
          padding-left: 15px;
          font-family: sans-serif;
        "
        v-model="newUser.userPwd"
      />
    </div>

    <label
      for="emailid"
      style="width: 500px; text-align: start; font-size: 25px"
      >이메일</label
    >
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
        <option value="google.com">구글</option>
        <option value="naver.com">네이버</option>
        <option value="kakao.com">카카오</option>
      </select>
    </div>

    <div id="submits">
      <button type="submit" class="submit" id="btn-plan" @click="viewPlans">
        보관함
      </button>
      <button type="submit" class="submit" id="btn-update" @click="updateUser">
        수정하기
      </button>
      <button type="submit" class="submit" id="btn-delete" @click="deleteUser">
        탈퇴하기
      </button>
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
  background-color: antiquewhite;
  border-radius: 70%;
}
#name {
  font-size: 20px;
  width: 500px;
  margin-bottom: 20px;
}
#id {
  background-color: aquamarine;
  font-size: 20px;
  width: 500px;
  margin-bottom: 20px;
}
#password {
  background-color: blueviolet;
  font-size: 20px;
  width: 500px;
  margin-bottom: 20px;
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

<!-- <template>
  <div class="container mt-5 pt-5">
    <div class="row justify-content-center mt-5">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">마이페이지</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="mb-3"></div>
        <img class="avatar me-2 float-md-start bg-light p-2" v-bind:src="newUser.profileImage" />
        <div class="mb-3">
          <input multiple @change="imageUpload($event.target.files)" accept="image/*" type="file" />
        </div>
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
</template> -->
