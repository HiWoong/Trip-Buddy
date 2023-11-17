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
  <label for="comment">Comments:</label>

  <textarea
    class="form-control"
    rows="5"
    id="comment"
    name="text"
    v-model="content"
  ></textarea>
  <div class="d-flex">
    <button
      type="button"
      id="btn-register"
      class="btn btn-outline-warning mt-2 ms-auto"
      @click="createComment"
    >
      작성하기
    </button>
  </div>
</template>
<style scoped></style>
