<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import CommentList from "@/components/comment/CommentList.vue";
import http from "@/util/http-common.js";
import CommentWrite from "@/components/comment/CommentWrite.vue";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const router = useRouter();
const route = useRoute();
const userinfo = JSON.parse(sessionStorage.getItem("userinfo"));

const uid = cookies.get("userId");
console.log(uid);

const article = ref({
  articleNo: Number,
  userId: String,
  subject: String,
  content: String,
  hit: Number,
  registerTime: String,
});

const comments = ref([]);
onMounted(async () => {
  await http.get("/articleapi/view/" + route.params.articleNo).then(({ data }) => {
    article.value = data;
  });

  await http
    .get("/commentapi/list", { params: { articleNo: article.value.articleNo } })
    .then(({ data }) => {
      comments.value = data;
    });
});

const deleteArticle = () => {
  http.get("articleapi/delete/" + route.params.articleNo).then(({ data }) => {
    let msg = "삭제 처리 시 문제가 발생했습니다.";
    if (data === 1) {
      msg = "삭제가 완료되었습니다.";
    }

    console.log(route.params.articleNo);

    alert(msg);
    moveList();
  });
};

const moveList = () => {
  router.push({ name: "BoardList" });
};
</script>

<template>
  <div>
    <div>
      <div class="title">
        {{ article.subject }}
      </div>
      <div class="content">
        <div class="firstContent">
          <img
            src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
            style="width: 50px; height: 50px; margin-right: 10px"
          />
          <div style="font-size: 20px; color: gray">|</div>
          <div style="font-size: 20px; margin-left: 30px">{{ article.userId }}</div>
        </div>
        <div class="secondContent">
          <div>{{ article.registerTime }}</div>
        </div>
        <div class="thirdContent">조회 : {{ article.hit }}</div>
        <div class="fourthContent">댓글 : {{ comments.length }}</div>
        <div class="fifthContent">{{ article.content }}</div>
        <div>
          <button type="button" id="btn-list">
            <RouterLink aria-current="page" to="/board">글목록</RouterLink>
          </button>
          <span v-if="uid == article.userId">
            <button type="button" id="btn-mv-modify">
              <RouterLink
                aria-current="page"
                :to="{
                  name: 'BoardModify',
                  params: { articleNo: article.articleNo },
                }"
              >
                글수정
              </RouterLink>
            </button>
          </span>
          <span v-if="uid == article.userId">
            <button type="button" id="btn-delete" @click="deleteArticle">글삭제</button>
          </span>
        </div>
      </div>
      <template v-if="comments != null">
        <CommentList
          v-for="(comment, index) in comments"
          :key="comment.commentId"
          v-bind="comment"
          :index="index + 1"
        />
      </template>
      <template v-if="true">
        <CommentWrite :user-id="uid" :article-no="Number(article.articleNo)" />
      </template>
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
.title {
  width: 100%;
  height: 60px;
  font-family: "NanumSquareB";
  font-size: 60px;
  padding: 0 150px 0 0;
}
.content {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  width: 75%;
}
.firstContent {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
}
.secondContent,
.thirdContent,
.fourthContent {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
  font-size: 17px;
}
.fifthContent {
  height: 600px;
  margin: 0 15% 0 20.6%;
  padding: 15px;
  width: 85%;
  border: 2px solid gray;
  border-radius: 15px;
  word-break: break-all;
  font-size: 20px;
}
</style>
