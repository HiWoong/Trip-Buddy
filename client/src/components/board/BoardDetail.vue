<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import CommentList from "@/components/comment/CommentList.vue";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";
import CommentWrite from "@/components/comment/CommentWrite.vue";

const router = useRouter();
const route = useRoute();
const userinfo = JSON.parse(sessionStorage.getItem("userinfo"));
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
</script>

<template>
  <div class="row justify-content-center mt-5">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">글보기</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <div class="row my-2">
        <h2 class="text-secondary">{{ article.subject }}</h2>
      </div>
      <div class="row">
        <div class="col-md-8">
          <div class="clearfix align-content-center">
            <img
              class="avatar me-2 float-md-start bg-light p-2"
              src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
            />
            <p>
              <span class="fw-bold">{{ article.userId }}</span> <br />
              <span class="text-secondary fw-light">
                {{ article.registerTime }} 조회 : {{ article.hit }}
              </span>
            </p>
          </div>
        </div>
        <div class="col-md-4 align-self-center text-end">댓글 : 0</div>
        <div class="divider mb-3"></div>
        <div class="text-secondary">{{ article.content }}</div>
        <div class="divider mt-3 mb-3"></div>
        <div class="d-flex justify-content-end">
          <button
            type="button"
            id="btn-list"
            class="btn btn-outline-primary mb-3"
          >
            <RouterLink class="nav-link active" aria-current="page" to="/board"
              >글목록</RouterLink
            >
          </button>
          <button
            type="button"
            id="btn-mv-modify"
            class="btn btn-outline-success mb-3 ms-1"
          >
            <RouterLink
              class="nav-link active"
              aria-current="page"
              :to="{
                name: 'BoardModify',
                params: { articleNo: article.articleNo },
              }"
            >
              글수정
            </RouterLink>
          </button>
          <button
            type="button"
            id="btn-delete"
            class="btn btn-outline-danger mb-3 ms-1"
            @click="deleteArticle"
          >
            글삭제
          </button>
        </div>
      </div>
      <template v-if="comments != null">
        <CommentList
          v-for="comment in comments"
          :key="comment.commentId"
          v-bind="comment"
        />
      </template>
      <template v-if="userinfo != null">
        <CommentWrite
          :user-id="userinfo.userId"
          :article-no="article.articleNo"
        />
      </template>
    </div>
  </div>
</template>

<style scoped></style>
