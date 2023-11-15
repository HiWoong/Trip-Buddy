<script setup>
import { ref } from "vue";
import http from "@/util/http-common.js";
import { useRouter } from "vue-router";
const router = useRouter();
const userinfo = JSON.parse(sessionStorage.getItem("userinfo"));
const props = defineProps({
  articleNo: Number,
  commentId: Number,
  userId: String,
  content: String,
  createdDate: String,
  updatedDate: String,
  index: Number,
});
const newId = ref("#C" + props.index);
const newModalId = ref("C" + props.index);
const nowContent = ref(props.content);

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

const modifyComment = () => {
  http
    .post("/commentapi/modify", { commentId: props.commentId, content: nowContent.value })
    .then(({ data }) => {
      let msg = "수정 처리 시 문제가 발생했습니다.";
      if (data === 1) {
        msg = "수정이 완료되었습니다.";
      }
      alert(msg);
      document.getElementById("modalClose").click();
      router.go();
    });
};
</script>

<template>
  <div class="col-lg-8 col-md-10 col-sm-12 border border-1 rounded">
    <div class="row">
      <div class="col-md-8">
        <div class="clearfix align-content-center">
          <span class="fw-bold">{{ props.index }}</span>
          <span style="color: gray"> | </span>
          <span class="text-secondary fw-light"> {{ props.updatedDate }} </span>
          <div class="text-secondary">{{ props.content }}</div>
        </div>
      </div>
    </div>
    <span v-if="userinfo != null && userinfo.userId == userId" class="d-flex justify-content-end">
      <button
        class="btn btn-outline-success ms-1"
        data-bs-toggle="modal"
        :data-bs-target="newId"
        @click="initData"
      >
        수정
      </button>
      <button class="btn btn-outline-danger ms-1" @click="deleteComment">삭제</button>
    </span>
  </div>
  <br />
  <div class="modal" :id="newModalId">
    <div class="modal-dialog">
      <div class="modal-content">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">{{ props.index }}</h4>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div>
          <!-- Modal body -->
          <textarea
            class="form-control"
            rows="5"
            id="nowContent"
            name="text"
            v-model="nowContent"
          ></textarea>
        </div>

        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" @click="modifyComment">Update</button>
          <button type="button" class="btn btn-danger" id="modalClose" data-bs-dismiss="modal">
            Close
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
