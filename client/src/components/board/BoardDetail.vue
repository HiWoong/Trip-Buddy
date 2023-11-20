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
  await http
    .get("/articleapi/view/" + route.params.articleNo)
    .then(({ data }) => {
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

const moveModify = () => {
  router.push({
    name: "BoardModify",
    params: { articleNo: article.value.articleNo },
  });
};
</script>

<template>
  <div>
    <div>
      <div class="titleDiv">
        <div class="title">
          {{ article.subject }}
        </div>
        <div></div>
      </div>
      <div class="content">
        <div class="firstContent">
          <img
            src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
            style="width: 50px; height: 50px; margin-right: 10px"
          />
          <div style="font-size: 20px; color: gray">|</div>
          <div style="font-size: 20px; margin-left: 30px">
            {{ article.userId }}
          </div>
        </div>
        <div class="secondContent">
          <div>{{ article.registerTime }}</div>
        </div>
        <div class="thirdContent">조회 : {{ article.hit }}</div>
        <div class="fourthContent">댓글 : {{ comments.length }}</div>
        <div class="fifthContent">
          <template v-if="uid == article.userId">
            <div class="modifyButtonDiv">
              <input
                class="modifyButton"
                type="submit"
                style="width: 80px; height: 40px"
                value="글수정"
                @click="moveModify"
              />
            </div>
            <div class="deleteButtonDiv">
              <input
                class="deleteButton"
                type="submit"
                style="width: 80px; height: 40px"
                @click="deleteArticle"
                value="글삭제"
              />
            </div>
          </template>
          <div class="listButtonDiv">
            <input
              class="listButton"
              type="submit"
              style="width: 80px; height: 40px"
              value="글목록"
              @click="moveList"
            />
          </div>
        </div>
        <div class="sixthContent">{{ article.content }}</div>
      </div>
      <template v-if="comments != null">
        <CommentList
          v-for="(comment, index) in comments"
          :key="comment.commentId"
          v-bind="comment"
          :index="index + 1"
        />
      </template>
      <template v-if="uid == article.userId">
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
.titleDiv {
  margin: 20px 0 0 0;
  width: 100%;
  height: 60px;
}
.title {
  font-family: "NanumSquareB";
  font-size: 50px;
  padding: 0 150px 0 0;
  text-decoration: underline;
  text-decoration-thickness: 2px;
  text-decoration-color: gray;
  text-underline-position: under;
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
  display: flex;
  margin: 0 0 1vh 320px;
  align-items: center;
  height: 50px;
}
.modifyButtonDiv {
  margin: 0 1vh 0 0;
  width: 80px;
  height: 40px;
}
.modifyButton {
  background-color: rgb(178, 214, 111);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(178, 214, 111);
  font-size: 16px;
  font-weight: 600;
}
.modifyButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(178, 214, 111);
  margin-top: 10px;
  margin-bottom: 0px;
}

.deleteButtonDiv {
  margin: 0 50vh 0 0;
  width: 80px;
  height: 40px;
}
.deleteButton {
  background-color: rgb(247, 127, 157);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(247, 127, 157);
  font-size: 16px;
  font-weight: 600;
}
.deleteButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(247, 127, 157);
  margin-top: 10px;
  margin-bottom: 0px;
}

.listButtonDiv {
  margin: 0 0 0 50vh;
  width: 80px;
  height: 40px;
}

.listButton {
  background-color: rgb(238, 179, 71);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(238, 179, 71);
  font-size: 16px;
  font-weight: 600;
}
.listButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(238, 179, 71);
  margin-top: 10px;
  margin-bottom: 0px;
}

.sixthContent {
  height: 400px;
  margin: 0 15% 0 20.6%;
  padding: 15px;
  width: 85%;
  border: 2px solid gray;
  border-radius: 15px;
  word-break: break-all;
  font-size: 20px;
  text-align: start;
}
</style>
