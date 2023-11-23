<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import http from "@/util/http-common.js";
const router = useRouter();
const props = defineProps({
  commentId: String,
  content: Number,
});
const content = ref(props.content);
const emit = defineEmits(["toggleModal"]);

const modifyComment = () => {
  http
    .post("/commentapi/modify", {
      commentId: props.commentId,
      content: content.value,
    })
    .then(({ data }) => {
      let msg = "수정 처리 시 문제가 발생했습니다.";
      if (data === 1) {
        msg = "수정이 완료되었습니다.";
      }
      alert(msg);
      emit("toggleModal");
      router.go();
    });
};
</script>
<template>
  <div class="wholeCommentContents">
    <div class="title">{{ commentId }}번 댓글 수정</div>
    <div style="display: flex; height: 120px">
      <div class="comment">
        <textarea class="commentContent" v-model="content" />
      </div>
      <input type="button" class="registerButton" @click="modifyComment" value="수정하기" />
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
  background-color: rgb(247 240 208);
  margin: 0 15% 0 15%;
  padding: 0 0 0 20px;
  width: 1170px;
  border-radius: 10px;
}
.title {
  margin: 5px 0 5px 0;
  display: flex;
  font-size: 20px;
}
.commentContent {
  padding: 3px 3px 3px 6px;
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
  background-color: rgb(178, 214, 111);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(178, 214, 111);
  font-size: 16px;
  font-weight: 600;
}
.registerButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(178, 214, 111);
  margin-top: 25px;
  margin-bottom: 10px;
}
</style>
