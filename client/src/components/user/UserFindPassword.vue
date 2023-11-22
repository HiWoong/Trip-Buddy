<script setup>
  import { ref } from "vue";
  import { createNewPassword } from "@/api/userApi";
  import { httpStatusCode } from "@/util/http-status";
  import { RouterLink, useRouter } from "vue-router";

  const router = useRouter();

  const uid = ref("");

  const sendEmail = () => {
    createNewPassword(
      uid.value,
      (response) => {
        if (response.status === httpStatusCode.OK){
          alert("임시 비밀번호가 이메일로 전송되었습니다.")
          router.push("/user/login");
        } else {
          alert("존재하지 않는 사용자 아이디입니다. 다시 한번 확인해주세욧!");
        }
      },
      (error) => {
        console.log("error : ", error);
      }
    )
  }
</script>


<template>
  <div class="findPasswordLayout">
    <div
      style="
        border: 3px dashed gray;
        border-radius: 15px;
        width: 800px;
        height: 600px;
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 100px 0 0 0;
      "
    >
      <div
        style="
          width: 500px;
          font-size: 45px;
          text-align: center;
          font-family: 'NanumSquareB';
          font-weight: bold;
          margin-bottom: 70px;
        "
      >
        비밀번호 초기화
      </div>
      <div
        for="userId"
        style="width: 500px; text-align: start; font-size: 25px; margin-bottom: 20px"
      >
        입력하신 아이디에 등록된 이메일로 <br />임시 비밀번호가 전송됩니다.
      </div>
      <div id="id">
        <input
          type="text"
          id="userId"
          name="userId"
          placeholder="아이디를 입력해주세요."
          style="
            width: 500px;
            height: 60px;
            padding-left: 15px;
            font-size: 25px;
            border: 2px solid black;
            border-radius: 15px;
          "
          v-model="uid"
        />
      </div>
      <div>
        <input class="submitButton" type="button" value="비밀번호 찾기" @click="sendEmail()"/>
      </div>
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
.findPasswordLayout {
  display: flex;
  flex-direction: column;
  width: 1903px;
  height: 900px;
  align-items: center;
  padding: 150px 0 0 0;
  /* background-color: antiquewhite; */
}
.submitButton {
  width: 300px;
  height: 40px;
  margin: 35px 0 0 0;
  border: 4px solid magenta;
  border-radius: 10px;
  /* background-color: rgb(255, 213, 255); */
  color: darkgray;
  font-size: 22px;
  transition: 0.2s;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  text-decoration: none;
  font-weight: 800;
}
.submitButton:hover {
  background-color: magenta;
  color: #424242;
}
</style>
