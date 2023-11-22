<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import http from "@/util/http-common.js";
const router = useRouter();
const content = ref("");
const props = defineProps({
  userId: String,
  articleNo: Number,
});
const createComment = () => {
  console.log(content.value);
  http
    .post("/commentapi/write", {
      content: content.value,
      userId: props.userId,
      articleNo: props.articleNo,
    })
    .then(({ data }) => {
      let msg = "등록 처리 시 문제가 발생했습니다.";
      if (data === 1) {
        msg = "등록이 완료되었습니다.";
      }
      alert(msg);
      router.go();
    });
};
</script>
<template>
  <div class="wholeCommentContents">
    <div class="title">댓글 작성하기</div>
    <div style="display: flex; height: 120px">
      <div class="comment">
        <textarea class="commentContent" v-model="content" />
      </div>
      <input type="button" class="registerButton" @click="createComment" value="작성하기" />
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
.wholeCommentContents {
  margin: 0 16% 0 16%;
}
.title {
  margin: 5px 0 5px 0;
  display: flex;
  font-size: 20px;
}
.commentContent {
  padding: 3px;
  height: 100px;
  width: 1000px;
  border: 2px solid gray;
  border-radius: 10px;
  font-size: 20px;
  resize: none;
}
.comment {
  height: 100px;
  width: 1000px;
  margin: 0 20px 20px 0;
  align-items: center;
}
.registerButton {
  height: 50px;
  width: 100px;
  margin: 20px 0 0 20px;
  background-color: rgb(129, 202, 231);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(129, 202, 231);
  font-size: 16px;
  font-weight: 600;
}
.registerButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(129, 202, 231);
  margin-top: 25px;
  margin-bottom: 15px;
}
</style>
