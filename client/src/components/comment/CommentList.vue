<script setup>
import { ref } from "vue";
import http from "@/util/http-common.js";
import { useRouter } from "vue-router";
import CommentModify from "@/components/comment/CommentModify.vue";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
const uid = cookies.get("userId");

const router = useRouter();

const props = defineProps({
  articleNo: Number,
  commentId: Number,
  userId: String,
  content: String,
  createdDate: String,
  updatedDate: String,
  index: Number,
});

const isOpenModal = ref(false);

const deleteComment = () => {
  http.get("/commentapi/delete/" + props.commentId).then(({ data }) => {
    let msg = "삭제 처리 시 문제가 발생했습니다.";
    if (data === 1) {
      msg = "삭제가 완료되었습니다.";
    }
    alert(msg);
    router.go();
  });
};

const toggleModal = () => {
  isOpenModal.value = !isOpenModal.value;
};
</script>

<template>
  <div class="wholeContents">
    <div class="realContent">
      <div class="initContents">
        <div style="margin-right: 5px">{{ props.index }}</div>
        <div style="margin-right: 5px; color: gray">|</div>
        <div style="margin-right: 5px">{{ props.userId }}</div>
        <div style="margin-right: 5px; color: gray">|</div>
        <div>{{ props.updatedDate }}</div>
      </div>
      <div class="elseContent">{{ props.content }}</div>
    </div>
    <template v-if="uid == userId">
      <div class="commentModifyDiv">
        <input
          class="commentModify"
          type="button"
          @click="toggleModal"
          value="수정"
        />
      </div>
      <div class="commentDeleteDiv">
        <input
          class="commentDelete"
          type="button"
          @click="deleteComment"
          value="삭제"
        />
      </div>
    </template>
  </div>
  <div v-if="isOpenModal">
    <CommentModify :commentId="props.commentId" :content="props.content" />
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
  margin: 1vh 0 1vh 33vh;
  width: 1000px;
  width: 1000px;
  display: flex;
  flex-direction: row;
  align-items: center;
}
.realContent {
  padding: 5px 0 5px 5px;
  border: 2px solid gray;
  border-radius: 10px;
  word-break: break-all;
  margin: 0 10px 0 0;
}
.initContents {
  display: flex;
  width: 300px;
  justify-content: flex-start;
  font-size: 17px;
}
.elseContent {
  font-size: 17px;
}
.commentModifyDiv {
  text-align: center;
  width: 60px;
}
.commentModify {
  width: 50px;
  height: 30px;
}
.commentModify {
  background-color: rgb(178, 214, 111);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(178, 214, 111);
  font-size: 16px;
  font-weight: 600;
}
.commentModify:hover {
  box-shadow: 0px 0px 0px 0px rgb(178, 214, 111);
  margin-top: 15px;
  margin-bottom: 5px;
}
.commentDeleteDiv {
  text-align: center;
  width: 60px;
}
.commentDelete {
  width: 50px;
  height: 30px;
}
.commentDelete {
  background-color: rgb(247, 127, 157);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(247, 127, 157);
  font-size: 16px;
  font-weight: 600;
}
.commentDelete:hover {
  box-shadow: 0px 0px 0px 0px rgb(247, 127, 157);
  margin-top: 15px;
  margin-bottom: 5px;
}
</style>
