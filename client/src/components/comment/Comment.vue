<script setup>
defineProps({
  commentId: Number,
  userId: String,
  content: String,
  createdDate: String
})
</script>

<template>
  <div class="col-lg-8 col-md-10 col-sm-12">
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
  </div>
  <br />
</template>

<style scoped></style>
