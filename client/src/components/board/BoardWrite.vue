<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";

// cookies
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const router = useRouter();

const subject = ref("");
const content = ref("");
// const userinfo = JSON.parse(sessionStorage.getItem("userinfo"));

const createArticle = () => {
  http
    .post("articleapi/write", {
      subject: subject.value,
      content: content.value,
      userId: cookies.get("userId"),
    })
    .then(({ data }) => {
      let msg = "등록 처리 시 문제가 발생했습니다.";
      if (data === 1) {
        msg = "등록이 완료되었습니다.";
      }

      alert(msg);
      moveList();
    });
};

const resetContents = () => {
  subject.value = "";
  content.value = "";
};

const moveList = () => {
  router.push({ name: "BoardList" });
};
</script>

<template>
  <div class="wholeContents">
    <div class="titleDiv">
      <div class="title">Q&A 게시판 작성하기</div>
    </div>
    <div class="main">
      <div class="titleC">제목</div>
      <div>
        <input
          type="text"
          id="titleContent"
          name="titleContent"
          placeholder="제목을 입력해주세요."
          v-model="subject"
        />
      </div>
      <div class="mainC">내용</div>
      <div>
        <textarea
          type="text"
          id="mainContent"
          name="mainContent"
          placeholder="내용을 입력해주세요."
          v-model="content"
        />
      </div>
    </div>
    <div class="submit">
      <input class="writeButton" type="button" @click="createArticle" value="글작성" />
      <input class="resetButton" type="reset" value="초기화" @click="resetContents" />
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
.wholeContents {
  display: flex;
  flex-direction: column;
  width: 100%;
}
.titleDiv {
  margin: 20px 0 0 0;
  width: 100%;
  height: 60px;
}
.title {
  font-family: "NanumSquareB";
  font-size: 50px;
  padding: 0 0 0 30px;
}
.main {
  display: flex;
  flex-direction: column;
  margin: 50px 0 0 50vh;
}
.titleC {
  /* margin: 0 0 0 20vh; */
  width: 1000px;
  text-align: start;
  font-size: 25px;
}
#titleContent {
  margin: 0 0 30px 0;
  width: 1000px;
  height: 50px;
  padding-left: 15px;
  border: 2px solid gray;
  border-radius: 10px;
}
.mainC {
  width: 500px;
  text-align: start;
  font-size: 25px;
}
#mainContent {
  border: 2px solid gray;
  border-radius: 10px;
  width: 1000px;
  height: 520px;
  padding: 10px 0 0 15px;
  resize: none;
}
.submit {
  margin: 15px 0 15px 90vh;
  display: flex;
  height: 50px;
}
.writeButton {
  margin: 0 0 0 70px;
  width: 90px;
  height: 45px;
  background-color: rgb(175, 171, 243);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(175, 171, 243);
  font-size: 16px;
  font-weight: 600;
}
.writeButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(175, 171, 243);
  margin-top: 5px;
  margin-bottom: 5px;
}
.resetButton {
  margin: 0 0 0 360px;
  width: 90px;
  height: 45px;
  background-color: rgb(247, 127, 157);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(247, 127, 157);
  font-size: 16px;
  font-weight: 600;
}
.resetButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(247, 127, 157);
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
