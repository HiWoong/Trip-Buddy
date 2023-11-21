<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import http from "@/util/http-common.js";

const router = useRouter();
const route = useRoute();

const article = ref({
  articleNo: Number,
  userId: String,
  subject: String,
  content: String,
  hit: Number,
  registerTime: String,
});

http.get("/articleapi/view/" + route.params.articleNo).then(({ data }) => {
  article.value = data;
});

const modifyArticle = () => {
  http
    .post("/articleapi/modify", {
      articleNo: article.value.articleNo,
      subject: article.value.subject,
      content: article.value.content,
    })
    .then(({ data }) => {
      let msg = "수정 처리 시 문제가 발생했습니다.";
      if (data === 1) {
        msg = "수정이 완료되었습니다.";
      }

      alert(msg);
      moveDetail();
    });
};

const moveDetail = () => {
  router.push({ name: "BoardDetail", params: { articleNo: article.value.articleNo } });
};
</script>

<template>
  <div class="wholeContents">
    <div class="titleDiv">
      <div class="title">Q&A 게시판 수정하기</div>
    </div>
    <div class="main">
      <div class="titleC">제목</div>
      <div>
        <input
          type="text"
          id="titleContent"
          name="titleContent"
          placeholder="제목을 입력해주세요."
          v-model="article.subject"
        />
      </div>
      <div class="mainC">내용</div>
      <div>
        <textarea
          type="text"
          id="mainContent"
          name="mainContent"
          placeholder="내용을 입력해주세요."
          v-model="article.content"
        />
      </div>
    </div>
    <div class="submit">
      <input class="modifyButton" type="button" @click="modifyArticle" value="글수정" />
      <input class="detailButton" type="button" value="뒤로가기" @click="moveDetail" />
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
}
.submit {
  margin: 15px 0 15px 90vh;
  display: flex;
  height: 50px;
}
.modifyButton {
  margin: 0 0 0 70px;
  width: 90px;
  height: 45px;
  background-color: rgb(178, 214, 111);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(178, 214, 111);
  font-size: 16px;
  font-weight: 600;
}
.modifyButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(178, 214, 111);
  margin-top: 5px;
  margin-bottom: 5px;
}
.detailButton {
  margin: 0 0 0 360px;
  width: 90px;
  height: 45px;
  background-color: rgb(238, 179, 71);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(238, 179, 71);
  font-size: 16px;
  font-weight: 600;
}
.detailButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(238, 179, 71);
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
