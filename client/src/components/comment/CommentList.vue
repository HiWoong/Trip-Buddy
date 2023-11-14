<script setup>
const userinfo = JSON.parse(sessionStorage.getItem("userinfo"))
defineProps({
  commentId: Number,
  userId: String,
  content: String,
  createdDate: String
})
</script>

<template>
  <div class="col-lg-8 col-md-10 col-sm-12 border border-1 rounded">
    <div class="row">
      <div class="col-md-8">
        <div class="clearfix align-content-center">
          <p>
            <span class="fw-bold">{{commentId}}</span>
            <span style="color:gray"> | </span>
            <span class="text-secondary fw-light"> {{createdDate}} </span>
            <div class="text-secondary">{{content}}</div>
          </p>
        </div>
      </div>
    </div>
    <span v-if="userinfo != null && userinfo.userId == userId" class="d-flex justify-content-end">
      <button class="btn btn-outline-success ms-1" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>
      <button class="btn btn-outline-danger ms-1" @click="deleteComment">삭제</button>
    </span>
  </div>
  <br />
    <div class="modal" id="myModal">
      <div class="modal-dialog">
        <div class="modal-content">

          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">{{commentId}}</h4>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>

          <!-- Modal body -->
          <div class="modal-body">
            {{content}}
          </div>  

          <!-- Modal footer -->
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
          </div>

      </div>
    </div>
  </div>
</template>

<style scoped></style>
